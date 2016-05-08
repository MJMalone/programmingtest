package com.oldfashionedsoftware.programmingtest.parser;

import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class DocumentParserImpl implements DocumentParser {
    private final boolean keepWhitespace;

    public DocumentParserImpl(final boolean keepWhitespace) {
        this.keepWhitespace = keepWhitespace;
    }

    public DocumentParserImpl() {
        this(true);
    }

    @Override
    public Document parse(final List<Token> tokens) {
        return parse(tokens, new SentenceParserImpl());
    }

    @Override
    public Document parse(final List<Token> tokens, final SentenceParser sentenceParser) {
        final Document doc = new Document();

        for (final Token token : tokens) {
            if (shouldKeep(token)) {
                parseToken(sentenceParser, doc, token);
            }
        }

        if (!sentenceParser.isEmpty()) {
            doc.add(sentenceParser.getSentence());
        }

        return doc;
    }

    private boolean shouldKeep(final Token token) {
        return keepWhitespace || token.getType() != TokenType.WHITESPACE;
    }

    private void parseToken(final SentenceParser sentenceParser, final Document doc, final Token token) {
        sentenceParser.add(token);
        if (sentenceParser.isComplete()) {
            doc.add(sentenceParser.getSentence());
            sentenceParser.reset();
        }
    }

}
