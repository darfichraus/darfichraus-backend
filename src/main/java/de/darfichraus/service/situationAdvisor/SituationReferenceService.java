package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationReference;
import de.darfichraus.repository.situationAdvisor.SituationReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SituationReferenceService {

    private final SituationReferenceRepository situationReferenceRepository;

    @Autowired
    public SituationReferenceService(SituationReferenceRepository situationReferenceRepository) {
        this.situationReferenceRepository = situationReferenceRepository;
    }

    public boolean deleteById(String id) {
        if (!this.situationReferenceRepository.existsById(id)) {
            return false;
        }

        this.situationReferenceRepository.deleteById(id);
        return true;
    }

    public de.darfichraus.model.SituationReference getById(String id) {
        return this.situationReferenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
    }

    public de.darfichraus.model.SituationReference save(SituationReference situationReference) {
        return this.situationReferenceRepository.save(situationReference);
    }
}
