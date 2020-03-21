package de.darfichraus.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mapping {
    @Id
    private String id;

    private String zip;
    private String city;
    private String county;
    private String state;
    private String country;

    public Mapping() {
    }

    public Mapping(String id, String zip, String state, String county, String country) {
        this.id = id;
        this.zip = zip;
        this.state = state;
        this.county = county;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
