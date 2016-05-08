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

    public String convert(final String fileName) throws URISyntaxException, IOException {
        final String content = reader.getFromClasspath(fileName);

        final List<Token> tokens = lexer.analyze(content);

        final Document doc = parser.parse(tokens);

        final String xml = xmlFormatter.toXml(doc);

        return xml;
    }
}
