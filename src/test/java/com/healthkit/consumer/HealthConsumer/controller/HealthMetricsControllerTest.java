package com.healthkit.consumer.HealthConsumer.controller;

import com.healthkit.consumer.HealthConsumer.model.HealthKitResponse;
import com.healthkit.consumer.HealthConsumer.model.document.HealthKitMetric;
import com.healthkit.consumer.HealthConsumer.repository.HealthKitMetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class HealthMetricsControllerTest {
    @InjectMocks
    private HealthMetricsController controller;

    @Mock
    private HealthKitMetricRepository repository;


    @Test
    public void healthMetricsController_findMetrics_success() {
        MockitoAnnotations.initMocks(this);
        HealthKitMetric metricOne = new HealthKitMetric();
        metricOne.setValue("112.0");
        metricOne.setMetricName("heartRate");
        metricOne.setId("id");
        when(repository.findAll()).thenReturn(Flux.just(metricOne));

        ResponseEntity<Mono<HealthKitResponse>> response = controller.findAllMetrics();
        response.getBody().subscribe(value ->  {
            assertEquals(metricOne, value.getMetrics().get(0));
            assertEquals(200, value.getStatusCode());
        });
    }

}
