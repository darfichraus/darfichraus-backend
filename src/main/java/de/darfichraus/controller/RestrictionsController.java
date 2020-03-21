package de.darfichraus.controller;

import de.darfichraus.entity.Restriction;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestrictionsController {

    private RestrictionService restrictionService;

    @Autowired
    public RestrictionsController(RestrictionService restrictionService) {
        this.restrictionService = restrictionService;
    }

    @GetMapping("/restrictions")
    public ResponseEntity allRestrictions() {

        return this.filterByArealIdentifier("");
    }

    @GetMapping("/restrictions/{id}")
    public ResponseEntity detailsForRestriction(@PathVariable String id) {
        return new ResponseEntity<>(
                this.restrictionService.getRestriction(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/restrictions/zip/{zip}")
    public ResponseEntity allRestrictionsByZip(@PathVariable String zip) {
        return this.filterByArealIdentifier(zip);
    }

    @GetMapping("/restrictions/states/{state}")
    public ResponseEntity allRestrictionsByState(@PathVariable String state) {
        return this.filterByArealIdentifier(state);
    }

    private ResponseEntity<List<Restriction>> filterByArealIdentifier(String arealIdentifier) {

        List<Restriction> restrictions;
        try {
            restrictions = arealIdentifier.isEmpty() ? this.restrictionService.getRestrictions() : this.restrictionService.getRestrictions(arealIdentifier);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(
                restrictions,
                HttpStatus.OK
        );
    }

}
