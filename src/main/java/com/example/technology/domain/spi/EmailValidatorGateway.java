package com.example.technology.domain.spi;

import com.example.technology.domain.model.EmailValidationResult;
import reactor.core.publisher.Mono;

public interface EmailValidatorGateway {

    Mono<EmailValidationResult> validateEmail(String email, String messageId);
}
