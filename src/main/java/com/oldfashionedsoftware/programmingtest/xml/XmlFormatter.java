package com.oldfashionedsoftware.programmingtest.xml;

import java.util.Collection;

import com.oldfashionedsoftware.programmingtest.model.Document;

public interface XmlFormatter {

    public String toXml(Document doc);

    public String toXml(Collection<Document> documents);

}
