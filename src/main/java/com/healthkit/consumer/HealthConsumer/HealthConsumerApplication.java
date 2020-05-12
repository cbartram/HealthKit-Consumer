package com.healthkit.consumer.HealthConsumer;

import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@Slf4j
@SpringBootApplication
public class HealthConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthConsumerApplication.class, args);
	}

	@Bean
	public UnicastProcessor<HealthKitMetric> eventPublisher() {
		return UnicastProcessor.create();
	}

	@Bean
	@DependsOn("eventPublisher")
	public Flux<HealthKitMetric> events(UnicastProcessor<HealthKitMetric> eventPublisher) {
		return eventPublisher
				.replay(1)
				.autoConnect();
	}
}