package com.redsky.client.exception;

public class PriceNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    private int code;

    public PriceNotFoundException(final String message) {
        super(message);
    }

    public PriceNotFoundException(final String message, final int code) {
        super(message);
    }

    public int getCode() {
        return code;
    }

}
