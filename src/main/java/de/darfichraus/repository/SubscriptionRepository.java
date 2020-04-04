package de.darfichraus.repository;


import de.darfichraus.model.Areal;
import de.darfichraus.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    List<Subscription> findAllByArealAndArealIdentifier(Areal areal, String arealIdentifier);
}
