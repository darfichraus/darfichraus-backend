package de.darfichraus.repository;


import de.darfichraus.model.WebResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebResourceRepository extends MongoRepository<WebResource, String> {


}
