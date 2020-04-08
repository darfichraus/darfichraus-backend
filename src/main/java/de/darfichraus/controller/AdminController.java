package de.darfichraus.controller;

import de.darfichraus.api.AdminApi;
import de.darfichraus.dto.Credentials;
import de.darfichraus.model.Areal;
import de.darfichraus.model.CredentialsWithRoles;
import de.darfichraus.model.Restriction;
import de.darfichraus.model.Subscription;
import de.darfichraus.service.AdditionalInformationService;
import de.darfichraus.service.RestrictionService;
import de.darfichraus.service.SubscriptionService;
import de.darfichraus.service.UserService;
import org.pac4j.mongo.profile.MongoProfile;
import org.pac4j.springframework.annotation.ws.RequireAnyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController implements AdminApi {

    private final RestrictionService restrictionService;
    private final AdditionalInformationService additionalInformationService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public AdminController(RestrictionService restrictionService, AdditionalInformationService additionalInformationService, UserService userService, SubscriptionService subscriptionService) {
        this.restrictionService = restrictionService;
        this.additionalInformationService = additionalInformationService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }


    @Override
    public ResponseEntity<Void> deleteRestriction(@Valid Restriction restriction) {
        HttpStatus status = restrictionService.deleteRestriction(restriction) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteUser(final String email) {
        userService.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> editUser(MongoProfile mongoProfile) {
        userService.changeUser(mongoProfile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequireAnyRole("ROLE_ADMIN")
    @Override
    public ResponseEntity<List<MongoProfile>> getProfiles() {
        return ResponseEntity.ok(userService.getProfiles());
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> registerUser(@Valid CredentialsWithRoles credentialsWithRoles) {
        userService.create(credentialsWithRoles);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> changePassword(@Valid Credentials credentials) {
        userService.changePassword(credentials);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Restriction>> getAllRestrictions() {
        return ResponseEntity.ok(restrictionService.getAllRestrictions());
    }

    @Override
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @Override
    public ResponseEntity<List<Subscription>> getSubscriptionsByArealAndArealIdentifier(Areal areal, String arealIdentifier) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByArealAndArealIdentifier(areal, arealIdentifier));
    }

    @Override
    public ResponseEntity<Void> updateRestriction(@Valid Restriction restriction) {
        restrictionService.update(restriction);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> addAdditionalInformationToCategory(String category, @Valid Map<String, String> requestBody) {
        additionalInformationService.addAdditionalInformationToCategory(category, requestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
