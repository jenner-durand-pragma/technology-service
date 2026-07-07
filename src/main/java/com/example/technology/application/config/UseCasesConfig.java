package com.example.technology.application.config;

import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.domain.usecase.TechnologyUseCase;
import com.example.technology.infrastructure.adapters.persistenceadapter.TechnologyPersistenceAdapter;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

        private final ITechnologyEntityRepository technologyEntityRepository;
        private final ITechnologyEntityMapper technologyEntityMapper;

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
}
