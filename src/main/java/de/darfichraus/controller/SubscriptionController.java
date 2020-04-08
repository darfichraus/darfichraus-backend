package de.darfichraus.controller;

import de.darfichraus.api.SubscriptionsApi;
import de.darfichraus.model.Subscription;
import de.darfichraus.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteSubscription(@Valid Subscription subscription) {
        HttpStatus status = subscriptionService.delete(subscription) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }
}
