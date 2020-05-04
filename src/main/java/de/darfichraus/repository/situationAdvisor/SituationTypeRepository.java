package de.darfichraus.repository.situationAdvisor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SituationTypeRepository extends MongoRepository<de.darfichraus.model.SituationType, String> {
}
