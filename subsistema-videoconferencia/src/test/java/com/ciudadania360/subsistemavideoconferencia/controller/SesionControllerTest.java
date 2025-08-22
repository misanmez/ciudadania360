package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.service.SesionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SesionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void listAndCreate() throws Exception {
        SesionService svc = mock(SesionService.class);

        UUID fixedId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Instant fixedStart = Instant.parse("2025-08-22T09:00:00.000Z");
        Instant fixedEnd = fixedStart.plusSeconds(3600);

        SesionResponse e = SesionResponse.builder()
                .id(fixedId)
                .solicitudId(UUID.randomUUID())
                .fechaInicio(fixedStart)
                .fechaFin(fixedEnd)
                .estado("Programada")
                .plataforma("Zoom")
                .enlace("https://zoom.example.com/123")
                .codigoAcceso("ABC123")
                .grabacionUri("https://storage.example.com/video.mp4")
                .motivo("Reunión")
                .operadorId("OP123")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(SesionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(SesionRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new SesionController(svc)).build();

        mvc.perform(get("/api/sesiones")).andExpect(status().isOk());

        SesionRequest req = SesionRequest.builder()
                .solicitudId(UUID.randomUUID())
                .fechaInicio(fixedStart)
                .fechaFin(fixedEnd)
                .estado("Programada")
                .plataforma("Zoom")
                .enlace("https://zoom.example.com/123")
                .codigoAcceso("ABC123")
                .grabacionUri("https://storage.example.com/video.mp4")
                .motivo("Reunión")
                .operadorId("OP123")
                .build();

        mvc.perform(post("/api/sesiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/sesiones/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/sesiones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/sesiones/" + e.getId())).andExpect(status().isNoContent());
    }
}
