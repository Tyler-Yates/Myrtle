package com.tyleryates.util;

/**
 * Provides efficient methods for performing complex string processing operations.
 * <p/>
 * This class is able to handle strings with whitespace characters.
 */
public class Word {
    private final String string;
    private final CountingMap<Character> characterCount;

    /**
     * Creates a new word from the given string.
     *
     * @param string the given string
     */
    public Word(String string) {
        this.string = string;
        this.characterCount = constructCharacterCount(string);
    }

    private static CountingMap<Character> constructCharacterCount(String string) {
        final CountingMap<Character> characterCount = new HashCountingMap<>();
        for (final char ch : string.toCharArray()) {
            characterCount.incrementCount(ch);
        }
        return characterCount;
    }

    /**
     * Returns the string that the current word was created from.
     *
     * @return the string the current word was created from
     */
    public String getString() {
        return string;
    }

    /**
     * Returns whether the given string can be made using the characters in the current word.
     * <p/>
     * For example, "lot" can be made from "lottery" whereas "car" cannot be made from "can". The empty string can be
     * made by any word.
     *
     * @param string the given string
     *
     * @return whether the given string can be made from the characters in the current word
     */
    public boolean canMake(String string) {
        return canMake(new Word(string));
    }

    /**
     * See {@link #canMake(String)}.
     *
     * @param otherWord the given word
     *
     * @return whether the given string can be made from the characters in the current word
     */
    public boolean canMake(Word otherWord) {
        return characterCount.subsumes(otherWord.characterCount);
    }
}
