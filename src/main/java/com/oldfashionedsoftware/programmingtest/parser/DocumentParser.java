package com.oldfashionedsoftware.programmingtest.parser;

import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Token;

public interface DocumentParser {

    public Document parse(List<Token> tokens);

    public Document parse(List<Token> tokens, SentenceParser sentenceParser);

}
