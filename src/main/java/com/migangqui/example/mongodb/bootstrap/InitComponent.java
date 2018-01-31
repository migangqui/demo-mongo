package com.migangqui.example.mongodb.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

import com.migangqui.example.mongodb.entity.Event;
import com.migangqui.example.mongodb.repository.EventRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class InitComponent {

    @Autowired
    private EventRepository eventRepository;

    @PostConstruct
    public void init() {
//        eventRepository.deleteAll();

        log.info("Total object in db {}", eventRepository.count());
        Event event;

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 125_000; i++) {

            log.info("Registering event A and B {}", i + 1);

            event = new Event();
            event.setName("eventA" + i);
            event.setLocation(new GeoJsonPoint(-5.994724, 37.397159));

            eventRepository.save(event);

            event = new Event();
            event.setName("eventB" + i);
            event.setLocation(new GeoJsonPoint(-4.773935, 37.882503));

            eventRepository.save(event);

        }

        for (int i = 0; i < 123; i++) {

            log.info("Registering event C {}", i + 1);

            event = new Event();
            event.setName("eventC" + i);
            event.setLocation(new GeoJsonPoint(2.325399, 48.823206));

            eventRepository.save(event);

        }

        log.info("Time in insert data in DB -> {}", System.currentTimeMillis() - startTime);

        Point center = new Point(2.320237, 48.821198);
        Double rangeInKilometers = 50.0;
        Double normalizedDistance = new Distance(rangeInKilometers, Metrics.KILOMETERS).getNormalizedValue();

        startTime = System.currentTimeMillis();
        List<Event> events = eventRepository.findByLocationNear(center, normalizedDistance);
        log.info("Time in find {} object betweewn {} -> {}", events.size(), eventRepository.count(), System.currentTimeMillis() - startTime);

        System.exit(0);
    }

}
