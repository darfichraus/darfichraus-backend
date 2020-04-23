package de.darfichraus.service;

import de.darfichraus.model.AdditionalInformationCategory;
import de.darfichraus.repository.AdditionalInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdditionalInformationService {

    private AdditionalInformationRepository additionalInformationRepository;

    @Autowired
    public AdditionalInformationService(AdditionalInformationRepository additionalInformationRepository) {
        this.additionalInformationRepository = additionalInformationRepository;
    }

    public List<AdditionalInformationCategory> getAdditionalCategories() {
        return additionalInformationRepository.findAll();
    }

    public AdditionalInformationCategory getAdditionalCategory(final String category) {
        return additionalInformationRepository.findByCategory(category).orElseThrow(() -> new IllegalArgumentException("no additional information found for given category"));
    }

    public void addAdditionalInformationToCategory(final String category, final Map<String, String> additionalInformation) {
        final Optional<AdditionalInformationCategory> additionalInformationRepositoryByCategory = additionalInformationRepository.findByCategory(category);
        AdditionalInformationCategory additionalInformationCategory;
        if (additionalInformationRepositoryByCategory.isPresent()) {
            additionalInformationCategory = additionalInformationRepositoryByCategory.get();
            additionalInformationCategory.getAdditionalInformation().putAll(additionalInformation);

        } else {
            additionalInformationCategory = new AdditionalInformationCategory();
            additionalInformationCategory.setAdditionalInformation(additionalInformation);
            additionalInformationCategory.setCategory(category);
        }
        additionalInformationRepository.save(additionalInformationCategory);
    }


}
