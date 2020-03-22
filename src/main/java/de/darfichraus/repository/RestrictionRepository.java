package de.darfichraus.repository;

import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestrictionRepository extends MongoRepository<Restriction, String> {

    List<Restriction> findAllByArealAndArealIdentifierAndRestrictionEndIsLessThanEqual(Areal areal, String arealIdentifier, LocalDate restrictionEnd);

}
