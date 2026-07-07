package com.example.technology.infrastructure.entrypoints.presentation.capacitytechnology.handlers;

import com.example.technology.domain.api.ICapacityTechnologyServicePort;
import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.model.CapacityTechnology;
import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.entrypoints.dto.capacitytechnology.CreateCapacityTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.CreateTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
import com.example.technology.infrastructure.entrypoints.mapper.ICapacityTechnologyDtoMapper;
import com.example.technology.infrastructure.entrypoints.mapper.ITechnologyDtoMapper;
import com.example.technology.infrastructure.entrypoints.presentation.technology.handlers.CreateTechnologyHandler;
import com.example.technology.infrastructure.entrypoints.validation.dto.IDtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCapacityTechnologyHandlerTest {

    @Mock
    private ICapacityTechnologyServicePort capacityTechnologyServicePort;

    @Spy
    private ICapacityTechnologyDtoMapper capacityTechnologyDtoMapper = Mappers.getMapper(ICapacityTechnologyDtoMapper.class);

    @Mock
    private IDtoValidator dtoValidator;

    private CreateCapacityTechnologyHandler createCapacityTechnologyHandler;

    private CreateCapacityTechnologyDto createCapacityTechnologyDto;

    @BeforeEach
    void setUp() {
        createCapacityTechnologyHandler = new CreateCapacityTechnologyHandler(
                capacityTechnologyServicePort,
                capacityTechnologyDtoMapper,
                dtoValidator
        );

        createCapacityTechnologyDto = new CreateCapacityTechnologyDto(
                1L,
                List.of(1L, 2L, 3L)
        );
    }

    @Test
    @DisplayName("Should return no content and dto mapped successfully")
    void shouldReturnNoContentAndDtoMappedSuccessfully_Handle() {
        when(dtoValidator.validate(any(CreateCapacityTechnologyDto.class)))
                .thenReturn(Mono.just(createCapacityTechnologyDto));

        when(capacityTechnologyServicePort.assignTechnologiesToCapacities(anyList()))
                .thenReturn(Mono.empty());

        var request = MockServerRequest.builder()
                .body(Mono.just(createCapacityTechnologyDto));

        StepVerifier.create(createCapacityTechnologyHandler.handle(request))
                .assertNext(response -> {
                    assertThat(response.statusCode().value()).isEqualTo(204);
                })
                .verifyComplete();

        ArgumentCaptor<List<CapacityTechnology>> capacityTechnologies = ArgumentCaptor.captor();
        verify(capacityTechnologyServicePort).assignTechnologiesToCapacities(capacityTechnologies.capture());

        var capacityTechnologiesCaptured = capacityTechnologies.getValue();

        assertThat(capacityTechnologiesCaptured)
                .isNotNull()
                .hasSize(3)
                .extracting(CapacityTechnology::getCapacityId, CapacityTechnology::getTechnologyId)
                .containsExactlyInAnyOrder(
                        tuple(1L, 1L),
                        tuple(1L, 2L),
                        tuple(1L, 3L)
                );
    }
}
