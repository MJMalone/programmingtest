package com.oldfashionedsoftware.programmingtest.text;

import java.io.IOException;
import java.net.URISyntaxException;

public interface TextFileReader {
    public String getFromClasspath(String fileName) throws URISyntaxException, IOException;
}
