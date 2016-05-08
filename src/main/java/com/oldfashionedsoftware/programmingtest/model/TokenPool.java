package com.oldfashionedsoftware.programmingtest.model;

import java.util.HashMap;
import java.util.Map;

public class TokenPool {

    private final Map<String, Token> tokenMap = new HashMap<>();

    // Prevents us from creating, for example, thousands of Token(" ", TokenType.WHITESPACE)
    public synchronized Token getToken(final String text, final TokenType type) {
        Token token = tokenMap.get(text);
        if (token == null) {
            token = new Token(text, type);
            tokenMap.put(text, token);
        }
        return token;
    }

}
