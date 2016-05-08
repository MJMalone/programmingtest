package com.oldfashionedsoftware.programmingtest.lexer;

import java.util.List;

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
        final String regex = regexBuilder.generateLexerRegex();
        return tokenGenerator.generate(text, regex);
    }

}
