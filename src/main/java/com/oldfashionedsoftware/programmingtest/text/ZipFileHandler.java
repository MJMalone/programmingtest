package com.oldfashionedsoftware.programmingtest.text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileHandler {

    public void forEachFileInZip(final String zipFileName, final Consumer<FileInfo> fileInfoConsumer)
        throws URISyntaxException, IOException
    {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final URL resource = classloader.getResource(zipFileName);

        try (final ZipFile zipFile = new ZipFile(new File(resource.toURI()))) {

            final Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                if (isValid(entry)) {
                    handleEntry(fileInfoConsumer, zipFile, entry);
                }
            }
        }
    }

    private boolean isValid(final ZipEntry entry) {
        // Skip directories and MAC resource fork items
        if (entry.getName().startsWith("__MACOSX/") || entry.isDirectory()) {
            return false;
        }
        return true;
    }

    private void handleEntry(
        final Consumer<FileInfo> fileInfoConsumer,
        final ZipFile zipFile,
        final ZipEntry entry)
        throws IOException
    {
        final InputStream stream = zipFile.getInputStream(entry);
        try (final Scanner scanner = new Scanner(stream)) {
            scanner.useDelimiter("\\A"); // Scan the whole file
            if (scanner.hasNext()) {
                final String contents = scanner.next();
                fileInfoConsumer.accept(new FileInfo(entry.getName(), contents));
            }
        }
    }
}
