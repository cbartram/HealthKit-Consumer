package com.healthkit.consumer.HealthConsumer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Event {

    public enum Type {
        CHAT_MESSAGE, USER_JOINED, USER_STATS, USER_LEFT;
    }

    private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @NonNull
    private Type type;

    @NonNull
    private String payload;

    private final int id;
    private final long timestamp;

    @JsonCreator
    public Event(@JsonProperty("type") final Type type, @JsonProperty("payload") final String payload) {
        this.type = type;
        this.payload = payload;
        this.id = ID_GENERATOR.addAndGet(1);
        this.timestamp = System.currentTimeMillis();
    }
}
