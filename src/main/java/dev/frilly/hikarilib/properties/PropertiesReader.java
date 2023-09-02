package dev.frilly.hikarilib.properties;

import com.google.common.collect.Sets;
import dev.frilly.hikarilib.collections.Attempt;
import dev.frilly.hikarilib.collections.MutableProperty;
import dev.frilly.hikarilib.misc.Strings;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import lombok.val;
import lombok.var;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Reads in .properties file.
 */
@ExtensionMethod({Strings.class})
@UtilityClass
public final class PropertiesReader {

    private final Set<String> commentStarters = Sets.newHashSet("!", "#");

    /**
     * Checks if the provided line is a comment, by checking if it starts with any of the comment starters.
     *
     * @param line the line to check
     * @return true if it is a comment
     */
    private boolean isComment(final @NonNull String line) {
        return commentStarters.stream().anyMatch(line::startsWith);
    }

    private String sanitizeKey(final @NonNull String key) {
        return key.replace("\\\\", "\\").replace("\\:", ":")
                .replace("\\=", "=").replace("\\ ", " ");
    }

    /**
     * It has to be guaranteed that the provided line is NOT a comment.
     *
     * @param line the line to check
     * @return true if it has an odd number of trailing backslashes.
     */
    private boolean hasOddTrailingBackslashes(final @NonNull String line) {
        var backslashCount = 0;
        for (var i = line.length() - 1; i >= 0; i--) {
            if (line.charAt(i) == '\\')
                backslashCount++;
            else
                break;
        }
        return backslashCount % 2 == 1;
    }

    /**
     * Looks for the first considered delimiter in the provided line.
     * <p>
     * A delimiter can be a colon, an equals sign, or simply a space.
     *
     * @param line the line to check
     * @return the index of the first delimiter, or -1 if none was found
     */
    private int findFirstDelimiter(final @NonNull String line) {
        val delimiters = Sets.newHashSet('=', ':');
        var isEscaped = false;

        for (var i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case ' ':
                    if (isEscaped) {
                        isEscaped = false;
                        continue;
                    }
                    // Found the delimiter.
                    if (i + 1 < line.length() && delimiters.contains(line.charAt(i + 1)))
                        return i + 1;
                    if (i + 1 >= line.length() || !delimiters.contains(line.charAt(i + 1)))
                        return i; // The delimiter is now the space.
                    break;
                case ':':
                case '=':
                    if (!isEscaped)
                        return i;
                    else isEscaped = false;
                    break;
                case '\\':
                    isEscaped = !isEscaped;
                    continue;
            }
        }
        return -1;
    }

    private void handleSingleLine(final @NonNull String line, final @NonNull MutableProperty<String> key,
                                  final @NonNull MutableProperty<String> value) {
        val firstDelim = findFirstDelimiter(line);
        key.set(sanitizeKey(line.substring(0, firstDelim).removeSuffix(" ")));
        value.set(line.substring(firstDelim + 1).replace("\\\\", "\\").removePrefix(" "));
    }

    /**
     * Reads in a list of properties key from the provided file.
     * <p>
     * This always returns an instance of {@link Attempt}.
     * <p>
     * If the file does not exist, or it is not a .properties file, it will yield a "successful" attempt
     * with an empty list as the value.
     * <p>
     * If there are errors and problems in reading and parsing the file, it will yield a "failed" attempt.
     *
     * @param file the file to read from
     * @return the list of keys
     */
    @NonNull
    @SneakyThrows
    public List<PropertyKey> read(final @NonNull File file) {
        val keys = new ArrayList<PropertyKey>();
        if (!file.exists() || !file.getName().endsWith(".properties"))
            return keys;

        // Read in UTF-8 because I want other languages too.
        val lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        var i = 0;

        while (i < lines.size()) {
            if (lines.get(i).isEmpty()) {
                i++;
                continue;
            }

            val comments = new ArrayList<String>();
            val key = MutableProperty.of("");
            val value = MutableProperty.of("");

            // Collect all comments at once.
            while (isComment(lines.get(i))) {
                comments.add(lines.get(i));
                i++;
            }

            // Next line is a key-value pair. If there are an even number of \, it's a single line value.
            if (!hasOddTrailingBackslashes(lines.get(i))) {
                handleSingleLine(lines.get(i), key, value);
                keys.add(new PropertyKey(key.get(), value.get(), comments));
                i++;
                continue;
            }

            // Otherwise, it's a multi-line value.
            val firstDelim = findFirstDelimiter(lines.get(i));
            val joiner = new StringJoiner("\n");
            key.set(sanitizeKey(lines.get(i).substring(0, firstDelim).removeSuffix(" ")));
            joiner.add(lines.get(i).substring(firstDelim + 1).replace("\\\\", "\\").removeSuffix("\\").removePrefix(" "));

            // Handle next lines
            i++;
            while (hasOddTrailingBackslashes(lines.get(i)))
                // Remove margins.
                joiner.add(lines.get(i++).replace("\\\\", "\\").removeSuffix("\\").trimLeft());

            // Last line has even trailing backslashes, but not counted yet.
            joiner.add(lines.get(i++).replace("\\\\", "\\").trimLeft());
            value.set(joiner.toString());

            // Add back.
            keys.add(new PropertyKey(key.get(), value.get(), comments));
        }

        return keys;
    }

}
