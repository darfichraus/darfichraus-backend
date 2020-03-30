package de.darfichraus.controller;

import de.darfichraus.service.AdditionalInformationService;
import de.darfichraus.service.RestrictionService;
import de.darfichraus.service.UserService;
import de.wirvsvirus.darfichrausde.api.AdminApi;
import de.wirvsvirus.darfichrausde.model.Credentials;
import de.wirvsvirus.darfichrausde.model.Restriction;
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

    @Autowired
    public AdminController(RestrictionService restrictionService, AdditionalInformationService additionalInformationService, UserService userService) {
        this.restrictionService = restrictionService;
        this.additionalInformationService = additionalInformationService;
        this.userService = userService;
    }


    @Override
    public ResponseEntity<Void> deleteRestriction(@Valid Restriction restriction) {
        HttpStatus status = restrictionService.deleteRestriction(restriction) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @Override
    public ResponseEntity<List<Restriction>> getAllRestrictions() {
        return ResponseEntity.ok(restrictionService.getAllRestrictions());
    }

    @Override
    public ResponseEntity<Void> registerUser(@Valid Credentials credentials) {
        userService.create(credentials);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
