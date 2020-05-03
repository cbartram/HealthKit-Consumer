package com.healthkit.consumer.HealthConsumer.controller.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import com.healthkit.consumer.HealthConsumer.repository.HealthKitMetricRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ObjectMapper mapper;

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
        // Double brace initialization creates anonymous inner class and sets values with syntax sugar
        return new SimpleUrlHandlerMapping() {{
            setUrlMap(Map.of("ws/metrics", webSocketHandler));
            setOrder(10);
        }};
    }


    @Bean
    public WebSocketHandler webSocketHandler(HealthKitMetricRepository healthKitMetricRepository) {
        return session -> {
            log.info("New websocket session established with client: {}", session.getId());
            Flux<WebSocketMessage> map =  session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map((text) -> {
                        try {
                            return mapper.readValue(text, HealthKitMetric.class);
                        } catch(Exception e) {
                            log.error("Failed to serialize json string: \"{}\" into a new HealthKitMetric class", text);
                            return new HealthKitMetric();
                        }
                    })
                    .flatMap(healthKitMetricRepository::save)
                    .map(HealthKitMetric::toString)
                    .map(session::textMessage);
            return session.send(map);
        };
    }
}

