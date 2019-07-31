package com.adidas.microservices.subscriptionservice.service;

import com.adidas.microservices.subscriptionservice.entity.Subscription;
import com.adidas.microservices.subscriptionservice.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService implements ISubscriptionService {

    private static Logger log = LoggerFactory.getLogger(SubscriptionService.class.getName());

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Long save(Subscription subscription) {
        log.info("Saving subscription = " + subscription);
        subscriptionRepository.save(subscription);
        log.info("Subscription = " + subscription + " saved successfully");
        return subscription.getId();
    }

    @Override
    public Optional<Subscription> findOne(Long id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }
}
