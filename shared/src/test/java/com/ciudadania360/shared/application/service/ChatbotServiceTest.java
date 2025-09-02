package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.domain.entity.IaChatMessage;
import com.ciudadania360.shared.domain.entity.IaConversation;
import com.ciudadania360.shared.domain.repository.IaChatMessageRepository;
import com.ciudadania360.shared.domain.repository.IaConversationRepository;
import com.ciudadania360.shared.ia.client.IAClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatbotServiceTest {

    @Mock
    private IAClient iaClient;

    @Mock
    private IaConversationRepository conversationRepository;

    @Mock
    private IaChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatbotService chatbotService;

    @Test
    void sendMessage_createsNewConversationAndSavesMessage() {
        UUID conversationId = UUID.randomUUID();
        ChatRequest request = new ChatRequest(conversationId, "Hola chatbot");

        // Mock IAClient
        when(iaClient.chat(conversationId, "Hola chatbot")).thenReturn("Hola humano");

        // Mock repositorio de conversaciones -> no existe, se crea
        when(conversationRepository.findByConversationId(conversationId)).thenReturn(Optional.empty());
        when(conversationRepository.save(any(IaConversation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Mock repositorio de mensajes -> simplemente devuelve el mensaje guardado
        when(chatMessageRepository.save(any(IaChatMessage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutamos
        ChatResponse response = chatbotService.sendMessage(request);

        // Verificaciones
        assertThat(response.getResponseText()).isEqualTo("Hola humano");
        assertThat(response.getConversationId()).isEqualTo(conversationId);
        verify(iaClient).chat(conversationId, "Hola chatbot");
        verify(conversationRepository).findByConversationId(conversationId);
        verify(conversationRepository).save(any(IaConversation.class));
        verify(chatMessageRepository).save(any(IaChatMessage.class));
    }

    @Test
    void sendMessage_usesExistingConversation() {
        UUID conversationId = UUID.randomUUID();
        ChatRequest request = new ChatRequest(conversationId, "Hola otra vez");

        IaConversation existingConversation = IaConversation.builder()
                .conversationId(conversationId)
                .build();

        // Mock IAClient
        when(iaClient.chat(conversationId, "Hola otra vez")).thenReturn("Respuesta IA");

        // Mock repositorio de conversaciones -> existe
        when(conversationRepository.findByConversationId(conversationId))
                .thenReturn(Optional.of(existingConversation));

        // Mock repositorio de mensajes
        when(chatMessageRepository.save(any(IaChatMessage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ChatResponse response = chatbotService.sendMessage(request);

        assertThat(response.getResponseText()).isEqualTo("Respuesta IA");
        assertThat(response.getConversationId()).isEqualTo(conversationId);
        verify(conversationRepository, never()).save(any(IaConversation.class));
        verify(chatMessageRepository).save(any(IaChatMessage.class));
    }
}
