package dev.frilly.hikarilib.misc;

import dev.frilly.hikarilib.collections.Pair;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.val;
import lombok.var;
import org.bukkit.ChatColor;

import java.util.Objects;

/**
 * Utility class for String operations.
 */
@UtilityClass
public final class Strings {

    /**
     * Colorizes a string.
     *
     * @param s the string
     * @return the colorized string
     */
    @NonNull
    public String color(final @NonNull String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

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

    /**
     * Replaces all occurrences of %value% with the corresponding value.
     * <p>
     * For example, a Pair containing "name" and "John" will replace all occurrences of %name% with John.
     *
     * @param s            the string
     * @param replacements the replacements
     * @return the string with the replacements
     */
    @NonNull
    public String replacePlaceholders(final @NonNull String s, final @NonNull Pair<?, ?>... replacements) {
        var result = s;
        for (val pair : replacements)
            result = result.replace("%" + String.valueOf(pair.getFirst()) + "%", Objects.toString(pair.getSecond()));
        return result;
    }

    /**
     * Replaces all occurrences of %value% with the corresponding value.
     *
     * @param s            the string
     * @param replacements the replacements
     * @return the string with the replacements
     */
    @NonNull
    public String replacePlaceholders(final @NonNull String s, final Object... replacements) {
        if(replacements == null || replacements.length == 0)
            return s;
        if (replacements.length % 2 != 0)
            throw new IllegalArgumentException("replacements must be a multiple of 2");
        val array = new Pair<?, ?>[replacements.length / 2];
        for (int i = 0; i < replacements.length; i += 2)
            array[i / 2] = Pair.of(replacements[i], replacements[i + 1]);
        return replacePlaceholders(s, array);
    }

}
