package de.wirvsvirus.darfichrausde.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Region {

    @Id
    private Long id;

    private int zip;
    private String name;

    @ManyToOne
    private State state;

    public Region() {

    }

    public Region(Long id, int postalCode, String name, State state) {
        this.id = id;
        this.zip = postalCode;
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
