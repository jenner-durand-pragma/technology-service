package com.example.technology.domain.model;

import com.example.technology.domain.exceptions.technology.TechnologyFieldInvalidLengthException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Technology {

    private Long id;
    private String name;
    private String description;

    public static final Integer MAX_LENGTH_NAME = 50;
    public static final Integer MAX_LENGTH_DESCRIPTION = 90;

    public void checkNameLength() {
        if (name == null || name.isBlank() || name.length() > MAX_LENGTH_NAME) {
            throw TechnologyFieldInvalidLengthException.name();
        }
    }

    public void checkDescriptionLength() {
        if (description == null || description.isBlank() || description.length() > MAX_LENGTH_DESCRIPTION) {
            throw TechnologyFieldInvalidLengthException.description();
        }
    }
}
