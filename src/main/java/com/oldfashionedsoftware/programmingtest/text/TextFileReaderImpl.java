package com.oldfashionedsoftware.programmingtest.text;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextFileReaderImpl implements TextFileReader {

    @Override
    public String getFromClasspath(final String fileName) throws URISyntaxException, IOException {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final URL resource = classloader.getResource(fileName);
        final URI uri = resource.toURI();
        final byte[] byteArray = Files.readAllBytes(Paths.get(uri));

        return new String(byteArray, Charset.defaultCharset());
    }

}
