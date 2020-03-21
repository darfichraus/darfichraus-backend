package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.repository.MappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    private MappingRepository mappingRepository;

    @Autowired
    public MappingService(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    public Mapping getZipStateMappingByZip(String zip) {
        return mappingRepository.findAllByZip(zip).get(0);
    }

    public Mapping getZipStateMappingByCounty(String county) {
        return mappingRepository.findAllByCounty(county).get(0);
    }

    public Mapping getZipStateMappingByState(String state) {
        return mappingRepository.findAllByState(state).get(0);
    }
}
