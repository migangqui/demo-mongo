package com.migangqui.example.repository;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.migangqui.example.entity.Event;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    @Query("{'location' : {'$nearSphere' : ?0 , '$maxDistance' : ?1}}")
    List<Event> findByLocationNear(Point location, Double distance);

}
