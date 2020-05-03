package com.healthkit.consumer.HealthConsumer.controller;

import com.healthkit.consumer.HealthConsumer.model.HealthKitResponse;
import com.healthkit.consumer.HealthConsumer.repository.HealthKitMetricRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HealthMetricsController {

    @NonNull
    HealthKitMetricRepository healthKitMetricRepository;

    @PostMapping(value = "/health/metrics", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Mono<HealthKitResponse>> findAllMetrics() {
        Mono<HealthKitResponse> metrics = healthKitMetricRepository
                .findAll()
                .collectList()
                .map(list -> new HealthKitResponse(list, 200));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(metrics);
    }

}
