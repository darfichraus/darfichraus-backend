package de.darfichraus.service.situationAdvisor;

import de.darfichraus.repository.situationAdvisor.SituationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SituationTypeService {

    private SituationTypeRepository situationTypeRepository;

    @Autowired
    public SituationTypeService(SituationTypeRepository situationTypeRepository) {
        this.situationTypeRepository = situationTypeRepository;
    }

    public boolean deleteSituationType(String id) {
        if (!this.situationTypeRepository.existsById(id)) {
            return false;
        }
        this.situationTypeRepository.deleteById(id);
        return true;
    }

    public de.darfichraus.model.SituationType getSituationTypeById(String id) {
        return this.situationTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " unknown"));
    }

    public List<de.darfichraus.model.SituationType> getAll() {
        return this.situationTypeRepository.findAll();
    }

    public de.darfichraus.model.SituationType save(de.darfichraus.model.SituationType situationType) {
        return this.situationTypeRepository.save(situationType);
    }
}
