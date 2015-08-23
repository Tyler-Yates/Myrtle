package com.tyleryates.util;

import java.util.Random;

/**
 * Utility class that provides various helper methods for dealing with integers.
 */
public final class Integers {

    /**
     * Returns whether the given number is positive i.e. {@code n > 0}.
     *
     * @param n the given number
     *
     * @return whether the given number is positive
     */
    public static boolean isPositive(int n) {
        return n > 0;
    }

    /**
     * Returns whether the given number is negative i.e. {@code n < 0}.
     *
     * @param n the given number
     *
     * @return whether the given number is negative
     */
    public static boolean isNegative(int n) {
        return n < 0;
    }

    /**
     * Returns whether the given number is non-negative i.e. {@code n >= 0}.
     *
     * @param n the given number
     *
     * @return whether the given number is non-negative
     */
    public static boolean isNonNegative(int n) {
        return n >= 0;
    }

    /**
     * Returns whether the given number is non-positive i.e. {@code n <= 0}.
     *
     * @param n the given number
     *
     * @return whether the given number is non-positive
     */
    public static boolean isNonPositive(int n) {
        return n <= 0;
    }

    /**
     * Returns whether the given number is even.
     *
     * @param n the given number
     *
     * @return whether the given number is even
     */
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    /**
     * Returns whether the given number is odd.
     *
     * @param n the given number
     *
     * @return whether the given number is odd
     */
    public static boolean isOdd(int n) {
        return n % 2 != 0;
    }

    /**
     * Returns a random integer within the given range {@code [lowerBound, upperBound]}.
     *
     * @param lowerBound the lower bound of the given range (inclusive)
     * @param upperBound the upper bound of the given range (inclusive)
     *
     * @return a random integer withing the given range
     */
    public static int randomInt(int lowerBound, int upperBound) {
        return new Random().nextInt(upperBound + 1 - lowerBound) + lowerBound;
    }
}
