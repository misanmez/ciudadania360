package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.ContrataServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContrataControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        ContrataServicio svc = mock(ContrataServicio.class);

        Contrata e = Contrata.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre de Contrata")
                .cif("123456789")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        ContrataController controller = new ContrataController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/contratas"))
                .andExpect(status().isOk());

        // Create
        Contrata newContrata = Contrata.builder()
                .nombre("Nueva Contrata")
                .cif("987654321")
                .build();

        mvc.perform(post("/api/contratas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newContrata)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/contratas/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Contrata updatedContrata = Contrata.builder()
                .nombre("Contrata Actualizada")
                .cif("111222333")
                .build();

        mvc.perform(put("/api/contratas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedContrata)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/contratas/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verificar que el servicio fue llamado al eliminar
        verify(svc).delete(e.getId());
    }
}
