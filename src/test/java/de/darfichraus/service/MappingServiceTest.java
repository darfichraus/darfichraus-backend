package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.model.Areal;
import de.darfichraus.repository.MappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MappingServiceTest {
    @Mock
    MappingRepository mappingRepository;
    @InjectMocks
    MappingService tested;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        final Mapping testMapping = new Mapping();
        when(mappingRepository.findAllByArealAndArealIdentifier(anyString(), anyString())).thenReturn(Stream.of(testMapping));
    }

    @Test
    void testWithCountry() {
        tested.getMappingForAreal(Areal.COUNTRY, "test");
        verify(mappingRepository, times(1)).findAllByArealAndArealIdentifier("country", "test");
    }
}