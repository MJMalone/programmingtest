package com.oldfashionedsoftware.programmingtest.xml;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class XmlFormatterImplTest {

    private XmlFormatterImpl formatter;
    private TokenPool tokenPool;

    @Before
    public void before() {
        tokenPool = new TokenPool();
        formatter = new XmlFormatterImpl();
    }

    @Test
    public void testEmptyDocument() {
        final String result = formatter.toXml(new Document());
        assertEquals(
            "<?xml version=\"1.0\"?>\n" +
            "<archive>\n" +
            "  <document>\n" +
            "  </document>\n" +
            "</archive>\n",
            result);
    }

    @Test
    public void testOneEmptySentence() {
        final Document doc = new Document();
        doc.add(new Sentence(new LinkedList<>(), false));

        final String result = formatter.toXml(doc);
        assertEquals(
            "<?xml version=\"1.0\"?>\n" +
            "<archive>\n" +
            "  <document>\n" +
            "    <sentence>\n" +
            "    </sentence>\n" +
            "  </document>\n" +
            "</archive>\n",
            result);
    }

    @Test
    public void testOneSimpleSentence() {
        final Document doc = new Document();
        doc.add(new Sentence(Arrays.asList(
            tokenPool.getToken("Hello", TokenType.WORD),
            tokenPool.getToken(",", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("World", TokenType.WORD),
            tokenPool.getToken("!", TokenType.SENTENCE_TERMINAL)), true));
        doc.setName("test");

        final String result = formatter.toXml(doc);
        assertEquals(
            "<?xml version=\"1.0\"?>\n" +
            "<archive>\n" +
            "  <document name=\"test\">\n" +
            "    <sentence>\n" +
            "      <token type=\"WORD\">Hello</token>\n" +
            "      <token type=\"PUNCTUATION\"><![CDATA[,]]></token>\n" +
            "      <token type=\"WHITESPACE\"><![CDATA[ ]]></token>\n" +
            "      <token type=\"WORD\">World</token>\n" +
            "      <token type=\"SENTENCE_TERMINAL\">!</token>\n" +
            "    </sentence>\n" +
            "  </document>\n" +
            "</archive>\n",
            result);
    }

    @Test
    public void testMultipleSentences() {
        final Document doc = new Document();

        doc.add(new Sentence(Arrays.asList(
            tokenPool.getToken("Hello", TokenType.WORD),
            tokenPool.getToken(",", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("World", TokenType.WORD),
            tokenPool.getToken("!", TokenType.SENTENCE_TERMINAL)), true));

        doc.add(new Sentence(Arrays.asList(
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("And", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("Goodbye", TokenType.WORD),
            tokenPool.getToken(".", TokenType.SENTENCE_TERMINAL)), true));

        doc.setName("test_name");

        final String result = formatter.toXml(doc);
        assertEquals(
            "<?xml version=\"1.0\"?>\n" +
            "<archive>\n" +
            "  <document name=\"test_name\">\n" +
            "    <sentence>\n" +
            "      <token type=\"WORD\">Hello</token>\n" +
            "      <token type=\"PUNCTUATION\"><![CDATA[,]]></token>\n" +
            "      <token type=\"WHITESPACE\"><![CDATA[ ]]></token>\n" +
            "      <token type=\"WORD\">World</token>\n" +
            "      <token type=\"SENTENCE_TERMINAL\">!</token>\n" +
            "    </sentence>\n" +
            "    <sentence>\n" +
            "      <token type=\"WHITESPACE\"><![CDATA[ ]]></token>\n" +
            "      <token type=\"WORD\">And</token>\n" +
            "      <token type=\"WHITESPACE\"><![CDATA[ ]]></token>\n" +
            "      <token type=\"WORD\">Goodbye</token>\n" +
            "      <token type=\"SENTENCE_TERMINAL\">.</token>\n" +
            "    </sentence>\n" +
            "  </document>\n" +
            "</archive>\n",
            result);
    }

    @Test
    public void testMultipleDocuments() {
        final Document doc1 = new Document();
        doc1.add(new Sentence(Arrays.asList(
            tokenPool.getToken("Hello", TokenType.WORD),
            tokenPool.getToken(",", TokenType.PUNCTUATION),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("World", TokenType.WORD),
            tokenPool.getToken("!", TokenType.SENTENCE_TERMINAL)), true));
        doc1.setName("doc1");

        final Document doc2 = new Document();
        doc2.add(new Sentence(Arrays.asList(
            tokenPool.getToken("And", TokenType.WORD),
            tokenPool.getToken(" ", TokenType.WHITESPACE),
            tokenPool.getToken("Goodbye", TokenType.WORD),
            tokenPool.getToken(".", TokenType.SENTENCE_TERMINAL)), true));
        doc2.setName("doc2");

        final String result = formatter.toXml(Arrays.asList(doc1, doc2));
        assertEquals(
            "<?xml version=\"1.0\"?>\n" +
            "<archive>\n" +
            "  <document name=\"doc1\">\n" +
            "    <sentence>\n" +
            "      <token type=\"WORD\">Hello</token>\n" +
            "      <token type=\"PUNCTUATION\"><![CDATA[,]]></token>\n" +
            "      <token type=\"WHITESPACE\"><![CDATA[ ]]></token>\n" +
            "      <token type=\"WORD\">World</token>\n" +
            "      <token type=\"SENTENCE_TERMINAL\">!</token>\n" +
            "    </sentence>\n" +
            "  </document>\n" +
            "  <document name=\"doc2\">\n" +
            "    <sentence>\n" +
            "      <token type=\"WORD\">And</token>\n" +
            "      <token type=\"WHITESPACE\"><![CDATA[ ]]></token>\n" +
            "      <token type=\"WORD\">Goodbye</token>\n" +
            "      <token type=\"SENTENCE_TERMINAL\">.</token>\n" +
            "    </sentence>\n" +
            "  </document>\n" +
            "</archive>\n",
            result);
    }
}
