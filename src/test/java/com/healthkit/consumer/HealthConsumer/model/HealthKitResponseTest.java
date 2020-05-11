package com.healthkit.consumer.HealthConsumer.model;

import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthKitResponseTest {

    @Test
    public void healthKitResponse_getSetValues_success() {
        final HealthKitResponse response = new HealthKitResponse(Collections.emptyList(), 200);
        assertEquals(Collections.emptyList(), response.getMetrics());
        assertEquals(200, response.getStatusCode());

        response.setMetrics(Collections.singletonList(new HealthKitMetric()));
        response.setStatusCode(400);

        assertEquals(1, response.getMetrics().size());
        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void healthKitResponse_toString_success() {
        final HealthKitResponse response = new HealthKitResponse(Collections.emptyList(), 200);
        assertEquals(Collections.emptyList(), response.getMetrics());
        assertEquals(200, response.getStatusCode());

        assertEquals("", response.toString());
    }
}
