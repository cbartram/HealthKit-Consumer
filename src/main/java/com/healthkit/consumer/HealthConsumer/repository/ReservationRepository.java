package com.healthkit.consumer.HealthConsumer.repository;

import com.healthkit.consumer.HealthConsumer.model.document.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReservationRepository extends ReactiveCrudRepository<Reservation, String> {
}
