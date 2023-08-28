package dev.frilly.hikarilib.collections;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a mutable field.
 *
 * @param <T> The type of the field.
 */
public final class MutableProperty<T> extends Property<T> {

    MutableProperty(@Nullable T value) {
        super(value);
    }

    /**
     * Creates a new {@link MutableProperty} with the given value.
     *
     * @param value The value of the field.
     * @param <T>   The type of the field.
     * @return The new {@link MutableProperty}.
     */
    public static <T> MutableProperty<T> of(@Nullable T value) {
        return new MutableProperty<>(value);
    }

    /**
     * Creates a new empty {@link MutableProperty}.
     *
     * @param <T> The type of the field.
     * @return The new {@link MutableProperty}.
     */
    public static <T> MutableProperty<T> empty() {
        return new MutableProperty<>(null);
    }

    /**
     * Mutates the value of this field.
     *
     * @param value The new value of this field.
     */
    public void set(@Nullable T value) {
        this.value = value;
    }

    /**
     * Converts this mutable field to an immutable field.
     *
     * @return An immutable field with the same value as this field.
     */
    public Property<T> toImmutable() {
        return Property.of(value);
    }

    /**
     * Mutates the value of this field and returns itself.
     *
     * @param value The new value of this field.
     * @return This field.
     */
    @NonNull
    public MutableProperty<T> with(@Nullable T value) {
        set(value);
        return this;
    }

}
