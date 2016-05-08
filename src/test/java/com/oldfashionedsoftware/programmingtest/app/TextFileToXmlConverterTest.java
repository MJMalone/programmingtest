package com.oldfashionedsoftware.programmingtest.app;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oldfashionedsoftware.programmingtest.app.TextFileToNamedEntityListConverter;
import com.oldfashionedsoftware.programmingtest.app.TextFileToXmlConverter;
import com.oldfashionedsoftware.programmingtest.app.TextToDocumentConverter;
import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;
import com.oldfashionedsoftware.programmingtest.text.TextFileReader;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;

@RunWith(MockitoJUnitRunner.class)
public class TextFileToXmlConverterTest {

    @Mock private TextFileReader reader;
    @Mock private TextFileToNamedEntityListConverter namedEntityConverter;
    @Mock private TextToDocumentConverter docConverter;
    @Mock private XmlFormatter xmlFormatter;

    @InjectMocks private TextFileToXmlConverter converter;

    @Test
    public void testConvert() throws Exception {
        final List<NamedEntity> emptyNEList = new LinkedList<>();
        Arrays.asList(
            new TokenPool().getToken("word", TokenType.WORD));
        final Document doc = new Document();

        // This is the pipeline that TextFileToXmlConverter defines:
        // From file name to file contents
        when(reader.getFromClasspath("test_file_name")).thenReturn("raw file data");
        // From file name to named entity list
        when(namedEntityConverter.convert(null)).thenReturn(emptyNEList);
        // From text and named entities to a Document
        when(docConverter.convert("raw file data", emptyNEList)).thenReturn(doc);
        // From a Document to an XML string
        when(xmlFormatter.toXml(doc)).thenReturn("expected result");

        final String result = converter.convert("test_file_name");

        assertEquals("expected result", result);

        verify(reader).getFromClasspath("test_file_name");
        verify(namedEntityConverter).convert(null);
        verify(docConverter).convert("raw file data", emptyNEList);
        verify(xmlFormatter).toXml(doc);

        verifyNoMoreInteractions(reader, namedEntityConverter, docConverter, xmlFormatter);
    }

    @Test
    public void testConvertWithNamedEntities() throws Exception {
        final List<NamedEntity> neList = new LinkedList<>();
        Arrays.asList(
            new TokenPool().getToken("word", TokenType.WORD));
        final Document doc = new Document();

        // This is the pipeline that TextFileToXmlConverter defines:
        // From file name to file contents
        when(reader.getFromClasspath("test_file_name")).thenReturn("raw file data");
        // From file name to named entity list
        when(namedEntityConverter.convert("named_entity_file_name")).thenReturn(neList);
        // From text and named entities to a Document
        when(docConverter.convert("raw file data", neList)).thenReturn(doc);
        // From a Document to an XML string
        when(xmlFormatter.toXml(doc)).thenReturn("expected result");

        final String result = converter.convert("test_file_name", "named_entity_file_name");

        assertEquals("expected result", result);

        verify(reader).getFromClasspath("test_file_name");
        verify(namedEntityConverter).convert("named_entity_file_name");
        verify(docConverter).convert("raw file data", neList);
        verify(xmlFormatter).toXml(doc);

        verifyNoMoreInteractions(reader, namedEntityConverter, docConverter, xmlFormatter);
    }
}
