package de.darfichraus.repository.situationAdvisor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface SituationMessageRepository extends MongoRepository<de.darfichraus.model.SituationMessage, String> {

    List<de.darfichraus.model.SituationMessage> findAllByModifiedAfter(ZonedDateTime lastModified);
}
