package com.redsky.client.validator;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Component;

import com.redsky.client.exception.ResourceValidationException;

@Component
public class BeanValidator {

    private ValidatorFactory factory;
    private Validator validator;

    @PostConstruct
    public void init() throws ResourceValidationException {
        try {
            factory = Validation.buildDefaultValidatorFactory();
        } catch (final ValidationException e) {
            throw new ResourceValidationException("Couldn't build ValidatorFactory ", e);
        }
        validator = factory.getValidator();
    }

    public boolean validate(final Object bean) throws ResourceValidationException {

        final Set<String> messages = new LinkedHashSet<>();

        if (bean == null) {
            messages.add("Bean is null, can't validate bean");
        } else {
            final Set<ConstraintViolation<Object>> violations = validator.validate(bean);
            if (violations != null) {
                for (final ConstraintViolation<Object> violation : violations) {
                    messages.add(violation.getMessage());
                }
            }
        }
        if (!messages.isEmpty()) { throw new ResourceValidationException("Validation error", messages); }

        return true;
    }
}
