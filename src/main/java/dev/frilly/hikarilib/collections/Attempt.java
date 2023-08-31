package dev.frilly.hikarilib.collections;

import org.jetbrains.annotations.Nullable;
import dev.frilly.hikarilib.exceptions.FailedAccessException;

/**
 * Represents a generic attempt at getting a value.
 *
 * @param <T> the type of the value
 */
public interface Attempt<T> {

    /**
     * Checks whether the attempt was successful.
     *
     * @return true if successful
     */
    boolean isSuccess();

    /**
     * Checks whether the attempt was a failure.
     *
     * @return true if failed
     */
    boolean isFailure();

    /**
     * Gets the value if successful. If the attempt failed, but the value is <bold>not</bold> null, this will
     * return that value. Else, it will throw a {@link FailedAccessException}.
     *
     * @return the value
     */
    T getValue();

    /**
     * Gets the exception if failed, otherwise null.
     *
     * @return the exception
     */
    @Nullable
    Throwable getException();

}
