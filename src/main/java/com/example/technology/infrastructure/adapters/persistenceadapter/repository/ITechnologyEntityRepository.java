package com.example.technology.infrastructure.adapters.persistenceadapter.repository;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.adapters.persistenceadapter.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyEntityRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {
    Mono<Boolean> existsByName(String name);
    Flux<Technology> findAllByIdIn(List<Long> ids);
}
