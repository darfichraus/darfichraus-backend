package de.darfichraus.controller;

import de.darfichraus.service.AdditionalInformationService;
import de.wirvsvirus.darfichrausde.api.AdditionalInformationApi;
import de.wirvsvirus.darfichrausde.model.AdditionalInformationCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = "*")
public class AdditionalInformationController implements AdditionalInformationApi {

    private final AdditionalInformationService additionalInformationService;

    public AdditionalInformationController(AdditionalInformationService additionalInformationService) {
        this.additionalInformationService = additionalInformationService;
    }


    @Override
    public ResponseEntity<Void> addAdditionalInformationToCategory(String category, @Valid Map<String, String> requestBody) {
        additionalInformationService.addAdditionalInformationToCategory(category, requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
