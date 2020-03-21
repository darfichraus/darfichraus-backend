package de.darfichraus.controller;

import de.darfichraus.entity.Areal;
import de.darfichraus.entity.Restriction;
import de.darfichraus.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
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
        return ResponseEntity.ok(restrictionService.getRestrictions());
    }

    @GetMapping("/restrictions/{id}")
    public ResponseEntity<Restriction> getRestrictionById(@PathVariable(name = "id") @Valid String id) {
        return ResponseEntity.ok(restrictionService.getRestriction(id));
    }

    @GetMapping("/restrictions/{areal}/{arealIdentifier}")
    public ResponseEntity<List<Restriction>> detailsForRestriction(@PathVariable(name = "areal") @Valid Areal areal, @Valid @PathVariable(name = "arealIdentifier") String arealIdentifier) {
        return ResponseEntity.ok(this.restrictionService.getRestrictions(areal, arealIdentifier));
    }

    @DeleteMapping("/restrictions/{id}")
    public void deleteRestrictionById(@PathVariable(name = "id") @Valid String id) {
        restrictionService.deleteRestrictionById(id);
    }

    @PostMapping("/restrictions")
    public void newRestriction(@RequestBody @Valid Restriction restriction) {
        restrictionService.save(Collections.singletonList(restriction));
    }

}
