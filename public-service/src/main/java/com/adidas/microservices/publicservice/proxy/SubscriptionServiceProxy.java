package com.adidas.microservices.publicservice.proxy;

import com.adidas.microservices.publicservice.configs.FeignConfig;
import com.adidas.microservices.publicservice.entity.Subscription;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "subscription-service", url = "localhost:8100", configuration = FeignConfig.class)
public interface SubscriptionServiceProxy {
    @PostMapping("/subscriptions/create")
    public Long createSubscription(Subscription subscription);
}
