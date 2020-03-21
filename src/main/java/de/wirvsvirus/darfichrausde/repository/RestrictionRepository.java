package de.wirvsvirus.darfichrausde.repository;

import de.wirvsvirus.darfichrausde.entity.Restriction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, Long> {

}
