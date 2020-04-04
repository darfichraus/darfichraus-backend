package de.darfichraus.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.credentials.password.SpringSecurityPasswordEncoder;
import org.pac4j.core.matching.matcher.PathMatcher;
import org.pac4j.core.profile.AnonymousProfile;
import org.pac4j.core.util.JavaSerializationHelper;
import org.pac4j.http.client.direct.DirectBearerAuthClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.mongo.profile.MongoProfile;
import org.pac4j.mongo.profile.service.MongoProfileService;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
public class Pac4JConfig {

    final MongoClient mongoClient;
    @Value("${salt}")
    private String salt;

    @Value("${http.auth.headerName}")
    private String authHeaderName;

    @Value("${API_KEY}")
    private String authHeaderValue;

    public Pac4JConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Bean
    public Config config() {
        HeaderClient headerClient = new HeaderClient(authHeaderName, ((credentials, context) -> {
            final TokenCredentials tokenCredentials = (TokenCredentials) credentials;
            if (!tokenCredentials.getToken().equals(authHeaderValue)) {
                return;
            }
            credentials.setUserProfile(new AnonymousProfile());
        }));


        DirectBearerAuthClient jwtClient = new DirectBearerAuthClient(jwtAuthenticator());
        Clients clients = new Clients(jwtClient, headerClient);
        final Config config = new Config(clients);
        config.addMatcher("excludedPath", new PathMatcher().excludeRegex("^/error$"));
        return config;
    }

    @Bean
    public SecretSignatureConfiguration secretSignatureConfiguration() {
        return new SecretSignatureConfiguration(salt);
    }

    @Bean
    public JwtAuthenticator jwtAuthenticator() {
        final JwtAuthenticator jwtAuthenticator = new JwtAuthenticator();
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
    public MongoProfileServiceWithReadAccess mongoProfileService() {

        return new MongoProfileServiceWithReadAccess(mongoClient, passwordEncoder());

    }

    public class MongoProfileServiceWithReadAccess extends MongoProfileService {

        private JavaSerializationHelper javaSerializationHelper = new JavaSerializationHelper();

        public MongoProfileServiceWithReadAccess(MongoClient mongoClient, PasswordEncoder passwordEncoder) {
            super(mongoClient, passwordEncoder);
        }

        public List<MongoProfile> getUserProfiles() {
            List<MongoProfile> mongoProfiles = new ArrayList<>();
            final MongoCursor<Document> iterator = this.getCollection().find().iterator();

            iterator.forEachRemaining(document -> {
                final MongoProfile mongoProfile = (MongoProfile) javaSerializationHelper.deserializeFromBase64(document.getString(SERIALIZED_PROFILE));
                mongoProfiles.add(mongoProfile);
            });
            return mongoProfiles;
        }

    }

}
