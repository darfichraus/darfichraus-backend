package de.darfichraus.repository;

import de.darfichraus.entity.Mapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface MappingRepository extends MongoRepository<Mapping, String> {
    @Query(value = "{?0 : ?1}")
    Stream<Mapping> findAllByArealAndArealIdentifier(String areal, String arealIdentifier);

}
