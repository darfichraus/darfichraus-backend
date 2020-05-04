package de.darfichraus.controller;

import de.darfichraus.api.AdminApi;
import de.darfichraus.dto.Credentials;
import de.darfichraus.model.Restriction;
import de.darfichraus.model.Restriction;
import de.darfichraus.model.CredentialsWithRoles;
import de.darfichraus.model.Subscription;
import de.darfichraus.model.Areal;

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
import java.time.OffsetDateTime;
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
    public ResponseEntity<Void> deleteSituation(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSituationMessage(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSituationMessageType(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSituationReference(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteSituationType(String id) {
        return null;
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
    public ResponseEntity<de.darfichraus.model.Situation> getSituation(String id) {
        return null;
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationMessage> getSituationMessage(String id) {
        return null;
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationMessageType> getSituationMessageType(String id) {
        return null;
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationReference> getSituationReference(String id) {
        return null;
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationType> getSituationType(String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationType>> getSituationTypes() {
        return null;
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
    public ResponseEntity<List<de.darfichraus.model.SituationMessageType>> getAllSituationMessageTypes() {
        return null;
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationMessage>> getAllSituationMessages(@Valid OffsetDateTime lastRequest) {
        return null;
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationReference>> getAllSituationReferences() {
        return null;
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.Situation>> getAllSituations() {
        return null;
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
    public ResponseEntity<Void> updateSituation(de.darfichraus.model.@Valid Situation situation) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateSituationMessage(de.darfichraus.model.@Valid SituationMessage situationMessage) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateSituationMessageType(de.darfichraus.model.@Valid SituationMessageType situationMessageType) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateSituationReference(de.darfichraus.model.@Valid SituationReference situationReference) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateSituationType(de.darfichraus.model.@Valid SituationType situationType) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addAdditionalInformationToCategory(String category, @Valid Map<String, String> requestBody) {
        additionalInformationService.addAdditionalInformationToCategory(category, requestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> addSituation(de.darfichraus.model.@Valid Situation situation) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addSituationMessage(de.darfichraus.model.@Valid SituationMessage situationMessage) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addSituationMessageType(de.darfichraus.model.@Valid SituationMessageType situationMessageType) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addSituationReference(de.darfichraus.model.@Valid SituationReference situationReference) {
        return null;
    }

    @Override
    public ResponseEntity<Void> addSituationType(de.darfichraus.model.@Valid SituationType situationType) {
        return null;
    }
}
