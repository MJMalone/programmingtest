package com.oldfashionedsoftware.programmingtest.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class DocumentTest {
    private Document doc;

    private Sentence sentence1;
    private Sentence sentence2;
    private Sentence sentence3;

    @Before
    public void before() {
        sentence1 = new Sentence(Arrays.asList(new Token("one", TokenType.WORD)), false);
        sentence2 = new Sentence(Arrays.asList(new Token("two", TokenType.WORD)), false);
        sentence3 = new Sentence(Arrays.asList(new Token("three", TokenType.WORD)), false);

        doc = new Document();
        doc.add(sentence1);
        doc.add(sentence2);
        doc.add(sentence3);
    }

    @Test
    public void testGetSentences() {
        assertEquals(
            Arrays.asList(sentence1, sentence2, sentence3),
            doc.getSentences());
    }

    @Test
    public void testToString() {
        assertEquals(
            "[WD(one)]\n" +
            "[WD(two)]\n" +
            "[WD(three)]",
            doc.toString());
    }
}
