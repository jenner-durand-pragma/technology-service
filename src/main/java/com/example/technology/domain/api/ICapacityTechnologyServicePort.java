package com.example.technology.domain.api;

import com.example.technology.domain.model.CapacityTechnology;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapacityTechnologyServicePort {
    Mono<Void> assignTechnologiesToCapacities(List<CapacityTechnology> capacityTechnologies);
}
