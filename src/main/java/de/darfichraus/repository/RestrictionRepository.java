package de.darfichraus.repository;


import de.darfichraus.model.Areal;
import de.darfichraus.model.Restriction;
import de.darfichraus.model.RestrictionState;
import de.darfichraus.model.RestrictionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, String> {

    List<Restriction> findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal areal, String arealIdentifier, List<RestrictionState> restrictionStates);

    List<Restriction> findAllByRestrictionType(RestrictionType restrictionType);

}
