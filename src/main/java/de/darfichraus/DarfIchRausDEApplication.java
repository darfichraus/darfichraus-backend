package de.darfichraus;

import de.darfichraus.service.GeoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@EnableMongoAuditing
@PropertySource(value = "file:/etc/appData/appData.properties", ignoreResourceNotFound = true)
public class DarfIchRausDEApplication implements CommandLineRunner {


    @Autowired
    GeoDataService geoDataService;

    public static void main(String[] args) {
        SpringApplication.run(DarfIchRausDEApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //use this for simple command line tests
    }

}
