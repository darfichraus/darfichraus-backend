package de.wirvsvirus.darfichrausde.service;

import de.wirvsvirus.darfichrausde.entity.Restriction;
import de.wirvsvirus.darfichrausde.entity.State;
import de.wirvsvirus.darfichrausde.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionService {


    @Autowired
    public RestrictionService(final RestrictionRepository restrictionRepository) {
    }

    public List<Restriction> getRestrictions() {
        return null;
    }

    public List<Restriction> getRestrictions(int zip) {
        return null;
    }

    public List<Restriction> getRestrictions(State state) {
        return null;
    }
}
