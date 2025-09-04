package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemaciudadano.application.service.EncuestaService;
import com.ciudadania360.shared.exception.GlobalExceptionHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EncuestaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());  // ✅ soporte para Instant

    @Test
    void listAndCrudOperations() throws Exception {
        EncuestaService service = mock(EncuestaService.class);
        UUID id = UUID.randomUUID();
        UUID solicitudId = UUID.randomUUID();

        // Encuesta de ejemplo (Response DTO)
        EncuestaResponse e = EncuestaResponse.builder()
                .id(id)
                .solicitudId(solicitudId)
                .tipo("Satisfacción")
                .estado("Completada")
                .fechaEnvio(Instant.now())
                .fechaRespuesta(Instant.now())
                .respuestas("{\"q1\":5}")
                .metadata("{\"nota\":\"test\"}")
                .build();

        // Mock del servicio
        when(service.list()).thenReturn(List.of(e));
        when(service.get(id)).thenReturn(e);
        when(service.create(any(EncuestaRequest.class))).thenReturn(e);
        when(service.update(eq(id), any(EncuestaRequest.class))).thenReturn(e);

        EncuestaController controller = new EncuestaController(service);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        // List
        mvc.perform(get("/api/encuestas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].tipo").value("Satisfacción"));

        // Create (válido)
        EncuestaRequest createRequest = new EncuestaRequest(
                solicitudId,
                "Satisfacción",
                "Pendiente",
                Instant.now(),
                Instant.now(),
                "{\"q1\":5}",
                "{\"nota\":\"test\"}"
        );

        mvc.perform(post("/api/encuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.tipo").value("Satisfacción"));

        // Simular error de validación usando BadRequestException
        EncuestaRequest badRequest = new EncuestaRequest(); // campos vacíos
        when(service.create(any(EncuestaRequest.class)))
                .thenThrow(new BadRequestException("SolicitudId es obligatorio"));

        mvc.perform(post("/api/encuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("SolicitudId es obligatorio"))
                .andExpect(header().exists("X-Request-Id"));

        // Get by ID
        mvc.perform(get("/api/encuestas/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // Update
        EncuestaRequest updateRequest = new EncuestaRequest(
                solicitudId,
                "Satisfacción",
                "Completada",
                Instant.now(),
                Instant.now(),
                "{\"q1\":5}",
                "{\"nota\":\"actualizado\"}"
        );

        mvc.perform(put("/api/encuestas/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // Delete
        doNothing().when(service).delete(id);
        mvc.perform(delete("/api/encuestas/" + id))
                .andExpect(status().isNoContent());
    }
}
