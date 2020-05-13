package de.darfichraus.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "healthCountyInformation")
public class HealthCountyInformation {

    @Id
    private String id;

    @Field("GEN")
    private String gen;

    @Field("BEZ")
    private String bez;

    @Field(name = "EWZ")
    private int ewz;

    @Field(name = "death_rate")
    private double deathRate;

    private int cases;

    private int deaths;

    @Field(name = "cases_per_100k")
    private double casesPer100k;

    @Field(name = "cases_per_population")
    private double casesPerPopulation;

    @Field(name = "BL")
    private String bl;

    private String county;

    @Field(name = "last_update")
    private String lastUpdate;

    @Field(name = "cases7_per_100k")
    private double cases7Per100k;

    private String districtType;

    public HealthCountyInformation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEwz() {
        return ewz;
    }

    public void setEwz(int ewz) {
        this.ewz = ewz;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(double deathRate) {
        this.deathRate = deathRate;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public double getCasesPer100k() {
        return casesPer100k;
    }

    public void setCasesPer100k(double casesPer100k) {
        this.casesPer100k = casesPer100k;
    }

    public double getCasesPerPopulation() {
        return casesPerPopulation;
    }

    public void setCasesPerPopulation(double casesPerPopulation) {
        this.casesPerPopulation = casesPerPopulation;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public double getCases7Per100k() {
        return cases7Per100k;
    }

    public void setCases7Per100k(double cases7Per100k) {
        this.cases7Per100k = cases7Per100k;
    }

    public String getDistrictType() {
        return districtType;
    }

    public void setDistrictType(String districtType) {
        this.districtType = districtType;
    }
}
