package com.ciudadania360.shared.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.ia.client.IAClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ChatbotServiceTest {

    @Mock
    private IAClient iaClient;

    @InjectMocks
    private ChatbotService chatbotService;

    @Test
    void sendMessageDelegatesToClient() {
        ChatRequest request = new ChatRequest("conv1", "Hola chatbot");
        Mockito.when(iaClient.chat(request.getConversationId(), request.getMessage())).thenReturn("Hola humano");

        ChatResponse response = chatbotService.sendMessage(request);

        assertThat(response.getResponseText()).isEqualTo("Hola humano");
        assertThat(response.getConversationId()).isEqualTo("conv1");
        Mockito.verify(iaClient).chat(request.getConversationId(), request.getMessage());
    }
}
