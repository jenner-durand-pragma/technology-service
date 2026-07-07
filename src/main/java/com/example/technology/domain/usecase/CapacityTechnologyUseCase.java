package com.example.technology.domain.usecase;

import com.example.technology.domain.api.ICapacityTechnologyServicePort;
import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.domain.spi.ITransactionalPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CapacityTechnologyUseCase implements ICapacityTechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;
    private final ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort;
    private final ITransactionalPersistencePort transactionalPersistencePort;

    @Override
    public Mono<Void> assignTechnologiesToCapacities(List<CapacityTechnology> capacityTechnologies) {
        return Mono.empty();
    }
}
