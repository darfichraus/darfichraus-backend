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
import de.darfichraus.service.situationAdvisor.*;
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
    private final SituationTypeService situationTypeService;
    private final SituationService situationService;
    private final SituationMessageTypeService situationMessageTypeService;
    private final SituationMessageService situationMessageService;
    private final SituationReferenceService situationReferenceService;

    @Autowired
    public AdminController(RestrictionService restrictionService,
                           AdditionalInformationService additionalInformationService,
                           UserService userService,
                           SubscriptionService subscriptionService,
                           SituationTypeService situationTypeService,
                           SituationService situationService,
                           SituationMessageTypeService situationMessageTypeService,
                           SituationMessageService situationMessageService,
                           SituationReferenceService situationReferenceService) {
        this.restrictionService = restrictionService;
        this.additionalInformationService = additionalInformationService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.situationTypeService = situationTypeService;
        this.situationService = situationService;
        this.situationMessageTypeService = situationMessageTypeService;
        this.situationMessageService = situationMessageService;
        this.situationReferenceService = situationReferenceService;
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteSituation(String id) {
        HttpStatus status = this.situationService.deleteById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(status);
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteSituationMessage(String id) {
        HttpStatus status = this.situationMessageService.deleteById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteSituationMessageType(String id) {
        HttpStatus status = this.situationMessageTypeService.deleteById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteSituationReference(String id) {
        HttpStatus status = this.situationReferenceService.deleteById(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteSituationType(String id) {
        HttpStatus status = this.situationTypeService.deleteSituationType(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
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
    public ResponseEntity<Void> addAdditionalInformationToCategory(String category, @Valid Map<String, String> requestBody) {
        return null;
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.Situation> addSituation(de.darfichraus.model.@Valid Situation situation) {
        return new ResponseEntity<>(
                this.situationService.save(situation),
                HttpStatus.OK
        );
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.SituationMessage> addSituationMessage(de.darfichraus.model.@Valid SituationMessage situationMessage) {
        return new ResponseEntity<>(
                this.situationMessageService.save(situationMessage),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationMessageType> addSituationMessageType(de.darfichraus.model.@Valid SituationMessageType situationMessageType) {
        return new ResponseEntity<>(
                this.situationMessageTypeService.save(situationMessageType),
                HttpStatus.OK
        );
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.SituationReference> addSituationReference(de.darfichraus.model.@Valid SituationReference situationReference) {
        return new ResponseEntity<>(
                this.situationReferenceService.save(situationReference),
                HttpStatus.OK
        );
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.SituationType> addSituationType(de.darfichraus.model.@Valid SituationType situationType) {
        return new ResponseEntity<>(
                this.situationTypeService.save(situationType),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> changePassword(@Valid Credentials credentials) {
        userService.changePassword(credentials);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteRestriction(String id) {
        return null;
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
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.Situation> updateSituation(de.darfichraus.model.@Valid Situation situation) {
        return new ResponseEntity<>(
                this.situationService.save(situation),
                HttpStatus.OK
        );
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.SituationMessage> updateSituationMessage(de.darfichraus.model.@Valid SituationMessage situationMessage) {
        return new ResponseEntity<>(
                this.situationMessageService.save(situationMessage),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationMessageType> updateSituationMessageType(de.darfichraus.model.@Valid SituationMessageType situationMessageType) {
        return new ResponseEntity<>(
                this.situationMessageTypeService.save(situationMessageType),
                HttpStatus.OK
        );
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.SituationReference> updateSituationReference(de.darfichraus.model.@Valid SituationReference situationReference) {
        return new ResponseEntity<>(
                this.situationReferenceService.save(situationReference),
                HttpStatus.OK
        );
    }

    @Override
    @RequireAnyRole("ROLE_ADMIN")
    public ResponseEntity<de.darfichraus.model.SituationType> updateSituationType(de.darfichraus.model.@Valid SituationType situationType) {
        return new ResponseEntity<>(
                this.situationTypeService.save(situationType),
                HttpStatus.OK
        );
    }

}
