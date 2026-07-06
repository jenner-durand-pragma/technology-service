package com.example.technology.domain.api;

import com.example.technology.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<User> registerUser(User user, String messageId);
}
