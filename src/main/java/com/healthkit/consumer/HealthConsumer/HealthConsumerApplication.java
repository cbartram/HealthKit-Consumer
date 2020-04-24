package com.healthkit.consumer.HealthConsumer;

import com.healthkit.consumer.HealthConsumer.model.document.Reservation;
import com.healthkit.consumer.HealthConsumer.repository.ReservationRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@SpringBootApplication
public class HealthConsumerApplication extends AbstractReactiveMongoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(HealthConsumerApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> routes(ReservationRepository reservationRepository) {
		return route()
				.GET("/reservations", request -> ok().body(reservationRepository.findAll(), Reservation.class))
				.build();
	}

	@Bean
	public SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
		return new SimpleUrlHandlerMapping() {
			{
				setUrlMap(Map.of("ws/greetings", webSocketHandler));
				setOrder(10);
			}
		};
	}

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
	}

	@Override
	protected String getDatabaseName() {
		return "people";
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return mongoClient();
	}
}