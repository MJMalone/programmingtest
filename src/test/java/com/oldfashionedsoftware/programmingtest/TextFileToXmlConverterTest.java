package com.oldfashionedsoftware.programmingtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oldfashionedsoftware.programmingtest.lexer.Lexer;
import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParser;
import com.oldfashionedsoftware.programmingtest.text.TextFileReader;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;

@RunWith(MockitoJUnitRunner.class)
public class TextFileToXmlConverterTest {

    @Mock private Lexer lexer;
    @Mock private DocumentParser parser;
    @Mock private TextFileReader reader;
    @Mock private XmlFormatter xmlFormatter;

    @InjectMocks private TextFileToXmlConverter converter;

    @Test
    public void testConvert() throws Exception {
        final List<Token> tokenList = Arrays.asList(
            new TokenPool().getToken("word", TokenType.WORD));
        final Document doc = new Document();

        // This is the pipeline that TextFileToXmlConverter defines:
        // From file name to file contents
        when(reader.getFromClasspath("test_file_name")).thenReturn("raw file data");
        // From file contents to a list of Tokens
        when(lexer.analyze("raw file data")).thenReturn(tokenList);
        // From list of Tokens to a Document
        when(parser.parse(tokenList)).thenReturn(doc);
        // From a Document to an XML string
        when(xmlFormatter.toXml(doc)).thenReturn("expected result");

        final String result = converter.convert("test_file_name");

        assertEquals("expected result", result);

        verify(reader).getFromClasspath("test_file_name");
        verify(lexer).analyze("raw file data");
        verify(parser).parse(tokenList);
        verify(xmlFormatter).toXml(doc);

        verifyNoMoreInteractions(lexer, parser, reader, xmlFormatter);
    }
}
