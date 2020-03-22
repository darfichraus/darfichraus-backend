package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import de.darfichraus.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RestrictionService {

    private RestrictionRepository restrictionRepository;
    private MappingService mappingService;

    @Autowired
    public RestrictionService(final RestrictionRepository restrictionRepository, final MappingService mappingService) {
        this.restrictionRepository = restrictionRepository;
        this.mappingService = mappingService;
    }

    // get all restrictions
    public List<Restriction> getRestrictions() {
        return this.restrictionRepository.findAll();
    }

    // get restrictions by areal and areal identifier
    // areal : zip, date, country
    public List<Restriction> getRestrictions(Areal areal, String arealIdentifier) {
        List<Restriction> restrictions = new ArrayList<>();

        if (areal.equals(Areal.ZIP)) {
            Mapping mapping = mappingService.getZipStateMappingByZip(arealIdentifier);

            restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(areal, mapping.getZip()));
            restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.STATE, mapping.getState()));
        } else if (areal.equals(Areal.COUNTY)) {
            Mapping mapping = mappingService.getZipStateMappingByCounty(arealIdentifier);

            restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(areal, mapping.getCounty()));
            restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.STATE, mapping.getState()));
        } else if (areal.equals(Areal.STATE)) {
            Mapping mapping = mappingService.getZipStateMappingByState(arealIdentifier);

            restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(areal, mapping.getState()));
        }

        restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.COUNTRY, "Germany"));

        return restrictions;
    }

    // save list of restrictions
    public void save(List<Restriction> restrictions) {
        this.restrictionRepository.saveAll(restrictions);
    }

    // get single restriction
    public Restriction getRestriction(String id) {
        return this.restrictionRepository.findById(id).orElse(new Restriction());
    }

    // delete single restriction
    public void deleteRestrictionById(String id) {
        this.restrictionRepository.deleteById(id);
    }
}
