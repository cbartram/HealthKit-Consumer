package com.healthkit.consumer.HealthConsumer;

import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

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

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
		return http
				.authorizeExchange()
				.pathMatchers("/ws/**").permitAll()
				.anyExchange().authenticated()
				.and().build();
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User
				.withUsername("user")
				.password(passwordEncoder().encode("{noop}password"))
				.roles("USER")
				.build();
		return new MapReactiveUserDetailsService(user);
	}
}