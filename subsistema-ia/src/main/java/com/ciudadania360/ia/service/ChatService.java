package com.ciudadania360.ia.service;

import com.ciudadania360.ia.config.IaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final WebClient iaWebClient;
    private final IaProperties iaProperties;

    @Autowired
    public ChatService(WebClient iaWebClient, IaProperties iaProperties) {
        this.iaWebClient = iaWebClient;
        this.iaProperties = iaProperties;
    }

    public Mono<String> chat(String prompt) {
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", iaProperties.getModel());
        payload.put("messages", List.of(message));

        return iaWebClient
                .post()
                .uri("/v1/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Object choicesObj = response.get("choices");
                    if (choicesObj instanceof List<?> choices && !choices.isEmpty()) {
                        Object first = choices.get(0);
                        if (first instanceof Map<?, ?> firstMap) {
                            Object messageObj = firstMap.get("message");
                            if (messageObj instanceof Map<?, ?> messageMap) {
                                Object contentObj = messageMap.get("content");
                                return contentObj != null ? contentObj.toString() : "";
                            }
                        }
                    }
                    return "";
                });
    }
}

