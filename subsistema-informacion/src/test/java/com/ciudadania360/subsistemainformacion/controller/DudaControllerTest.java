package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.DudaServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DudaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCreate() throws Exception {
        DudaServicio svc = mock(DudaServicio.class);

        // Duda de ejemplo
        Duda e = Duda.builder()
                .id(UUID.randomUUID())
                .pregunta("¿Cómo puedo obtener mi certificado de nacimiento?")
                .respuesta("Necesito ayuda con el proceso.")
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        DudaController controller = new DudaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/dudas"))
                .andExpect(status().isOk());

        // Create
        Duda newDuda = Duda.builder()
                .pregunta("¿Cómo puedo obtener mi certificado de nacimiento?")
                .respuesta("Necesito ayuda con el proceso.")
                .build();

        mvc.perform(post("/api/dudas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDuda)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/dudas/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Duda updatedDuda = Duda.builder()
                .pregunta("¿Cómo puedo obtener mi certificado actualizado?")
                .respuesta("Proceso actualizado disponible.")
                .build();

        mvc.perform(put("/api/dudas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDuda)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/dudas/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
