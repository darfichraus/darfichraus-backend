package de.darfichraus.repository;


import de.darfichraus.model.AdditionalInformationCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdditionalInformationRepository extends MongoRepository<AdditionalInformationCategory, String> {

    Optional<AdditionalInformationCategory> findByCategory(String category);
}
