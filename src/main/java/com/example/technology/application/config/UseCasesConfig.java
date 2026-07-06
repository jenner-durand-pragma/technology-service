package com.example.technology.application.config;

import com.example.technology.domain.spi.EmailValidatorGateway;
import com.example.technology.domain.spi.UserPersistencePort;
import com.example.technology.domain.usecase.UserUseCase;
import com.example.technology.domain.api.UserServicePort;
import com.example.technology.infrastructure.adapters.persistenceadapter.UserPersistenceAdapter;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.UserEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
        private final UserRepository userRepository;
        private final UserEntityMapper userEntityMapper;

        @Bean
        public UserPersistencePort usersPersistencePort() {
                return new UserPersistenceAdapter(userRepository,userEntityMapper);
        }

        @Bean
        public UserServicePort usersServicePort(UserPersistencePort usersPersistencePort, EmailValidatorGateway emailValidatorGateway){
                return new UserUseCase(usersPersistencePort, emailValidatorGateway);
        }
}
