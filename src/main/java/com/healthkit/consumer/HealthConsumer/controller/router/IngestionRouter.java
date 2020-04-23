package com.healthkit.consumer.HealthConsumer.controller.router;

import com.healthkit.consumer.HealthConsumer.controller.handler.IngestionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class IngestionRouter {

    @Bean
    public RouterFunction<ServerResponse> route(IngestionHandler ingestionHandler) {

        return RouterFunctions.route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), ingestionHandler::ingest);
    }
}