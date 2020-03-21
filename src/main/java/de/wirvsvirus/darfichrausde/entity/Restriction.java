package de.wirvsvirus.darfichrausde.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Restriction {

    @Id
    private Long id;

    private Areal areal;
    private RestrictionType restrictionType;
    private LocalDate restrictionStart;
    private int restrictionDuration;
    private String shortDescription;
    private String restrictionDescription;
    private String furtherInformation;

    @OneToMany
    private List<Region> regions;

    public Restriction() {

    }

    public Restriction(Long id, Areal areal, RestrictionType restrictionType, LocalDate restrictionStart, int restrictionDuration, String shortDescription, String restrictionDescription, String furtherInformation, List<Region> regions) {
        this.id = id;
        this.areal = areal;
        this.restrictionType = restrictionType;
        this.restrictionStart = restrictionStart;
        this.restrictionDuration = restrictionDuration;
        this.shortDescription = shortDescription;
        this.restrictionDescription = restrictionDescription;
        this.furtherInformation = furtherInformation;
        this.regions = regions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
