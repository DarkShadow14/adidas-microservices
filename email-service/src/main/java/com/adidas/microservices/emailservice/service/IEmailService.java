package com.adidas.microservices.emailservice.service;

import com.adidas.microservices.emailservice.dto.Subscription;
import org.springframework.stereotype.Service;

@Service
public interface IEmailService {
    void sendSimpleMessage(Subscription subscription);
}
