package dev.frilly.hikarilib.properties;

import lombok.*;
import lombok.experimental.UtilityClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Writes .properties files.
 */
@UtilityClass
public final class PropertiesWriter {

    private String encodeKey(final @NonNull String key) {
        // Replace: "\" -> "\\", ":" -> "\:", "=" -> "\=", " " -> "\ "
        return key.replace("\\", "\\\\").replace(":", "\\:")
                .replace("=", "\\=").replace(" ", "\\ ");
    }

    private String encodeValue(final @NonNull String value) {
        return value.replace("\\", "\\\\");
    }

    private void write(final @NonNull PrintWriter writer, final @NonNull PropertyKey key) {
        // Adds an empty line between keys with comments.
        if(!key.comments.isEmpty())
            writer.println();
        
        key.comments.forEach(writer::println);
        val split = key.value.split("\n");

        // If it's a one-liner, just write it out.
        if (split.length == 1) {
            writer.printf("%s=%s\n", encodeKey(key.key), encodeValue(split[0]));
            return;
        }

        // It's a multi-line then.
        writer.printf("%s=%s \\\n", encodeKey(key.key), encodeValue(split[0])); // First line is handled separately.
        for (var i = 1; i < split.length; i++) {
            if (i == split.length - 1) // Last line is also handled separately.
                writer.printf("  %s\n", encodeValue(split[i]));
            else
                writer.printf("  %s \\\n", encodeValue(split[i]));
        }
    }

    /**
     * Writes a .properties file with the provided keys.
     *
     * @param file the file to write to
     * @param keys the keys to write
     */
    @SneakyThrows
    public void write(final @NonNull File file, final @NonNull List<PropertyKey> keys) {
        @Cleanup val writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        keys.forEach(it -> write(writer, it));
        writer.flush();
    }

}
