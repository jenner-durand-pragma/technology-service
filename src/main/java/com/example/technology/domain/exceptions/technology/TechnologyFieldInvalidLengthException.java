package com.example.technology.domain.exceptions.technology;

import com.example.technology.domain.exceptions.BusinessRuleException;

import static com.example.technology.domain.model.Technology.MAX_LENGTH_DESCRIPTION;
import static com.example.technology.domain.model.Technology.MAX_LENGTH_NAME;

public class TechnologyFieldInvalidLengthException extends BusinessRuleException {

    private static final String ERROR_MESSAGE = "The attribute %s cannot have more characters than %d.";

    public TechnologyFieldInvalidLengthException(String field, Integer length) {
        super(String.format(ERROR_MESSAGE, field, length));
    }

    public static TechnologyFieldInvalidLengthException name() {
        return new TechnologyFieldInvalidLengthException("name", MAX_LENGTH_NAME);
    }

    public static TechnologyFieldInvalidLengthException description() {
        return new TechnologyFieldInvalidLengthException("description", MAX_LENGTH_DESCRIPTION);
    }
}
