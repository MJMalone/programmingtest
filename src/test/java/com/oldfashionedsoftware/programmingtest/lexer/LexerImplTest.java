package com.oldfashionedsoftware.programmingtest.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.oldfashionedsoftware.programmingtest.model.Token;
import com.oldfashionedsoftware.programmingtest.model.TokenPool;

@RunWith(MockitoJUnitRunner.class)
public class LexerImplTest {

    private LexerImpl lexer;

    @Mock private LexerRegexBuilder regexBuilder;
    @Mock private RegexTokenGenerator tokenGenerator;

    @Before
    public void before() {
        lexer = new LexerImpl(regexBuilder, tokenGenerator);
    }

    @Test
    public void testDefaultConstructorEmptyString() {
        final List<Token> result = new LexerImpl(new TokenPool()).analyze("");
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testAnalyze() {
        final List<Token> expectedResult = new LinkedList<>();
        final String expectedText = "expectedText";
        final String expectedRegex = "expectedRegex";

        when(regexBuilder.generateLexerRegex()).thenReturn(expectedRegex);
        when(tokenGenerator.generate(expectedText, expectedRegex)).thenReturn(expectedResult);

        final List<Token> result = lexer.analyze(expectedText);

        verify(regexBuilder).generateLexerRegex();
        verify(tokenGenerator).generate(expectedText, expectedRegex);

        assertSame(expectedResult, result);

        verifyNoMoreInteractions(regexBuilder, tokenGenerator);
    }
}
