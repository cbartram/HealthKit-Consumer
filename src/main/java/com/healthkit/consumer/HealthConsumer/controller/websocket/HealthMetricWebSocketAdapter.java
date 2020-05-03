package com.healthkit.consumer.HealthConsumer.controller.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Component
public class HealthMetricWebSocketAdapter {

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
