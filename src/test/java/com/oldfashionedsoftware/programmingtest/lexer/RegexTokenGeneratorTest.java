package com.oldfashionedsoftware.programmingtest.lexer;

import static com.oldfashionedsoftware.programmingtest.lexer.LexerRegexBuilderTest.EXPECTED_REGEX_NO_NAMED_ENTITIES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class RegexTokenGeneratorTest {

    private List<NamedEntity> namedEntities;
    private RegexTokenGenerator gen;
    private TokenPool tokenPool;

    @Before
    public void before() {
        namedEntities = new LinkedList<>();
        tokenPool = new TokenPool();
        gen = new RegexTokenGenerator(tokenPool);
    }

    @Test
    public void testEmptyText() {
        final List<Token> result = gen.generate("", EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testSingleWord() {
        final String testString = "Word";
        final List<Token> expectedResult = Arrays.asList(tokenPool.getToken("Word", TokenType.WORD));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testShortWord() {
        final String testString = "a";
        final List<Token> expectedResult = Arrays.asList(tokenPool.getToken("a", TokenType.WORD));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSeveralShortWords() {
        final String testString = "a b c d";
        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("a", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("b", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("c", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("d", TokenType.WORD));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testTwoWords() {
        final String testString = "Word1 word2";
        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("Word1", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("word2", TokenType.WORD));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testHyphenatedWords() {
        final String testString = "-One two- three-four - words";
        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("-", TokenType.PUNCTUATION),
            tokenPool.getToken("One", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("two", TokenType.WORD),
            tokenPool.getToken("-", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("three-four", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("-", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("words", TokenType.WORD));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testWhitespace() {
        final String testString = "One two\tthree\nfour\rfive \t\n\rsix";
        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("One", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("two", TokenType.WORD),
            tokenPool.getToken("\t", TokenType.WHITESPACE),
            tokenPool.getToken("three", TokenType.WORD),
            tokenPool.getToken("\n", TokenType.WHITESPACE),
            tokenPool.getToken("four", TokenType.WORD),
            tokenPool.getToken("\r", TokenType.WHITESPACE),
            tokenPool.getToken("five", TokenType.WORD),
            tokenPool.getToken(" \t\n\r", TokenType.WHITESPACE),
            tokenPool.getToken("six", TokenType.WORD));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSentenceTerminators() {
        final String testString = "Hello. What? Oh my!";
        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("Hello", TokenType.WORD),
            tokenPool.getToken(".", TokenType.SENTENCE_TERMINAL),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("What", TokenType.WORD),
            tokenPool.getToken("?", TokenType.SENTENCE_TERMINAL),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("Oh", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("my", TokenType.WORD),
            tokenPool.getToken("!", TokenType.SENTENCE_TERMINAL));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testMix() {
        final String testString = "Testing testing, 1 2 3. Is ... is this thing on?";
        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("Testing", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("testing", TokenType.WORD),
            tokenPool.getToken(",", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("1", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("2", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("3", TokenType.WORD),
            tokenPool.getToken(".", TokenType.SENTENCE_TERMINAL),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("Is", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("...", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("is", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("this", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("thing", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("on", TokenType.WORD),
            tokenPool.getToken("?", TokenType.SENTENCE_TERMINAL));

        final List<Token> result = gen.generate(testString, EXPECTED_REGEX_NO_NAMED_ENTITIES, namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testWithNamedEntities() {
        namedEntities.add(new NamedEntity("Elvis Presley"));
        final String testString = "Attention! Elvis Presley has left the building.";

        final List<Token> expectedResult = Arrays.asList(
            tokenPool.getToken("Attention", TokenType.WORD),
            tokenPool.getToken("!", TokenType.SENTENCE_TERMINAL),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("Elvis Presley", TokenType.NAMED_ENTITY),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("has", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("left", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("the", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("building", TokenType.WORD),
            tokenPool.getToken(".", TokenType.SENTENCE_TERMINAL));

        final List<Token> result = gen.generate(
            testString,
            "(?<NE>George\\s+Washington|Elvis\\s+Presley|Abraham\\s+Lincoln)|" +
            EXPECTED_REGEX_NO_NAMED_ENTITIES,
            namedEntities);
        assertEquals(expectedResult, result);
    }

    @Test(expected = RuntimeException.class)
    public void testIncompleteRegex() {
        final String INCOMPLETE_REGEX =
            "(?<WS>\\s+)|" +
            "(?<WD>[\\w][-\\w]*[\\w]|[\\w])|" +
            "(?<PC>\\.\\.\\.|[,#'])|" + // Incomplete list of punctuation characters
            "(?<ST>[!?.])|" +
            "(.*)";

        // This string includes a character '^' that is not covered by named capture groups.
        final String testString = "a ^";

        gen.generate(testString, INCOMPLETE_REGEX, namedEntities);
    }
}
