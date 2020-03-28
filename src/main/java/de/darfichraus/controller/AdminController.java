package de.darfichraus.controller;

import de.darfichraus.service.RestrictionService;
import de.wirvsvirus.darfichrausde.api.AdminApi;
import de.wirvsvirus.darfichrausde.model.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminController implements AdminApi {

    private RestrictionService restrictionService;

    @Autowired
    public AdminController(RestrictionService restrictionService) {
        this.restrictionService = restrictionService;
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
    public ResponseEntity<Void> updateRestriction(@Valid Restriction restriction) {
        restrictionService.update(restriction);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
