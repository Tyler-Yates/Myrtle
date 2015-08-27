package com.tyleryates.util;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("JavaDoc")
public class WordTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Word word;
    private static final String STRING = "lottery";

    @Before
    public void setUp() throws Exception {
        word = new Word(STRING);
    }

    @Test
    public void testWordConstructor() {
        exception.expect(IllegalArgumentException.class);
        new Word(null);
    }

    @Test
    public void testGetWord() {
        assertEquals(STRING, word.getString());
        assertEquals("", new Word("").getString());
    }

    @Test
    public void testCanMake() throws Exception {
        assertTrue(word.canMake(word));
        assertTrue(word.canMake("lot"));
        assertTrue(word.canMake("try"));
        assertTrue(word.canMake("let"));
        assertTrue(word.canMake(""));

        assertFalse(word.canMake("lotteries"));
        assertFalse(word.canMake("caring"));
    }
}