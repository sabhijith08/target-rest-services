package com.redsky.client.exception;

public class ProductNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private int code;

    public ProductNotFoundException(final String message) {
        super(message);
    }

    public ProductNotFoundException(final String message, final int code) {
        super(message);
    }

    public int getCode() {
        return code;
    }
}
