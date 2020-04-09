package de.darfichraus.repository;


import de.darfichraus.model.Areal;
import de.darfichraus.model.Restriction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, String> {

    List<Restriction> findAllByArealAndArealIdentifier(Areal areal, String arealIdentifier);
}
