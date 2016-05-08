package com.oldfashionedsoftware.programmingtest.lexer;

import com.oldfashionedsoftware.programmingtest.model.TokenType;

class LexerRegexBuilder {

    String generateLexerRegex() {
        final StringBuilder compositeRegex = new StringBuilder();

        for (final TokenType tokenType : TokenType.values()) {
            addNamedCaptureGroup(compositeRegex, tokenType);
        }

        // If none of the tokens is matched, something is wrong.
        // Get the rest of the string in an un-named group.
        compositeRegex.append("(.*)");

        return compositeRegex.toString();
    }

    private void addNamedCaptureGroup(final StringBuilder compositeRegex, final TokenType tokenType) {
        compositeRegex
            .append("(?<") // start a named capture group
            .append(tokenType.getShortName())
            .append(">") // close the name
            .append(tokenType.getRegex())
            .append(")|"); // end capture group
    }

}
