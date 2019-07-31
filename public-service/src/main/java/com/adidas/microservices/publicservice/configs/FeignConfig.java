package com.adidas.microservices.publicservice.configs;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Value("${subscription.username}")
    private String subsciptionUsername;

    @Value("${subscription.password}")
    private String subsciptionPassword;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {

        return new BasicAuthRequestInterceptor(subsciptionUsername, subsciptionPassword);
    }
}
