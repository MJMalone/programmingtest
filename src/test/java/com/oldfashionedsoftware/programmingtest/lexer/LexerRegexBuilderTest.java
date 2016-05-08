package com.oldfashionedsoftware.programmingtest.lexer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LexerRegexBuilderTest {
    public static final String EXPECTED_REGEX =
        "(?<WS>\\s+)|" +
        "(?<WD>[\\w][-\\w]*[\\w]|[\\w])|" +
        "(?<PC>\\.\\.\\.|[^\\w\\s!?.])|" +
        "(?<ST>[!?.])|" +
        "(.*)";

    private LexerRegexBuilder builder;

    @Before
    public void before() {
        builder = new LexerRegexBuilder();
    }

    @Test
    public void testGenerateLexerRegex() {
        final String regex = builder.generateLexerRegex();
        assertEquals(EXPECTED_REGEX, regex);
    }
}
