package com.oldfashionedsoftware.programmingtest.lexer;

import java.util.List;
import java.util.stream.Collectors;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

class LexerRegexBuilder {

    String generateLexerRegex(final List<NamedEntity> namedEntities) {
        final StringBuilder compositeRegex = new StringBuilder();

        for (final TokenType tokenType : TokenType.values()) {
            // If there are no named entities, don't create a named capture group
            if (TokenType.NAMED_ENTITY == tokenType && namedEntities.size() > 0) {
                constructNamedEntityCaptureGroup(compositeRegex, namedEntities);
            }
            else if (TokenType.NAMED_ENTITY != tokenType) {
                addNamedCaptureGroup(compositeRegex, tokenType);
            }
        }

        // If none of the tokens is matched, something is wrong.
        // Get the rest of the string in an un-named group.
        compositeRegex.append("(.*)");

        return compositeRegex.toString();
    }

    private void constructNamedEntityCaptureGroup(
        final StringBuilder compositeRegex,
        final List<NamedEntity> namedEntities)
    {
        compositeRegex
            .append("(?<") // start a named capture group
            .append(TokenType.NAMED_ENTITY.getShortName())
            .append(">"); // close the name

        final String joinedRegex = namedEntities.stream()
            .map(ne -> ne.getRegex())
            .collect(Collectors.joining("|"));

        compositeRegex.append(joinedRegex).append(")|"); // end capture group
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
