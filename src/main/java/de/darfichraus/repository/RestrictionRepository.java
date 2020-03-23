package de.darfichraus.repository;

import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import de.darfichraus.entity.enums.RestrictionState;
import de.darfichraus.entity.enums.RestrictionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, String> {

    List<Restriction> findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal areal, String arealIdentifier, List<RestrictionState> restrictionStates);

    List<Restriction> findAllByRestrictionType(RestrictionType restrictionType);

}
