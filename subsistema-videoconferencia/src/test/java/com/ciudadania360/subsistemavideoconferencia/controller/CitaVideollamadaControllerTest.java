package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.CitaVideollamadaServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CitaVideollamadaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        CitaVideollamadaServicio svc = mock(CitaVideollamadaServicio.class);

        CitaVideollamada e = CitaVideollamada.builder()
                .id(UUID.randomUUID())
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .empleadoId("empleado-01")
                .fechaProgramadaInicio(Instant.parse("2023-10-01T10:00:00Z"))
                .fechaProgramadaFin(Instant.parse("2023-10-01T11:00:00Z"))
                .estado("PENDIENTE")
                .canalNotificacion("EMAIL")
                .notas("Notas de prueba")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        CitaVideollamadaController controller = new CitaVideollamadaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/citavideollamadas"))
                .andExpect(status().isOk());

        // Create
        CitaVideollamada newCita = CitaVideollamada.builder()
                .sesionId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .empleadoId("empleado-02")
                .fechaProgramadaInicio(Instant.parse("2023-10-02T10:00:00Z"))
                .fechaProgramadaFin(Instant.parse("2023-10-02T11:00:00Z"))
                .estado("PENDIENTE")
                .canalNotificacion("SMS")
                .notas("Nueva cita")
                .build();

        mvc.perform(post("/api/citavideollamadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCita)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/citavideollamadas/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        CitaVideollamada updatedCita = CitaVideollamada.builder()
                .sesionId(e.getSesionId())
                .ciudadanoId(e.getCiudadanoId())
                .empleadoId("empleado-03")
                .fechaProgramadaInicio(Instant.parse("2023-10-01T11:00:00Z"))
                .fechaProgramadaFin(Instant.parse("2023-10-01T12:00:00Z"))
                .estado("CONFIRMADA")
                .canalNotificacion("EMAIL")
                .notas("Cita actualizada")
                .build();

        mvc.perform(put("/api/citavideollamadas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCita)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/citavideollamadas/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verificar que el servicio fue llamado al eliminar
        verify(svc).delete(e.getId());
    }
}
