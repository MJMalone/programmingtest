package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class TokenPoolTest {

    private TokenPool tokenPool;

    @Before
    public void before() {
        tokenPool = new TokenPool();
    }

    @Test
    public void testGetWhitespaceToken() {
        final Token token = tokenPool.getToken(" ", TokenType.WHITESPACE);
        assertNotNull(token);
        assertEquals(" ", token.getText());
        assertEquals(TokenType.WHITESPACE, token.getType());
    }

    @Test
    public void testGetWordToken() {
        final Token token = tokenPool.getToken("Word", TokenType.WORD);
        assertNotNull(token);
        assertEquals("Word", token.getText());
        assertEquals(TokenType.WORD, token.getType());
    }

    @Test
    public void testGetPunctuationToken() {
        final Token token = tokenPool.getToken(",", TokenType.PUNCTUATION);
        assertNotNull(token);
        assertEquals(",", token.getText());
        assertEquals(TokenType.PUNCTUATION, token.getType());
    }

    @Test
    public void testGetSentenceTerminalToken() {
        final Token token = tokenPool.getToken("!", TokenType.SENTENCE_TERMINAL);
        assertNotNull(token);
        assertEquals("!", token.getText());
        assertEquals(TokenType.SENTENCE_TERMINAL, token.getType());
    }

    @Test
    public void testNoDuplicatesNormal() {
        final Token token1 = tokenPool.getToken("Word", TokenType.WORD);
        final Token token2 = tokenPool.getToken("Word", TokenType.WORD);
        final Token token3 = tokenPool.getToken("Word", TokenType.WORD);

        assertSame(token1, token2);
        assertSame(token2, token3);
    }

    @Test
    public void testDesignAssumption() {
        // The tokens are mapped by their text only, not by type.
        // Callers must make sure to use the proper type.
        final Token token1 = tokenPool.getToken("Word", TokenType.WORD);
        final Token token2 = tokenPool.getToken("Word", TokenType.WHITESPACE);

        assertSame(token1, token2);

        assertEquals(TokenType.WORD, token1.getType());
        assertEquals(TokenType.WORD, token2.getType());
    }

}
