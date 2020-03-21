package de.darfichraus.entity;

public enum Areal {
    ZIP("zip"),
    STATE("state"),
    COUNTRY("country");

    private final String arealType;

    Areal(String areal) {
        this.arealType = areal;
    }

    @Override
    public String toString() {
        return arealType;
    }
}
