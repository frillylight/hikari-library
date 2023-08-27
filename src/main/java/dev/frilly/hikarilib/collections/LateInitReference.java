package dev.frilly.hikarilib.collections;

import dev.frilly.hikarilib.exceptions.LateInitAccessException;
import dev.frilly.hikarilib.exceptions.LateInitAlreadyInitializedException;
import lombok.NonNull;

/**
 * A reference to an object that is initialized late, and acts effectively final
 * once initialized.
 *
 * @param <T> The type of the value.
 */
public final class LateInitReference<T> {

    private T value = null;
    private boolean computed = false;

    /**
     * Creates a new {@link LateInitReference} with no value.
     *
     * @param <T> The type of the value.
     * @return The new {@link LateInitReference}.
     */
    @NonNull
    public static <T> LateInitReference<T> empty() {
        return new LateInitReference<>();
    }

    /**
     * Gets the late init value, throwing an exception if it has not been initialized.
     *
     * @return The value.
     */
    @NonNull
    public T get() {
        if (!computed)
            throw new LateInitAccessException();
        return value;
    }

    /**
     * Sets the late init value, throwing an exception if it has already been initialized.
     *
     * @param value The value to set.
     */
    public void set(@NonNull T value) {
        if (computed)
            throw new LateInitAlreadyInitializedException();
        this.value = value;
        this.computed = true;
    }

}
