package de.darfichraus.controller;

import de.darfichraus.service.SubscriptionService;
import de.wirvsvirus.darfichrausde.api.SubscriptionsApi;
import de.wirvsvirus.darfichrausde.model.Areal;
import de.wirvsvirus.darfichrausde.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SubscriptionController implements SubscriptionsApi {

    private SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }


    @Override
    public ResponseEntity<Void> addSubscription(@Valid Subscription subscription) {
        subscriptionService.save(subscription);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @Override
    public ResponseEntity<List<Subscription>> getSubscriptionsByArealAndArealIdentifier(Areal areal, String arealIdentifier) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByArealAndArealIdentifier(areal, arealIdentifier));
    }
}
