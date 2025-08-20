package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.RecomendacionServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Recomendacion;
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

class RecomendacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        RecomendacionServicio svc = mock(RecomendacionServicio.class);

        // Objeto de prueba
        Recomendacion e = Recomendacion.builder()
                .id(UUID.randomUUID())
                .titulo("Recomendación de Ejemplo")
                .detalle("Detalle de la recomendación")
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        RecomendacionController controller = new RecomendacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/recomendacions"))
                .andExpect(status().isOk());

        // Create
        Recomendacion newRec = Recomendacion.builder()
                .titulo("Recomendación de Ejemplo")
                .detalle("Detalle de la recomendación")
                .build();

        mvc.perform(post("/api/recomendacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRec)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/recomendacions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Recomendacion updatedRec = Recomendacion.builder()
                .titulo("Recomendación Actualizada")
                .detalle("Nuevo detalle de la recomendación")
                .build();

        mvc.perform(put("/api/recomendacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRec)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/recomendacions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
