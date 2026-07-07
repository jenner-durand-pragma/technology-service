package com.example.technology.domain.spi;

import com.example.technology.domain.model.CapacityTechnology;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacityTechnologyPersistencePort {
    Mono<Void> saveAll(List<CapacityTechnology> capacityTechnologies);
}
