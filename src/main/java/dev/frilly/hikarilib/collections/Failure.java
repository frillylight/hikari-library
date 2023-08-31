package dev.frilly.hikarilib.collections;

import dev.frilly.hikarilib.exceptions.FailedAccessException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a failed attempt to get a value.
 *
 * @param <T> the type of the value, that should have been here
 */
@RequiredArgsConstructor(staticName = "of")
public final class Failure<T> implements Attempt<T> {

    private final T value;
    private final Throwable throwable;

    /**
     * Creates a new failure with the provided exception.
     *
     * @param exception the exception
     * @return the failure
     */
    @NonNull
    public static <T> Failure<T> of(@NonNull final Throwable exception) {
        return new Failure<>(null, exception);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public T getValue() {
        if (value == null)
            throw new FailedAccessException();
        return value;
    }

    @Override
    public Throwable getException() {
        return throwable;
    }

}
