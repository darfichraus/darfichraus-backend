package de.darfichraus;

import de.darfichraus.entity.*;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        List<RestrictionDetail> restrictionDetails = new ArrayList<>();
        restrictionDetails.add(new RestrictionDetail(RestrictionState.RESTRICTION, RestrictionType.EVENTS_AND_ASSEMBLIES, "Maximum of 100 persons"));
        restrictionDetails.add(new RestrictionDetail(RestrictionState.BAN, RestrictionType.PUBLIC_TRANSPORTATION, "No public transportation anymore"));
        restrictionDetails.add(new RestrictionDetail(RestrictionState.RESTRICTION, RestrictionType.GASTRONOMY, "Only delivery restaurants are allowed to open"));


        List<Restriction> restrictions = new ArrayList<>();

        Restriction zipRestriction = new Restriction();
        zipRestriction.setAreal(Areal.ZIP);
        zipRestriction.setArealIdentifier("36124");
        zipRestriction.setRestrictionDetails(restrictionDetails);
        zipRestriction.setRestrictionStart(LocalDate.of(2020, 3, 19));
        zipRestriction.setRestrictionDuration(15);
        zipRestriction.setRecipient("population");
        zipRestriction.setPublisher("government");
        zipRestriction.setShortDescription("Stay the fuck home");
        zipRestriction.setRestrictionDescription("Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim. T-bone porchetta kevin, hamburger boudin chuck ribeye bacon short loin salami picanha capicola cupim ball tip biltong. Beef pastrami shoulder burgdoggen ball tip sausage leberkas fatback pancetta. Meatloaf pork chop ground round boudin, frankfurter venison hamburger cupim cow pig alcatra biltong ball tip turkey kielbasa. Pancetta beef ribs chicken, buffalo rump jerky shankle ground round t-bone short ribs short loin sirloin strip steak.");
        zipRestriction.setFurtherInformation("https://www.rki.de/DE/Home/homepage_node.html");

        Restriction stateRestriction = new Restriction();
        stateRestriction.setAreal(Areal.STATE);
        stateRestriction.setArealIdentifier("Bayern");
        stateRestriction.setRestrictionDetails(restrictionDetails);
        stateRestriction.setRestrictionStart(LocalDate.of(2020, 3, 19));
        stateRestriction.setRestrictionDuration(15);
        stateRestriction.setRecipient("population");
        stateRestriction.setPublisher("government");
        stateRestriction.setShortDescription("Stay the fuck home");
        stateRestriction.setRestrictionDescription("Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim. T-bone porchetta kevin, hamburger boudin chuck ribeye bacon short loin salami picanha capicola cupim ball tip biltong. Beef pastrami shoulder burgdoggen ball tip sausage leberkas fatback pancetta. Meatloaf pork chop ground round boudin, frankfurter venison hamburger cupim cow pig alcatra biltong ball tip turkey kielbasa. Pancetta beef ribs chicken, buffalo rump jerky shankle ground round t-bone short ribs short loin sirloin strip steak.");
        stateRestriction.setFurtherInformation("https://www.rki.de/DE/Home/homepage_node.html");

        Restriction countryRestriction = new Restriction();
        countryRestriction.setAreal(Areal.COUNTRY);
        countryRestriction.setArealIdentifier("Germany");
        countryRestriction.setRestrictionDetails(restrictionDetails);
        countryRestriction.setRestrictionStart(LocalDate.of(2020, 3, 19));
        countryRestriction.setRestrictionDuration(15);
        countryRestriction.setRecipient("population");
        countryRestriction.setPublisher("government");
        countryRestriction.setShortDescription("Stay the fuck home");
        countryRestriction.setRestrictionDescription("Bacon ipsum dolor amet shankle turkey corned beef bresaola, boudin filet mignon short loin fatback alcatra pastrami jerky kevin rump cupim. T-bone porchetta kevin, hamburger boudin chuck ribeye bacon short loin salami picanha capicola cupim ball tip biltong. Beef pastrami shoulder burgdoggen ball tip sausage leberkas fatback pancetta. Meatloaf pork chop ground round boudin, frankfurter venison hamburger cupim cow pig alcatra biltong ball tip turkey kielbasa. Pancetta beef ribs chicken, buffalo rump jerky shankle ground round t-bone short ribs short loin sirloin strip steak.");
        countryRestriction.setFurtherInformation("https://www.rki.de/DE/Home/homepage_node.html");

        restrictions.add(zipRestriction);
        restrictions.add(stateRestriction);
        restrictions.add(countryRestriction);

        //this.restrictionService.save(restrictions);

    }
}
