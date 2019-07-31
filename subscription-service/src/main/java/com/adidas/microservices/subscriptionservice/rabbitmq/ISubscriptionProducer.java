package com.adidas.microservices.subscriptionservice.rabbitmq;

import com.adidas.microservices.subscriptionservice.entity.Subscription;

public interface ISubscriptionProducer {
    void produce(Subscription subscription);
}
