package com.oldfashionedsoftware.programmingtest.xml;

import java.util.Arrays;
import java.util.Collection;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class XmlFormatterImpl implements XmlFormatter {

    @Override
    public String toXml(final Collection<Document> documents) {
        final StringBuilder buf = new StringBuilder();
        buf.append("<?xml version=\"1.0\"?>\n");
        buf.append("<archive>\n");

        for (final Document doc : documents) {
            addDocument(buf, doc);
        }

        buf.append("</archive>\n");
        return buf.toString();
    }

    @Override
    public String toXml(final Document doc) {
        return toXml(Arrays.asList(doc));
    }

    private void addDocument(final StringBuilder buf, final Document doc) {
        buf.append("  <document");

        final String name = doc.getName();
        if (name != null && name.length() > 0) {
            buf.append(" name=\"").append(name).append("\"");
        }

        buf.append(">\n");

        for (final Sentence sentence : doc.getSentences()) {
            addSentence(buf, sentence);
        }
        buf.append("  </document>\n");
    }

    private void addSentence(final StringBuilder buf, final Sentence sentence) {
        buf.append("    <sentence>\n");
        for (final Token token : sentence.getTokens()) {
            addToken(buf, token);
        }
        buf.append("    </sentence>\n");
    }

    private void addToken(final StringBuilder buf, final Token token) {
        final TokenType type = token.getType();

        buf.append("      <token type=\"").append(type.name()).append("\">");

        // Using CDATA for whitespace, in case  and punctuation
        if (TokenType.WHITESPACE == type || TokenType.PUNCTUATION == type) {
            buf.append("<![CDATA[").append(token.getText()).append("]]>");
        }
        else {
            buf.append(token.getText());
        }

        buf.append("</token>\n");
    }

}
