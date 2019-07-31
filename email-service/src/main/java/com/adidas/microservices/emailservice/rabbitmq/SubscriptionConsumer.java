package com.adidas.microservices.emailservice.rabbitmq;

import com.adidas.microservices.emailservice.dto.Subscription;
import com.adidas.microservices.emailservice.service.IEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SubscriptionConsumer {

    @Autowired
    private IEmailService emailService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger log = LoggerFactory.getLogger(SubscriptionConsumer.class.getName());

    @RabbitListener(
        id = "subscription-listener",
        bindings = @QueueBinding(
            value = @Queue(value = "subscription.queue", durable = "true"),
            exchange = @Exchange(value = "subscription"),
            key = "subscription.routing.key"
        )
    )
    public void receiveMessage(Message message) {

        try {
            log.info("Received subscription message: " + message.toString());
            Subscription subscription = objectMapper.readValue(message.getBody(), Subscription.class);

            emailService.sendSimpleMessage(subscription);
            subscription.setNewsletterSent(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
