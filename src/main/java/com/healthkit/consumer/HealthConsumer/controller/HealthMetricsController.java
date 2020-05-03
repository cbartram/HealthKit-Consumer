package com.healthkit.consumer.HealthConsumer.controller;

import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import com.healthkit.consumer.HealthConsumer.repository.HealthKitMetricRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class HealthMetricsController {

    @NonNull
    HealthKitMetricRepository healthKitMetricRepository;

    @PostMapping(value = "/health/metrics", produces = "application/json", consumes = "application/json")
    public Flux<HealthKitMetric> findAllMetrics() {
        return healthKitMetricRepository.findAll();
    }

}
