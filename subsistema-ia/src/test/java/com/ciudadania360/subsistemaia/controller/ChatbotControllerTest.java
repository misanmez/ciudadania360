package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.application.service.ChatbotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ChatbotControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void sendMessage() throws Exception {
        ChatbotService svc = mock(ChatbotService.class);

        UUID conversationId = UUID.randomUUID();
        ChatRequest request = new ChatRequest(conversationId, "Hola chatbot");
        ChatResponse response = new ChatResponse("Hola humano", conversationId, "raw");

        when(svc.sendMessage(request)).thenReturn(response);

        ChatbotController controller = new ChatbotController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(post("/api/chatbot/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseText").value("Hola humano"))
                .andExpect(jsonPath("$.conversationId").value(conversationId.toString())); // ✅ Usamos toString

        verify(svc).sendMessage(request);
    }
}
