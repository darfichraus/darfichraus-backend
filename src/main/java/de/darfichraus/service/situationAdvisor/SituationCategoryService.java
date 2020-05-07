package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationCategory;
import de.darfichraus.repository.situationAdvisor.SituationCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SituationCategoryService {

    private final SituationCategoryRepository situationCategoryRepository;

    @Autowired
    public SituationCategoryService(SituationCategoryRepository situationCategoryRepository) {
        this.situationCategoryRepository = situationCategoryRepository;
    }

    public de.darfichraus.model.SituationCategory getById(String id) {
        return this.situationCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
    }

    public List<de.darfichraus.model.SituationCategory> getAll() {
        return this.situationCategoryRepository.findAll();
    }

    public boolean deleteById(String id) {
        if (!this.situationCategoryRepository.existsById(id)) {
            return false;
        }

        this.situationCategoryRepository.deleteById(id);
        return true;
    }

    public de.darfichraus.model.SituationCategory save(SituationCategory situationCategory) {
        return this.situationCategoryRepository.save(situationCategory);
    }
}
