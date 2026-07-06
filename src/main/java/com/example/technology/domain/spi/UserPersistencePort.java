package com.example.technology.domain.spi;

import com.example.technology.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserPersistencePort {
    Mono<User> save(User user);
    Mono<Boolean> existByEmail(String email);
}
