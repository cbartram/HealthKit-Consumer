package com.healthkit.consumer.HealthConsumer;

import com.healthkit.consumer.HealthConsumer.model.document.Reservation;
import com.healthkit.consumer.HealthConsumer.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Log4j2
@Component
@RequiredArgsConstructor
public class SampleDataInitializer {

    private final ReservationRepository reservationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void go() {

        log.info("Running!");
        var just = Flux.just("Luis", "Josh", "Anna", "Spencer", "Cornelia", "Veronica", "Maduras")
                .map(name -> new Reservation(null, name))
                .flatMap(reservationRepository::save);

        this.reservationRepository.deleteAll()
                .thenMany(just)
                .thenMany(this.reservationRepository.findAll())
                .subscribe(log::info);
    }
}