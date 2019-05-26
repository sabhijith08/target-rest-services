package com.redsky.client.validator;

import com.redsky.client.exception.ResourceValidationException;

public interface RequestValidator<B> {

    boolean validate(B bean) throws ResourceValidationException;
}
