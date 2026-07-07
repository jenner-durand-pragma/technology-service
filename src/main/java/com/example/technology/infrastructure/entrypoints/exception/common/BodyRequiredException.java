package com.example.technology.infrastructure.entrypoints.exception.common;

import com.example.technology.domain.exceptions.BusinessRuleException;

public class BodyRequiredException extends BusinessRuleException {

    private static final String ERROR_MESSAGE = "Body is required.";

    public BodyRequiredException() {
        super(ERROR_MESSAGE);
    }
}
