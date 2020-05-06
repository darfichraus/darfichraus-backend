package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.model.Subscription;
import de.darfichraus.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.darfichraus.model.Areal.*;


@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MappingService mappingService;

    @Autowired
    public SubscriptionService(final SubscriptionRepository subscriptionRepository, final MappingService mappingService) {
        this.subscriptionRepository = subscriptionRepository;
        this.mappingService = mappingService;
    }


    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> getSubscriptionsByArealAndArealIdentifier(de.darfichraus.model.Areal areal, String arealIdentifier) {
        List<Subscription> subscriptions = new ArrayList<>();
        Optional<Mapping> possibleMapping = mappingService.getMappingForAreal(areal, arealIdentifier);
        if (!possibleMapping.isPresent()) {
            throw new IllegalArgumentException(MessageFormat.format("{0} was not found for {1}", arealIdentifier, areal));
        }
        final Mapping mapping = possibleMapping.get();
        switch (areal) {
            case ZIP:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(ZIP, mapping.getZip()));
            case COUNTY:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(COUNTY, mapping.getCounty()));
            case STATE:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(STATE, mapping.getState()));
            case COUNTRY:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(COUNTRY, mapping.getCountry()));
                break;
            default:
                return new ArrayList<>();
        }

        return subscriptions;
    }

    public boolean deleteById(String id) {
        if (!subscriptionRepository.existsById(id)) {
            return false;
        }

        this.subscriptionRepository.deleteById(id);
        return true;
    }

}
