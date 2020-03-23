package de.darfichraus.repository;

import de.darfichraus.entity.Mapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MappingRepository extends MongoRepository<Mapping, String> {
    Optional<Mapping> findFirstByZip(final String zip);

    Optional<Mapping> findFirstByCounty(final String county);

    Optional<Mapping> findFirstByState(final String state);

    Optional<Mapping> findFirstByCountry(final String country);
}
