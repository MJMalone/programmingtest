package com.oldfashionedsoftware.programmingtest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.oldfashionedsoftware.programmingtest.lexer.Lexer;
import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParser;
import com.oldfashionedsoftware.programmingtest.text.TextFileReader;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;

public class TextFileToXmlConverter {
    private final TextFileReader reader;
    private final Lexer lexer;
    private final DocumentParser parser;
    private final XmlFormatter xmlFormatter;

    public TextFileToXmlConverter(
        final TextFileReader reader,
        final Lexer lexer,
        final DocumentParser parser,
        final XmlFormatter xmlFormatter)
    {
        this.reader = reader;
        this.lexer = lexer;
        this.parser = parser;
        this.xmlFormatter = xmlFormatter;
    }

    public String convert(final String textFileName)
        throws URISyntaxException, IOException
    {
        final String text = reader.getFromClasspath(textFileName);

        return convertTextUsingNamedEntityString(text, "");
    }

    public String convert(final String textFileName, final String namedEntityFileName)
        throws URISyntaxException, IOException
    {
        final String text = reader.getFromClasspath(textFileName);
        final String namedEntityString = reader.getFromClasspath(namedEntityFileName);

        return convertTextUsingNamedEntityString(text, namedEntityString);
    }

    private String convertTextUsingNamedEntityString(final String text, final String namedEntityString)
        throws URISyntaxException, IOException
    {
        final List<Token> tokens = lexer.analyze(text, namedEntityString);

        final Document doc = parser.parse(tokens);

        final String xml = xmlFormatter.toXml(doc);

        return xml;
    }
}
