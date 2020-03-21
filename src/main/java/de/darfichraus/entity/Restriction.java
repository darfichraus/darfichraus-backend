package de.darfichraus.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Restriction {

    @Id
    private String id;

    private Areal areal;

    private String arealIdentifier;

    private RestrictionType restrictionType;

    private LocalDate restrictionStart;
    private int restrictionDuration;
    private String shortDescription;
    private String restrictionDescription;
    private String furtherInformation;

    public Restriction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Areal getAreal() {
        return areal;
    }

    public void setAreal(Areal areal) {
        this.areal = areal;
    }

    public RestrictionType getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(RestrictionType restrictionType) {
        this.restrictionType = restrictionType;
    }

    public LocalDate getRestrictionStart() {
        return restrictionStart;
    }

    public void setRestrictionStart(LocalDate restrictionStart) {
        this.restrictionStart = restrictionStart;
    }

    public int getRestrictionDuration() {
        return restrictionDuration;
    }

    public void setRestrictionDuration(int restrictionDuration) {
        this.restrictionDuration = restrictionDuration;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getRestrictionDescription() {
        return restrictionDescription;
    }

    public void setRestrictionDescription(String restrictionDescription) {
        this.restrictionDescription = restrictionDescription;
    }

    public String getFurtherInformation() {
        return furtherInformation;
    }

    public void setFurtherInformation(String furtherInformation) {
        this.furtherInformation = furtherInformation;
    }

    public String getArealIdentifier() {
        return arealIdentifier;
    }

    public void setArealIdentifier(String arealIdentifier) {
        this.arealIdentifier = arealIdentifier;
    }
}
