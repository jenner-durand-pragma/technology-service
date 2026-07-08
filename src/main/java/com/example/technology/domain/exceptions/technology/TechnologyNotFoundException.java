package com.example.technology.domain.exceptions.technology;

import com.example.technology.domain.exceptions.NotFoundException;
import com.example.technology.domain.model.Technology;

import java.util.List;
import java.util.stream.Collectors;

public class TechnologyNotFoundException extends NotFoundException {

    private static final String ERROR_MESSAGE_SINGLE =
            "The following technology id was not found: ";

    private static final String ERROR_MESSAGE_MASSIVE =
            "The following technology ids were not found: ";

    public TechnologyNotFoundException(List<Long> ids) {
        super(ERROR_MESSAGE_MASSIVE + ids.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                Technology.class.getSimpleName()
        );
    }

    public TechnologyNotFoundException(Long id) {
        super(ERROR_MESSAGE_SINGLE + id.toString(),
                Technology.class.getSimpleName()
        );
    }
}
