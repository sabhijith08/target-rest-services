package com.redsky.client.exception;

public class JsonParseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public JsonParseException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public JsonParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
