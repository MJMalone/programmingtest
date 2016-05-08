package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenTypeTest {

    @Test
    public void testOrder() {
        // The ordering of TokenTypes is significant.
        final TokenType[] types = TokenType.values();

        assertEquals(4, types.length);

        assertEquals(TokenType.WHITESPACE, types[0]);
        assertEquals(TokenType.WORD, types[1]);
        assertEquals(TokenType.PUNCTUATION, types[2]);
        assertEquals(TokenType.SENTENCE_TERMINAL, types[3]);
    }

    @Test
    public void testGetShortName() {
        assertEquals("WD", TokenType.WORD.getShortName());
    }

    @Test
    public void testGetRegex() {
        assertEquals("[!?.]", TokenType.SENTENCE_TERMINAL.getRegex());
    }

}
