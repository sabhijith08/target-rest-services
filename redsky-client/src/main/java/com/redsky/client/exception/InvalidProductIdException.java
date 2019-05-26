package com.redsky.client.exception;

public class InvalidProductIdException extends Exception {

    private static final long serialVersionUID = 1L;

    private int code;

    public InvalidProductIdException(final String message) {
        super(message);
    }

    public InvalidProductIdException(final String message, final int code) {
        super(message);
    }

    public int getCode() {
        return code;
    }

}
