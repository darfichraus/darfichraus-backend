package de.darfichraus.service;

import de.wirvsvirus.darfichrausde.model.Credentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.util.Pac4jConstants;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.mongo.profile.MongoProfile;
import org.pac4j.mongo.profile.service.MongoProfileService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {


    MongoProfileService mongoProfileService;
    SecretSignatureConfiguration secretSignatureConfiguration;

    public UserService(MongoProfileService mongoProfileService, SecretSignatureConfiguration secretSignatureConfiguration) {
        this.mongoProfileService = mongoProfileService;
        this.secretSignatureConfiguration = secretSignatureConfiguration;
    }

    public String authenticateUser(@Valid UsernamePasswordCredentials credentials) {
        mongoProfileService.validate(credentials, null);
        final CommonProfile userProfile = credentials.getUserProfile();
        if (userProfile != null) {
            JwtGenerator<CommonProfile> generator = new JwtGenerator<>(secretSignatureConfiguration);
            return generator.generate(userProfile);
        }
        throw new CredentialsException("could not find user" + credentials.getUsername());
    }

    public void create(Credentials credentials) {

        MongoProfile mongoProfile = new MongoProfile();
        mongoProfile.setId(credentials.getUsername());
        mongoProfile.addAttribute(Pac4jConstants.USERNAME, credentials.getUsername());
        mongoProfileService.create(mongoProfile, credentials.getPassword());

    }
}
