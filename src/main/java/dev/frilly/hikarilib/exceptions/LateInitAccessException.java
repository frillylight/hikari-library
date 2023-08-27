package dev.frilly.hikarilib.exceptions;

/**
 * An exception thrown when a late-init property is accessed before it is initialized.
 */
public final class LateInitAccessException extends RuntimeException {
    
    /**
     * Creates a new {@link LateInitAccessException} exception.
     */
    public LateInitAccessException() {
        super("Late-init property has not been initialized");
    }
    
}
