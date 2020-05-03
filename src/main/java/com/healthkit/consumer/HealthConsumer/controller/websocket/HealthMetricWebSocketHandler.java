package com.healthkit.consumer.HealthConsumer.controller.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthkit.consumer.HealthConsumer.HealthConsumerApplication;
import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import com.healthkit.consumer.HealthConsumer.repository.HealthKitMetricRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Flux;

import java.util.Map;

@Log4j2
@Component
public class HealthMetricWebSocketHandler {
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
        // Double brace initialization creates anonymous inner class and sets values with syntax sugar
        return new SimpleUrlHandlerMapping() {{
            setUrlMap(Map.of("ws/greetings", webSocketHandler));
            setOrder(10);
        }};
    }


    @Bean
    public WebSocketHandler webSocketHandler(HealthConsumerApplication.GreetingProducer producer, HealthKitMetricRepository healthKitMetricRepository) {
        final ObjectMapper mapper = new ObjectMapper();
//		Flux.just(new HealthKitMetric( "heartRate", "62.0"))
//				.flatMap(healthKitMetricRepository::save)
//				.subscribe(log::info);

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
                    .map(HealthConsumerApplication.GreetingsResponse::getMessage)
                    .map(session::textMessage);
            return session.send(map);
        };
    }
}

