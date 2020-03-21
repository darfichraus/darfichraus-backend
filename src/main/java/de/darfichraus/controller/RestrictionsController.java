package de.darfichraus.controller;

import de.darfichraus.entity.Areal;
import de.darfichraus.entity.Restriction;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(value = "*")
public class RestrictionsController {

    private RestrictionService restrictionService;

    @Autowired
    public RestrictionsController(RestrictionService restrictionService) {
        this.restrictionService = restrictionService;
    }

    @GetMapping("/restrictions")
    public ResponseEntity<List<Restriction>> allRestrictions() {
        return new ResponseEntity<>(
                restrictionService.getRestrictions(),
                HttpStatus.OK
        );
    }

    @GetMapping("/restrictions/{areal}/{arealIdentifier}")
    public ResponseEntity<List<Restriction>> detailsForRestriction(@PathVariable(name = "areal") Areal areal, @PathVariable(name = "arealIdentifier") String arealIdentifier) {
        return new ResponseEntity<>(
                this.restrictionService.getRestrictions(areal, arealIdentifier),
                HttpStatus.OK
        );
    }

    @PostMapping("/restrictions")
    public void newRestriction(@RequestBody Restriction restriction) {
        restrictionService.save(Arrays.asList(restriction));
    }

}
