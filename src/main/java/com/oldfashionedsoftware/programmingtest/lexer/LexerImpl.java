package com.oldfashionedsoftware.programmingtest.lexer;

import java.util.LinkedList;
import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;

public class LexerImpl implements Lexer {
    private final LexerRegexBuilder regexBuilder;
    private final RegexTokenGenerator tokenGenerator;

    // package constructor useful for testing
    LexerImpl(
        final LexerRegexBuilder regexBuilder,
        final RegexTokenGenerator tokenGenerator)
    {
        this.regexBuilder = regexBuilder;
        this.tokenGenerator = tokenGenerator;
    }

    public LexerImpl(final TokenPool tokenPool) {
        this(new LexerRegexBuilder(), new RegexTokenGenerator(tokenPool));
    }

    @Override
    public List<Token> analyze(final String text) {
        return analyze(text, new LinkedList<>());
    }

    @Override
    public List<Token> analyze(final String text, final String namedEntityListing) {
        final List<NamedEntity> namedEntities = stringToNamedEntityList(namedEntityListing);
        return analyze(text, namedEntities);
    }

    private List<NamedEntity> stringToNamedEntityList(final String namedEntityListing) {
        final List<NamedEntity> namedEntities = new LinkedList<>();

        for (final String line : namedEntityListing.split("\n")) {
            if (line != null) {
                final String trimmedLine = line.trim();
                if (trimmedLine.length() > 0) {
                    namedEntities.add(new NamedEntity(trimmedLine));
                }
            }
        }

        return namedEntities;
    }

    @Override
    public List<Token> analyze(final String text, final List<NamedEntity> namedEntities) {
        final String regex = regexBuilder.generateLexerRegex(namedEntities);
        return tokenGenerator.generate(text, regex, namedEntities);
    }

}
