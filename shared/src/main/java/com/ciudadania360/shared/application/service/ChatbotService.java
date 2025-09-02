package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.domain.entity.IAChatMessage;
import com.ciudadania360.shared.domain.entity.IAConversation;
import com.ciudadania360.shared.domain.repository.IAChatMessageRepository;
import com.ciudadania360.shared.domain.repository.IAConversationRepository;
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

    private final IAClient iaClient;
    private final IAConversationRepository conversationRepository;
    private final IAChatMessageRepository chatMessageRepository;
    private final ChatbotTrainingService chatbotTrainingService;

    public ChatResponse sendMessage(ChatRequest request) {
        // 1️⃣ Buscar conversación o crear nueva
        IAConversation conversation = conversationRepository.findByConversationId(request.getConversationId())
                .orElseGet(() -> conversationRepository.save(
                        IAConversation.builder()
                                .conversationId(request.getConversationId())
                                .build()
                ));

        // 2️⃣ Enviar mensaje al cliente IA
        String rawResponse = iaClient.chat(request.getConversationId(), request.getMessage());

        // 3️⃣ Guardar mensaje en BBDD
        IAChatMessage chatMessage = IAChatMessage.builder()
                .conversation(conversation)
                .userMessage(request.getMessage())
                .response(rawResponse)
                .rawResponse("{\"raw\":\"" + rawResponse + "\"}")
                .createdAt(LocalDateTime.now())
                .build();
        chatMessageRepository.save(chatMessage);

        // 4️⃣ Generar ejemplos de entrenamiento para este mensaje
        chatbotTrainingService.generateTrainingData();

        log.info("Mensaje procesado para conversación {}", request.getConversationId());

        // 5️⃣ Devolver DTO
        return new ChatResponse(rawResponse, request.getConversationId(), rawResponse);
    }

}
