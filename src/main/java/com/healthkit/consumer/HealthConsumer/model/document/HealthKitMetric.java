package com.healthkit.consumer.HealthConsumer.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@RequiredArgsConstructor
@NoArgsConstructor
public class HealthKitMetric {
    @Id
    private String id;

    @NonNull
    @JsonProperty("metric")
    private String metricName;

    @NonNull
    private String value;
}
