package com.oldfashionedsoftware.programmingtest.lexer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;

public class LexerRegexBuilderTest {
    public static final String EXPECTED_REGEX_NO_NAMED_ENTITIES =
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
        final String regex = builder.generateLexerRegex(new LinkedList<>());
        assertEquals(EXPECTED_REGEX_NO_NAMED_ENTITIES, regex);
    }

    @Test
    public void testGenerateLexerRegexWithNamedEntities() {
        final String regex = builder.generateLexerRegex(
            Arrays.asList(
                new NamedEntity("George Washington"),
                new NamedEntity("Abraham Lincoln")
                ));
        assertEquals(
            "(?<NE>George\\s+Washington|Abraham\\s+Lincoln)|" +
            EXPECTED_REGEX_NO_NAMED_ENTITIES,
            regex);
    }
}
