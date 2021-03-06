package de.darfichraus.service;

import de.darfichraus.dto.Credentials;
import de.darfichraus.entity.Users;
import de.darfichraus.model.CredentialsWithRoles;
import de.darfichraus.model.Role;
import de.darfichraus.repository.UserRepository;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.util.Pac4jConstants;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.mongo.profile.MongoProfile;
import org.pac4j.mongo.profile.service.MongoProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    final MongoProfileService mongoProfileService;
    final SecretSignatureConfiguration secretSignatureConfiguration;
    final ProfileManager profileManager;
    final MailService mailService;
    final UserRepository userRepository;

    public UserService(MongoProfileService mongoProfileService, UserRepository userRepository, SecretSignatureConfiguration secretSignatureConfiguration, ProfileManager profileManager, MailService mailService) {
        this.mongoProfileService = mongoProfileService;
        this.userRepository = userRepository;
        this.secretSignatureConfiguration = secretSignatureConfiguration;
        this.profileManager = profileManager;
        this.mailService = mailService;
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

    public void create(CredentialsWithRoles credentials) {
        if (mongoProfileService.findById(credentials.getUsername()) != null) {
            throw new IllegalArgumentException(MessageFormat.format("User {0} exists already", credentials.getUsername()));
        }
        MongoProfile mongoProfile = new MongoProfile();
        mongoProfile.setId(credentials.getUsername());
        mongoProfile.addAttribute(Pac4jConstants.USERNAME, credentials.getUsername());
        final List<String> listOfRoles = credentials.getRoles().stream().map(Role::toString).collect(Collectors.toList());
        mongoProfile.addRoles(listOfRoles);
        mongoProfileService.create(mongoProfile, credentials.getPassword());
        mailService.sendCredentialsMail(credentials.getUsername(), credentials.getPassword());
    }

    public void changePassword(Credentials credentials) {
        final Optional<CommonProfile> mongoProfile = profileManager.get(false);
        if (!mongoProfile.isPresent()) {
            throw new IllegalArgumentException("could not find profile of user");
        }
        final CommonProfile profile = mongoProfile.get();
        if (!profile.getId().equals(credentials.getUsername())) {
            throw new IllegalArgumentException("users provided did not match");

        }
        final MongoProfile userProfile = mongoProfileService.findById(credentials.getUsername());
        if (userProfile == null) {
            return;
        }
        mongoProfileService.update(userProfile, credentials.getPassword());
    }

    public void changeUser(MongoProfile mongoProfile) {
        final Users user = userRepository.findByUsernameEquals(mongoProfile.getId());
        if (user == null) {
            throw new IllegalArgumentException("there was no existing profile found");
        }
        MongoProfile profileInDb = user.getMongoProfile();
        BeanUtils.copyProperties(mongoProfile, profileInDb, "linkedid");
        user.setMongoProfile(profileInDb);
        userRepository.save(user);
    }

    public List<MongoProfile> getProfiles() {
        List<Users> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(Users::getMongoProfile)
                .collect(Collectors.toList());
    }

    public void delete(String username) {
        mongoProfileService.removeById(username);
    }
}
