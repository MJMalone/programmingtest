package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TokenTest {

    private Token token;

    @Before
    public void before() {
        token = new Token("Word", TokenType.WORD);
    }

    @Test
    public void testGetText() {
        assertEquals("Word", token.getText());
    }

    @Test
    public void testGetType() {
        assertEquals(TokenType.WORD, token.getType());
    }

    @Test
    public void testToString() {
        assertEquals("WD(Word)", token.toString());
    }

    @Test
    public void testToStringWhitespace() {
        assertEquals("WS", new Token("   ", TokenType.WHITESPACE).toString());
        assertEquals("WS", new Token("\n \r \t \f", TokenType.WHITESPACE).toString());
    }

    @Test
    public void testMismatchedTextAndType() {
        final Token mismatched1 = new Token("word", TokenType.WHITESPACE);
        assertNotNull(mismatched1);
        assertEquals("word", mismatched1.getText());
        assertEquals(TokenType.WHITESPACE, mismatched1.getType());
        assertEquals("WS", mismatched1.toString());

        final Token mismatched2 = new Token("   ", TokenType.WORD);
        assertNotNull(mismatched2);
        assertEquals("   ", mismatched2.getText());
        assertEquals(TokenType.WORD, mismatched2.getType());
        assertEquals("WD(   )", mismatched2.toString());
    }

    // Note: I would normally just use EqualsVerifier (http://www.jqno.nl/equalsverifier/)
    @Test
    public void testHashAndEquals() {
        final Token wordToken1 = new Token("Word", TokenType.WORD);
        final Token wordToken2 = new Token("Word", TokenType.WORD);
        final Token wordToken3 = new Token("other", TokenType.WORD);
        final Token punctToken1 = new Token("\"", TokenType.PUNCTUATION);
        final Token punctToken2 = new Token("\"", TokenType.PUNCTUATION);
        final Token punctToken3 = new Token("'", TokenType.PUNCTUATION);

        // Equal objects must have equal hashes
        assertSameHash(wordToken1, wordToken2);
        assertSameHash(punctToken1, punctToken2);

        // Different hashes are not strictly required, but if we find a hash
        // collision here then we should look into our hashCode implementation.
        assertDifferentHash(wordToken1, wordToken3, punctToken1, punctToken3);

        // Test equals
        assertTokensEqual(wordToken1, wordToken2);
        assertTokensNotEqual(wordToken1, wordToken3);
        assertTokensNotEqual(wordToken2, wordToken3);

        assertTokensEqual(punctToken1, punctToken2);
        assertTokensNotEqual(punctToken1, punctToken3);
        assertTokensNotEqual(punctToken2, punctToken3);

        assertTokensNotEqual(punctToken1, wordToken1, wordToken2, wordToken3);
        assertTokensNotEqual(punctToken2, wordToken1, wordToken2, wordToken3);
        assertTokensNotEqual(punctToken3, wordToken1, wordToken2, wordToken3);

        // Trivial cases
        assertTrue(wordToken1.equals(wordToken1));
        assertFalse(wordToken1.equals(null));
        assertFalse(wordToken1.equals("string"));
    }

    private void assertSameHash(final Token... tokens) {
        final int origHash = tokens[0].hashCode();
        for (final Token curToken : tokens) {
            assertEquals(origHash, curToken.hashCode());
        }
    }

    private void assertDifferentHash(final Token... tokens) {
        final Set<Integer> hashes = new HashSet<>();
        for (final Token curToken : tokens) {
            hashes.add(curToken.hashCode());
        }
        assertEquals(
            "There are " + tokens.length + " tokens but " + hashes.size() + " unique hash codes.",
            hashes.size(), tokens.length);
    }

    private void assertTokensEqual(final Token token1, final Token token2) {
        assertTrue(token1.equals(token2));
        assertTrue(token2.equals(token1));
    }

    private void assertTokensNotEqual(final Token token1, final Token... others) {
        for (final Token other : others) {
            assertFalse(token1.equals(other));
            assertFalse(other.equals(token1));
        }
    }
}
