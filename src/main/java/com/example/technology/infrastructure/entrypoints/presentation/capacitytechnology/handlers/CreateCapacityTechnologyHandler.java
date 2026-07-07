package com.example.technology.infrastructure.entrypoints.presentation.capacitytechnology.handlers;

import com.example.technology.domain.api.ICapacityTechnologyServicePort;
import com.example.technology.infrastructure.entrypoints.handler.IRouteHandler;
import com.example.technology.infrastructure.entrypoints.mapper.ICapacityTechnologyDtoMapper;
import com.example.technology.infrastructure.entrypoints.validation.dto.IDtoValidator;
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
    public Mono<ServerResponse> handle(ServerRequest request) {
        return Mono.empty();
    }
}
