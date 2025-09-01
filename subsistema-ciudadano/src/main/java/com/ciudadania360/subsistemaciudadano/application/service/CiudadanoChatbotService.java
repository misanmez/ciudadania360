package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.application.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CiudadanoChatbotService {
    private final ChatbotService chatbotService;

    public ChatResponse responderCiudadano(String conversationId, String mensaje) {
        ChatRequest req = new ChatRequest(conversationId, mensaje);
        return chatbotService.sendMessage(req);
    }
}
