package com.oldfashionedsoftware.programmingtest.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.text.ZipFileHandler;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;

public class ZipFileToXmlConverter {
    private final ZipFileHandler zipFileHandler;
    private final TextFileToNamedEntityListConverter namedEntityConverter;
    private final TextToDocumentConverter docConverter;
    private final XmlFormatter xmlFormatter;
    private final int numberOfThreads;

    public ZipFileToXmlConverter(
        final ZipFileHandler zipFileHandler,
        final TextFileToNamedEntityListConverter namedEntityConverter,
        final TextToDocumentConverter docConverter,
        final XmlFormatter xmlFormatter,
        final int numberOfThreads)
    {
        this.zipFileHandler = zipFileHandler;
        this.namedEntityConverter = namedEntityConverter;
        this.docConverter = docConverter;
        this.xmlFormatter = xmlFormatter;
        this.numberOfThreads = numberOfThreads;
    }

    public String convert(final String zipFileName)
        throws URISyntaxException, IOException
    {
        return convert(zipFileName, null);
    }

    public String convert(final String zipFileName, final String namedEntityFileName)
        throws URISyntaxException, IOException
    {
        final List<NamedEntity> namedEntities = namedEntityConverter.convert(namedEntityFileName);

        final Deque<Document> documents = new ConcurrentLinkedDeque<>();
        final ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);

        zipFileHandler.forEachFileInZip(zipFileName, fileInfo -> {
            threadPool.submit(
                () -> {
                    try {
                        final Document doc = docConverter.convert(fileInfo.getContents(), namedEntities);
                        doc.setName(fileInfo.getName());
                        documents.add(doc);
                    } catch (URISyntaxException | IOException ex) {
                        throw new RuntimeException(
                            "Exception while processing file " + zipFileName + ", " +
                            "contents \"" + abbreviate(fileInfo.getContents(), 20) + "\"",
                            ex);
                    }
                }
                );
        });

        awaitTerminationSeconds(threadPool, 60);

        return xmlFormatter.toXml(documents);
    }

    private void awaitTerminationSeconds(final ExecutorService threadPool, final int seconds) {
        try {
            threadPool.shutdown();
            threadPool.awaitTermination(seconds, TimeUnit.SECONDS);
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            if (!threadPool.isTerminated()) {
                System.out.println("Could not shut down threadpool.  Forcing.");
                threadPool.shutdownNow();
                throw new RuntimeException("Document conversion in unknown state. Could not shut down thread pool.");
            }
        }
    }

    private String abbreviate(final String fileContents, final int abbrevLength) {
        final int stringLength = fileContents.length();
        if (stringLength <= abbrevLength) {
            return fileContents;
        }
        else {
            return fileContents.substring(0, abbrevLength) + "...";
        }
    }

}
