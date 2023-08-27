package dev.frilly.hikarilib.exceptions;

/**
 * An exception thrown when a late-init property is initialized more than once.
 */
public final class LateInitAlreadyInitializedException extends RuntimeException {

    /**
     * Creates a new {@link LateInitAlreadyInitializedException} exception.
     */
    public LateInitAlreadyInitializedException() {
        super("Late-init property has already been initialized");
    }

}
