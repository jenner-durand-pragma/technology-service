package com.example.technology.domain.usecase;

import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    @Override
    public Mono<Technology> createTechnology(Technology technology) {
        return Mono.empty();
    }

}
