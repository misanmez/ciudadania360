package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.SuscripcionServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SuscripcionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        SuscripcionServicio svc = mock(SuscripcionServicio.class);

        // Suscripción de ejemplo
        Suscripcion s = Suscripcion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tema("Suscripción de Ejemplo")
                .activo(true)
                .version(0L)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(s));
        when(svc.create(any())).thenReturn(s);
        when(svc.get(s.getId())).thenReturn(s);
        when(svc.update(eq(s.getId()), any())).thenReturn(s);

        SuscripcionController controller = new SuscripcionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/suscripcions"))
                .andExpect(status().isOk());

        // Create
        Suscripcion newSuscripcion = Suscripcion.builder()
                .ciudadanoId(UUID.randomUUID())
                .tema("Nueva Suscripción")
                .activo(false)
                .build();

        mvc.perform(post("/api/suscripcions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSuscripcion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/suscripcions/" + s.getId()))
                .andExpect(status().isOk());

        // Update
        Suscripcion updatedSuscripcion = Suscripcion.builder()
                .tema("Suscripción Actualizada")
                .activo(true)
                .build();

        mvc.perform(put("/api/suscripcions/" + s.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSuscripcion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/suscripcions/" + s.getId()))
                .andExpect(status().isNoContent());
    }
}
