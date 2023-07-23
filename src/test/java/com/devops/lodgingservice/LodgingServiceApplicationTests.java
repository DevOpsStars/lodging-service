package com.devops.lodgingservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = TestLodgingServiceApplication.class)
class LodgingServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
