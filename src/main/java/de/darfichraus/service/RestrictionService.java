package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import de.darfichraus.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    // areal : zip, date, county, country
    public List<Restriction> getRestrictions(final Areal areal, final String arealIdentifier) {
        List<Restriction> restrictions = new ArrayList<>();
        Optional<Mapping> possibleMapping = mappingService.getMappingForAreal(areal, arealIdentifier);
        if (!possibleMapping.isPresent()) {
            throw new IllegalArgumentException(MessageFormat.format("{0} was not found for {1}", arealIdentifier, areal));
        }
        final Mapping mapping = possibleMapping.get();
        switch (areal) {
            case ZIP:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsLessThanEqual(Areal.ZIP, mapping.getZip(), LocalDate.now()));
            case COUNTY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsLessThanEqual(Areal.COUNTY, mapping.getCounty(), LocalDate.now()));
            case STATE:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsLessThanEqual(Areal.STATE, mapping.getState(), LocalDate.now()));
            case COUNTRY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsLessThanEqual(Areal.COUNTRY, mapping.getCountry(), LocalDate.now()));
        }
        return restrictions;
    }

    // save list of restrictions
    public void save(final Restriction restriction) {
        if (restriction.getRestrictionStart().isAfter(restriction.getRestrictionEnd())) {
            throw new IllegalArgumentException("RestrictionStart must be before or equal to RestrictionEnd");
        }
        this.restrictionRepository.save(restriction);
    }

    // get single restriction
    public Restriction getRestriction(final String id) {
        return this.restrictionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("id: {0}could not be associated with a restriction", id)));
    }

    // delete single restriction
    public void deleteRestrictionById(final String id) {
        this.restrictionRepository.deleteById(id);
    }
}
