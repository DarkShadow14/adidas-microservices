package com.adidas.microservices.subscriptionservice.rabbitmq;

import com.adidas.microservices.subscriptionservice.entity.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class SubscriptionProducer implements ISubscriptionProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${subscription.rabbitmq.routing.key}")
    private String routingKey;

    @Value("${subscription.rabbitmq.exchange}")
    private String exchange;

    private static Logger log = LoggerFactory.getLogger(SubscriptionProducer.class.getName());

    @Override
    public void produce(Subscription subscription) {

        log.info("Going to send = " + subscription + " to email service");

        try {
            MessageProperties properties = new MessageProperties();
            properties.setContentType(APPLICATION_JSON_VALUE);
            properties.setHeader("message-type", "subscription");
            Message message = null;
            message = new Message(objectMapper.writeValueAsBytes(subscription), properties);
            amqpTemplate.convertAndSend(exchange, routingKey, message);
            log.info("Message sent = " + subscription);
        } catch (AmqpException | JsonProcessingException e) {
            log.error("Message was not published " + e.getLocalizedMessage());
        }
    }
}
