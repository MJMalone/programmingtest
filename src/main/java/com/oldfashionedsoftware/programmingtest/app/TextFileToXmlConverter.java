package com.oldfashionedsoftware.programmingtest.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.text.TextFileReader;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;

public class TextFileToXmlConverter {
    private final TextFileReader reader;
    private final TextFileToNamedEntityListConverter namedEntityConverter;
    private final TextToDocumentConverter docConverter;
    private final XmlFormatter xmlFormatter;

    public TextFileToXmlConverter(
        final TextFileReader reader,
        final TextFileToNamedEntityListConverter namedEntityConverter,
        final TextToDocumentConverter docConverter,
        final XmlFormatter xmlFormatter)
    {
        this.reader = reader;
        this.namedEntityConverter = namedEntityConverter;
        this.docConverter = docConverter;
        this.xmlFormatter = xmlFormatter;
    }

    public String convert(final String textFileName)
        throws URISyntaxException, IOException
    {
        return convert(textFileName, null);
    }

    public String convert(final String textFileName, final String namedEntityFileName)
        throws URISyntaxException, IOException
    {
        final String text = reader.getFromClasspath(textFileName);
        final List<NamedEntity> namedEntities = namedEntityConverter.convert(namedEntityFileName);

        final Document doc = docConverter.convert(text, namedEntities);
        doc.setName(textFileName);

        final String xml = xmlFormatter.toXml(doc);

        return xml;
    }
}
