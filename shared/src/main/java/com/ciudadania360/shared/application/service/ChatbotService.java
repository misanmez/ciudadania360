package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.domain.entity.IaChatMessage;
import com.ciudadania360.shared.domain.entity.IaConversation;
import com.ciudadania360.shared.domain.repository.IaChatMessageRepository;
import com.ciudadania360.shared.domain.repository.IaConversationRepository;
import com.ciudadania360.shared.ia.client.IAClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private static final Logger log = LoggerFactory.getLogger(ChatbotService.class);

    private final IAClient iaClient; // se inyectará HttpIAClient por defecto (@Primary)
    private final IaConversationRepository conversationRepository;
    private final IaChatMessageRepository chatMessageRepository;

    public ChatResponse sendMessage(ChatRequest request) {
        // 1️⃣ Buscar conversación o crear nueva
        IaConversation conversation = conversationRepository.findByConversationId(request.getConversationId())
                .orElseGet(() -> conversationRepository.save(
                        IaConversation.builder()
                                .conversationId(request.getConversationId())
                                .build()
                ));

        // 2️⃣ Enviar mensaje al cliente IA
        String rawResponse = iaClient.chat(request.getConversationId(), request.getMessage());

        // 3️⃣ Guardar mensaje en BBDD
        IaChatMessage chatMessage = IaChatMessage.builder()
                .conversation(conversation)
                .userMessage(request.getMessage())
                .response(rawResponse)
                .rawResponse("{\"raw\":\"" + rawResponse + "\"}")
                .createdAt(LocalDateTime.now())
                .build();
        chatMessageRepository.save(chatMessage);

        log.info("Mensaje procesado para conversación {}", request.getConversationId());

        // 4️⃣ Devolver DTO
        return new ChatResponse(rawResponse, request.getConversationId(), rawResponse);
    }
}
