package com.example.technology.domain.usecase;

import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.exceptions.technology.TechnologyNameAlreadyExistsException;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    @Override
    public Mono<Technology> createTechnology(Technology technology) {
        return Mono.just(technology)
                .doOnNext(Technology::checkNameLength)
                .doOnNext(Technology::checkDescriptionLength)
                .flatMap(this::validateNameUniqueness)
                .flatMap(technologyPersistencePort::save);
    }

    private Mono<Technology> validateNameUniqueness(Technology technology) {
        return technologyPersistencePort.existsByName(technology.getName())
                .flatMap(exists -> {
                    if(Boolean.TRUE.equals(exists)) {
                        return Mono.error(new TechnologyNameAlreadyExistsException());
                    }

                    return Mono.just(technology);
                });
    }
}
