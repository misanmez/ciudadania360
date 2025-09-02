package com.ciudadania360.subsistemaia.controller;

import com.ciudadania360.shared.application.dto.RequestDto;
import com.ciudadania360.shared.application.dto.chatbot.ChatRequest;
import com.ciudadania360.shared.application.service.IAService;
import com.ciudadania360.shared.ia.client.IAClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IAControllerTest {

    private MockMvc mvc;
    private IAClient iaClient;
    private IAService iaService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        iaClient = mock(IAClient.class);
        iaService = mock(IAService.class);
        IAController controller = new IAController(iaClient, iaService);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void chatEndpoint() throws Exception {
        UUID conversationId = UUID.randomUUID();
        ChatRequest request = new ChatRequest(conversationId, "Hola IA");
        when(iaClient.chat(conversationId, "Hola IA")).thenReturn("Hola humano");

        mvc.perform(post("/api/ia/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseText").value("Hola humano"))
                .andExpect(jsonPath("$.conversationId").value(conversationId.toString()));

        verify(iaClient).chat(conversationId, "Hola IA");
    }

    @Test
    void processTextEndpoint() throws Exception {
        when(iaClient.processText("Texto a procesar", "resumen")).thenReturn("Resumen");

        mvc.perform(post("/api/ia/process")
                        .param("task", "resumen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Texto a procesar"))
                .andExpect(status().isOk())
                .andExpect(content().string("Resumen"));

        verify(iaClient).processText("Texto a procesar", "resumen");
    }

    @Test
    void classifyDocumentEndpoint() throws Exception {
        when(iaClient.classifyDocument("Contenido del doc", "Factura")).thenReturn("Factura");

        mvc.perform(post("/api/ia/classify")
                        .param("docType", "Factura")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Contenido del doc"))
                .andExpect(status().isOk())
                .andExpect(content().string("Factura"));

        verify(iaClient).classifyDocument("Contenido del doc", "Factura");
    }

    @Test
    void predictEndpoint() throws Exception {
        RequestDto request = new RequestDto("Hola IA", "gpt-test");
        when(iaService.predict(request.prompt(), request.model())).thenReturn("Respuesta IA");

        mvc.perform(post("/api/ia/predict")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Respuesta IA"))
                .andExpect(jsonPath("$.model").value("gpt-test"));

        verify(iaService).predict(request.prompt(), request.model());
    }

    @Test
    void transcribeEndpoint() throws Exception {
        byte[] fileContent = "audio content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "audio.mp3",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileContent
        );

        when(iaClient.transcribe(fileContent, "es")).thenReturn("Texto transcrito");

        mvc.perform(multipart("/api/ia/transcribe")
                        .file(file)
                        .param("language", "es"))
                .andExpect(status().isOk())
                .andExpect(content().string("Texto transcrito"));

        verify(iaClient).transcribe(fileContent, "es");
    }

}
