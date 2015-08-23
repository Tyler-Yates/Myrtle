package com.tyleryates.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link Integers}.
 */
@SuppressWarnings("JavaDoc")
public class IntegersTest {

    @Test
    public void testIsPositive() {
        assertTrue(Integers.isPositive(Integer.MAX_VALUE));
        assertTrue(Integers.isPositive(1));
        assertFalse(Integers.isPositive(0));
        assertFalse(Integers.isPositive(-1));
        assertFalse(Integers.isPositive(Integer.MIN_VALUE));
    }

    @Test
    public void testIsNegative() {
        assertFalse(Integers.isNegative(Integer.MAX_VALUE));
        assertFalse(Integers.isNegative(1));
        assertFalse(Integers.isNegative(0));
        assertTrue(Integers.isNegative(-1));
        assertTrue(Integers.isNegative(Integer.MIN_VALUE));
    }

    @Test
    public void testIsNonNegative() {
        assertTrue(Integers.isNonNegative(Integer.MAX_VALUE));
        assertTrue(Integers.isNonNegative(1));
        assertTrue(Integers.isNonNegative(0));
        assertFalse(Integers.isNonNegative(-1));
        assertFalse(Integers.isNonNegative(Integer.MIN_VALUE));
    }

    @Test
    public void testIsNonPositive() {
        assertFalse(Integers.isNonPositive(Integer.MAX_VALUE));
        assertFalse(Integers.isNonPositive(1));
        assertTrue(Integers.isNonPositive(0));
        assertTrue(Integers.isNonPositive(-1));
        assertTrue(Integers.isNonPositive(Integer.MIN_VALUE));
    }

    @Test
    public void testIsEven() {
        assertTrue(Integers.isEven(2_000_000_000));
        assertTrue(Integers.isEven(2));
        assertTrue(Integers.isEven(0));
        assertTrue(Integers.isEven(-2));
        assertTrue(Integers.isEven(-2_000_000_000));

        assertFalse(Integers.isEven(2_000_000_001));
        assertFalse(Integers.isEven(1));
        assertFalse(Integers.isEven(-1));
        assertFalse(Integers.isEven(-2_000_000_001));
    }

    @Test
    public void testIsOdd() {
        assertFalse(Integers.isOdd(2_000_000_000));
        assertFalse(Integers.isOdd(2));
        assertFalse(Integers.isOdd(0));
        assertFalse(Integers.isOdd(-2));
        assertFalse(Integers.isOdd(-2_000_000_000));

        assertTrue(Integers.isOdd(2_000_000_001));
        assertTrue(Integers.isOdd(1));
        assertTrue(Integers.isOdd(-1));
        assertTrue(Integers.isOdd(-2_000_000_001));
    }
}
