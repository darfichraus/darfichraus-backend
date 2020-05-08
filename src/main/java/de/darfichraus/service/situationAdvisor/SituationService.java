package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.SituationsBySituationTypeResponse;
import de.darfichraus.repository.situationAdvisor.SituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SituationService {

    private final SituationRepository situationRepository;
    private final SituationTypeService situationTypeService;

    @Autowired
    public SituationService(SituationRepository situationRepository,
                            SituationTypeService situationTypeService) {
        this.situationRepository = situationRepository;
        this.situationTypeService = situationTypeService;
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

    public de.darfichraus.model.SituationsBySituationTypeResponse getAllBySituationTypeId(String id) {

        de.darfichraus.model.SituationsBySituationTypeResponse situationsBySituationTypeResponse = new SituationsBySituationTypeResponse();

        de.darfichraus.model.SituationType situationType = this.situationTypeService.getSituationTypeById(id);
        List<de.darfichraus.model.Situation> situations = this.situationRepository.findAllBySituationTypeId(id);

        situationsBySituationTypeResponse.setSituationType(situationType);
        situationsBySituationTypeResponse.setSituations(situations);
        return situationsBySituationTypeResponse;
    }
}
