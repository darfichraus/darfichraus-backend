package de.wirvsvirus.darfichrausde.controller;

import de.wirvsvirus.darfichrausde.entity.Restriction;
import de.wirvsvirus.darfichrausde.entity.State;
import de.wirvsvirus.darfichrausde.service.RestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestrictionsController {

    private RestrictionService service;

    @Autowired
    public RestrictionsController(RestrictionService service) {
        this.service = service;
    }

    @GetMapping("/restrictions")
    public List<Restriction> allRestrictions() {
        return service.getRestrictions();
    }

    @GetMapping("/restrictions/zip/{zip}")
    public List<Restriction> allRestrictionsByZip(@PathVariable(name = "zip") int zip) {
        return service.getRestrictions(zip);
    }

    @GetMapping("/restrictions/states/{state}")
    public List<Restriction> allRestrictionsByState(@PathVariable(name = "state") State state) {
        return service.getRestrictions(state);
    }
}
