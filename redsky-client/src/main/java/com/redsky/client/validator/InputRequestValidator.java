package com.redsky.client.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redsky.client.exception.ResourceValidationException;
import com.redsky.client.pojo.RequestPayload;

@Component
public class InputRequestValidator implements RequestValidator<RequestPayload> {

    @Autowired
    private BeanValidator validator;

    @Autowired
    private NameValidator nameValidator;

    @Override
    public boolean validate(final RequestPayload bean) throws ResourceValidationException {
        boolean result = false;
        if (bean != null) {
            result = validator.validate(bean);
        }
        if (result) {
            result = nameValidator.isValidName(bean.getProduct().getName());
        }

        return result;
    }

}
