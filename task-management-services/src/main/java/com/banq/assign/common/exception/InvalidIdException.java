package com.banq.assign.common.exception;
/**
 * Exception thrown when a requested id is not valid.
 * Extends {@link Throwable} to provide custom exception handling for id not valid scenarios.
 */
public class InvalidIdException extends Throwable {
    /**
     * Constructs a new InvalidIdException with the specified detail message.
     *
     * @param invalidTaskIdProvided The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidIdException(String invalidTaskIdProvided) {
        super(invalidTaskIdProvided);
    }
}
