package com.example.technology.infrastructure.entrypoints.dto.technology;

import com.example.technology.domain.model.Technology;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Necessary fields to create a new technology")
public record CreateTechnologyDto(
        @Schema(description = "Name of the technology", example = "Spring boot")
        @NotBlank(message = "Name must have value")
        @Size(
                max = 50,
                message = "Name must be lower or equals than {max} characters"
        )
        String name,

        @Schema(description = "Description of the technology", example = "It's a Java framework")
        @NotBlank(message = "Description must have value")
        @Size(
                max = 90,
                message = "Description must be lower or equals than {max} characters"
        )
        String description
) { }
