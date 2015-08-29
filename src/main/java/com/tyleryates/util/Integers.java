package com.tyleryates.util;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

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
     *
     * @throws IllegalArgumentException if {@code lowerBound} >= {@code upperBound}.
     */
    public static int randomInt(int lowerBound, int upperBound) {
        return randomInt(lowerBound, upperBound, new Random());
    }

    /**
     * Returns a random integer within the given range {@code [lowerBound, upperBound]} using the given {@link Random}
     * instance.
     *
     * @param lowerBound the lower bound of the given range (inclusive)
     * @param upperBound the upper bound of the given range (inclusive)
     * @param random the given {@link Random} instance
     *
     * @return a random integer withing the given range
     *
     * @throws IllegalArgumentException if {@code lowerBound} >= {@code upperBound} or {@code random} is {@code null}.
     */
    public static int randomInt(int lowerBound, int upperBound, Random random) {
        checkArgument(lowerBound < upperBound);
        checkArgument(random != null);

        return random.nextInt(upperBound + 1 - lowerBound) + lowerBound;
    }
}
