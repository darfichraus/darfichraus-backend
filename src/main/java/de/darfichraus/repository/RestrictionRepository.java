package de.darfichraus.repository;


import de.wirvsvirus.darfichrausde.model.Areal;
import de.wirvsvirus.darfichrausde.model.Restriction;
import de.wirvsvirus.darfichrausde.model.RestrictionState;
import de.wirvsvirus.darfichrausde.model.RestrictionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, String> {

    List<Restriction> findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal areal, String arealIdentifier, List<RestrictionState> restrictionStates);

    List<Restriction> findAllByRestrictionType(RestrictionType restrictionType);

}
