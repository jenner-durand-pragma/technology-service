package com.example.technology.infrastructure.entrypoints.presentation.capacitytechnology.integrations;

import com.example.technology.infrastructure.adapters.persistenceadapter.entity.CapacityTechnologyEntity;
import com.example.technology.infrastructure.adapters.persistenceadapter.entity.TechnologyEntity;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ICapacityTechnologyEntityRepository;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import com.example.technology.infrastructure.entrypoints.dto.capacitytechnology.CreateCapacityTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.common.ErrorResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CreateCapacityTechnologyIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ITechnologyEntityRepository technologyEntityRepository;

    @Autowired
    private ICapacityTechnologyEntityRepository capacityTechnologyEntityRepository;

    @BeforeEach
    void setUp() {
        capacityTechnologyEntityRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should return no content and save associations successfully")
    void shouldReturnNoContentAndSaveAssociationsSuccessfully_EndToEnd(){
        var requestDto = new CreateCapacityTechnologyDto(
                1L,
                List.of(1L, 2L, 3L)
        );

        technologyEntityRepository.saveAll(List.of(
                TechnologyEntity.builder()
                        .name("Spring boot 1")
                        .description("Example")
                        .build(),
                TechnologyEntity.builder()
                        .name("Spring boot 2")
                        .description("Example")
                        .build(),
                TechnologyEntity.builder()
                        .name("Spring boot 3")
                        .description("Example")
                        .build()
        )).blockLast();

        webTestClient.post()
                .uri("/api/capacities/technologies")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(capacityTechnologyEntityRepository.findAll().collectList())
                .assertNext(capacityTechnologies ->
                        assertThat(capacityTechnologies)
                                .isNotNull()
                                .hasSize(3)
                                .extracting(
                                        CapacityTechnologyEntity::getCapacityId,
                                        CapacityTechnologyEntity::getTechnologyId
                                )
                                .containsExactlyInAnyOrder(
                                        tuple(1L, 1L),
                                        tuple(1L, 2L),
                                        tuple(1L, 3L)
                                )
                ).verifyComplete();
    }

    @Test
    @DisplayName("Should return a validation error")
    void shouldReturnValidationError_EndToEnd() {
        var requestDto = new CreateCapacityTechnologyDto(
                null,
                Arrays.asList(1L, 2L, null)
        );

        technologyEntityRepository.saveAll(List.of(
                TechnologyEntity.builder()
                        .name("Spring boot 1")
                        .description("Example")
                        .build(),
                TechnologyEntity.builder()
                        .name("Spring boot 2")
                        .description("Example")
                        .build(),
                TechnologyEntity.builder()
                        .name("Spring boot 3")
                        .description("Example")
                        .build()
        )).blockLast();

        var errorResponseDTO = webTestClient.post()
                .uri("/api/capacities/technologies")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody(ErrorResponseDTO.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorResponseDTO).isNotNull();
        assertThat(errorResponseDTO.status()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}
