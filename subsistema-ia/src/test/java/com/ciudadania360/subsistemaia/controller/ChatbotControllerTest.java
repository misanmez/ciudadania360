package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.shared.application.service.ChatbotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ChatbotControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void sendMessage() throws Exception {
        ChatbotService svc = mock(ChatbotService.class);

        ChatRequest request = new ChatRequest("conv1", "Hola chatbot");
        ChatResponse response = new ChatResponse("Hola humano", "conv1", "raw");

        when(svc.sendMessage(request)).thenReturn(response);

        ChatbotController controller = new ChatbotController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(post("/api/chatbot/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseText").value("Hola humano"))
                .andExpect(jsonPath("$.conversationId").value("conv1"));

        verify(svc).sendMessage(request);
    }
}
