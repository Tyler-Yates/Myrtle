package com.tyleryates.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("JavaDoc")
public class WordTest {
    private Word word;

    @Before
    public void setUp() throws Exception {
        word = new Word("lottery");
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