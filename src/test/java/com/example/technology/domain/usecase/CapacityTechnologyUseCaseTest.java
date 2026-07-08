package com.example.technology.domain.usecase;

import com.example.technology.domain.exceptions.technology.TechnologyNotFoundException;
import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.domain.model.Technology;
import com.example.technology.domain.spi.ICapacityTechnologyPersistencePort;
import com.example.technology.domain.spi.ITechnologyPersistencePort;
import com.example.technology.domain.spi.ITransactionalPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapacityTechnologyUseCaseTest {

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    @Mock
    private ICapacityTechnologyPersistencePort capacityTechnologyPersistencePort;

    @Mock
    private ITransactionalPersistencePort transactionalPersistencePort;

    @InjectMocks
    private CapacityTechnologyUseCase capacityTechnologyUseCase;

    @Test
    @DisplayName("Should return technologies with provided ids successfully")
    void shouldReturnTechnologiesWithProvidedIdsSuccessfully_AssignTechnologiesToCapacities() {
        var capacityTechnologies = List.of(
                CapacityTechnology.builder().capacityId(1L).technologyId(1L).build(),
                CapacityTechnology.builder().capacityId(1L).technologyId(2L).build(),
                CapacityTechnology.builder().capacityId(1L).technologyId(3L).build(),
                CapacityTechnology.builder().capacityId(1L).technologyId(4L).build()
        );
        var technologiesInDatabase = List.of(
                Technology.builder().id(1L).name("Spring boot").description("It's a framework").build(),
                Technology.builder().id(2L).name("Spring boot 1").description("It's a framework").build(),
                Technology.builder().id(3L).name("Spring boot 2").description("It's a framework").build(),
                Technology.builder().id(4L).name("Spring boot 3").description("It's a framework").build()
        );

        when(technologyPersistencePort.findAllByIdIn(List.of(1L, 2L, 3L, 4L)))
                .thenReturn(Flux.fromIterable(technologiesInDatabase));
        when(capacityTechnologyPersistencePort.saveAll(any()))
                .thenReturn(Mono.empty());
        when(transactionalPersistencePort.execute(any()))
                .thenAnswer(
                        invocation -> {
                            Supplier<Mono<Void>> supplier = invocation.getArgument(0);

                            return supplier.get();
                        }
                );

        StepVerifier.create(capacityTechnologyUseCase.assignTechnologiesToCapacities(capacityTechnologies))
                .verifyComplete();

        verify(technologyPersistencePort).findAllByIdIn(anyList());
        verify(transactionalPersistencePort).execute(any());
        verify(capacityTechnologyPersistencePort).saveAll(any());
    }

    @Test
    @DisplayName("Should return a TechnologyNotFoundException when any provided technology id was not found")
    void shouldReturnTechnologyNotFoundExceptionWhenAnyProvidedTechnologyWasNotFound_AssignTechnologiesToCapacities() {
        var capacityTechnologies = List.of(
                CapacityTechnology.builder().capacityId(1L).technologyId(1L).build(),
                CapacityTechnology.builder().capacityId(1L).technologyId(2L).build(),
                CapacityTechnology.builder().capacityId(1L).technologyId(3L).build(),
                CapacityTechnology.builder().capacityId(1L).technologyId(4L).build()
        );
        var technologiesInDatabase = List.of(
                Technology.builder().id(2L).name("Spring boot").description("It's a framework").build()
        );

        when(technologyPersistencePort.findAllByIdIn(List.of(1L, 2L, 3L, 4L)))
                .thenReturn(Flux.fromIterable(technologiesInDatabase));

        StepVerifier.create(capacityTechnologyUseCase.assignTechnologiesToCapacities(capacityTechnologies))
                .expectErrorSatisfies(
                        error -> assertInstanceOf(TechnologyNotFoundException.class, error)
                ).verify();

        verify(technologyPersistencePort).findAllByIdIn(anyList());
        verify(transactionalPersistencePort, never()).execute(any());
        verify(capacityTechnologyPersistencePort, never()).saveAll(any());
    }
}
