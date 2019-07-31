package com.adidas.microservices.subscriptionservice.service;

import com.adidas.microservices.subscriptionservice.entity.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ISubscriptionService {

    Long save(Subscription subscription);

    Optional<Subscription> findOne(Long id);

    List<Subscription> findAll();
}
