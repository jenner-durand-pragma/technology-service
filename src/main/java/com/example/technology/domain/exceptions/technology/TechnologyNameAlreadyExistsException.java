package com.example.technology.domain.exceptions.technology;

import com.example.technology.domain.exceptions.ConflictException;

public class TechnologyNameAlreadyExistsException extends ConflictException {

    private static final String ERROR_MESSAGE = "The name of the technology already exists.";
    private static final String ERROR_FIELD = "name";

    public TechnologyNameAlreadyExistsException() {
        super(ERROR_MESSAGE, ERROR_FIELD);
    }
}
