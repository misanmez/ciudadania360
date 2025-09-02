package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.application.service.ChatbotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CiudadanoChatbotServiceTest {

    @Mock
    private ChatbotService chatbotService;

    @InjectMocks
    private CiudadanoChatbotService service;

    @Test
    void sendMessageDelegatesToSharedService() {
        UUID conversationId = UUID.randomUUID();
        String message = "Hola";

        ChatResponse response = new ChatResponse("Hola respuesta", conversationId, "raw");
        when(chatbotService.sendMessage(any(ChatRequest.class))).thenReturn(response);

        ChatResponse result = service.responderCiudadano(conversationId, message);

        assertThat(result).isEqualTo(response);
        verify(chatbotService).sendMessage(argThat(req ->
                req.getConversationId().equals(conversationId) &&
                        req.getMessage().equals(message)
        ));
    }
}
