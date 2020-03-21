package de.darfichraus.entity;

public enum RestrictionType {
    BAN("ban"),
    QUARANTINE("quarantine"),
    NONE("none");


    private final String restrictionTypeValue;

    RestrictionType(String restrictionType) {
        this.restrictionTypeValue = restrictionType;
    }

    @Override
    public String toString() {
        return restrictionTypeValue;
    }
}
