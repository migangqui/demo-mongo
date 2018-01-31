package com.migangqui.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Event {

    @Id
    private String id;

    private String name;

    @GeoSpatialIndexed(
            type = GeoSpatialIndexType.GEO_2DSPHERE
    )
    private GeoJsonPoint location;

    // Getters and setters are omitted for brevity
}
