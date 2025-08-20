package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.IntegracionServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
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

class IntegracionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCrudOperations() throws Exception {
        IntegracionServicio svc = mock(IntegracionServicio.class);

        Integracion e = Integracion.builder()
                .id(UUID.randomUUID())
                .sistema("sistema")
                .tipo("tipo")
                .endpoint("endpoint")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        IntegracionController controller = new IntegracionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/integracions"))
                .andExpect(status().isOk());

        // Create
        Integracion newIntegracion = Integracion.builder()
                .sistema("nuevoSistema")
                .tipo("nuevoTipo")
                .endpoint("nuevoEndpoint")
                .build();

        mvc.perform(post("/api/integracions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newIntegracion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/integracions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Integracion updatedIntegracion = Integracion.builder()
                .sistema("sistemaActualizado")
                .tipo("tipoActualizado")
                .endpoint("endpointActualizado")
                .build();

        mvc.perform(put("/api/integracions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIntegracion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/integracions/" + e.getId()))
                .andExpect(status().isNoContent());

        verify(svc).delete(e.getId());
    }
}
