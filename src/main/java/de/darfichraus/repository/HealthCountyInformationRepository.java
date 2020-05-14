package de.darfichraus.repository;

import de.darfichraus.entity.HealthCountyInformation;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthCountyInformationRepository extends MongoRepository<HealthCountyInformation, String> {

    @Query("{\"geometry\": \"?0\"}")
    HealthCountyInformation findByGeometry(String id);

    @Query("{\"GEN\": { \"$regex\": \"?0.*\"}}")
    List<HealthCountyInformation> findByCounty(String county, Sort sort);
}
