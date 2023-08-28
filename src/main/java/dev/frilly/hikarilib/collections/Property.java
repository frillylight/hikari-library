package dev.frilly.hikarilib.collections;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a read-only field.
 */
public class Property<T> {

    private static final Property<?> EMPTY = new Property<>(null);
    protected T value;

    Property(@Nullable T value) {
        this.value = value;
    }

    /**
     * Retrieves an empty read-only field.
     *
     * @param <T> The type of the field.
     * @return An empty read-only field.
     */
    @SuppressWarnings("unchecked")
    public static <T> Property<T> empty() {
        return (Property<T>) EMPTY;
    }

    /**
     * Retrieves a read-only field with the given value.
     *
     * @param value The value of the field.
     * @param <T>   The type of the field.
     * @return A read-only field with the given value.
     */
    @SuppressWarnings("unchecked")
    public static <T> Property<T> of(@Nullable T value) {
        return value == null ? (Property<T>) EMPTY : new Property<>(value);
    }

    /**
     * Checks if the value of this field is present.
     *
     * @return {@code true} if the value is present, {@code false} otherwise.
     */
    public final boolean isPresent() {
        return value != null;
    }

    /**
     * Checks if the value of this field is not present.
     *
     * @return {@code true} if the value is not present, {@code false} otherwise.
     */
    public final boolean isEmpty() {
        return value == null;
    }

    /**
     * Gets the value of this field. Will throw a {@link NullPointerException} if the value is not present.
     * For nullability, refer to {@link Property#getOrNull()}.
     *
     * @return The value of this field.
     */
    @NonNull
    public final T get() {
        return Objects.requireNonNull(value, "Property value is not present");
    }

    /**
     * Gets the value of this field, or null if the value is not present.
     *
     * @return The value of this field, or null if the value is not present.
     */
    @Nullable
    public final T getOrNull() {
        return value;
    }

    /**
     * If the value of this field is present, run the given consumer with the value.
     *
     * @param consumer The consumer to run.
     */
    public final void ifPresent(final @NonNull Consumer<T> consumer) {
        if (isPresent()) {
            consumer.accept(get());
        }
    }

    /**
     * If the value of this field is not present, transform the value of this field using the given mapper.
     *
     * @param mapper The mapper to use.
     * @param <R>    The type of the mapped value.
     * @return A new {@link Property} with the mapped value, or an empty {@link Property} if the value is not present.
     */
    public final <R> Property<R> map(final @NonNull Function<T, R> mapper) {
        return isPresent() ? Property.of(mapper.apply(get())) : Property.empty();
    }

    /**
     * Creates a new copy of this field, but now mutable.
     *
     * @return A new {@link MutableProperty} with the same value as this field.
     */
    public final MutableProperty<T> toMutable() {
        return new MutableProperty<>(value);
    }

    @Override
    public final String toString() {
        return isPresent() ? value.toString() : "null";
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Property) {
            Property<?> other = (Property<?>) obj;
            return Objects.equals(value, other.value);
        }
        return Objects.equals(value, obj);
    }

    @Override
    public final int hashCode() {
        return value.hashCode();
    }


}
