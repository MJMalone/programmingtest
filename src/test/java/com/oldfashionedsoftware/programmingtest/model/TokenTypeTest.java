package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TokenTypeTest {

    @Test
    public void testOrder() {
        // The ordering of TokenTypes is significant.
        final TokenType[] types = TokenType.values();

        assertEquals(5, types.length);

        assertEquals(TokenType.NAMED_ENTITY, types[0]);
        assertEquals(TokenType.WHITESPACE, types[1]);
        assertEquals(TokenType.WORD, types[2]);
        assertEquals(TokenType.PUNCTUATION, types[3]);
        assertEquals(TokenType.SENTENCE_TERMINAL, types[4]);
    }

    @Test
    public void testGetShortName() {
        assertEquals("WD", TokenType.WORD.getShortName());
    }

    @Test
    public void testGetRegex() {
        assertEquals("[!?.]", TokenType.SENTENCE_TERMINAL.getRegex());
    }

    @Test
    public void testGetRegexNamedEntity() {
        assertNull(TokenType.NAMED_ENTITY.getRegex());
    }

}
