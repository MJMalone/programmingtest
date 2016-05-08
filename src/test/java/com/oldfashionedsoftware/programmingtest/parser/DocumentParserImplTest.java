package com.oldfashionedsoftware.programmingtest.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class DocumentParserImplTest {

    private List<Token> simpleDocument;

    @Before
    public void before() {
        final TokenPool tokenPool = new TokenPool();

        simpleDocument = Arrays.asList(
            tokenPool.getToken("Hello", TokenType.WORD),
            tokenPool.getToken(".", TokenType.SENTENCE_TERMINAL),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("How", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("are", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("you", TokenType.WORD),
            tokenPool.getToken("?", TokenType.SENTENCE_TERMINAL));
    }

    @Test
    public void testSimpleDocumentWithDefaultSentenceParserNoWhitespace() {
        final DocumentParserImpl parser = new DocumentParserImpl(false);

        final Document doc = parser.parse(simpleDocument);
        assertNotNull(doc);

        final List<Sentence> sentences = doc.getSentences();
        assertNotNull(sentences);
        assertEquals(2, sentences.size());

        final Sentence sentence1 = sentences.get(0);
        assertTrue(sentence1.isComplete());
        final List<Token> tokenList1 = sentence1.getTokens();

        assertNotNull(tokenList1);
        assertEquals(2, tokenList1.size());
        assertEquals(simpleDocument.get(0), tokenList1.get(0));
        assertEquals(simpleDocument.get(1), tokenList1.get(1));

        final Sentence sentence2 = sentences.get(1);
        assertTrue(sentence2.isComplete());
        final List<Token> tokenList2 = sentence2.getTokens();

        assertNotNull(tokenList2);
        assertEquals(4, tokenList2.size());

        // Note that the whitespace tokens are skipped.
        assertEquals(simpleDocument.get(3), tokenList2.get(0));
        assertEquals(simpleDocument.get(5), tokenList2.get(1));
        assertEquals(simpleDocument.get(7), tokenList2.get(2));
        assertEquals(simpleDocument.get(8), tokenList2.get(3));
    }

    @Test
    public void testLastSentenceIncomplete() {
        final DocumentParserImpl parser = new DocumentParserImpl(false);

        // Omit the final question mark
        final List<Token> shortenedList = simpleDocument.subList(0, simpleDocument.size() - 1);
        final Document doc = parser.parse(shortenedList);
        assertNotNull(doc);

        final List<Sentence> sentences = doc.getSentences();
        assertNotNull(sentences);
        assertEquals(2, sentences.size());

        final Sentence sentence1 = sentences.get(0);
        assertTrue(sentence1.isComplete());
        final List<Token> tokenList1 = sentence1.getTokens();

        assertNotNull(tokenList1);
        assertEquals(2, tokenList1.size());
        assertEquals(simpleDocument.get(0), tokenList1.get(0));
        assertEquals(simpleDocument.get(1), tokenList1.get(1));

        final Sentence sentence2 = sentences.get(1);
        // Second sentence incomplete
        assertFalse(sentence2.isComplete());
        final List<Token> tokenList2 = sentence2.getTokens();

        assertNotNull(tokenList2);
        assertEquals(3, tokenList2.size());

        // Only 3 tokens
        assertEquals(simpleDocument.get(3), tokenList2.get(0));
        assertEquals(simpleDocument.get(5), tokenList2.get(1));
        assertEquals(simpleDocument.get(7), tokenList2.get(2));
    }

    @Test
    public void testSimpleDocumentWithDefaultSentenceParserWithWhitespace() {
        runWhitespaceTestWithParser(new DocumentParserImpl(true));
    }

    @Test
    public void testSimpleDocumentWithDefaultSentenceParserDefaultConstructor() {
        runWhitespaceTestWithParser(new DocumentParserImpl());
    }

    private void runWhitespaceTestWithParser(final DocumentParserImpl parser) {

        final Document doc = parser.parse(simpleDocument);
        assertNotNull(doc);

        final List<Sentence> sentences = doc.getSentences();
        assertNotNull(sentences);
        assertEquals(2, sentences.size());

        final Sentence sentence1 = sentences.get(0);
        assertTrue(sentence1.isComplete());
        final List<Token> tokenList1 = sentence1.getTokens();

        assertNotNull(tokenList1);
        assertEquals(2, tokenList1.size());
        assertEquals(simpleDocument.get(0), tokenList1.get(0));
        assertEquals(simpleDocument.get(1), tokenList1.get(1));

        final Sentence sentence2 = sentences.get(1);
        assertTrue(sentence2.isComplete());
        final List<Token> tokenList2 = sentence2.getTokens();

        assertNotNull(tokenList2);
        assertEquals(7, tokenList2.size());
        assertEquals(simpleDocument.get(2), tokenList2.get(0));
        assertEquals(simpleDocument.get(3), tokenList2.get(1));
        assertEquals(simpleDocument.get(4), tokenList2.get(2));
        assertEquals(simpleDocument.get(5), tokenList2.get(3));
        assertEquals(simpleDocument.get(6), tokenList2.get(4));
        assertEquals(simpleDocument.get(7), tokenList2.get(5));
        assertEquals(simpleDocument.get(8), tokenList2.get(6));
    }
}
