package com.healthkit.consumer.HealthConsumer.controller.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import reactor.core.publisher.UnicastProcessor;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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

    private static class WebSocketMessageSubscriber {
        private UnicastProcessor<HealthKitMetric> eventPublisher;
        private Optional<HealthKitMetric> lastReceivedEvent = Optional.empty();

        public WebSocketMessageSubscriber(final UnicastProcessor<HealthKitMetric> eventPublisher) {
            this.eventPublisher = eventPublisher;
        }

        public void onNext(final HealthKitMetric event) {
            lastReceivedEvent = Optional.of(event);
            eventPublisher.onNext(event);
        }

        public void onError(Throwable error) {
            log.error("Something went wrong with the websocket message subscriber:", error);
        }

        public void onComplete() {
            lastReceivedEvent.ifPresent(event -> eventPublisher.onNext(new HealthKitMetric("disconnected", "62")));
        }
    }

    private HealthKitMetric toHealthKitMetric(String json) {
        try {
            return mapper.readValue(json, HealthKitMetric.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON:" + json, e);
        }
    }

    private String toJSON(HealthKitMetric event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Bean
    public WebSocketHandler webSocketHandler(UnicastProcessor<HealthKitMetric> eventPublisher, Flux<HealthKitMetric> events, HealthKitMetricRepository healthKitMetricRepository) {
        WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(eventPublisher);
        return session -> {
            log.info("New websocket session established with client: {}", session.getId());
            return session.receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .map(this::toHealthKitMetric)
                    .flatMap(healthKitMetricRepository::save)
                    .doOnNext(subscriber::onNext)
                    .doOnError(subscriber::onError)
                    .doOnComplete(subscriber::onComplete)
                    .zipWith(session.send(Flux.from(events).map(this::toJSON).map(session::textMessage)))
                    .then();
        };
    }
}

