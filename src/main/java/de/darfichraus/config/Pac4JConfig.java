package de.darfichraus.config;

import com.mongodb.MongoClient;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.credentials.password.SpringSecurityPasswordEncoder;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.mongo.profile.service.MongoProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class Pac4JConfig {

    final MongoClient mongoClient;
    @Value("${salt}")
    private String salt;

    public Pac4JConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Bean
    public Config config() {
        HeaderClient jwtClient = new HeaderClient("Authorization", "Bearer", jwtAuthenticator());
        Clients clients = new Clients(jwtClient);
        return new Config(clients);
    }

    @Bean
    public SecretSignatureConfiguration secretSignatureConfiguration() {
        return new SecretSignatureConfiguration(salt);
    }

    @Bean
    public SecretEncryptionConfiguration secretEncryptionConfiguration() {
        return new SecretEncryptionConfiguration(salt, JWEAlgorithm.A256GCMKW, EncryptionMethod.A256GCM);
    }

    @Bean
    public JwtAuthenticator jwtAuthenticator() {
        final JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
        jwtAuthenticator.addEncryptionConfiguration(secretEncryptionConfiguration());
        jwtAuthenticator.addSignatureConfiguration(secretSignatureConfiguration());
        return jwtAuthenticator;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(salt.getBytes());
        return new SpringSecurityPasswordEncoder(new BCryptPasswordEncoder(11, secureRandom));

    }

    @Bean
    public MongoProfileService mongoProfileService() {

        return new MongoProfileService(mongoClient, passwordEncoder());

    }

}
