package de.darfichraus.service;

import de.darfichraus.entity.Areal;
import de.darfichraus.entity.Restriction;
import de.darfichraus.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RestrictionService {

    private RestrictionRepository restrictionRepository;

    @Autowired
    public RestrictionService(final RestrictionRepository restrictionRepository) {
        this.restrictionRepository = restrictionRepository;
    }

    // get all restrictions
    public List<Restriction> getRestrictions() {
        return this.restrictionRepository.findAll();
    }

    // get restrictions by areal and areal identifier
    // areal : zip, date, country
    public List<Restriction> getRestrictions(Areal areal, String arealIdentifier) {
        return this.restrictionRepository.findAllByArealAndArealIdentifier(areal, arealIdentifier);
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
