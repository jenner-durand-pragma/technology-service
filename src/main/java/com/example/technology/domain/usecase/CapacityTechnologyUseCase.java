package com.example.technology.domain.usecase;

import com.example.technology.domain.api.ICapacityTechnologyServicePort;
import com.example.technology.domain.exceptions.technology.TechnologyNotFoundException;
import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.domain.spi.ITransactionalPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CapacityTechnologyUseCase implements ICapacityTechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;
    private final ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort;
    private final ITransactionalPersistencePort transactionalPersistencePort;

    @Override
    public Mono<Void> assignTechnologiesToCapacities(List<CapacityTechnology> capacityTechnologies) {
        var technologyIds = capacityTechnologies.stream()
                .map(CapacityTechnology::getTechnologyId)
                .distinct()
                .toList();

        return technologyPersistencePort.findAllByIdIn(technologyIds)
                .map(Technology::getId)
                .collect(Collectors.toSet())
                .flatMap(existingIds -> {
                    var missingIds = technologyIds.stream()
                            .filter(id -> !existingIds.contains(id))
                            .toList();

                    if (!missingIds.isEmpty()) {
                        return Mono.error(new TechnologyNotFoundException(missingIds));
                    }

                    return transactionalPersistencePort.execute(
                            () -> capacityTechnologyPersistencePort.saveAll(capacityTechnologies).then()
                    ).then();
                });
    }
}
