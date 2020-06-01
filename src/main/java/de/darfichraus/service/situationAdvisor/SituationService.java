package de.darfichraus.service.situationAdvisor;

import de.darfichraus.model.Situation;
import de.darfichraus.model.SituationsBySituationTypeResponse;
import de.darfichraus.repository.situationAdvisor.SituationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.darfichraus.model.SituationsResponse;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

    public SituationsResponse getById(String id) {
        return mapToSituationsResponse(this.situationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id + " not found")));
    }

    public SituationsResponse save(de.darfichraus.model.Situation situation) {
        return mapToSituationsResponse(this.situationRepository.save(situation));
    }



    public List<SituationsResponse> getAll() {
        return this.situationRepository.findAll().stream().map(this::mapToSituationsResponse).collect(Collectors.toList());
    }

    public SituationsBySituationTypeResponse getAllBySituationTypeId(String id) {

        SituationsBySituationTypeResponse situationsBySituationTypeResponse = new SituationsBySituationTypeResponse();

        de.darfichraus.model.SituationType situationType = this.situationTypeService.getSituationTypeById(id);
        List<Situation> situations = this.situationRepository.findAllBySituationTypeId(id);

        situationsBySituationTypeResponse.setSituationType(situationType);
        situationsBySituationTypeResponse.setSituations(situations);
        return situationsBySituationTypeResponse;
    }

    private SituationsResponse mapToSituationsResponse(Situation situation) {
        final SituationsResponse situationsResponse = new SituationsResponse();
        BeanUtils.copyProperties(situation,situationsResponse);
        situationsResponse.setSituationType(situationTypeService.getSituationTypeById(situation.getSituationTypeId()));
        return situationsResponse;
    }
}
