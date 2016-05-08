package com.oldfashionedsoftware.programmingtest.app;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oldfashionedsoftware.programmingtest.lexer.Lexer;
import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParser;

@RunWith(MockitoJUnitRunner.class)
public class TextToDocumentConverterTest {

    @Mock private Lexer lexer;
    @Mock private DocumentParser parser;

    @InjectMocks private TextToDocumentConverter converter;

    @Test
    public void testConvert() throws Exception {
        final List<NamedEntity> namedEntities = new LinkedList<>();
        final List<Token> tokens = new LinkedList<>();
        final Document doc = new Document();

        when(lexer.analyze("text", namedEntities)).thenReturn(tokens);
        when(parser.parse(tokens)).thenReturn(doc);

        converter.convert("test", namedEntities);

        verify(lexer).analyze("text", namedEntities);
        verify(parser).parse(tokens);

        verifyNoMoreInteractions(lexer, parser);
    }

}
