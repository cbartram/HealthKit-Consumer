package com.healthkit.consumer.HealthConsumer.repository;

import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HealthKitMetricRepository extends ReactiveCrudRepository<HealthKitMetric, String> {
}
