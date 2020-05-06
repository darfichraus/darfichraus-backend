package de.darfichraus.service.situationAdvisor;

import de.darfichraus.repository.situationAdvisor.SituationMessageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SituationMessageTypeService {

    private final SituationMessageTypeRepository situationMessageTypeRepository;

    @Autowired
    public SituationMessageTypeService(SituationMessageTypeRepository situationMessageTypeRepository) {
        this.situationMessageTypeRepository = situationMessageTypeRepository;
    }

    public boolean deleteById(String id) {
        if (!this.situationMessageTypeRepository.existsById(id)) {
            return false;
        }

        this.situationMessageTypeRepository.deleteById(id);
        return true;
    }

    public de.darfichraus.model.SituationMessageType findById(String id) {
        return this.situationMessageTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found"));
    }

    public de.darfichraus.model.SituationMessageType save(de.darfichraus.model.SituationMessageType situationMessageType) {
        return this.situationMessageTypeRepository.save(situationMessageType);
    }

    public List<de.darfichraus.model.SituationMessageType> findAll() {
        return this.situationMessageTypeRepository.findAll();
    }
}
