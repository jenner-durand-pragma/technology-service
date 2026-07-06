package com.example.technology.infrastructure.entrypoints.presentation.technology.handlers;

import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.infrastructure.entrypoints.handler.IRouteHandler;
import com.example.technology.infrastructure.entrypoints.mapper.ITechnologyDtoMapper;
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
public class CreateTechnologyHandler implements IRouteHandler {

    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyDtoMapper technologyDtoMapper;
    private final IDtoValidator dtoValidator;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return Mono.empty();
    }
}
