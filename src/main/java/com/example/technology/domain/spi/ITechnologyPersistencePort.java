package com.example.technology.domain.spi;

import com.example.technology.domain.model.Technology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyPersistencePort {
    Mono<Technology> save(Technology technology);
    Mono<Boolean> existsByName(String name);
    Flux<Technology> findAllByIdIn(List<Long> technologyIds);
}
