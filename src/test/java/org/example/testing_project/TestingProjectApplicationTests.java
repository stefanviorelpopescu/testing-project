package org.example.testing_project;

import org.example.testing_project.dao.Pensioner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.example.testing_project.service.PensionerMother.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TestingProjectApplicationTests {

	@Autowired
	WebTestClient client;

	@Test
	public void savePensionerAndVerifyId() {
		//given
		Pensioner pensioner = getPensioner(null, DEFAULT_NAME, DEFAULT_AGE);

		//when
		Pensioner savedPensioner =
				client
						.post()
						.uri("/pensioners")
						.bodyValue(pensioner)
						.exchange()
						.expectStatus().isOk()
						.expectBody(Pensioner.class)
						.returnResult()
						.getResponseBody();

		//then
		assertNotNull(savedPensioner.getId());
		assertEquals(DEFAULT_NAME, savedPensioner.getName());
		assertEquals(DEFAULT_AGE, savedPensioner.getAge());
	}

}

