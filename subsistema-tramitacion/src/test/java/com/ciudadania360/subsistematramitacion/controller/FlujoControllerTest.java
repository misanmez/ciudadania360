package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.FlujoServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
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

class FlujoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCrudOperations() throws Exception {
        FlujoServicio svc = mock(FlujoServicio.class);

        Flujo e = Flujo.builder()
                .id(UUID.randomUUID())
                .nombre("Flujo de Prueba")
                .descripcion("Descripción del flujo de prueba")
                .activo(true)
                .tipo("tipo1")
                .slaHoras(24)
                .pasosDefinition("{\"pasos\":[]}")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        FlujoController controller = new FlujoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/flujos"))
                .andExpect(status().isOk());

        // Create
        Flujo newFlujo = Flujo.builder()
                .nombre("Nuevo Flujo")
                .descripcion("Descripción del nuevo flujo")
                .activo(false)
                .tipo("tipo2")
                .slaHoras(48)
                .pasosDefinition("{\"pasos\":[{\"nombre\":\"inicio\"}]}")
                .build();

        mvc.perform(post("/api/flujos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newFlujo)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/flujos/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Flujo updatedFlujo = Flujo.builder()
                .nombre("Flujo Actualizado")
                .descripcion("Descripción actualizada")
                .activo(true)
                .tipo("tipo1")
                .slaHoras(36)
                .pasosDefinition("{\"pasos\":[{\"nombre\":\"inicio\"},{\"nombre\":\"fin\"}]}")
                .build();

        mvc.perform(put("/api/flujos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFlujo)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/flujos/" + e.getId()))
                .andExpect(status().isNoContent());

        verify(svc).delete(e.getId());
    }
}
