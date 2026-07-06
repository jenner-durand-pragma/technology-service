package com.example.technology.infrastructure.adapters.persistenceadapter;

import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort {

    private final ITechnologyEntityRepository technologyEntityRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return Mono.empty();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return Mono.empty();
    }
}
