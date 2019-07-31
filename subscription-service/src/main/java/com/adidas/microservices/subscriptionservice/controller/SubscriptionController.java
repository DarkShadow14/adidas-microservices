package com.adidas.microservices.subscriptionservice.controller;

import com.adidas.microservices.subscriptionservice.entity.Subscription;
import com.adidas.microservices.subscriptionservice.rabbitmq.ISubscriptionProducer;
import com.adidas.microservices.subscriptionservice.service.ISubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private static Logger log = LoggerFactory.getLogger(SubscriptionController.class.getName());

    @Autowired
    private ISubscriptionService subscriptionService;

    @Autowired
    private ISubscriptionProducer subscriptionProducer;

    @PostMapping("/create")
    public Long createSubscription(@RequestBody Subscription subscription) {

        log.info("Received subscription = " + subscription);
        subscriptionService.save(subscription);
        subscriptionProducer.produce(subscription);

        return subscription.getId();
    }
}
