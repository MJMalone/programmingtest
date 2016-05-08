package com.oldfashionedsoftware.programmingtest.parser;

import java.util.LinkedList;
import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class SentenceParserImpl implements SentenceParser {
    private static final String QUOTE = "\"";

    private final List<Token> tokens = new LinkedList<>();
    private boolean inQuotes = false;
    private boolean terminalFound = false;

    @Override
    public void add(final Token token) {
        tokens.add(token);
        if (QUOTE.equals(token.getText())) {
            inQuotes = !inQuotes;
        }
        if (TokenType.SENTENCE_TERMINAL == token.getType()) {
            terminalFound = true;
        }
    }

    @Override
    public boolean isComplete() {
        return terminalFound && !inQuotes;
    }

    @Override
    public boolean isEmpty() {
        return tokens.size() == 0;
    }

    @Override
    public Sentence getSentence() {
        return new Sentence(tokens, isComplete());
    }

    @Override
    public void reset() {
        tokens.clear();
        inQuotes = false;
        terminalFound = false;
    }

}
