package de.darfichraus.repository;

import de.darfichraus.entity.Mapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MappingRepository extends MongoRepository<Mapping, String> {
    List<Mapping> findAllByZip(String zip);

    List<Mapping> findAllByCounty(String county);

    List<Mapping> findAllByState(String state);
}
