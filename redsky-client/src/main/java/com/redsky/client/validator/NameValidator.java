package com.redsky.client.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class NameValidator {

    private static final String NAME_PATTERN = "^[a-zA-Z0-9\\._\\s\\-]+$";

    public boolean isValidName(final String name) {
        final Pattern pattern = Pattern.compile(NAME_PATTERN);
        final Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

}
