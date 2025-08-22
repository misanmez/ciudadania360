package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.RespuestaEncuestaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RespuestaEncuestaControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        RespuestaEncuestaService svc = mock(RespuestaEncuestaService.class);

        RespuestaEncuestaResponse e = RespuestaEncuestaResponse.builder()
                .id(UUID.randomUUID())
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"1\":\"Sí\"}")
                .puntuacion(10)
                .comentarios("Comentario de prueba")
                .fecha(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(RespuestaEncuestaRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(RespuestaEncuestaRequest.class))).thenReturn(e);

        RespuestaEncuestaController controller = new RespuestaEncuestaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/respuestas-encuestas")).andExpect(status().isOk());

        RespuestaEncuestaRequest newResp = RespuestaEncuestaRequest.builder()
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"1\":\"Sí\"}")
                .puntuacion(10)
                .comentarios("Comentario de prueba")
                .fecha(Instant.now())
                .build();

        mvc.perform(post("/api/respuestas-encuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newResp)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/respuestas-encuestas/" + e.getId())).andExpect(status().isOk());

        RespuestaEncuestaRequest updatedResp = RespuestaEncuestaRequest.builder()
                .encuestaId(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .respuestas("{\"1\":\"No\"}")
                .puntuacion(5)
                .comentarios("Actualizado")
                .fecha(Instant.now())
                .build();

        mvc.perform(put("/api/respuestas-encuestas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedResp)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/respuestas-encuestas/" + e.getId())).andExpect(status().isNoContent());
    }
}
