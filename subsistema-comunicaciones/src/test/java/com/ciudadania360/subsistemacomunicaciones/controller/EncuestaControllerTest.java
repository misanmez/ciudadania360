package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.EncuestaServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EncuestaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())              // permite serializar Instant
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // fechas en formato ISO

    @Test
    void listAndCreate() throws Exception {
        EncuestaServicio svc = mock(EncuestaServicio.class);

        // Encuesta de ejemplo
        Encuesta e = Encuesta.builder()
                .id(UUID.randomUUID())
                .titulo("Encuesta de Satisfacción")
                .descripcion("Descripción de la encuesta")
                .preguntas("{}")
                .estado("Activo")
                .audiencia("General")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .version(0L)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        EncuestaController controller = new EncuestaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/encuestas"))
                .andExpect(status().isOk());

        // Create
        Encuesta newEncuesta = Encuesta.builder()
                .titulo("Encuesta de Satisfacción")
                .descripcion("Descripción de la encuesta")
                .preguntas("{}")
                .estado("Activo")
                .audiencia("General")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .build();

        mvc.perform(post("/api/encuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEncuesta)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/encuestas/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Encuesta updatedEncuesta = Encuesta.builder()
                .titulo("Encuesta Actualizada")
                .descripcion("Descripción actualizada")
                .preguntas("{\"actualizado\":true}")
                .estado("Inactivo")
                .audiencia("Privada")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(7200))
                .vinculadaSolicitudId(UUID.randomUUID())
                .build();

        mvc.perform(put("/api/encuestas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEncuesta)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/encuestas/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
