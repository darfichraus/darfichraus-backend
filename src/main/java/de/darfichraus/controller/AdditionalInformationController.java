package de.darfichraus.controller;

import de.darfichraus.api.AdditionalInformationApi;
import de.darfichraus.model.AdditionalInformationCategory;
import de.darfichraus.service.AdditionalInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdditionalInformationController implements AdditionalInformationApi {

    private final AdditionalInformationService additionalInformationService;

    public AdditionalInformationController(AdditionalInformationService additionalInformationService) {
        this.additionalInformationService = additionalInformationService;
    }

    @Override
    public ResponseEntity<List<AdditionalInformationCategory>> getAdditionalInformation() {
        return ResponseEntity.ok(additionalInformationService.getAdditionalCategories());
    }

    @Override
    public ResponseEntity<AdditionalInformationCategory> getAdditionalInformationForCategory(String category) {
        return ResponseEntity.ok(additionalInformationService.getAdditionalCategory(category));
    }
}
