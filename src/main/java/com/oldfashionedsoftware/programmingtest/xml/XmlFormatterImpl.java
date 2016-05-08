package com.oldfashionedsoftware.programmingtest.xml;

import com.oldfashionedsoftware.programmingtest.model.Document;
import com.oldfashionedsoftware.programmingtest.model.Sentence;
import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenType;

public class XmlFormatterImpl implements XmlFormatter {

    @Override
    public String toXml(final Document doc) {
        final StringBuilder buf = new StringBuilder();
        buf.append("<?xml version=\"1.0\"?>\n");

        addDocument(buf, doc);

        return buf.toString();
    }

    private void addDocument(final StringBuilder buf, final Document doc) {
        buf.append("<document>\n");
        for (final Sentence sentence : doc.getSentences()) {
            addSentence(buf, sentence);
        }
        buf.append("</document>\n");
    }

    private void addSentence(final StringBuilder buf, final Sentence sentence) {
        buf.append("  <sentence>\n");
        for (final Token token : sentence.getTokens()) {
            addToken(buf, token);
        }
        buf.append("  </sentence>\n");
    }

    private void addToken(final StringBuilder buf, final Token token) {
        final TokenType type = token.getType();

        buf.append("    <token type=\"").append(type.name()).append("\">");

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
