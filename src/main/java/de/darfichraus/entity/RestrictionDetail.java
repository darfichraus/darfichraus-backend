package de.darfichraus.entity;

import de.darfichraus.entity.enums.RestrictionState;
import de.darfichraus.entity.enums.RestrictionType;

public class RestrictionDetail {
    private RestrictionState restrictionState;
    private RestrictionType restrictionType;
    private String shortDescription;

    public RestrictionDetail(RestrictionState restrictionState, RestrictionType restrictionType, String shortDescription) {
        this.restrictionState = restrictionState;
        this.restrictionType = restrictionType;
        this.shortDescription = shortDescription;
    }

    public RestrictionState getRestrictionState() {
        return restrictionState;
    }

    public void setRestrictionState(RestrictionState restrictionState) {
        this.restrictionState = restrictionState;
    }

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
