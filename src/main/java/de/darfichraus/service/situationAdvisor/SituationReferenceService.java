package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationReference;
import de.darfichraus.repository.situationAdvisor.SituationReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    public List<de.darfichraus.model.SituationReference> resolveIds(List<String> documents) {

        List<SituationReference> references = new ArrayList<>();
        documents.forEach(id -> {
            try {
                references.add(this.getById(id));
            } catch (EntityNotFoundException enfe) {
                // ToDo: Logging
            }
        });
        
        return references;
    }
}
