package com.example.technology.infrastructure.entrypoints.presentation.technology.handlers;

import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.exceptions.BusinessRuleException;
import com.example.technology.infrastructure.entrypoints.dto.common.ErrorResponseDTO;
import com.example.technology.infrastructure.entrypoints.dto.technology.CreateTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
import com.example.technology.infrastructure.entrypoints.exception.common.BodyRequiredException;
import com.example.technology.infrastructure.entrypoints.handler.IRouteHandler;
import com.example.technology.infrastructure.entrypoints.mapper.ITechnologyDtoMapper;
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
public class CreateTechnologyHandler implements IRouteHandler {

    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyDtoMapper technologyDtoMapper;
    private final IDtoValidator dtoValidator;

    @Override
    @Operation(
            tags = {"Technology API"},
            summary = "Create a technology",
            description = "Allow register a technology",
            requestBody = @RequestBody(
                    description = "Technology data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateTechnologyDto.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Technology registered successfully",
            content = @Content(schema = @Schema(implementation = TechnologyDto.class)))
    @ApiResponse(responseCode = "409", description = "Conflict error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "422", description = "Business rule error or validation error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request
                .bodyToMono(CreateTechnologyDto.class)
                .switchIfEmpty(Mono.error(new BodyRequiredException()))
                .flatMap(dtoValidator::validate)
                .map(technologyDtoMapper::fromCreateToModel)
                .flatMap(technologyServicePort::createTechnology)
                .map(technologyDtoMapper::fromModelToResponse)
                .flatMap(technologyDto -> ServerResponse.ok().bodyValue(technologyDto));
    }
}
