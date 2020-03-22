package de.darfichraus.entity.enums;

public enum RestrictionState {
    NONE("none"), // keine Einschränkung
    RESTRICTION("restriction"), // Einschränkung
    BAN("ban"); // Verbot

    private final String restrictionTypeValue;

    RestrictionState(String restrictionType) {
        this.restrictionTypeValue = restrictionType;
    }

    @Override
    public String toString() {
        return restrictionTypeValue;
    }
}
