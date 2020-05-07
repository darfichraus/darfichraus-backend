package de.darfichraus.repository.situationAdvisor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface SituationMessageRepository extends MongoRepository<de.darfichraus.model.SituationMessage, String> {

    List<de.darfichraus.model.SituationMessage> findAllByModifiedAfter(Instant modified);

    List<de.darfichraus.model.SituationMessage> findAllBySituationId(String id);

    List<de.darfichraus.model.SituationMessage> findAllByAffectedCategories(String id);
}
