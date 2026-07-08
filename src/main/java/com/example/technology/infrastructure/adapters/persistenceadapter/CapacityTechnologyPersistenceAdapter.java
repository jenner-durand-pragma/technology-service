package com.example.technology.infrastructure.adapters.persistenceadapter;

import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ICapacityTechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ICapacityTechnologyEntityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CapacityTechnologyPersistenceAdapter implements ICapacityTechnologyPersistencePort {

    private final ICapacityTechnologyEntityRepository capacityTechnologyEntityRepository;
    private final ICapacityTechnologyEntityMapper capacityTechnologyEntityMapper;

    @Override
    public Mono<Void> saveAll(List<CapacityTechnology> capacityTechnologies) {
        return capacityTechnologyEntityRepository.saveAll(
                capacityTechnologies.stream()
                        .map(capacityTechnologyEntityMapper::toEntity)
                        .toList()
        ).then();
    }
}
