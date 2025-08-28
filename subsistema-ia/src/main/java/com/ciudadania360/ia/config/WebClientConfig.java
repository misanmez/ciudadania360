package com.ciudadania360.ia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final IaProperties iaProperties;

    @Autowired
    public WebClientConfig(IaProperties iaProperties) {
        this.iaProperties = iaProperties;
    }

    @Bean
    public WebClient iaWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
            .build();

        return WebClient.builder()
                .baseUrl(iaProperties.getProviderBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + iaProperties.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchangeStrategies(strategies)
                .build();
    }
}

