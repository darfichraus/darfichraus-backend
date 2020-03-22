package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.entity.Restriction;
import de.darfichraus.entity.enums.Areal;
import de.darfichraus.entity.enums.RestrictionState;
import de.darfichraus.entity.enums.RestrictionType;
import de.darfichraus.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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
    public List<Restriction> getRestrictions(RestrictionType restrictionType) {
        List<Restriction> restrictions;
        if (restrictionType == null) {
            restrictions = this.restrictionRepository.findAll();
        } else {
            restrictions = this.restrictionRepository.findAllByRestrictionType(restrictionType);
        }
        return restrictions.stream().filter(restriction -> !restriction.getRestrictionEnd().isBefore(LocalDate.now())).collect(Collectors.toList());
    }

    // get restrictions by areal and areal identifier
    // areal : zip, date, county, country
    public List<Restriction> getRestrictions(final Areal areal, final String arealIdentifier, RestrictionState state) {
        List<Restriction> restrictions = new ArrayList<>();
        Optional<Mapping> possibleMapping = mappingService.getMappingForAreal(areal, arealIdentifier);
        if (!possibleMapping.isPresent()) {
            throw new IllegalArgumentException(MessageFormat.format("{0} was not found for {1}", arealIdentifier, areal));
        }
        final Mapping mapping = possibleMapping.get();
        List<RestrictionState> restrictionStates = Collections.singletonList(state);
        if (state == null) {
            restrictionStates = Arrays.asList(RestrictionState.values());
        }
        switch (areal) {
            case ZIP:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsGreaterThanEqualAndRestrictionStateIn(Areal.ZIP, mapping.getZip(), LocalDate.now(), restrictionStates));
            case COUNTY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsGreaterThanEqualAndRestrictionStateIn(Areal.COUNTY, mapping.getCounty(), LocalDate.now(), restrictionStates));
            case STATE:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsGreaterThanEqualAndRestrictionStateIn(Areal.STATE, mapping.getState(), LocalDate.now(), restrictionStates));
            case COUNTRY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionEndIsGreaterThanEqualAndRestrictionStateIn(Areal.COUNTRY, mapping.getCountry(), LocalDate.now(), restrictionStates));
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
        return this.restrictionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("id: {0} could not be associated with a restriction", id)));
    }

    // delete single restriction
    public void deleteRestrictionById(final String id) {
        this.restrictionRepository.deleteById(id);
    }
}
