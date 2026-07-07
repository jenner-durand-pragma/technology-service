package com.example.technology.infrastructure.entrypoints.presentation.capacitytechnology.handlers;

import com.example.technology.domain.api.ICapacityTechnologyServicePort;
import com.example.technology.infrastructure.entrypoints.dto.capacitytechnology.CreateCapacityTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.common.ErrorResponseDTO;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
import com.example.technology.infrastructure.entrypoints.exception.common.BodyRequiredException;
import com.example.technology.infrastructure.entrypoints.handler.IRouteHandler;
import com.example.technology.infrastructure.entrypoints.mapper.ICapacityTechnologyDtoMapper;
import com.example.technology.infrastructure.entrypoints.validation.dto.IDtoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateCapacityTechnologyHandler implements IRouteHandler {

    private final ICapacityTechnologyServicePort capacityTechnologyServicePort;
    private final ICapacityTechnologyDtoMapper capacityTechnologyDtoMapper;
    private final IDtoValidator dtoValidator;

    @Override
    @Operation(
            tags = {"Capacity Technology API"},
            summary = "Associate capacity with its technologies",
            description = "Allow register an association between a capacity and its technologies",
            requestBody = @RequestBody(
                    description = "Capacity Technology data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateCapacityTechnologyDto.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Association between capacity and its technologies saved successfully",
            content = @Content(schema = @Schema(implementation = TechnologyDto.class)))
    @ApiResponse(responseCode = "404", description = "A technology was not found",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "422", description = "Business rule error or validation error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request
                .bodyToMono(CreateCapacityTechnologyDto.class)
                .switchIfEmpty(Mono.error(new BodyRequiredException()))
                .flatMap(dtoValidator::validate)
                .map(capacityTechnologyDtoMapper::toModel)
                .flatMap(capacityTechnologyServicePort::assignTechnologiesToCapacities)
                .then(ServerResponse.noContent().build());
    }
}
