package com.example.technology.infrastructure.adapters.persistenceadapter.repository;

import com.example.technology.infrastructure.adapters.persistenceadapter.entity.CapacityTechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ICapacityTechnologyEntityRepository extends ReactiveCrudRepository<CapacityTechnologyEntity, Long> {
}
