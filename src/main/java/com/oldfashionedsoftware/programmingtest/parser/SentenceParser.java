package com.oldfashionedsoftware.programmingtest.parser;

import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.Token;

public interface SentenceParser {

    public void add(final Token token);

    public boolean isComplete();

    public boolean isEmpty();

    public Sentence getSentence();

    public void reset();

}
