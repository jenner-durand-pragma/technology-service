package com.example.technology.application.config;

import com.example.technology.domain.api.ICapacityTechnologyServicePort;
import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.domain.spi.ITransactionalPersistencePort;
import com.example.technology.domain.usecase.CapacityTechnologyUseCase;
import com.example.technology.domain.usecase.TechnologyUseCase;
import com.example.technology.infrastructure.adapters.persistenceadapter.CapacityTechnologyPersistenceAdapter;
import com.example.technology.infrastructure.adapters.persistenceadapter.TechnologyPersistenceAdapter;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ICapacityTechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ICapacityTechnologyEntityRepository;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import com.example.technology.infrastructure.adapters.transactionalgateway.impl.TransactionalGatewayImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
        private final TransactionalOperator transactionalOperator;

        private final ITechnologyEntityRepository technologyEntityRepository;
        private final ITechnologyEntityMapper technologyEntityMapper;

        private final ICapacityTechnologyEntityRepository capacityTechnologyEntityRepository;
        private final ICapacityTechnologyEntityMapper capacityTechnologyEntityMapper;

        @Bean
        public ITransactionalPersistencePort transactionalPersistencePort() {
                return new TransactionalGatewayImpl(
                        transactionalOperator
                );
        }

        @Bean
        public ITechnologyPersistencePort technologyPersistencePort() {
                return new TechnologyPersistenceAdapter(
                        technologyEntityRepository,
                        technologyEntityMapper
                );
        }

        @Bean
        public ITechnologyServicePort technologyServicePort(
                ITechnologyPersistencePort technologyPersistencePort
        ) {
                return new TechnologyUseCase(technologyPersistencePort);
        }

        @Bean
        public ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort() {
                return new CapacityTechnologyPersistenceAdapter(
                        capacityTechnologyEntityRepository,
                        capacityTechnologyEntityMapper
                );
        }

        @Bean
        public ICapacityTechnologyServicePort capacityTechnologyServicePort(
                ITechnologyPersistencePort technologyPersistencePort,
                ICapacityTechnologyPersistencePort capacityTechnologyServicePort,
                ITransactionalPersistencePort transactionalPersistencePort
        ) {
                return new CapacityTechnologyUseCase(
                        technologyPersistencePort,
                        capacityTechnologyServicePort,
                        transactionalPersistencePort
                );
        }
}
