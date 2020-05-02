package de.darfichraus.entity;

import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

@Document(collection = "geodata")
public class GeoData {

    GeoJsonMultiPolygon geometry;
    @Id
    private String id;
    private Map<String, Object> properties;


}
