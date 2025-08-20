package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.PasoServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PasoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCrudOperations() throws Exception {
        PasoServicio svc = mock(PasoServicio.class);

        Paso e = Paso.builder()
                .id(UUID.randomUUID())
                .flujoId(UUID.randomUUID())
                .orden(1)
                .nombre("Paso de Prueba")
                .tipo("tipo")
                .responsableRole("role")
                .slaHoras(24)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        PasoController controller = new PasoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/pasos"))
                .andExpect(status().isOk());

        // Create
        Paso newPaso = Paso.builder()
                .flujoId(UUID.randomUUID())
                .orden(2)
                .nombre("Nuevo Paso")
                .tipo("tipoNuevo")
                .responsableRole("roleNuevo")
                .slaHoras(48)
                .build();

        mvc.perform(post("/api/pasos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPaso)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/pasos/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Paso updatedPaso = Paso.builder()
                .flujoId(e.getFlujoId())
                .orden(1)
                .nombre("Paso de Prueba Actualizado")
                .tipo("tipoActualizado")
                .responsableRole("roleActualizado")
                .slaHoras(36)
                .build();

        mvc.perform(put("/api/pasos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPaso)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/pasos/" + e.getId()))
                .andExpect(status().isNoContent());

        verify(svc).delete(e.getId());
    }
}
