package com.oldfashionedsoftware.programmingtest.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.oldfashionedsoftware.programmingtest.lexer.Lexer;
import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParser;

public class TextToDocumentConverter {
    private final Lexer lexer;
    private final DocumentParser parser;

    public TextToDocumentConverter(
        final Lexer lexer,
        final DocumentParser parser)
    {
        this.lexer = lexer;
        this.parser = parser;
    }

    public Document convert(final String text, final List<NamedEntity> namedEntities)
        throws URISyntaxException, IOException
    {
        final List<Token> tokens = lexer.analyze(text, namedEntities);

        return parser.parse(tokens);
    }

}
