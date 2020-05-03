package com.healthkit.consumer.HealthConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import com.healthkit.consumer.HealthConsumer.repository.HealthKitMetricRepository;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log4j2
@SpringBootApplication
public class HealthConsumerApplication extends AbstractReactiveMongoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(HealthConsumerApplication.class, args);
	}

	@Bean
	@CrossOrigin
	public RouterFunction<ServerResponse> routes(HealthKitMetricRepository healthKitMetricRepository) {
		return route()
				.GET("/health/metrics", request -> ok().body(healthKitMetricRepository.findAll(), HealthKitMetric.class))
				.build();
	}

	@Bean
	CorsWebFilter corsWebFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
		corsConfig.setMaxAge(8000L);
		corsConfig.addAllowedMethod("PUT");
		corsConfig.addAllowedHeader("Baeldung-Allowed");

		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}

	@Bean
	public SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
		// Double brace initialization creates anonymous inner class and sets values with syntax sugar
		return new SimpleUrlHandlerMapping() {{
				setUrlMap(Map.of("ws/greetings", webSocketHandler));
				setOrder(10);
		}};
	}

	@Bean
	public WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	public WebSocketHandler webSocketHandler(GreetingProducer producer, HealthKitMetricRepository healthKitMetricRepository) {
		final ObjectMapper mapper = new ObjectMapper();
		Flux.just(new HealthKitMetric( "heartRate", "62.0"))
				.flatMap(healthKitMetricRepository::save)
				.subscribe(log::info);

		return session -> {
			log.info("New session established");
			Flux<WebSocketMessage> map =  session.receive()
					.map(wsm -> wsm.getPayloadAsText())
					.map((text) -> {
						try {
							return mapper.readValue(text, HealthKitMetric.class);
						} catch(Exception e) {
							log.error("Failed to serialize json string: \"{}\" into a new HealthKitMetric class", text);
							return new HealthKitMetric();
						}
					})
					.flatMap(healthKitMetricRepository::save)
					.map(res -> {
						log.info("Saved Item: {}", res);
						return res;
					})
					.flatMap(producer::greet)
					.map(GreetingsResponse::getMessage)
					.map(session::textMessage);
			return session.send(map);
		};
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class GreetingsResponse {
		private String message;
	}


	// This will be represented by the iOS device/client
	@Service
	class GreetingProducer {
		Flux<GreetingsResponse> greet(final HealthKitMetric request) {
			return Flux.fromStream(Stream.generate(() -> new GreetingsResponse("Streaming -> " + request.getMetricName())))
					.delayElements(Duration.ofSeconds(3));
		}
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
		return "metrics";
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return mongoClient();
	}
}