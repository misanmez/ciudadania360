package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.RespuestaEncuestaServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RespuestaEncuestaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())              // permite serializar Instant
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // fechas en formato ISO

    @Test
    void listAndCreate() throws Exception {
        RespuestaEncuestaServicio svc = mock(RespuestaEncuestaServicio.class);

        // Respuesta de encuesta de ejemplo
        RespuestaEncuesta r = RespuestaEncuesta.builder()
                .id(UUID.randomUUID())
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"pregunta1\": \"Sí\"}")
                .puntuacion(5)
                .comentarios("Muy buena encuesta")
                .fecha(Instant.now())
                .version(0L)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(r));
        when(svc.create(any())).thenReturn(r);
        when(svc.get(r.getId())).thenReturn(r);
        when(svc.update(eq(r.getId()), any())).thenReturn(r);

        RespuestaEncuestaController controller = new RespuestaEncuestaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/respuestaencuestas"))
                .andExpect(status().isOk());

        // Create
        RespuestaEncuesta newRespuesta = RespuestaEncuesta.builder()
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"pregunta1\": \"No\"}")
                .puntuacion(3)
                .comentarios("Aceptable")
                .fecha(Instant.now())
                .build();

        mvc.perform(post("/api/respuestaencuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRespuesta)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/respuestaencuestas/" + r.getId()))
                .andExpect(status().isOk());

        // Update
        RespuestaEncuesta updatedRespuesta = RespuestaEncuesta.builder()
                .respuestas("{\"pregunta1\": \"Sí, totalmente\"}")
                .puntuacion(5)
                .comentarios("Excelente encuesta")
                .fecha(Instant.now().plusSeconds(3600))
                .build();

        mvc.perform(put("/api/respuestaencuestas/" + r.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRespuesta)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/respuestaencuestas/" + r.getId()))
                .andExpect(status().isNoContent());
    }
}
