package com.example.technology.infrastructure.adapters.persistenceadapter;

import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements ITechnologyPersistencePort {

    private final ITechnologyEntityRepository technologyEntityRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return Mono.just(technology)
                .map(technologyEntityMapper::toEntity)
                .flatMap(technologyEntityRepository::save)
                .map(technologyEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return Mono.just(name)
                .flatMap(technologyEntityRepository::existsByName);
    }

    @Override
    public Flux<Technology> findAllByIdIn(List<Long> technologyIds) {
        return technologyEntityRepository.findAllByIdIn(technologyIds);
    }
}
