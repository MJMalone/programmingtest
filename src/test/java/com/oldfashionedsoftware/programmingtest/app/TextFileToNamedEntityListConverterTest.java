package com.oldfashionedsoftware.programmingtest.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.oldfashionedsoftware.programmingtest.model.NamedEntity;
import com.oldfashionedsoftware.programmingtest.text.TextFileReaderImpl;

public class TextFileToNamedEntityListConverterTest {

    private TextFileToNamedEntityListConverter converter;

    @Before
    public void before() {
        // Doing an integration test for simplicity
        converter = new TextFileToNamedEntityListConverter(new TextFileReaderImpl());
    }

    @Test
    public void testWithNullFilename() throws Exception {
        final List<NamedEntity> result = converter.convert(null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testSimple() throws Exception {
        final List<NamedEntity> result = converter.convert("test_NER.txt");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new NamedEntity("Abraham Lincoln"), result.get(0));
        assertEquals(new NamedEntity("George Washington"), result.get(1));
    }

    @Test
    public void testWithWhitespace() throws Exception {
        final List<NamedEntity> result = converter.convert("test_NER_with_whitespace.txt");
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(new NamedEntity("Abraham Lincoln"), result.get(0));
        assertEquals(new NamedEntity("George Washington"), result.get(1));
    }

}
