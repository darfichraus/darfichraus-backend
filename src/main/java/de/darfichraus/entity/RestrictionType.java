package de.darfichraus.entity;

public enum RestrictionType {
    PUBLIC_TRANSPORTATION("Public Transportation", "Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim."),
    EVENTS_AND_ASSEMBLIES("Events and Assemblies", "Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim."),
    GASTRONOMY("Gastronomy", "Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim."),
    PUBLIC_PLACES("Public Places", "Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim."),
    RETAIL("Retail", "Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim."),
    CURFEW("Curfew", "Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim.");

    private String restrictionTypeValue;
    private String shortDescription;

    RestrictionType(String restrictionTypeValue, String shortDescription) {
        this.restrictionTypeValue = restrictionTypeValue;
        this.shortDescription = shortDescription;
    }

}
