package dev.frilly.hikarilib.misc;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Utility class for String operations.
 */
@UtilityClass
public final class Strings {

    /**
     * Removes the prefix from the string if it starts with it.
     *
     * @param s      the string
     * @param prefix the prefix
     * @return the string without the prefix
     */
    @NonNull
    public String removePrefix(final @NonNull String s, final @NonNull String prefix) {
        return s.startsWith(prefix) ? s.substring(prefix.length()) : s;
    }

    /**
     * Removes the suffix from the string if it ends with it.
     *
     * @param s      the string
     * @param suffix the suffix
     * @return the string without the suffix
     */
    @NonNull
    public String removeSuffix(final @NonNull String s, final @NonNull String suffix) {
        return s.endsWith(suffix) ? s.substring(0, s.length() - suffix.length()) : s;
    }

    /**
     * Removes only leading whitespaces.
     *
     * @param s the string
     * @return the string without leading whitespaces
     */
    @NonNull
    public String trimLeft(final @NonNull String s) {
        return s.replaceAll("^\\s+", "");
    }

    /**
     * Removes only trailing whitespaces.
     *
     * @param s the string
     * @return the string without trailing whitespaces
     */
    @NonNull
    public String trimRight(final @NonNull String s) {
        return s.replaceAll("\\s+$", "");
    }

}
