package de.darfichraus.service.situationAdvisor;

import de.darfichraus.repository.situationAdvisor.SituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SituationService {

    private final SituationRepository situationRepository;

    @Autowired
    public SituationService(SituationRepository situationRepository) {
        this.situationRepository = situationRepository;
    }

    public boolean deleteById(String id) {
        if (!this.situationRepository.existsById(id)) {
            return false;
        }

        this.situationRepository.deleteById(id);
        return true;
    }

    public de.darfichraus.model.Situation getById(String id) {
        return this.situationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
    }

    public de.darfichraus.model.Situation save(de.darfichraus.model.Situation situation) {
        return this.situationRepository.save(situation);
    }

    public List<de.darfichraus.model.Situation> getAll() {
        return this.situationRepository.findAll();
    }
}
