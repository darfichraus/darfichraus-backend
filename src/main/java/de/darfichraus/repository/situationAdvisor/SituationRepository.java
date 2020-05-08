package de.darfichraus.repository.situationAdvisor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SituationRepository extends MongoRepository<de.darfichraus.model.Situation, String> {
    List<de.darfichraus.model.Situation> findAllBySituationTypeId(String id);
}

