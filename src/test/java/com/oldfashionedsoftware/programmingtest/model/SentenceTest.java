package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SentenceTest {

    private Sentence sentence;

    private Token token1;
    private Token token2;
    private Token token3;
    private Token token4;

    private List<Token> tokenList;

    @Before
    public void before() {
        token1 = new Token("Word", TokenType.WORD);
        token2 = new Token(" ", TokenType.WHITESPACE);
        token3 = new Token("#", TokenType.PUNCTUATION);
        token4 = new Token(".", TokenType.SENTENCE_TERMINAL);

        tokenList = Arrays.asList(token1, token2, token3, token4);

        sentence = new Sentence(tokenList, true); // complete sentence
    }

    @Test
    public void testIsComplete() {
        assertTrue(sentence.isComplete());

        final Sentence incomplete = new Sentence(Arrays.asList(token1), false);
        assertFalse(incomplete.isComplete());
    }

    @Test
    public void testGetTokens() {
        assertEquals(tokenList, sentence.getTokens());
    }

    @Test
    public void testToString() {
        assertEquals(
            "[WD(Word), WS, PC(#), ST(.)]",
            sentence.toString());
    }
}
