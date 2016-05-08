package com.oldfashionedsoftware.programmingtest.model;

import java.util.Objects;

public final class Token {
    private final String text;
    private final TokenType type;
    private final String toStringValue;

    Token(final String text, final TokenType type) {
        this.text = text;
        this.type = type;
        if (TokenType.WHITESPACE == type) {
            toStringValue = type.getShortName(); // No need to include newlines in toString output
        }
        else {
            toStringValue = type.getShortName() + "(" + text + ")";
        }
    }

    public String getText() {
        return text;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return toStringValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, type);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Token that = (Token) obj;

        return Objects.equals(this.text, that.text) &&
        Objects.equals(this.type, that.type);
    }

}
