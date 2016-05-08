package com.oldfashionedsoftware.programmingtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oldfashionedsoftware.programmingtest.lexer.Lexer;
import com.oldfashionedsoftware.programmingtest.lexer.LexerImpl;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParser;
import com.oldfashionedsoftware.programmingtest.parser.DocumentParserImpl;
import com.oldfashionedsoftware.programmingtest.text.TextFileReaderImpl;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatter;
import com.oldfashionedsoftware.programmingtest.xml.XmlFormatterImpl;

@Configuration
public class MainConfig {

    @Bean
    public TextFileToXmlConverter getTextFileToXmlConverter() {
        return new TextFileToXmlConverter(
            new TextFileReaderImpl(),
            getLexer(),
            getParser(),
            getXmlFormatter());
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