package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.repository.RestrictionRepository;
import de.wirvsvirus.darfichrausde.model.Areal;
import de.wirvsvirus.darfichrausde.model.Restriction;
import de.wirvsvirus.darfichrausde.model.RestrictionState;
import de.wirvsvirus.darfichrausde.model.RestrictionType;
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
        return sortAndFilterRestrictions(restrictions);
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
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal.ZIP, mapping.getZip(), restrictionStates));
            case COUNTY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal.COUNTY, mapping.getCounty(), restrictionStates));
            case STATE:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal.STATE, mapping.getState(), restrictionStates));
            case COUNTRY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifierAndRestrictionStateIn(Areal.COUNTRY, mapping.getCountry(), restrictionStates));
        }

        return sortAndFilterRestrictions(restrictions);
    }

    private List<Restriction> sortAndFilterRestrictions(List<Restriction> restrictions) {
        return restrictions.stream().filter(restriction -> !restriction.getRestrictionEnd().isBefore(LocalDate.now()))
                .filter(Restriction::getVerified)
                .sorted(Comparator.comparing(Restriction::getRestrictionStart).reversed())
                .collect(Collectors.toList());
    }

    // save restrictions
    public void save(final Restriction restriction) {
        if (restriction.getRestrictionStart().isAfter(restriction.getRestrictionEnd())) {
            throw new IllegalArgumentException("RestrictionStart must be before or equal to RestrictionEnd");
        }
        restriction.setVerified(false);
        restriction.setCreated(LocalDate.now());
        restriction.setModified(LocalDate.now());
        this.restrictionRepository.save(restriction);
    }

    // update restriction
    public void update(final Restriction restriction) {
        if (restriction.getRestrictionStart().isAfter(restriction.getRestrictionEnd())) {
            throw new IllegalArgumentException("RestrictionStart must be before or equal to RestrictionEnd");
        }
        restriction.setModified(LocalDate.now());
        this.restrictionRepository.save(restriction);
    }

    // get single restriction
    public List<Restriction> getAllRestrictions() {
        return this.restrictionRepository.findAll();
    }

    // delete single restriction
    public boolean deleteRestriction(final Restriction restriction) {
        if (!restrictionRepository.existsById(restriction.getId())) {
            return false;
        }
        this.restrictionRepository.delete(restriction);
        return true;
    }
}
