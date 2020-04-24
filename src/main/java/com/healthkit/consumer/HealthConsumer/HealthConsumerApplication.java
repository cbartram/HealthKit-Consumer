package com.healthkit.consumer.HealthConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@SpringBootApplication
public class HealthConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthConsumerApplication.class, args);
	}
}

@Document
class Reservation {
	@Id
	private String id;
	private String name;
}
