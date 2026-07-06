package com.example.technology;

import com.example.technology.domain.spi.UserPersistencePort;
import com.example.technology.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = TechnologyApiApplication.class)
class TechnologyApiApplicationTests {

	@MockBean
	private UserPersistencePort userPersistencePort;

	@Autowired
	private UserUseCase userUseCase;

	@Test
	void contextLoads() {
	}

}
