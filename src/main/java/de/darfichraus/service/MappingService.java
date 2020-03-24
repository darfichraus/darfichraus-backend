package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.repository.MappingRepository;
import de.wirvsvirus.darfichrausde.model.Areal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class MappingService {

    private MappingRepository mappingRepository;

    @Autowired
    public MappingService(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    public Optional<Mapping> getMappingForAreal(final Areal areal, final String arealIdentifier) {
        if (StringUtils.isEmpty(arealIdentifier)) {
            return Optional.empty();
        }
        if (Areal.ZIP.equals(areal)) {
            return mappingRepository.findFirstByZip(arealIdentifier);
        } else if (Areal.COUNTY.equals(areal)) {
            return mappingRepository.findFirstByCounty(arealIdentifier);
        } else if (Areal.STATE.equals(areal)) {
            return mappingRepository.findFirstByState(arealIdentifier);
        } else if (Areal.COUNTRY.equals(areal)) {
            return mappingRepository.findFirstByCountry(arealIdentifier);
        }
        return Optional.empty();
    }
}
