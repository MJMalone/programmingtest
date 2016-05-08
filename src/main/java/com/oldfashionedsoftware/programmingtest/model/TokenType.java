package com.oldfashionedsoftware.programmingtest.model;

public enum TokenType {

    // @formatter:off

    // NOTE that the order of these enumerated values is significant and should not be changed.

    // Any whitespace.
    WHITESPACE(             "WS", "\\s+"),

    // WORD is a string of letters, numbers, or underscores. It can contain a
    // hypen, but they hyphen has to be between two other characters.  So the
    // word "pear-shaped" is matched, but "What-" is not.
    WORD(                   "WD", "[\\w][-\\w]*[\\w]" + // Where length > 1, can have an *internal* hyphen
                                  "|" +
                                  "[\\w]"),             // Where length == 1, NOTE: This must be last

    // NOTE that it is vitally important that this token be next-to-last (before SENTENCE_TERMINAL)
    // and that it match any character that has not been matched by either of the preceding types except
    // the three sentence-terminating characters ([!?.]).  Which is to say, a punctuation is considered
    // to be either a multi-character punctuation mark such as an ellipsis, or any non-word, non-whitespace,
    // non-sentence-terminator ([!?.]) character.
    PUNCTUATION(            "PC", "\\.\\.\\.|" +   // Ellipsis
                                  "[^\\w\\s!?.]"), // Any single character that's not a word character,
                                                   // whitespace, "!", "?" or ".". The \w and \s are
                                                   // redundant since they would be caught by the previous
                                                   // expressions, but included for clarity.

    // These are sentence-ending punctuation marks.
    SENTENCE_TERMINAL(      "ST", "[!?.]");

    // @formatter:on

    private final String regex;
    private final String shortName;

    private TokenType(final String shortName, final String regex) {
        this.shortName = shortName;
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public String getShortName() {
        return shortName;
    }

}
