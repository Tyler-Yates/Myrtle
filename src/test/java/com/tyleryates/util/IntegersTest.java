package com.tyleryates.util;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests {@link Integers}.
 */
@SuppressWarnings("JavaDoc")
public class IntegersTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Random MAX_RANDOM;
    private Random MIN_RANDOM;

    @Before
    public void setup() {
        MAX_RANDOM = mock(Random.class);
        when(MAX_RANDOM.nextInt(anyInt())).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (int) invocationOnMock.getArguments()[0] - 1;
            }
        });

        MIN_RANDOM = mock(Random.class);
        when(MIN_RANDOM.nextInt(anyInt())).thenReturn(0);
    }

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

    @Test
    public void testRandomIntIllegalBound() {
        exception.expect(IllegalArgumentException.class);
        Integers.randomInt(1, 0);

        exception.expect(IllegalArgumentException.class);
        Integers.randomInt(1, -1);

        exception.expect(IllegalArgumentException.class);
        Integers.randomInt(1, 1);
    }

    @Test
    public void testRandomIntNullRandom() {
        exception.expect(IllegalArgumentException.class);
        Integers.randomInt(0, 1, null);
    }

    @Test
    public void testRandomInt() {
        assertEquals(5, Integers.randomInt(0, 5, MAX_RANDOM));
        assertEquals(Integer.MAX_VALUE, Integers.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE, MAX_RANDOM));

        assertEquals(0, Integers.randomInt(0, Integer.MAX_VALUE, MIN_RANDOM));
        assertEquals(Integer.MIN_VALUE, Integers.randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE, MIN_RANDOM));
    }
}
