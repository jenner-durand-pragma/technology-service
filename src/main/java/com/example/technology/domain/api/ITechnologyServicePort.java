package com.example.technology.domain.api;

import com.example.technology.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyServicePort {
    Mono<Technology> createTechnology(Technology technology);
}
