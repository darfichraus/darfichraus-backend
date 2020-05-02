package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.model.Areal;
import de.darfichraus.model.Restriction;
import de.darfichraus.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RestrictionService {

    private final RestrictionRepository restrictionRepository;
    private final MappingService mappingService;

    @Autowired
    public RestrictionService(final RestrictionRepository restrictionRepository, final MappingService mappingService) {
        this.restrictionRepository = restrictionRepository;
        this.mappingService = mappingService;
    }

    // get all restrictions
    public List<Restriction> getAllValidRestrictions() {
        return sortAndFilterRestrictions(this.restrictionRepository.findAll());
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
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.ZIP, mapping.getZip()));
            case COUNTY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.COUNTY, mapping.getCounty()));
            case STATE:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.STATE, mapping.getState()));
            case COUNTRY:
                restrictions.addAll(this.restrictionRepository.findAllByArealAndArealIdentifier(Areal.COUNTRY, mapping.getCountry()));
                break;
            default:
                return new ArrayList<>();
        }

        return sortAndFilterRestrictions(restrictions);
    }

    private List<Restriction> sortAndFilterRestrictions(List<Restriction> restrictions) {

        final int maxWords = 20;
        return restrictions.stream()
                .filter(restriction -> !restriction.getRestrictionEnd().isBefore(LocalDate.now()))
                .filter(Restriction::getVerified)
                .peek(
                        element -> {
                            if (element.getRestrictionDescription() == null || element.getRestrictionDescription().isEmpty()) {
                                return;
                            }

                            int copyRange = maxWords;
                            String[] textAsList = element.getRestrictionDescription().split(" ");
                            String addEllipsisMarker = " [EOT]";
                            if (textAsList.length < copyRange) {
                                copyRange = textAsList.length;
                                addEllipsisMarker = "";
                            }
                            element.setRestrictionDescription(String.join(" ", Arrays.copyOfRange(textAsList, 0, copyRange)) + addEllipsisMarker);
                        }
                )
                .sorted(Comparator.comparing(Restriction::getRestrictionStart).reversed())
                .collect(Collectors.toList());
    }

    // save restrictions
    public void save(final Restriction restriction) {
        if (restriction.getRestrictionStart().isAfter(restriction.getRestrictionEnd())) {
            throw new IllegalArgumentException("RestrictionStart must be before or equal to RestrictionEnd");
        }
        restriction.setVerified(false);
        this.restrictionRepository.save(restriction);
    }

    // update restriction
    public void update(final Restriction restriction) {
        if (restriction.getRestrictionStart().isAfter(restriction.getRestrictionEnd())) {
            throw new IllegalArgumentException("RestrictionStart must be before or equal to RestrictionEnd");
        }
        this.restrictionRepository.save(restriction);
    }

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

    public Restriction getRestrictionById(String id) {
        return this.restrictionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }
}
