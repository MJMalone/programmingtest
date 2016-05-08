package com.oldfashionedsoftware.programmingtest.lexer;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class RegexTokenGenerator {

    private final TokenPool tokenPool;

    public RegexTokenGenerator(final TokenPool tokenPool) {
        this.tokenPool = tokenPool;
    }

    public List<Token> generate(final String text, final String regex) {
        final List<Token> result = new LinkedList<>();

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            final Token token = getToken(matcher);
            if (isGoodToken(matcher, token)) {
                result.add(token);
            }
        }

        return result;
    }

    private boolean isGoodToken(final Matcher matcher, final Token token) {
        if (token == null) {
            final String matchedText = matcher.group();
            if (matchedText != null && matchedText.length() > 0) {
                throw new RuntimeException(
                    "Could not find token type for matched group: \"" + matcher.group() + "\"");
            }
            // Matched an empty string at the end
            return false;
        }
        return true;
    }

    private Token getToken(final Matcher matcher) {
        for (final TokenType type : TokenType.values()) {
            final String tokenString = matcher.group(type.getShortName());
            if (tokenString != null) {
                return tokenPool.getToken(tokenString, type);
            }
        }
        return null;
    }
}
