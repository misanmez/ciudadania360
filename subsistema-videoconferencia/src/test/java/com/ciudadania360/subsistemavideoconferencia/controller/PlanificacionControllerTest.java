package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.PlanificacionServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PlanificacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCrudOperations() throws Exception {
        PlanificacionServicio svc = mock(PlanificacionServicio.class);

        // Mock entity
        Planificacion e = Planificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre de la Planificación")
                .descripcion("Descripción de la Planificación")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        PlanificacionController controller = new PlanificacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/planificacions"))
                .andExpect(status().isOk());

        // Create
        Planificacion newPlanificacion = Planificacion.builder()
                .nombre("Nueva Planificación")
                .descripcion("Descripción nueva")
                .build();

        mvc.perform(post("/api/planificacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPlanificacion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/planificacions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Planificacion updatedPlanificacion = Planificacion.builder()
                .nombre("Nombre Actualizado")
                .descripcion("Descripción Actualizada")
                .build();

        mvc.perform(put("/api/planificacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPlanificacion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/planificacions/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verify delete was called
        verify(svc).delete(e.getId());
    }
}
