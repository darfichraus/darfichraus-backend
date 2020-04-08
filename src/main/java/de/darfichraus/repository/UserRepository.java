package de.darfichraus.repository;

import de.darfichraus.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends MongoRepository<Users, String> {

    Users findByUsernameEquals(final String username);
}
