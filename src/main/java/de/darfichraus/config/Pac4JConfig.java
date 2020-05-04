package de.darfichraus.config;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import de.darfichraus.entity.Users;
import de.darfichraus.repository.UserRepository;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.password.PasswordEncoder;
import org.pac4j.core.credentials.password.SpringSecurityPasswordEncoder;
import org.pac4j.core.matching.matcher.PathMatcher;
import org.pac4j.core.profile.AnonymousProfile;
import org.pac4j.http.client.direct.DirectBearerAuthClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.mongo.profile.service.MongoProfileService;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
public class Pac4JConfig {

    @Value("${salt}")
    private String salt;

    @Value("${http.auth.headerName}")
    private String authHeaderName;

    @Value("${API_KEY}")
    private String authHeaderValue;

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
        config.addMatcher("excludedPath", new PathMatcher().excludeRegex("^(\\/error)|(\\/actuator\\/.*)|(\\/api\\.yaml)$"));
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
    public MongoProfileService mongoProfileService(final MongoClient mongoClient) {
        return new MongoProfileService(mongoClient, passwordEncoder());
    }

    @Bean
    public UserRepository userRepository(final MongoClient mongoClient, MongoProfileService mongoProfileService) {
        MongoRepositoryFactoryBean<UserRepository, Users, String> myFactory = new MongoRepositoryFactoryBean<>(UserRepository.class);
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, mongoProfileService.getUsersDatabase());
        myFactory.setMongoOperations(new MongoTemplate(simpleMongoDbFactory, buildMongoConverter(simpleMongoDbFactory)));
        myFactory.afterPropertiesSet();
        return myFactory.getObject();
    }

    private MongoConverter buildMongoConverter(SimpleMongoDbFactory factory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MongoCustomConversions conversions = new MongoCustomConversions(Lists.newArrayList(new MongoConverters.MongoProfileToStringConverter(), new MongoConverters.StringToMongoProfileConverter()));

        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        mappingContext.afterPropertiesSet();

        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(conversions);
        converter.setCodecRegistryProvider(factory);
        converter.afterPropertiesSet();
        return converter;

    }


}
