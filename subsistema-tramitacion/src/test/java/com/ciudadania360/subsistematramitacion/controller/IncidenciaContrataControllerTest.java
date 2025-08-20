package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.IncidenciaContrataServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
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

class IncidenciaContrataControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCrudOperations() throws Exception {
        IncidenciaContrataServicio svc = mock(IncidenciaContrataServicio.class);

        IncidenciaContrata e = IncidenciaContrata.builder()
                .id(UUID.randomUUID())
                .contrataId(UUID.randomUUID())
                .descripcion("Descripción de la incidencia")
                .estado("Estado inicial")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        IncidenciaContrataController controller = new IncidenciaContrataController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/incidenciacontratas"))
                .andExpect(status().isOk());

        // Create
        IncidenciaContrata newIncidencia = IncidenciaContrata.builder()
                .contrataId(UUID.randomUUID())
                .descripcion("Nueva descripción")
                .estado("Nuevo estado")
                .build();

        mvc.perform(post("/api/incidenciacontratas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newIncidencia)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/incidenciacontratas/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        IncidenciaContrata updatedIncidencia = IncidenciaContrata.builder()
                .contrataId(e.getContrataId())
                .descripcion("Descripción actualizada")
                .estado("Estado actualizado")
                .build();

        mvc.perform(put("/api/incidenciacontratas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIncidencia)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/incidenciacontratas/" + e.getId()))
                .andExpect(status().isNoContent());

        verify(svc).delete(e.getId());
    }
}
