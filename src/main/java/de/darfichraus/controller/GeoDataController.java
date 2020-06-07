package de.darfichraus.controller;

import de.darfichraus.model.GeoNearRequest;
import de.darfichraus.model.GeoData;
import de.darfichraus.model.Location;
import de.darfichraus.service.GeoDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GeoDataController implements de.darfichraus.api.GeodataApi {

    final GeoDataService geoDataService;

    public GeoDataController(GeoDataService geoDataService) {
        this.geoDataService = geoDataService;
    }

    @Override
    public ResponseEntity<List<GeoData>> findAllAdminAreas(String state) {
        return ResponseEntity.ok(geoDataService.getAdminAreasForState(state));
    }

    @Override
    public ResponseEntity<List<GeoData>> findAllNearGeoData(@Valid GeoNearRequest geoNearRequest) {
        return ResponseEntity.ok(geoDataService.getGeoDataNearLocation(geoNearRequest));
    }

    @Override
    public ResponseEntity<List<GeoData>> findAllRegionalAreasInAdminArea(String adminArea) {
        return ResponseEntity.ok(geoDataService.getRegionalAreasForAdminArea(adminArea));
    }

    @Override
    public ResponseEntity<List<GeoData>> findAllStates() {
        return ResponseEntity.ok(geoDataService.getAllStates());
    }

    @Override
    public ResponseEntity<GeoData> findLocationById(String id) {
        return  ResponseEntity.ok(geoDataService.getGeoDataById(id));
    }

    @Override
    public ResponseEntity<de.darfichraus.model.LocationResponse> findLocationsByZip(String zip) {
        return new ResponseEntity<>(
                this.geoDataService.findLocationsByZip(zip),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.ZipSearchResponse> findLocationsByZipLike(String zip) {

        return new ResponseEntity<>(
                this.geoDataService.findLocationsByZipPart(zip),
                HttpStatus.OK
        );
    }
}
