package dev.frilly.hikarilib.properties;

import dev.frilly.hikarilib.collections.Property;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.val;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles a .properties file.
 */
public final class PropertiesFile {

    private final File file;
    private final Map<String, PropertyKey> values = new LinkedHashMap<>();

    /**
     * Constructs a new PropertiesFile.
     *
     * @param file the file
     */
    public PropertiesFile(@NonNull File file) {
        this.file = file;
        reload();
    }

    /**
     * Constructs a new PropertiesFile.
     *
     * @param name the name of the file
     */
    public PropertiesFile(@NonNull String name) {
        this(new File(name));
    }

    /**
     * Checks if the file actually exists on disk.
     *
     * @return true if it exists
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * Creates the file on disk.
     *
     * @return true if the file was created, false if already existed
     */
    @SneakyThrows
    public boolean create() {
        return file.createNewFile();
    }

    /**
     * Adds a key.
     *
     * @param key      the key
     * @param value    the value
     * @param comments the comments
     */
    public void addKey(final @NonNull String key, final @NonNull String value, final @NonNull List<String> comments) {
        addKey(new PropertyKey(key, value, comments));
    }

    /**
     * Adds a specific key with its value and comments to the file.
     *
     * @param key the key
     */
    public void addKey(final @NonNull PropertyKey key) {
        values.put(key.key, key);
    }

    /**
     * Gets a key.
     *
     * @param key the key
     * @return the key wrapped in a property if exists, an empty property otherwise.
     */
    public Property<PropertyKey> get(final @NonNull String key) {
        return Property.of(values.get(key));
    }

    /**
     * Gets the key without wrapping, throwing a {@link NullPointerException} if it does not exist.
     *
     * @param key the key
     * @return the key
     */
    @NonNull
    public PropertyKey getRaw(final @NonNull String key) {
        return get(key).get();
    }

    /**
     * Reloads from disk and ignores everything that was changed.
     */
    public void reload() {
        values.clear();
        val list = PropertiesReader.read(file);
        list.forEach(this::addKey);
    }

    /**
     * Saves all data to the disk, and ignores everything that was changed
     * directly to the file.
     */
    public void save() {
        PropertiesWriter.write(file, new ArrayList<>(values.values()));
    }

}
