package de.darfichraus;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.darfichraus.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class DarfIchRausDEApplication implements CommandLineRunner {


    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RestrictionRepository restrictionRepository;

    public static void main(String[] args) {
        SpringApplication.run(DarfIchRausDEApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*
        final File resource = new File(this.getClass().getClassLoader().getResource("data.json").getFile());
        final List<Restriction> restrictions = objectMapper.readValue(resource, new TypeReference<List<Restriction>>() {});

        restrictionRepository.deleteAll();
        restrictionRepository.saveAll(restrictions);
*/
    }

}
