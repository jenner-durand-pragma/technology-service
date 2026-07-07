package com.example.technology.domain.usecase;

import com.example.technology.domain.exceptions.technology.TechnologyFieldInvalidLengthException;
import com.example.technology.domain.exceptions.technology.TechnologyNameAlreadyExistsException;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    private Technology technology;

    @BeforeEach
    void setUp() {
        technology = Technology.builder()
                .id(1L)
                .name("Spring Boot")
                .description("It's a Java framework")
                .build();
    }

    @Test
    @DisplayName(
            "Should return a TechnologyFieldInvalidLengthException when " +
            "name have more characters than MAX_LENGTH_NAME"
    )
    void shouldReturnExceptionWhenNameHaveMoreCharactersThanMaxLengthName_CreateTechnology() {
        technology.setName("Spring boot text to validate characters limit here.");

        StepVerifier.create(technologyUseCase.createTechnology(technology))
                .expectError(TechnologyFieldInvalidLengthException.class)
                .verify();

        verify(technologyPersistencePort, never()).existsByName(any(String.class));
    }

    @Test
    @DisplayName(
            "Should return a TechnologyFieldInvalidLengthException when " +
            "description have more characters than MAX_LENGTH_DESCRIPTION"
    )
    void shouldReturnExceptionWhenNameHaveMoreCharactersThanMaxLengthDescription_CreateTechnology() {
        technology.setDescription(
                "This is a long description because I want to throw a validation exception, so " +
                "I hope this work fine at the first time"
        );

        StepVerifier.create(technologyUseCase.createTechnology(technology))
                .expectError(TechnologyFieldInvalidLengthException.class)
                .verify();

        verify(technologyPersistencePort, never()).existsByName(any(String.class));
    }

    @Test
    @DisplayName("Should return a TechnologyNameAlreadyExistsException when name already exists")
    void shouldReturnExceptionWhenNameAlreadyExists_CreateTechnology() {
        when(technologyPersistencePort.existsByName(any(String.class)))
                .thenReturn(Mono.just(true));

        StepVerifier.create(technologyUseCase.createTechnology(technology))
                .expectError(TechnologyNameAlreadyExistsException.class)
                .verify();

        verify(technologyPersistencePort, never()).save(any(Technology.class));
    }

    @Test
    @DisplayName("Should return technology domain model when it saved successfully")
    void shouldReturnTechnologyWhenItSavedSuccessfully_CreateTechnology() {
        var technologyId = 1L;
        technology.setId(null);

        when(technologyPersistencePort.existsByName(any(String.class)))
                .thenReturn(Mono.just(false));
        when(technologyPersistencePort.save(any(Technology.class)))
                .thenAnswer(invocation -> {
                    Technology technologyInvocation = invocation.getArgument(0);
                    technologyInvocation.setId(technologyId);

                    return Mono.just(technologyInvocation);
                });

        StepVerifier.create(technologyUseCase.createTechnology(technology))
                .expectNextMatches(technologyResult ->
                        technologyResult.getId() != null && technologyResult.getId().equals(technologyId))
                .verifyComplete();

        verify(technologyPersistencePort).existsByName(any(String.class));
        verify(technologyPersistencePort).save(any(Technology.class));
    }
}
