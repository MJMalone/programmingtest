package com.oldfashionedsoftware.programmingtest.text;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TextFileReaderImplTest {

    private TextFileReaderImpl reader;

    @Before
    public void before() {
        reader = new TextFileReaderImpl();
    }

    @Test
    public void testNormalFile() throws Exception {
        // Note: make sure that the test file hasn't been given different newlines
        final String expectedContents =
            "line one\n" +
            "line two\n" +
            "\n" +
            "line three\n";
        final String contents = reader.getFromClasspath("test_text_file.txt");
        assertEquals(expectedContents, contents);
    }
}
