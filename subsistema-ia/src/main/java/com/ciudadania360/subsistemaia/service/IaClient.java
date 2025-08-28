package com.ciudadania360.subsistemaia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IaClient {

    private final WebClient webClient = WebClient.builder().build();

    @Value("${ia.base-url}")
    private String baseUrl;

    @Value("${ia.api-key:}")
    private String apiKey;

    @Value("${ia.chat-model}")
    private String chatModel;

    @Value("${ia.embeddings-model}")
    private String embeddingsModel;

    public Mono<String> createChatCompletion(List<Map<String, String>> messages){
        return webClient.post()
                .uri(baseUrl + "/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Map.of(
                        "model", chatModel,
                        "messages", messages
                )))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> createEmbeddings(List<String> inputs){
        return webClient.post()
                .uri(baseUrl + "/embeddings")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Map.of(
                        "model", embeddingsModel,
                        "input", inputs
                )))
                .retrieve()
                .bodyToMono(String.class);
    }
}

