package com.healthkit.consumer.HealthConsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HealthKitProducer {

    @NonNull
    private ObjectMapper mapper;

    public Flux<String> produce(final HealthKitMetric request)  {
        return Flux.fromStream(Stream.generate(() -> {
            try {
                return mapper.writeValueAsString(request);
            } catch(JsonProcessingException e) {
                e.printStackTrace();
                return "failed";
            }
        })).delayElements(Duration.ofSeconds(3));
    }
}
