package com.healthkit.consumer.HealthConsumer;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@SpringBootApplication
@EnableReactiveMongoRepositories
public class HealthConsumerApplication extends AbstractReactiveMongoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(HealthConsumerApplication.class, args);
	}

	@Override
	protected String getDatabaseName() {
		return "people";
	}

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	@Override
	public com.mongodb.reactivestreams.client.MongoClient reactiveMongoClient() {
		return mongoClient();
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
	}
}