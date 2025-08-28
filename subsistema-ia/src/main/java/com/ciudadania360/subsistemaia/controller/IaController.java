package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.subsistemaia.service.IaClient;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/ia", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class IaController {

    private final IaClient iaClient;

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody Map<String, Object> body){
        Object prompt = body.get("prompt");
        String userPrompt = prompt == null ? "" : prompt.toString();
        List<Map<String, String>> messages = List.of(
                Map.of("role","system","content","Eres un asistente de Ciudadania 360."),
                Map.of("role","user","content", userPrompt)
        );
        return iaClient.createChatCompletion(messages);
    }

    @PostMapping("/embeddings")
    public Mono<String> embeddings(@RequestBody Map<String, Object> body){
        Object input = body.get("input");
        List<String> inputs;
        if(input instanceof List<?> list){
            inputs = list.stream().map(Object::toString).toList();
        } else {
            inputs = List.of(input == null ? "" : input.toString());
        }
        return iaClient.createEmbeddings(inputs);
    }
}

