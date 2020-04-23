package de.darfichraus.service;

import de.darfichraus.model.AdditionalInformationCategory;
import de.darfichraus.repository.AdditionalInformationRepository;
import net.bytebuddy.utility.RandomString;
import org.assertj.core.util.Maps;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdditionalInformationServiceTest {

    @Mock
    AdditionalInformationRepository repository;
    @InjectMocks
    AdditionalInformationService tested;
    private AdditionalInformationCategory additionalInformationCategory = new AdditionalInformationCategory();
    private String category = RandomString.make(3);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        additionalInformationCategory.category(category);
        additionalInformationCategory.putAdditionalInformationItem("1", "test");
        when(repository.findByCategory(category)).thenReturn(Optional.of(additionalInformationCategory));
        when(repository.findByCategory(not(eq(category)))).thenReturn(Optional.empty());
        when(repository.save(Mockito.any(AdditionalInformationCategory.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void knownCategoryAdd() {
        final String newKey = "2";
        tested.addAdditionalInformationToCategory(category, Maps.newHashMap(newKey, "c"));
        Assert.assertTrue(additionalInformationCategory.getAdditionalInformation().containsKey(newKey));
        verify(repository).save(additionalInformationCategory);
    }

    @Test
    void unknownCategoryAdd() {
        final String newKey = "2";
        tested.addAdditionalInformationToCategory("new", Maps.newHashMap(newKey, "c"));
        final AdditionalInformationCategory savedCat = verify(repository).save(not(eq(additionalInformationCategory)));
        Assert.assertNotEquals(savedCat, additionalInformationCategory);
    }

    @Test
    void knownCategoryGet() {
        final AdditionalInformationCategory additionalCategory = tested.getAdditionalCategory(category);
        Assert.assertEquals(additionalCategory, additionalInformationCategory);
    }

    @Test
    void unknownCategoryGet() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tested.getAdditionalCategory("new");
        });
    }
}