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

}
