package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.ia.client.IAClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final IAClient iaClient;

    public ChatResponse sendMessage(ChatRequest request) {
        String rawResponse = iaClient.chat(request.getConversationId(), request.getMessage());
        return new ChatResponse(rawResponse, request.getConversationId(), rawResponse);
    }
}
