package com.oldfashionedsoftware.programmingtest.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Document {
    private final List<Sentence> sentences = new LinkedList<>();

    public void add(final Sentence sentence) {
        sentences.add(sentence);
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        return sentences.stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
    }

}
