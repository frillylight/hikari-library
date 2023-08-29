package dev.frilly.hikarilib.misc;

import lombok.var;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@FunctionalInterface
public interface Recurse<T> {

    /**
     * Creates a finished recurse object. Once {@link Recurse#get()} hits this point, the "recursion"
     * is done.
     *
     * @param obj The value to return.
     * @param <T> The type of the value.
     * @return The finished recurse object.
     */
    static <T> Recurse<T> finish(@Nullable T obj) {
        return new Recurse<T>() {
            final T value = obj;

            @Override
            public boolean isCompleted() {
                return true;
            }

            @Override
            public T get() {
                return value;
            }

            @Override
            public Recurse<T> next() {
                return null;
            }
        };
    }

    /**
     * Extends the recursion to another call.
     *
     * @param supplier The supplier to get the next recurse object.
     * @param <T>      The type of the value.
     * @return The next recurse object.
     */
    static <T> Recurse<T> suspend(Supplier<Recurse<T>> supplier) {
        return supplier::get;
    }

    /**
     * Gets the next runner in the recursion.
     *
     * @return The next value.
     */
    Recurse<T> next();

    default boolean isCompleted() {
        return false;
    }

    /**
     * Gets the result of the recursion. If the recursion is not done, this will throw an error.
     * <p>
     * See {@link Recurse#isCompleted()} and {@link Recurse#get()}.
     *
     * @return The result of the recursion.
     */
    @Deprecated
    default T getResult() {
        throw new UnsupportedOperationException("Recursion is not done yet!");
    }

    /**
     * Gets the value evaluated after the recursion is finished.
     *
     * @return The value.
     */
    default T get() {
        var cur = this;
        while (!cur.isCompleted()) {
            cur = cur.next();
        }
        return cur.get();
    }

}
