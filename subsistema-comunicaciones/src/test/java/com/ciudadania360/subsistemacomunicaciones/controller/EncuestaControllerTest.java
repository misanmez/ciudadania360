package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.EncuestaService;
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

class EncuestaControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        EncuestaService svc = mock(EncuestaService.class);

        EncuestaResponse e = EncuestaResponse.builder()
                .id(UUID.randomUUID())
                .titulo("Encuesta prueba")
                .descripcion("Descripción de prueba")
                .preguntas("{\"1\":\"Sí\"}")
                .estado("ACTIVA")
                .audiencia("General")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(EncuestaRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(EncuestaRequest.class))).thenReturn(e);

        EncuestaController controller = new EncuestaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/encuestas")).andExpect(status().isOk());

        EncuestaRequest newEnc = EncuestaRequest.builder()
                .titulo("Encuesta prueba")
                .descripcion("Descripción de prueba")
                .preguntas("{\"1\":\"Sí\"}")
                .estado("ACTIVA")
                .audiencia("General")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .vinculadaSolicitudId(UUID.randomUUID())
                .build();

        mvc.perform(post("/api/encuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEnc)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/encuestas/" + e.getId())).andExpect(status().isOk());

        EncuestaRequest updatedEnc = EncuestaRequest.builder()
                .titulo("Encuesta actualizada")
                .descripcion("Descripción actualizada")
                .preguntas("{\"1\":\"No\"}")
                .estado("INACTIVA")
                .audiencia("Privada")
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(7200))
                .vinculadaSolicitudId(UUID.randomUUID())
                .build();

        mvc.perform(put("/api/encuestas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEnc)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/encuestas/" + e.getId())).andExpect(status().isNoContent());
    }
}
