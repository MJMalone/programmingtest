package com.oldfashionedsoftware.programmingtest.model;

import java.util.LinkedList;
import java.util.List;

public class Sentence {
    private final List<Token> tokens = new LinkedList<>();
    private final boolean complete;

    public Sentence(final List<Token> tokens, final boolean complete) {
        this.tokens.addAll(tokens);
        this.complete = complete;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return tokens.toString();
    }
}