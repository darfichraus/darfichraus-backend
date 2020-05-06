package de.darfichraus.repository.situationAdvisor;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituationReferenceRepository extends MongoRepository<de.darfichraus.model.SituationReference, String> {
}
