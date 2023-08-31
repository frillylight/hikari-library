package dev.frilly.hikarilib.exceptions;

import dev.frilly.hikarilib.collections.Attempt;
import dev.frilly.hikarilib.collections.Failure;

/**
 * Thrown when a {@link Failure} is accessed
 * through the {@link Attempt} interface while holding no values.
 */
public final class FailedAccessException extends RuntimeException {

    public FailedAccessException() {
        super("Attempted to access a failed attempt.");
    }

}
