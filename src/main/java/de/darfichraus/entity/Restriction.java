package de.darfichraus.entity;

import de.darfichraus.entity.enums.Areal;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

@Entity
@Document
public class Restriction {

    @Id
    private String id;

    private Areal areal;
    private String arealIdentifier;

    private List<RestrictionDetail> restrictionDetails;

    private LocalDate restrictionStart;
    private int restrictionDuration;
    private String recipient;
    private String publisher;
    private String shortDescription;
    private String restrictionDescription;
    private String furtherInformation;

    public Restriction() {
        //empty
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

    public String getArealIdentifier() {
        return arealIdentifier;
    }

    public void setArealIdentifier(String arealIdentifier) {
        this.arealIdentifier = arealIdentifier;
    }

    public List<RestrictionDetail> getRestrictionDetails() {
        return restrictionDetails;
    }

    public void setRestrictionDetails(List<RestrictionDetail> restrictionDetails) {
        this.restrictionDetails = restrictionDetails;
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
}
