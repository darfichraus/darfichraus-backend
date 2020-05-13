package de.darfichraus.controller;

import de.darfichraus.entity.HealthCountyInformation;
import de.darfichraus.service.HealthInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthInformationController {

    private final HealthInformationService healthInformationService;

    @Autowired
    public HealthInformationController(HealthInformationService healthInformationService) {
        this.healthInformationService = healthInformationService;
    }

    @GetMapping("/health-information/by-geometry/{geometry}")
    public ResponseEntity<HealthCountyInformation> informationByGeometry(@PathVariable("geometry") String id) {
        return new ResponseEntity<>(
                this.healthInformationService.getByGeometry(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/health-information")
    public ResponseEntity<List<HealthCountyInformation>> getAll() {
        return new ResponseEntity<>(
                this.healthInformationService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/health-information/by-county/{county}")
    public ResponseEntity<List<HealthCountyInformation>> getInformationByCounty(@PathVariable("county") String county) {
        return new ResponseEntity<>(
                this.healthInformationService.getByCounty(county),
                HttpStatus.OK
        );
    }

    @GetMapping("/health-information/by-zip/{zip}")
    public ResponseEntity<de.darfichraus.model.HealthInformationResponse> getInformationByZip(@PathVariable("zip") String zip) {
        return new ResponseEntity<>(
                this.healthInformationService.getByZip(zip),
                HttpStatus.OK
        );
    }

}
