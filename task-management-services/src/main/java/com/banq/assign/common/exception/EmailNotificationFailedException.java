package com.banq.assign.common.exception;

public class EmailNotificationFailedException extends Throwable {
    /**
     * Constructs a new InvalidIdException with the specified detail message.
     *
     * @param emailNotificationFailed The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public EmailNotificationFailedException(String emailNotificationFailed) {
        super(emailNotificationFailed);
    }
}
