package com.example.technology.infrastructure.entrypoints.presentation.technology.handlers;

import com.example.technology.domain.api.ITechnologyServicePort;
import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.entrypoints.dto.technology.CreateTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
import com.example.technology.infrastructure.entrypoints.mapper.ITechnologyDtoMapper;
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
import org.springframework.web.reactive.function.server.EntityResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTechnologyHandlerTest {

    @Mock
    private ITechnologyServicePort technologyServicePort;

    @Spy
    private ITechnologyDtoMapper technologyDtoMapper = Mappers.getMapper(ITechnologyDtoMapper.class);

    @Mock
    private IDtoValidator dtoValidator;

    private CreateTechnologyHandler createTechnologyHandler;

    private CreateTechnologyDto createTechnologyDto;

    @BeforeEach
    void setUp() {
        createTechnologyHandler = new CreateTechnologyHandler(
                technologyServicePort,
                technologyDtoMapper,
                dtoValidator
        );

        createTechnologyDto = new CreateTechnologyDto(
                "Spring boot",
                "It's a Java framework"
        );
    }

    @Test
    @DisplayName("Should return technology dto mapped successfully")
    void shouldReturnTechnologyDtoMappedSuccessfully_Handle() {
        var technologyId = 1L;
        var technology = Technology.builder()
                .name(createTechnologyDto.name())
                .description(createTechnologyDto.description())
                .build();

        when(dtoValidator.validate(any(CreateTechnologyDto.class)))
                .thenReturn(Mono.just(createTechnologyDto));

        when(technologyServicePort.createTechnology(any(Technology.class)))
                .thenAnswer(invocation -> {
                    Technology technologyAnswer = invocation.getArgument(0);
                    technologyAnswer.setId(technologyId);

                    return Mono.just(technologyAnswer);
                });

        var request = MockServerRequest.builder()
                .body(Mono.just(createTechnologyDto));

        StepVerifier.create(createTechnologyHandler.handle(request))
                .assertNext(response -> {
                    assertThat(response.statusCode().value()).isEqualTo(200);

                    @SuppressWarnings("unchecked")
                    var entityResponse = (EntityResponse<TechnologyDto>) response;
                    var technologyDto = entityResponse.entity();

                    assertThat(technologyDto)
                            .isNotNull()
                            .extracting(TechnologyDto::id, TechnologyDto::name, TechnologyDto::description)
                            .containsExactly(technologyId, technology.getName(), technology.getDescription());
                })
                .verifyComplete();

        var technologyCaptor = ArgumentCaptor.forClass(Technology.class);
        verify(technologyServicePort).createTechnology(technologyCaptor.capture());

        var technologyCaptured = technologyCaptor.getValue();

        assertThat(technologyCaptured)
                .isNotNull()
                .extracting(Technology::getId, Technology::getName, Technology::getDescription)
                .containsExactly(technologyId, technology.getName(), technology.getDescription());

    }
}
