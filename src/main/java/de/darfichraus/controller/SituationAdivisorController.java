package de.darfichraus.controller;

import de.darfichraus.model.SituationCategory;
import de.darfichraus.model.SituationMessagesBySituationCategoryResponse;
import de.darfichraus.model.SituationMessagesBySituationResponse;
import de.darfichraus.service.situationAdvisor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class SituationAdivisorController implements de.darfichraus.api.SituationAdvisorApi {

    private final SituationTypeService situationTypeService;
    private final SituationService situationService;
    private final SituationMessageTypeService situationMessageTypeService;
    private final SituationMessageService situationMessageService;
    private final SituationReferenceService situationReferenceService;
    private final SituationCategoryService situationCategoryService;

    @Autowired
    public SituationAdivisorController(SituationTypeService situationTypeService,
                                       SituationService situationService,
                                       SituationMessageTypeService situationMessageTypeService,
                                       SituationMessageService situationMessageService,
                                       SituationReferenceService situationReferenceService,
                                       SituationCategoryService situationCategoryService) {
        this.situationTypeService = situationTypeService;
        this.situationService = situationService;
        this.situationMessageTypeService = situationMessageTypeService;
        this.situationMessageService = situationMessageService;
        this.situationReferenceService = situationReferenceService;
        this.situationCategoryService = situationCategoryService;
    }

    @Override
    public ResponseEntity<de.darfichraus.model.Situation> getSituation(String id) {
        return new ResponseEntity<>(
                this.situationService.getById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<SituationCategory> getSituationCategory(String id) {
        return new ResponseEntity<>(
                this.situationCategoryService.getById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationMessageResponse> getSituationMessage(String id) {
        return new ResponseEntity<>(
                this.situationMessageService.resolveOne(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationMessageType> getSituationMessageType(String id) {
        return new ResponseEntity<>(
                this.situationMessageTypeService.findById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<SituationMessagesBySituationCategoryResponse>> getSituationMessagesBySituationCategory(String id) {
        return null;
    }


    @Override
    public ResponseEntity<de.darfichraus.model.SituationReference> getSituationReference(String id) {
        return new ResponseEntity<>(
                this.situationReferenceService.getById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationType> getSituationType(String id) {
        return new ResponseEntity<>(
                this.situationTypeService.getSituationTypeById(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationType>> getSituationTypes() {
        return new ResponseEntity<>(
                this.situationTypeService.getAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<SituationCategory>> getAllSituationCategories() {
        return new ResponseEntity<>(
                this.situationCategoryService.getAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<de.darfichraus.model.SituationsBySituationTypeResponse> getAllSituationForSituationType(String id) {
        return new ResponseEntity<>(
                this.situationService.getAllBySituationTypeId(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationMessageType>> getAllSituationMessageTypes() {
        return new ResponseEntity<>(
                this.situationMessageTypeService.findAll(),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationMessageResponse>> getAllSituationMessages(@Valid OffsetDateTime lastRequest) {
        return new ResponseEntity<>(
                this.situationMessageService.resolveAll(lastRequest),
                HttpStatus.OK
        );
    }


    @Override
    public ResponseEntity<SituationMessagesBySituationResponse> getAllSituationMessagesForSituation(String id, @Valid Boolean resolved) {
        return new ResponseEntity<>(
                this.situationMessageService.resolveAllBySituationId(id),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.SituationReference>> getAllSituationReferences() {
        return null;
    }

    @Override
    public ResponseEntity<List<de.darfichraus.model.Situation>> getAllSituations() {
        return new ResponseEntity<>(
                this.situationService.getAll(),
                HttpStatus.OK
        );
    }

}
