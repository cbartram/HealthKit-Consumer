package com.healthkit.consumer.HealthConsumer.model;

import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HealthKitResponse {
    private List<HealthKitMetric> metrics;
    private int statusCode;
}
