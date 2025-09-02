package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoChatbotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ciudadano/chatbot")
@RequiredArgsConstructor
public class CiudadanoChatbotController {

    private final CiudadanoChatbotService service;

    @PostMapping("/message")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(service.responderCiudadano(
                request.getConversationId(), request.getMessage()
        ));
    }
}