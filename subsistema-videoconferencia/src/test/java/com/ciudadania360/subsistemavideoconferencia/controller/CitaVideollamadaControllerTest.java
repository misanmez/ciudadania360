package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaResponse;
import com.ciudadania360.subsistemavideoconferencia.application.service.CitaVideollamadaService;
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

class CitaVideollamadaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); // <--- Maneja Instant correctamente

    @Test
    void listAndCreate() throws Exception {
        CitaVideollamadaService svc = mock(CitaVideollamadaService.class);

        UUID fixedId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Instant fixedInstant = Instant.parse("2025-08-22T09:02:58.214242400Z");

        CitaVideollamadaResponse e = CitaVideollamadaResponse.builder()
                .id(fixedId)
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .empleadoId("EMP123")
                .fechaProgramadaInicio(fixedInstant)
                .fechaProgramadaFin(fixedInstant.plusSeconds(3600))
                .estado("Pendiente")
                .canalNotificacion("Email")
                .notas("Notas de prueba")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(CitaVideollamadaRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(CitaVideollamadaRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new CitaVideollamadaController(svc)).build();

        mvc.perform(get("/api/citas-videollamada")).andExpect(status().isOk());

        CitaVideollamadaRequest req = CitaVideollamadaRequest.builder()
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .empleadoId("EMP123")
                .fechaProgramadaInicio(fixedInstant)
                .fechaProgramadaFin(fixedInstant.plusSeconds(3600))
                .estado("Pendiente")
                .canalNotificacion("Email")
                .notas("Notas de prueba")
                .build();

        mvc.perform(post("/api/citas-videollamada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/citas-videollamada/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/citas-videollamada/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/citas-videollamada/" + e.getId())).andExpect(status().isNoContent());
    }
}
