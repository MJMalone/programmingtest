package com.oldfashionedsoftware.programmingtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.oldfashionedsoftware.programmingtest.app.TextFileToNamedEntityListConverter;
import com.oldfashionedsoftware.programmingtest.app.TextFileToXmlConverter;
import com.oldfashionedsoftware.programmingtest.app.TextToDocumentConverter;
import com.oldfashionedsoftware.programmingtest.app.ZipFileToXmlConverter;
import com.oldfashionedsoftware.programmingtest.lexer.Lexer;
import com.oldfashionedsoftware.programmingtest.lexer.LexerImpl;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParser;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParserImpl;
import com.oldfashionedsoftware.programmingtest.text.TextFileReader;
import com.oldfashionedsoftware.programmingtest.text.TextFileReaderImpl;
import com.oldfashionedsoftware.programmingtest.text.ZipFileHandler;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatterImpl;

@Lazy
@Configuration
public class MainConfig {

    @Bean
    public TextFileToXmlConverter getTextFileToXmlConverter() {
        return new TextFileToXmlConverter(
            getTextFileReader(),
            getTextToNamedEntityListConverter(),
            getTextToDocumentConverter(),
            getXmlFormatter());
    }

    @Bean
    public ZipFileToXmlConverter getZipFileToXmlConverter() {
        return new ZipFileToXmlConverter(
            getZipFileHandler(),
            getTextToNamedEntityListConverter(),
            getTextToDocumentConverter(),
            getXmlFormatter(),
            10);
    }

    @Bean
    public ZipFileHandler getZipFileHandler() {
        return new ZipFileHandler();
    }

    @Bean
    public TextFileToNamedEntityListConverter getTextToNamedEntityListConverter() {
        return new TextFileToNamedEntityListConverter(getTextFileReader());
    }

    @Bean
    public TextFileReader getTextFileReader() {
        return new TextFileReaderImpl();
    }

    @Bean
    public TextToDocumentConverter getTextToDocumentConverter() {
        return new TextToDocumentConverter(getLexer(), getParser());
    }

    @Bean
    public DocumentParser getParser() {
        return new DocumentParserImpl(false);
    }

    @Bean
    public Lexer getLexer() {
        return new LexerImpl(getTokenPool());
    }

    @Bean
    public TokenPool getTokenPool() {
        return new TokenPool();
    }

    @Bean
    public XmlFormatter getXmlFormatter() {
        return new XmlFormatterImpl();
    }
}