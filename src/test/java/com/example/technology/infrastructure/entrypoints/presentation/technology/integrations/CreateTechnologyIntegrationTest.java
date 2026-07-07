package com.example.technology.infrastructure.entrypoints.presentation.technology.integrations;

import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import com.example.technology.infrastructure.entrypoints.dto.common.ErrorResponseDTO;
import com.example.technology.infrastructure.entrypoints.dto.technology.CreateTechnologyDto;
import com.example.technology.infrastructure.entrypoints.dto.technology.TechnologyDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CreateTechnologyIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ITechnologyEntityRepository technologyEntityRepository;

    @BeforeEach
    void setUp() {
        technologyEntityRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should return a technology saved successfully")
    void shouldReturnTechnologySavedSuccessfully_EndToEnd(){
        var requestDto = new CreateTechnologyDto(
                "Spring Boot",
                "It's a Java framework"
        );

        var technologyDto = webTestClient.post()
                .uri("/api/technologies")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TechnologyDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(technologyDto).isNotNull();
        assertThat(technologyDto.id()).isNotNull();
        assertThat(technologyDto.name()).isEqualTo(requestDto.name());
        assertThat(technologyDto.description()).isEqualTo(requestDto.description());

        StepVerifier.create(technologyEntityRepository.findById(technologyDto.id()))
                .assertNext(technology -> {
                    assertThat(technology).isNotNull();
                    assertThat(technology.getId()).isEqualTo(technologyDto.id());
                    assertThat(technology.getName()).isEqualTo(technologyDto.name());
                    assertThat(technology.getDescription()).isEqualTo(technologyDto.description());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return a validation error")
    void shouldReturnValidationError_EndToEnd() {
        var requestDto = new CreateTechnologyDto(
                "Spring boot text to validate characters limit here.",
                "This is a long description because I want to throw a validation exception, so " +
                        "I hope this work fine at the first time"
        );

        var errorResponseDTO = webTestClient.post()
                .uri("/api/technologies")
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
