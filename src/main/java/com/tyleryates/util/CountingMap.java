package com.tyleryates.util;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.Set;

/**
 * A collection that maps keys to the number of occurrences of those keys.
 *
 * @param <T> the type of key used by the map
 */
public interface CountingMap<T> {

    /**
     * Increments the occurrence count for the given key by one. If the key is not present in the map yet, it will be
     * put in the map with an occurrence count of one.
     *
     * @param key the key to increment the occurrence count
     *
     * @return the new occurrence count for the given key
     */
    int incrementCount(T key);

    /**
     * Increments the occurrence count for the given key by the given number. If the key is not present in the map yet,
     * it will be put in the map with an occurrence count equal to the given number.
     *
     * @param key the key to increment the occurrence count
     * @param countsToAdd the number to add to the occurrence count
     *
     * @return the new occurrence count for the given key
     *
     * @throws IllegalArgumentException if {@code countsToAdd} is negative
     */
    int incrementCount(T key, int countsToAdd) throws IllegalArgumentException;

    /**
     * Increments the occurrence count of each key in the given iterable by the number of times each key appears in the
     * iterable.
     * <p/>
     * For instance, if the map contains the following keys and occurrence counts:<br/>["bob" = 2, "sally" = 1]
     * <p/>
     * And the given iterable contains the following values:<br/>["bob, "joe", "bob"]
     * <p/>
     * Then after the invocation of this method the map will have the following keys and occurrence counts:<br/> ["bob"
     * = 4, "sally" = 1, "joe" = 1]
     *
     * @param keys the keys to increment the occurrence counts
     */
    void incrementCounts(Iterable<T> keys);

    /**
     * Merges the keys and occurrence counts from the given map into the current map.
     *
     * @param otherCounts the given map
     */
    void mergeCounts(CountingMap<T> otherCounts);

    /**
     * Returns the occurrence count for the given key. If the given key is not contained in the map, {@code 0} will be
     * returned as the occurrence count.
     *
     * @param key the given key
     *
     * @return the occurrence count for the given key or {@code 0} if the key is not in the map.
     */
    int getCount(T key);

    /**
     * Returns an immutable map with the keys and occurrence counts from the current map.
     *
     * @return a map from keys to occurrence counts
     */
    Map<T, Integer> getCounts();

    /**
     * Returns a key with the highest count if one exists. In case of ties, no guarantee is made as to which key will be
     * returned.
     *
     * @return a key with the highest count or {@link Optional#absent()} if the map is empty
     */
    Optional<T> getKeyWithHighestCount();

    /**
     * Returns the keys with the highest counts if they exist. No order is guaranteed for the keys.
     *
     * @return the keys with the highest count or an empty set if the map is empty
     */
    Set<T> getKeysWithHighestCount();

    /**
     * Returns the key with the lowest count if one exists. The returned key will have a count greater than zero. In
     * case of ties, no guarantee is made as to which key will be returned.
     *
     * @return a key with the lowest count or {@link Optional#absent()} if the map is empty
     */
    Optional<T> getKeyWithLowestCount();

    /**
     * Returns the keys with the lowest count if they exist.
     *
     * @return the keys with the lowest count or an empty set if the map is empty
     */
    Set<T> getKeysWithLowestCount();

    /**
     * Returns whether the given {@link CountingMap} is subsumed by the current {@link CountingMap}. By definition, map
     * {@code A} subsumes map {@code B} if and only if every key in {@code B} is also in {@code A} and the count for
     * every key in {@code B} is less than or equal to the corresponding count for that key in {@code A}. An empty map
     * is subsumed by any other map.
     *
     * @param other the other {@code CountingMap}
     *
     * @return whether the current map subsumes the given map.
     */
    boolean subsumes(CountingMap<T> other);

    /**
     * Returns the number of keys in the map.
     *
     * @return the number of keys
     */
    int size();

    /**
     * Returns whether the current map has any keys.
     *
     * @return {@code true} if the current map has at least one key, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Returns whether the given key is contained in the current map. This is equivalent to {@code getCount(key) > 0}.
     *
     * @param key the given key
     *
     * @return whether the given key is contained in the current map.
     */
    boolean containsKey(T key);
}
