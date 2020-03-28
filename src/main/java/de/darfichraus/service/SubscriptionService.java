package de.darfichraus.service;

import de.darfichraus.entity.Mapping;
import de.darfichraus.repository.SubscriptionRepository;
import de.wirvsvirus.darfichrausde.model.Areal;
import de.wirvsvirus.darfichrausde.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private MappingService mappingService;

    @Autowired
    public SubscriptionService(final SubscriptionRepository subscriptionRepository, final MappingService mappingService) {
        this.subscriptionRepository = subscriptionRepository;
        this.mappingService = mappingService;
    }


    public void save(Subscription subscription) {
        subscription.setCreated(LocalDate.now());
        subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public List<Subscription> getSubscriptionsByArealAndArealIdentifier(Areal areal, String arealIdentifier) {
        List<Subscription> subscriptions = new ArrayList<>();
        Optional<Mapping> possibleMapping = mappingService.getMappingForAreal(areal, arealIdentifier);
        if (!possibleMapping.isPresent()) {
            throw new IllegalArgumentException(MessageFormat.format("{0} was not found for {1}", arealIdentifier, areal));
        }
        final Mapping mapping = possibleMapping.get();
        switch (areal) {
            case ZIP:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(Areal.ZIP, mapping.getZip()));
            case COUNTY:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(Areal.COUNTY, mapping.getCounty()));
            case STATE:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(Areal.STATE, mapping.getState()));
            case COUNTRY:
                subscriptions.addAll(this.subscriptionRepository.findAllByArealAndArealIdentifier(Areal.COUNTRY, mapping.getCountry()));
        }

        return subscriptions;
    }

    public boolean delete(Subscription subscription) {
        if (!subscriptionRepository.existsById(subscription.getId())) {
            return false;
        }
        this.subscriptionRepository.delete(subscription);
        return true;
    }

}
