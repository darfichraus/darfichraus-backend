package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.model.Areal;
import de.darfichraus.repository.MappingRepository;
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
        return mappingRepository.findAllByArealAndArealIdentifier(areal.toString().toLowerCase(), arealIdentifier).findFirst();
    }
}
