package com.example.technology.infrastructure.entrypoints.dto.technology;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Technology DTO")
public record TechnologyDto(
        @Schema(description = "ID of the technology", example = "10")
        Long id,

        @Schema(description = "Name of the technology", example = "Spring boot")
        String name,

        @Schema(description = "Description of the technology", example = "It's a Java framework")
        String description
) { }
