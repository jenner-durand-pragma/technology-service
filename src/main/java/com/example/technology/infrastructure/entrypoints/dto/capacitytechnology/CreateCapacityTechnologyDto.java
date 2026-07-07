package com.example.technology.infrastructure.entrypoints.dto.capacitytechnology;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Necessary fields to associate capacity with its technologies")
public record CreateCapacityTechnologyDto (

        @Schema(description = "Id of capacity", example = "10")
        @NotNull(message = "Id of capacity must have a value")
        Long capacityId,

        @Schema(description = "Ids of technologies to associate")
        @NotEmpty(message = "Technology ids cannot be empty")
        List<Long> technologyIds
) { }
