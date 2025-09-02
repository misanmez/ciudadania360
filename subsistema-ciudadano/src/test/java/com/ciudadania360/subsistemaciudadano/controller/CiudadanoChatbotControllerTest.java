package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.shared.application.dto.chatbot.ChatResponse;
import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoChatbotService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CiudadanoChatbotControllerTest {

    @Test
    void sendMessage() throws Exception {
        CiudadanoChatbotService service = mock(CiudadanoChatbotService.class);

        UUID conversationId = UUID.randomUUID();
        String message = "Hola";
        ChatResponse response = new ChatResponse("Hola respuesta", conversationId, "raw");

        when(service.responderCiudadano(conversationId, message)).thenReturn(response);

        CiudadanoChatbotController controller = new CiudadanoChatbotController(service);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(post("/api/ciudadano/chatbot/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"conversationId\":\"" + conversationId + "\", \"message\":\"" + message + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseText").value("Hola respuesta"))
                .andExpect(jsonPath("$.conversationId").value(conversationId.toString()));

        verify(service).responderCiudadano(conversationId, message);
    }
}
