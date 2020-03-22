package de.darfichraus.repository;

import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import de.darfichraus.entity.enums.RestrictionState;
import de.darfichraus.entity.enums.RestrictionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, String> {

    List<Restriction> findAllByArealAndArealIdentifierAndRestrictionEndIsGreaterThanEqualAndRestrictionStateInOrderByRestrictionStartDesc(Areal areal, String arealIdentifier, LocalDate restrictionEnd, List<RestrictionState> restrictionStates);

    List<Restriction> findAllByRestrictionTypeOrderByRestrictionStartDesc(RestrictionType restrictionType);

}
