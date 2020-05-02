package de.darfichraus.controller;

import de.darfichraus.dto.NearGeoData;
import de.darfichraus.entity.GeoData;
import de.darfichraus.service.GeoDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GeoDataController {

    final GeoDataService geoDataService;

    public GeoDataController(GeoDataService geoDataService) {
        this.geoDataService = geoDataService;
    }

    @GetMapping("/geodata/states")
    public ResponseEntity<List<GeoData>> getStates() {
        return ResponseEntity.ok(geoDataService.getAllStates());
    }

    @GetMapping("/geodata/admin/{state}")
    public ResponseEntity<List<GeoData>> getAdminAreasForState(@PathVariable("state") final String state) {
        return ResponseEntity.ok(geoDataService.getAdminAreasForState(state));
    }

    @GetMapping("/geodata/regional/{admin}")
    public ResponseEntity<List<GeoData>> getRegionalAreasForAdminArea(@PathVariable("admin") final String admin) {
        return ResponseEntity.ok(geoDataService.getRegionalAreasForAdminArea(admin));
    }

    @PostMapping("/geodata/near")
    public ResponseEntity<List<GeoData>> getGeoDataNearLocation(@RequestBody final NearGeoData nearGeoData) {
        return ResponseEntity.ok(geoDataService.getGeoDataNearLocation(nearGeoData));
    }

}
