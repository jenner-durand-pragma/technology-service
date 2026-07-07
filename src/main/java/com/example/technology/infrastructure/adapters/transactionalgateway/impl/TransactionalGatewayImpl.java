package com.example.technology.infrastructure.adapters.transactionalgateway.impl;

import com.example.technology.domain.spi.ITransactionalPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class TransactionalGatewayImpl implements ITransactionalPersistencePort {
    private final TransactionalOperator transactionalOperator;

    @Override
    public <T> Mono<T> execute(Supplier<Mono<T>> action) {
        return transactionalOperator.transactional(action.get());
    }

    @Override
    public <T> Flux<T> executeMany(Supplier<Flux<T>> action) {
        return transactionalOperator.transactional(action.get());
    }
}
