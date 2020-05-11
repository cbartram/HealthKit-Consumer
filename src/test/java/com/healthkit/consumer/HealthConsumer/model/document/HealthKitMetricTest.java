package com.healthkit.consumer.HealthConsumer.model.document;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthKitMetricTest {

    @Test
    public void healthKitMetric_setsAndGetsValues_success() {
        final HealthKitMetric metric = new HealthKitMetric();
        metric.setId("id");
        metric.setMetricName("heartRate");
        metric.setValue("112.0");

        assertEquals("id", metric.getId());
        assertEquals("heartRate", metric.getMetricName());
        assertEquals("112.0", metric.getValue());
    }

    @Test
    public void healthKitMetric_toString_success() {
        final HealthKitMetric metric = new HealthKitMetric();
        metric.setId("id");
        metric.setMetricName("heartRate");
        metric.setValue("112.0");

        assertEquals("HealthKitMetric(id=id, metricName=heartRate, value=112.0)", metric.toString());
    }
}
