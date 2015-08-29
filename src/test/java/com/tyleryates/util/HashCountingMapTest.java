package com.tyleryates.util;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.jetbrains.annotations.Contract;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link HashCountingMap}.
 */
@SuppressWarnings("JavaDoc")
public class HashCountingMapTest {
    private static final String KEY = "key";
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String KEY3 = "key3";
    private static final List<String> KEYS = ImmutableList.of(KEY1, KEY2, KEY1, KEY3, KEY1);
    private static final Map<String, Integer> EXPECTED_OCCURRENCES_OF_KEYS = ImmutableMap.of(
            KEY1,
            numberOfOccurrencesOfKey(KEY1, KEYS),
            KEY2,
            numberOfOccurrencesOfKey(KEY2, KEYS),
            KEY3,
            numberOfOccurrencesOfKey(KEY3, KEYS));

    private CountingMap<String> countingMap;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        countingMap = new HashCountingMap<>();
    }

    @Test
    public void testConstructorWithIterable() {
        countingMap = new HashCountingMap<>(KEYS);
        assertEquals(EXPECTED_OCCURRENCES_OF_KEYS, countingMap.getCounts());
    }

    @Test
    public void testIncrementCount() {
        assertEquals(0, countingMap.getCount(KEY));

        for (int count = 1; count <= 100; count++) {
            countingMap.incrementCount(KEY);
            assertEquals(count, countingMap.getCount(KEY));
        }
    }

    @Test
    public void testIncrementCountWithNumber() {
        assertEquals(0, countingMap.getCount(KEY));

        final int increment = 100;
        countingMap.incrementCount(KEY, increment);
        assertEquals(increment, countingMap.getCount(KEY));

        countingMap.incrementCount(KEY, 0);
        assertEquals(increment, countingMap.getCount(KEY));
    }

    @Test
    public void testIncrementCountWithNegativeNumber() {
        exception.expect(IllegalArgumentException.class);
        countingMap.incrementCount(KEY, -1);
        exception.expect(IllegalArgumentException.class);
        countingMap.incrementCount(KEY, -100);
        exception.expect(IllegalArgumentException.class);
        countingMap.incrementCount(KEY, Integer.MIN_VALUE);
    }

    @Test
    public void testIncrementCounts() {
        assertEquals(Collections.emptyMap(), countingMap.getCounts());

        countingMap.incrementCounts(ImmutableList.<String>of());
        assertEquals(Collections.emptyMap(), countingMap.getCounts());

        countingMap.incrementCounts(KEYS);
        assertEquals(EXPECTED_OCCURRENCES_OF_KEYS, countingMap.getCounts());
    }

    @Test
    public void testMergeCountsWithEmptyMap() {
        assertEquals(Collections.emptyMap(), countingMap.getCounts());

        final CountingMap<String> otherMap = new HashCountingMap<>();
        otherMap.incrementCount(KEY1, 3);
        otherMap.incrementCount(KEY2, 12);
        otherMap.incrementCount(KEY3, 1);
        countingMap.mergeCounts(otherMap);

        assertEquals(otherMap.getCounts(), countingMap.getCounts());
    }

    @Test
    public void testMergeCountsWithFilledMap() {
        assertEquals(Collections.emptyMap(), countingMap.getCounts());
        countingMap.incrementCount(KEY1, 8);
        countingMap.incrementCount(KEY3, 17);

        final CountingMap<String> otherMap = new HashCountingMap<>();
        otherMap.incrementCount(KEY1, 3);
        otherMap.incrementCount(KEY2, 12);
        otherMap.incrementCount(KEY3, 1);

        final Map<String, Integer> expectedOccurrences = ImmutableMap.of(
                KEY1,
                getAggregateOccurrenceCount(KEY1, countingMap, otherMap),
                KEY2,
                getAggregateOccurrenceCount(KEY2, countingMap, otherMap),
                KEY3,
                getAggregateOccurrenceCount(KEY3, countingMap, otherMap));

        countingMap.mergeCounts(otherMap);
        assertEquals(expectedOccurrences, countingMap.getCounts());
    }

    @Test
    public void testSize() {
        assertEquals(0, countingMap.size());
        countingMap.incrementCount(KEY1);
        countingMap.incrementCount(KEY1, 3);
        assertEquals(1, countingMap.size());
        countingMap.incrementCount(KEY2, 2);
        assertEquals(2, countingMap.size());
    }

    @Test
    public void testContainsKey() {
        assertFalse(countingMap.containsKey(KEY1));
        assertFalse(countingMap.containsKey(KEY2));
        assertFalse(countingMap.containsKey(KEY3));

        countingMap.incrementCount(KEY1);
        assertTrue(countingMap.containsKey(KEY1));
        countingMap.incrementCount(KEY2, 3);
        assertTrue(countingMap.containsKey(KEY2));
    }

    @Test
    public void testGetKeyWithHighestCountNoKeys() {
        assertThat(countingMap.getKeyWithHighestCount()).isAbsent();
    }

    @Test
    public void testGetKeyWithHighestCountTie() {
        countingMap.incrementCount(KEY1);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeyWithHighestCount()).isAnyOf(Optional.of(KEY1), Optional.of(KEY2));
    }

    @Test
    public void testGetKeyWithHighestCount() {
        countingMap.incrementCount(KEY1, 2);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeyWithHighestCount()).hasValue(KEY1);

        countingMap.incrementCount(KEY2, 2);
        assertThat(countingMap.getKeyWithHighestCount()).hasValue(KEY2);
    }

    @Test
    public void testGetKeysWithHighestCountNoKeys() {
        assertThat(countingMap.getKeysWithHighestCount()).isEmpty();
    }

    @Test
    public void testGetKeysWithHighestCountTie() {
        countingMap.incrementCount(KEY1);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeysWithHighestCount()).containsExactly(KEY1, KEY2);

        countingMap.incrementCount(KEY3);
        assertThat(countingMap.getKeysWithHighestCount()).containsExactly(KEY1, KEY2, KEY3);
    }

    @Test
    public void testGetKeysWithHighestCount() {
        countingMap.incrementCount(KEY1, 2);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeysWithHighestCount()).containsExactly(KEY1);

        countingMap.incrementCount(KEY2, 2);
        assertThat(countingMap.getKeysWithHighestCount()).containsExactly(KEY2);
    }

    @Test
    public void testGetKeyWithLowestCountNoKeys() {
        assertThat(countingMap.getKeyWithLowestCount()).isAbsent();
    }

    @Test
    public void testGetKeyWithLowestCountTie() {
        countingMap.incrementCount(KEY1);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeyWithLowestCount()).isAnyOf(Optional.of(KEY1), Optional.of(KEY2));
    }

    @Test
    public void testGetKeyWithLowestCount() {
        countingMap.incrementCount(KEY1, 2);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeyWithLowestCount()).hasValue(KEY2);

        countingMap.incrementCount(KEY2, 2);
        assertThat(countingMap.getKeyWithLowestCount()).hasValue(KEY1);
    }

    @Test
    public void testGetKeysWithLowestCountNoKeys() {
        assertThat(countingMap.getKeysWithLowestCount()).isEmpty();
    }

    @Test
    public void testGetKeysWithLowestCountTie() {
        countingMap.incrementCount(KEY1);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeysWithLowestCount()).containsExactly(KEY1, KEY2);

        countingMap.incrementCount(KEY3);
        assertThat(countingMap.getKeysWithLowestCount()).containsExactly(KEY1, KEY2, KEY3);

        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeysWithLowestCount()).containsExactly(KEY1, KEY3);
    }

    @Test
    public void testGetKeysWithLowestCount() {
        countingMap.incrementCount(KEY1, 2);
        countingMap.incrementCount(KEY2);
        assertThat(countingMap.getKeysWithLowestCount()).containsExactly(KEY2);

        countingMap.incrementCount(KEY2, 2);
        assertThat(countingMap.getKeysWithLowestCount()).containsExactly(KEY1);
    }

    @Test
    public void testSubsumesEmpty() {
        assertTrue(countingMap.subsumes(new HashCountingMap<>()));
    }

    @Test
    public void testSubsumes() {
        final CountingMap<String> otherMap = new HashCountingMap<>();
        otherMap.incrementCount(KEY1, 2);
        assertFalse(countingMap.subsumes(otherMap));

        countingMap.incrementCount(KEY1);
        assertFalse(countingMap.subsumes(otherMap));

        countingMap.incrementCount(KEY1);
        assertTrue(countingMap.subsumes(otherMap));

        countingMap.incrementCount(KEY1);
        assertTrue(countingMap.subsumes(otherMap));

        otherMap.incrementCount(KEY2);
        assertFalse(countingMap.subsumes(otherMap));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(countingMap.isEmpty());

        countingMap.incrementCount(KEY1);
        assertFalse(countingMap.isEmpty());
    }

    private static int getAggregateOccurrenceCount(String key, CountingMap<String> map1, CountingMap<String> map2) {
        return map1.getCount(key) + map2.getCount(key);
    }

    @Contract(pure = true)
    private static int numberOfOccurrencesOfKey(String desiredKey, List<String> keys) {
        int occurrences = 0;
        for (final String key : keys) {
            if (key.equals(desiredKey)) {
                occurrences++;
            }
        }
        return occurrences;
    }
}
