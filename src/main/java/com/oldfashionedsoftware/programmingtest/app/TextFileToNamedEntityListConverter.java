package com.oldfashionedsoftware.programmingtest.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.text.TextFileReader;

public class TextFileToNamedEntityListConverter {

    private final TextFileReader reader;

    public TextFileToNamedEntityListConverter(final TextFileReader reader) {
        this.reader = reader;
    }

    public List<NamedEntity> convert(final String fileName) throws URISyntaxException, IOException {
        if (fileName == null || fileName.length() == 0) {
            return new LinkedList<>();
        }

        final String namedEntityString = reader.getFromClasspath(fileName);
        return stringToNamedEntityList(namedEntityString);
    }

    private List<NamedEntity> stringToNamedEntityList(final String namedEntityListing) {
        final List<NamedEntity> namedEntities = new LinkedList<>();

        for (final String line : namedEntityListing.split("\n")) {
            if (line != null) {
                final String trimmedLine = line.trim();
                if (trimmedLine.length() > 0) {
                    namedEntities.add(new NamedEntity(trimmedLine));
                }
            }
        }

        return namedEntities;
    }
}
