package de.darfichraus.entity;

import de.darfichraus.entity.enums.Areal;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Document
public class Restriction {

    @Id
    private String id;

    @NotNull(message = "Please provide an 'areal'")
    private Areal areal;
    @NotNull(message = "Please provide an 'arealIdentifier'")
    private String arealIdentifier;

    @NotNull(message = "Please provide an 'restrictionDetail'")
    private RestrictionDetail restrictionDetail;

    @NotNull(message = "Please provide a valid 'restrictionStart' for the restriction")
    private LocalDate restrictionStart;
    @Future(message = "Please provide a valid 'restrictionEnd' for the restriction")
    private LocalDate restrictionEnd;
    private String recipient;
    private String publisher;
    @NotEmpty(message = "Please provide a 'shortDescription' for the restriction")
    private String shortDescription;
    @NotEmpty(message = "Please provide a 'restrictionDescription' for the restriction")
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

    public RestrictionDetail getRestrictionDetail() {
        return restrictionDetail;
    }

    public void setRestrictionDetail(RestrictionDetail restrictionDetails) {
        this.restrictionDetail = restrictionDetails;
    }

    public LocalDate getRestrictionStart() {
        return restrictionStart;
    }

    public void setRestrictionStart(LocalDate restrictionStart) {
        this.restrictionStart = restrictionStart;
    }

    public LocalDate getRestrictionEnd() {
        return restrictionEnd;
    }

    public void setRestrictionEnd(LocalDate restrictionEnd) {
        this.restrictionEnd = restrictionEnd;
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
