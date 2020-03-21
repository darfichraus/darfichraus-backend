package de.darfichraus.service;

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

    public List<Restriction> getRestrictions() {
        return this.restrictionRepository.findAll();
    }

    public List<Restriction> getRestrictions(String identifier) {
        return this.restrictionRepository.findAllByArealIdentifier(identifier);
    }

    public void save(List<Restriction> restrictions) {
        this.restrictionRepository.saveAll(restrictions);
    }

    public Restriction getRestriction(String id) {
        return this.restrictionRepository.findById(id).orElse(new Restriction());
    }
}
