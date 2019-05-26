package com.redsky.client.exception;

import java.util.LinkedHashSet;
import java.util.Set;

public class ResourceValidationException extends Exception {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    private Set<String> violations = new LinkedHashSet<>();

    private int code;

    /**
     * @param message
     * @param messages
     */
    public ResourceValidationException(final String message, final Set<String> violations) {
        super(message);
        this.violations = violations;
    }

    public ResourceValidationException(final String message, final int code) {
        super(message);
        this.code = code;
    }

    public ResourceValidationException(final String message) {
        super(message);

    }

    public int getCode() {
        return code;
    }

    /**
     * @param message
     * @param cause
     */
    public ResourceValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public ResourceValidationException(final Throwable cause) {
        super(cause);
    }

    /**
     * @return the messages
     */
    public Set<String> getViolations() {
        return violations;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setViolations(final Set<String> messages) {
        violations = messages;
    }
}
