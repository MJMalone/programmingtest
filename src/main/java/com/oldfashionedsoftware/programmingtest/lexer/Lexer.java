package com.oldfashionedsoftware.programmingtest.lexer;

import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.model.Token;

public interface Lexer {

    public List<Token> analyze(String text);

    public List<Token> analyze(String text, String namedEntityListing);

    public List<Token> analyze(String text, List<NamedEntity> namedEntities);

}
