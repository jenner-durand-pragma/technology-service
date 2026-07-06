package com.example.technology.infrastructure.adapters.persistenceadapter;

import com.example.technology.domain.model.Technology;
import com.example.technology.infrastructure.adapters.persistenceadapter.mapper.ITechnologyEntityMapper;
import com.example.technology.infrastructure.adapters.persistenceadapter.repository.ITechnologyEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.test.StepVerifier;

@DataR2dbcTest
class TechnologyPersistenceAdapterTest {

    @Autowired
    private ITechnologyEntityRepository technologyEntityRepository;

    @Spy
    private ITechnologyEntityMapper technologyEntityMapper = Mappers.getMapper(ITechnologyEntityMapper.class);

    private TechnologyPersistenceAdapter technologyPersistenceAdapter;

    private Technology technology;

    @BeforeEach
    void setUp() {
        technologyPersistenceAdapter = new TechnologyPersistenceAdapter(
                technologyEntityRepository,
                technologyEntityMapper
        );

        technology = Technology.builder()
                .name("Spring Boot")
                .description("It's a Java framework")
                .build();

        technologyEntityRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Should return technology saved successfully")
    void shouldReturnTechnologySavedSuccessfully_Save() {
        StepVerifier.create(technologyPersistenceAdapter.save(technology))
                .expectNextMatches(technologyResult ->
                        technologyResult.getId() != null
                                && technologyResult.getName().equals(technology.getName()))
                .verifyComplete();
    }

    @ParameterizedTest(
            name = "Should return the boolean value if technology name exists or doesn't exists"
    )
    @ValueSource(booleans = {true, false})
    void shouldReturnBooleanValueIfTechnologyExistsOrDoesntExists_ExistsByName(Boolean value) {
        var name = Boolean.TRUE.equals(value) ? technology.getName() : "Another value";

        technologyEntityRepository.save(technologyEntityMapper.toEntity(technology)).block();

        StepVerifier.create(technologyPersistenceAdapter.existsByName(name))
                .expectNext(value)
                .verifyComplete();
    }
}
