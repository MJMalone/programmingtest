package com.oldfashionedsoftware.programmingtest.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class SentenceParserImplTest {

    private SentenceParserImpl parser;
    private TokenPool tokenPool;

    @Before
    public void before() {
        tokenPool = new TokenPool();
        parser = new SentenceParserImpl();
    }

    @Test
    public void testIsEmpty() {
        assertTrue(parser.isEmpty());
        parser.add(tokenPool.getToken("word", TokenType.WORD));
        assertFalse(parser.isEmpty());
    }

    @Test
    public void testResetEffectOnIsEmpty() {
        assertTrue(parser.isEmpty());

        parser.add(tokenPool.getToken("word", TokenType.WORD));
        assertFalse(parser.isEmpty());

        parser.reset();
        assertTrue(parser.isEmpty());
    }

    @Test
    public void testResetEffectOnGetSentence() {
        final Token token1 = tokenPool.getToken("word", TokenType.WORD);

        parser.add(token1);

        final Sentence sentence1 = parser.getSentence();
        assertEquals(1, sentence1.getTokens().size());
        assertEquals(token1, sentence1.getTokens().get(0));

        parser.reset();

        final Sentence sentence2 = parser.getSentence();
        assertEquals(0, sentence2.getTokens().size());
    }

    @Test
    public void testIsCompleteSimple() {
        assertFalse(parser.isComplete());

        addTokenAndAssertComplete("I", TokenType.WORD, false);
        addTokenAndAssertComplete(" ", TokenType.WHITESPACE, false);
        addTokenAndAssertComplete("am", TokenType.WORD, false);
        addTokenAndAssertComplete(".", TokenType.SENTENCE_TERMINAL, true);
    }

    @Test
    public void testIsCompleteWithQuotes() {
        assertFalse(parser.isComplete());

        addTokenAndAssertComplete("He", TokenType.WORD, false);
        addTokenAndAssertComplete(" ", TokenType.WHITESPACE, false);
        addTokenAndAssertComplete("said", TokenType.WORD, false);
        addTokenAndAssertComplete(" ", TokenType.WHITESPACE, false);
        addTokenAndAssertComplete("\"", TokenType.PUNCTUATION, false);
        addTokenAndAssertComplete("Hello", TokenType.WORD, false);
        addTokenAndAssertComplete(".", TokenType.SENTENCE_TERMINAL, false);
        addTokenAndAssertComplete("\"", TokenType.PUNCTUATION, true);
    }

    private void addTokenAndAssertComplete(final String text, final TokenType type, final boolean shouldBeComplete) {
        parser.add(tokenPool.getToken(text, type));
        assertEquals(shouldBeComplete, parser.isComplete());
    }
}
