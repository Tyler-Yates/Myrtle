package com.tyleryates.util;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Implements {@link CountingMap} using a hash data structure.
 * <p/>
 * This class has constant-time performance for most operations including {@link #incrementCount(Object)}, {@link
 * #incrementCount(Object, int)}, {@link #containsKey(Object)}, {@link #getCount(Object)}, {@link
 * #getKeyWithHighestCount()}, {@link #getKeysWithHighestCount()}, and {@link #size()}. The {@link
 * #getKeyWithLowestCount()} and {@link #getKeysWithLowestCount()} operations are not guaranteed to have constant-time
 * performance as they may require a full scan of the map.
 *
 * @param <T> the type of key used by the map
 */
public class HashCountingMap<T> implements CountingMap<T> {

    private final Map<T, Integer> occurrences;
    private final Set<T> keysWithHighestCount = new HashSet<>();
    private final Set<T> keysWithLowestCount = new HashSet<>();
    private int highestCount = 0;
    private int lowestCount = Integer.MAX_VALUE;

    /**
     * Creates an empty counting map.
     */
    public HashCountingMap() {
        occurrences = new HashMap<>();
    }

    /**
     * Creates a counting map with initial occurrence counts based on the given iterable.
     *
     * @param keys the given iterable
     */
    public HashCountingMap(Iterable<T> keys) {
        occurrences = new HashMap<>();
        incrementCounts(keys);
    }

    @Override
    public int incrementCount(T key) {
        return incrementCount(key, 1);
    }

    @Override
    public int incrementCount(T key, int countsToAdd) throws IllegalArgumentException {
        checkArgument(countsToAdd >= 0);

        final Integer currentCount = occurrences.get(key);
        final int newCount;
        if (currentCount == null) {
            newCount = countsToAdd;
        } else {
            newCount = currentCount + countsToAdd;
        }
        occurrences.put(key, newCount);

        if (newCount > highestCount) {
            highestCount = newCount;
            keysWithHighestCount.clear();
            keysWithHighestCount.add(key);
        } else if (newCount == highestCount) {
            keysWithHighestCount.add(key);
        }

        if (newCount < lowestCount) {
            lowestCount = newCount;
            keysWithLowestCount.clear();
            keysWithLowestCount.add(key);
        } else if (newCount > lowestCount) {
            // We need to remove keys that no longer belong to the set of lowest-count keys
            keysWithLowestCount.remove(key);
        } else {
            keysWithLowestCount.add(key);
        }

        return newCount;
    }

    @Override
    public void incrementCounts(Iterable<T> keys) {
        for (final T key : keys) {
            incrementCount(key);
        }
    }

    @Override
    public void mergeCounts(CountingMap<T> otherCounts) {
        for (final Map.Entry<T, Integer> entry : otherCounts.getCounts().entrySet()) {
            incrementCount(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public int getCount(T key) {
        final Integer occurrenceCount = occurrences.get(key);
        if (occurrenceCount == null) {
            return 0;
        }
        return occurrenceCount;
    }

    @Override
    public Map<T, Integer> getCounts() {
        return ImmutableMap.copyOf(occurrences);
    }

    @Override
    public Optional<T> getKeyWithHighestCount() {
        if (keysWithHighestCount.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(keysWithHighestCount.iterator().next());
    }

    @Override
    public Set<T> getKeysWithHighestCount() {
        return ImmutableSet.copyOf(keysWithHighestCount);
    }

    @Override
    public Optional<T> getKeyWithLowestCount() {
        if (keysWithLowestCount.isEmpty()) {
            if (occurrences.isEmpty()) {
                return Optional.absent();
            } else {
                // If the set of lowest-count keys is empty but the map is not, we need to rebuild the lowest-count set
                updateLowestCount();
            }
        }
        return Optional.of(keysWithLowestCount.iterator().next());
    }

    @Override
    public Set<T> getKeysWithLowestCount() {
        if (keysWithLowestCount.isEmpty() && !occurrences.isEmpty()) {
            // If the set of lowest-count keys is empty but the map is not, we need to rebuild the lowest-count set
            updateLowestCount();
        }
        return ImmutableSet.copyOf(keysWithLowestCount);
    }

    /**
     * Updates the lowest count for the map.
     */
    private void updateLowestCount() {
        lowestCount = Integer.MAX_VALUE;
        for (final Map.Entry<T, Integer> keyAndCount : occurrences.entrySet()) {
            final int count = keyAndCount.getValue();
            final T key = keyAndCount.getKey();
            if (count < lowestCount) {
                lowestCount = count;
                keysWithLowestCount.clear();
                keysWithLowestCount.add(key);
            } else if (count == lowestCount) {
                keysWithLowestCount.add(key);
            }
        }
    }

    @Override
    public boolean subsumes(CountingMap<T> other) {
        for (final Map.Entry<T, Integer> entry : other.getCounts().entrySet()) {
            if (entry.getValue() > getCount(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return occurrences.size();
    }

    @Override
    public boolean isEmpty() {
        return occurrences.size() == 0;
    }

    @Override
    public boolean containsKey(T key) {
        return occurrences.containsKey(key);
    }
}
