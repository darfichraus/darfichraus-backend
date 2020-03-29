package de.darfichraus.service;

import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.mongo.profile.service.MongoProfileService;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserService {


    MongoProfileService mongoProfileService;
    SecretEncryptionConfiguration secretEncryptionConfiguration;
    SecretSignatureConfiguration secretSignatureConfiguration;

    public UserService(MongoProfileService mongoProfileService, SecretEncryptionConfiguration secretEncryptionConfiguration, SecretSignatureConfiguration secretSignatureConfiguration) {
        this.mongoProfileService = mongoProfileService;
        this.secretEncryptionConfiguration = secretEncryptionConfiguration;
        this.secretSignatureConfiguration = secretSignatureConfiguration;
    }

    public String authenticateUser(@Valid UsernamePasswordCredentials credentials) {
        mongoProfileService.validate(credentials, null);
        final CommonProfile userProfile = credentials.getUserProfile();
        if (userProfile != null) {
            JwtGenerator<CommonProfile> generator = new JwtGenerator<>(secretSignatureConfiguration, secretEncryptionConfiguration);
            return generator.generate(userProfile);
        }
        throw new CredentialsException("could not find user" + credentials.getUsername());
    }
}
