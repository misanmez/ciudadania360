package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.SesionServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SesionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCrudOperations() throws Exception {
        SesionServicio svc = mock(SesionServicio.class);

        // Mock entity
        Sesion e = Sesion.builder()
                .id(UUID.randomUUID())
                .codigoAcceso("Codigo123")
                .estado("Programada")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .plataforma("Zoom")
                .enlace("https://zoom.us/j/123456789")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        SesionController controller = new SesionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/sesions"))
                .andExpect(status().isOk());

        // Create
        Sesion newSesion = Sesion.builder()
                .codigoAcceso("NuevoCodigo")
                .estado("Pendiente")
                .build();

        mvc.perform(post("/api/sesions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSesion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/sesions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Sesion updatedSesion = Sesion.builder()
                .codigoAcceso("CodigoActualizado")
                .estado("Finalizada")
                .build();

        mvc.perform(put("/api/sesions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSesion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/sesions/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verify delete was called
        verify(svc).delete(e.getId());
    }
}
