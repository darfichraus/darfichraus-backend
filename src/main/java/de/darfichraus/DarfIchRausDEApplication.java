package de.darfichraus;

import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import de.darfichraus.entity.enums.RestrictionState;
import de.darfichraus.entity.enums.RestrictionType;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.time.LocalDate;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class DarfIchRausDEApplication implements CommandLineRunner {


    @Autowired
    private RestrictionService restrictionService;

    public static void main(String[] args) {
        SpringApplication.run(DarfIchRausDEApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Restriction zipRestriction = new Restriction();
        zipRestriction.setAreal(Areal.ZIP);
        zipRestriction.setArealIdentifier("36124");
        zipRestriction.setRestrictionState(RestrictionState.RESTRICTION);
        zipRestriction.setRestrictionType(RestrictionType.EVENTS_AND_ASSEMBLIES);
        zipRestriction.setRestrictionStart(LocalDate.of(2020, 3, 19));
        zipRestriction.setRestrictionEnd(LocalDate.of(2020, 4, 19));
        zipRestriction.setRecipient("population");
        zipRestriction.setPublisher("government");
        zipRestriction.setShortDescription("Stay the fuck home");
        zipRestriction.setRestrictionDescription("Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim. T-bone porchetta kevin, hamburger boudin chuck ribeye bacon short loin salami picanha capicola cupim ball tip biltong. Beef pastrami shoulder burgdoggen ball tip sausage leberkas fatback pancetta. Meatloaf pork chop ground round boudin, frankfurter venison hamburger cupim cow pig alcatra biltong ball tip turkey kielbasa. Pancetta beef ribs chicken, buffalo rump jerky shankle ground round t-bone short ribs short loin sirloin strip steak.");
        zipRestriction.setFurtherInformation("https://www.rki.de/DE/Home/homepage_node.html");

        Restriction stateRestriction = new Restriction();
        stateRestriction.setAreal(Areal.STATE);
        stateRestriction.setArealIdentifier("Bayern");
        stateRestriction.setRestrictionState(RestrictionState.BAN);
        stateRestriction.setRestrictionType(RestrictionType.GASTRONOMY);
        stateRestriction.setRestrictionStart(LocalDate.of(2020, 3, 19));
        stateRestriction.setRestrictionEnd(LocalDate.of(2020, 4, 19));
        stateRestriction.setRecipient("population");
        stateRestriction.setPublisher("government");
        stateRestriction.setShortDescription("Stay the fuck home");
        stateRestriction.setRestrictionDescription("Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim. T-bone porchetta kevin, hamburger boudin chuck ribeye bacon short loin salami picanha capicola cupim ball tip biltong. Beef pastrami shoulder burgdoggen ball tip sausage leberkas fatback pancetta. Meatloaf pork chop ground round boudin, frankfurter venison hamburger cupim cow pig alcatra biltong ball tip turkey kielbasa. Pancetta beef ribs chicken, buffalo rump jerky shankle ground round t-bone short ribs short loin sirloin strip steak.");
        stateRestriction.setFurtherInformation("https://www.rki.de/DE/Home/homepage_node.html");

        Restriction countryRestriction = new Restriction();
        countryRestriction.setAreal(Areal.COUNTRY);
        countryRestriction.setArealIdentifier("Deutschland");
        countryRestriction.setRestrictionState(RestrictionState.RESTRICTION);
        countryRestriction.setRestrictionType(RestrictionType.RETAIL);
        countryRestriction.setRestrictionStart(LocalDate.of(2020, 3, 19));
        countryRestriction.setRestrictionEnd(LocalDate.of(2020, 4, 19));
        countryRestriction.setRecipient("population");
        countryRestriction.setPublisher("government");
        countryRestriction.setShortDescription("Stay the fuck home");
        countryRestriction.setRestrictionDescription("Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim. T-bone porchetta kevin, hamburger boudin chuck ribeye bacon short loin salami picanha capicola cupim ball tip biltong. Beef pastrami shoulder burgdoggen ball tip sausage leberkas fatback pancetta. Meatloaf pork chop ground round boudin, frankfurter venison hamburger cupim cow pig alcatra biltong ball tip turkey kielbasa. Pancetta beef ribs chicken, buffalo rump jerky shankle ground round t-bone short ribs short loin sirloin strip steak.");
        countryRestriction.setFurtherInformation("https://www.rki.de/DE/Home/homepage_node.html");

        //this.restrictionService.save(zipRestriction);
        //this.restrictionService.save(stateRestriction);
        //this.restrictionService.save(countryRestriction);

    }
}
