package com.adidas.microservices.publicservice.controller;

import com.adidas.microservices.publicservice.entity.Subscription;
import com.adidas.microservices.publicservice.proxy.SubscriptionServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class PublicController {

    private static Logger log = LoggerFactory.getLogger(PublicController.class.getName());

    @Autowired
    private SubscriptionServiceProxy subscriptionServiceProxy;

    @PostMapping("/newsletter-subscription")
    public Long submitSubscription(@Valid @RequestBody Subscription subscription) {
        log.info("Received subscription " + subscription + " to be created");
        return subscriptionServiceProxy.createSubscription(subscription);
    }
}
