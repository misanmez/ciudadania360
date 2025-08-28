package com.ciudadania360.ia.controller;

import com.ciudadania360.ia.dto.ChatRequest;
import com.ciudadania360.ia.dto.ChatResponse;
import com.ciudadania360.ia.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/ia")
@Tag(name = "IA", description = "Operaciones de chat LLM")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener respuesta del LLM a partir de un prompt")
    public Mono<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        return chatService.chat(request.getPrompt()).map(ChatResponse::new);
    }
}

