package de.darfichraus.entity.enums;

public enum RestrictionType {
    PUBLIC_TRANSPORTATION("Public Transportation"),
    EVENTS_AND_ASSEMBLIES("Events and Assemblies"),
    GASTRONOMY("Gastronomy"),
    PUBLIC_PLACES("Public Places"),
    RETAIL("Retail"),
    CURFEW("Curfew");

    private String restrictionTypeValue;

    RestrictionType(String restrictionTypeValue) {
        this.restrictionTypeValue = restrictionTypeValue;
    }

    @Override
    public String toString() {
        return restrictionTypeValue;
    }
}
