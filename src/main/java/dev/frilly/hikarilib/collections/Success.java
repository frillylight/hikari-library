package dev.frilly.hikarilib.collections;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a successful attempt at retrieving a value.
 *
 * @param <T> the type of the value
 */
@RequiredArgsConstructor(staticName = "of")
public final class Success<T> implements Attempt<T> {

    private final T value;

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public Throwable getException() {
        return null;
    }

    @Override
    public @NonNull Property<T> toProperty() {
        return Property.of(value);
    }
    
}
