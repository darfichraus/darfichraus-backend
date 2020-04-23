package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.model.Areal;
import de.darfichraus.model.Restriction;
import de.darfichraus.repository.RestrictionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestrictionServiceTest {


    private final Restriction restriction = new Restriction();
    @Mock
    RestrictionRepository repository;
    @Mock
    MappingService mappingService;
    @InjectMocks
    RestrictionService tested;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        restriction.restrictionStart(LocalDate.MIN);
        restriction.restrictionEnd(LocalDate.MAX);
        restriction.verified(true);
        when(repository.findAllByArealAndArealIdentifier(any(Areal.class), anyString())).thenReturn(Collections.singletonList(restriction));
        Mapping testMapping = new Mapping();
        testMapping.setCountry("Germany");
        testMapping.setState("Bayern");
        testMapping.setCounty("München");
        testMapping.setZip("12345");
        when(mappingService.getMappingForAreal(any(), anyString())).thenReturn(Optional.of(testMapping));
    }

    @Test
    void getRestrictionsForZip() {

        final String arealIdentifier = "12345";
        final List<Restriction> restrictions = tested.getRestrictions(Areal.ZIP, arealIdentifier);
        verify(repository).findAllByArealAndArealIdentifier(eq(Areal.ZIP), anyString());
        verify(repository).findAllByArealAndArealIdentifier(eq(Areal.COUNTY), anyString());
        verify(repository).findAllByArealAndArealIdentifier(eq(Areal.STATE), anyString());
        verify(repository).findAllByArealAndArealIdentifier(eq(Areal.COUNTRY), anyString());
        assertThat(restrictions, hasSize(4));
    }


    @Test
    void getRestrictionsForState() {

        final String arealIdentifier = "Bayern";
        final List<Restriction> restrictions = tested.getRestrictions(Areal.STATE, arealIdentifier);
        verify(repository, never()).findAllByArealAndArealIdentifier(eq(Areal.ZIP), anyString());
        verify(repository, never()).findAllByArealAndArealIdentifier(eq(Areal.COUNTY), anyString());
        verify(repository).findAllByArealAndArealIdentifier(eq(Areal.STATE), anyString());
        verify(repository).findAllByArealAndArealIdentifier(eq(Areal.COUNTRY), anyString());
        assertThat(restrictions, hasSize(2));
    }

    @Test
    void getRestrictionsCheckFilters() {

        final Restriction unverified = restriction;
        unverified.verified(false);
        final Restriction timeNotValid = new Restriction();
        timeNotValid.restrictionEnd(LocalDate.now());
        timeNotValid.restrictionStart(LocalDate.MAX);
        when(repository.findAllByArealAndArealIdentifier(any(Areal.class), anyString())).thenReturn(Collections.singletonList(unverified));
        when(repository.findAllByArealAndArealIdentifier(any(Areal.class), anyString())).thenReturn(Collections.singletonList(timeNotValid));
        final List<Restriction> restrictions = tested.getRestrictions(Areal.STATE, "Bayern");
        assertThat(restrictions, hasSize(0));
    }
}