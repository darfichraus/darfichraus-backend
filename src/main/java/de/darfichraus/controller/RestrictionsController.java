package de.darfichraus.controller;

import de.darfichraus.api.RestrictionsApi;
import de.darfichraus.model.Areal;
import de.darfichraus.model.Restriction;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestrictionsController implements RestrictionsApi {

    private RestrictionService restrictionService;

    @Autowired
    public RestrictionsController(RestrictionService restrictionService) {
        this.restrictionService = restrictionService;
    }


    @Override
    public ResponseEntity<Void> addRestriction(@Valid Restriction restriction) {
        restrictionService.save(restriction);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Restriction>> getAllValidRestrictions() {
        return ResponseEntity.ok(restrictionService.getAllValidRestrictions());
    }

    @Override
    public ResponseEntity<List<Restriction>> getRestrictionsByArealAndArealIdentifier(Areal areal, String arealIdentifier) {
        return ResponseEntity.ok(restrictionService.getRestrictions(areal, arealIdentifier));
    }
}
