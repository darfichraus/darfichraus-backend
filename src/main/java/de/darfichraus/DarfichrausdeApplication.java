package de.darfichraus;

import de.darfichraus.entity.Areal;
import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.RestrictionType;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class DarfichrausdeApplication  implements  CommandLineRunner{


    @Autowired
    private RestrictionService restrictionService;

    public static void main(String[] args) {
        SpringApplication.run(DarfichrausdeApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        List<Restriction> restrictions = new ArrayList<>();

        Restriction restriction = new Restriction();

        restriction.setAreal(Areal.ZIP);
        restriction.setArealIdentifier("83043");
        restriction.setFurtherInformation("http://shit/egal");
        restriction.setRestrictionType(RestrictionType.BAN);

        restrictions.add(restriction);

//        this.restrictionService.save(restrictions);

    }
}
