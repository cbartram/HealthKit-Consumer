package com.healthkit.consumer.HealthConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@SpringBootApplication
public class HealthConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthConsumerApplication.class, args);
	}


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class GreetingsResponse {
		private String message;
	}


	// This will be represented by the iOS device/client
	@Service
	public class GreetingProducer {

		@Autowired
		private ObjectMapper mapper;


		public Flux<String> greet(final HealthKitMetric request)  {
			return Flux.fromStream(Stream.generate(() -> {
				try {
					return mapper.writeValueAsString(request);
				} catch(JsonProcessingException e) {
					e.printStackTrace();
					return "failed";
				}
			}))
					.delayElements(Duration.ofSeconds(3));
		}
	}
}