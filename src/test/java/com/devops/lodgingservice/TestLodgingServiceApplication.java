package com.devops.lodgingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestLodgingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(LodgingServiceApplication::main).with(TestLodgingServiceApplication.class).run(args);
	}

}
