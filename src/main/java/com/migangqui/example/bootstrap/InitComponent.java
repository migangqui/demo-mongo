package com.migangqui.example.bootstrap;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Component;

import com.migangqui.example.entity.Event;
import com.migangqui.example.props.IPropertiesService;
import com.migangqui.example.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitComponent {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private IPropertiesService porpertiesService;

	@PostConstruct
	public void init() {
		long startTime = System.currentTimeMillis();

		if (porpertiesService.insertData()) {

			if (porpertiesService.deleteData()) {
				log.info("Deleting data");
				eventRepository.deleteAll();
			}

			log.info("Total object in db {}", eventRepository.count());
			Event event;

			log.info("Inserting data");

			for (int i = 0; i < porpertiesService.getTotalData(); i++) {

				log.info("Registering event A and B {}", i + 1);

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

			for (int i = 0; i < porpertiesService.getFoundData(); i++) {

				log.info("Registering event C {}", i + 1);

				event = new Event();
				event.setName("eventC" + i);
				event.setLocation(new GeoJsonPoint(2.325399, 48.823206));

				eventRepository.save(event);

			}

			log.info("Time in insert data in DB -> {} ms", System.currentTimeMillis() - startTime);

		}

		Point center = new Point(2.320237, 48.821198);
		Double rangeInKilometers = 50.0;
		Double normalizedDistance = new Distance(rangeInKilometers, Metrics.KILOMETERS).getNormalizedValue();

		startTime = System.currentTimeMillis();
		List<Event> events = eventRepository.findByLocationNear(center, normalizedDistance);
		log.info("Time in find {} object betweewn {} -> {} ms", events.size(), eventRepository.count(),
				System.currentTimeMillis() - startTime);

		System.exit(0);

	}

}
