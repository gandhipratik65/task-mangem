package com.banq.assign.common.exception;
/**
 * Exception thrown when a requested resource is not found.
 * Extends {@link Throwable} to provide custom exception handling for resource not found scenarios.
 */
public class ResourceNotFoundException extends Throwable {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
