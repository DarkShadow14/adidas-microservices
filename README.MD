# Newsletter microservices

## Description
Main purpose of application is:

- process entered info of user, mainly his/her email address and send him/her newsletter of currently ongoing campaign 

## Used technologies

Backend
- Maven
- Java 8
- Lombok
- Spring Boot
- Spring Boot Security
- Spring Boot Mail
- Spring Boot Actuator
- Swagger
- Swagger UI
- Spring AMQP
- RabbitMQ
- Spring Feign
- Spring JPA
- H2 Database

## Project Structure
Project is divided into three microservices - Public, Subscription and Email. 

Each one has its own Dockerfile to generate its appropriate docker image. Images are created just for local purposes without pushing to dockerhub repo.

Public service consists of PublicController with endpoint "/newsletter-subscription" where user can send personal info including email address to later receive newsletter.
Specific fields are validated and if not valid then appropriate message is returned to user.

This service is communicating via Feign proxy with Subscription service sending subscription details for current user. I have decided to use Feign because it is very popular framework,
makes easier declaration of REST communication between services and can be very well combined with other framework such as Ribbon and Eureka. However due to lack of time I was not able to fully
use its potential.

Subscription service's main purpose is to store received newsletter into H2 database and then send message asynchronously via RabbitMQ to Email service.

I chose H2 db because is light-weight, easily configurable and suitable for this demo purpose. Rabbit is used to propagate details for Email service, there is direct exchange with
one queue binded with routing key, all created by declaration via RabbitListener annotation in SubscriptionConsumer class.

Email service is using basic Spring Boot Mail as it is easy to use client for sending email even with more complicated templates, such as thymeleaf framework that could be used.

All in all every service is structured with specific packages that fits their purpose. 

## How to run
You need to have installed JDK 8 and docker on your machine

The first thing you need to create is local docker images by going into each service's root folder( e.g. ../adidas/email-service) and running
```bash
$ mvn clean package
```
Then you need to run docker-compose command in detached mode (from directory with docker-compose.yml file, should be located in ../adidas folder)
```bash
$ docker-compose up -d
```
I recommend running it on Linux-based system as I had problems with visibility of docker containers so network mode for each container is set as "host"
and linux is as I found out the only one which supports this network mode.

After that everything should be running.

Public service runs on localhost port 8000, Subscription on port 8100 and Email service on 8200.

## Usage
There is one main endpoint that can be used via any REST client such as Postman
- "/newsletter-subscription" POST mapping with RequestBody of application/json including attributes
```bash
    {
        "campaignId"= "test",
        "dateOfBirth"= "10.5.1999",
        "firstName"= "test",
        "gender"= "FEMALE",
        "email"= "test@test.com",
        "consent"= "true"
    }
```
where firstName and gender are optional.
After submitting POST you shall receive id of subscription as response and also receive email to email address specified in field email of request body.

## Future improvements

There are still many things that could be improved. What comes first to my mind is e.g. unit tests with proper mocking.
Implementing load-balancing of Ribbon or before-mentioned framework Eureka from Spring I stumbled upon. And also frontend part which is missing with proper mail template.

## API

For each service there is swagger and swagger-ui to be used on endpoints "/v2/api-docs" respectively "/swagger-ui.html"