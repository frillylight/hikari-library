package dev.frilly.hikarilib.collections;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A reference to an object that is lazily initialized, usually
 * something that is expensive to create.
 */
@RequiredArgsConstructor(staticName = "of")
public final class LazyReference<T> {

    private final @NonNull Supplier<T> supplier;

    private T value = null;
    private boolean computed = false;

    /**
     * Creates a new {@link LazyReference} with the given value
     *
     * @param value The value to initialize the reference with.
     * @param <T>   The type of the value.
     * @return The new {@link LazyReference}.
     */
    public static <T> LazyReference<T> of(@Nullable T value) {
        return new LazyReference<>(() -> value);
    }

    /**
     * Gets the value of this reference, initializing it if it has not
     * already been initialized.
     *
     * @return The value of this reference.
     */
    public T get() {
        if (!computed) {
            value = supplier.get();
            computed = true;
        }
        return value;
    }

}
