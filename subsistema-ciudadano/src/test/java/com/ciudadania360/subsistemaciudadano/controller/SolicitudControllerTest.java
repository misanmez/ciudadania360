package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SolicitudControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mvc;
    private SolicitudService svc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        svc = mock(SolicitudService.class);
        SolicitudController controller = new SolicitudController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        UUID id = UUID.randomUUID();

        // Response de ejemplo
        SolicitudResponse response = new SolicitudResponse();
        response.setId(id);
        response.setTitulo("Solicitud Inicial");
        response.setDescripcion("Descripción de la solicitud inicial");
        response.setTipo("Tipo A");
        response.setCanalEntrada("Email");
        response.setEstado("Abierta");
        response.setPrioridad("Alta");
        response.setNumeroExpediente("EXP-123");
        response.setFechaRegistro(Instant.now());
        response.setFechaLimiteSLA(Instant.now().plusSeconds(86400));
        response.setFechaCierre(null);
        response.setScoreRelevancia(BigDecimal.valueOf(0.75));
        response.setOrigen("Interno");

        when(svc.list()).thenReturn(List.of(response));
        when(svc.create(any(SolicitudRequest.class))).thenReturn(response);
        when(svc.get(id)).thenReturn(response);
        when(svc.update(eq(id), any(SolicitudRequest.class))).thenReturn(response);

        // LIST
        mvc.perform(get("/api/solicituds"))
                .andExpect(status().isOk());

        // CREATE
        SolicitudRequest newRequest = new SolicitudRequest();
        newRequest.setTitulo("Solicitud Nueva");
        newRequest.setDescripcion("Descripción de la nueva solicitud");
        newRequest.setTipo("Tipo B");
        newRequest.setCanalEntrada("Web");
        newRequest.setEstado("Pendiente");
        newRequest.setPrioridad("Media");
        newRequest.setNumeroExpediente("EXP-456");
        newRequest.setOrigen("Externo");

        mvc.perform(post("/api/solicituds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/solicituds/" + id))
                .andExpect(status().isOk());

        // UPDATE
        SolicitudRequest updateRequest = new SolicitudRequest();
        updateRequest.setTitulo("Solicitud Actualizada");
        updateRequest.setDescripcion("Descripción actualizada");
        updateRequest.setTipo("Tipo C");
        updateRequest.setCanalEntrada("Teléfono");
        updateRequest.setEstado("Cerrada");
        updateRequest.setPrioridad("Baja");
        updateRequest.setNumeroExpediente("EXP-789");
        updateRequest.setOrigen("Interno");

        mvc.perform(put("/api/solicituds/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/solicituds/" + id))
                .andExpect(status().isNoContent());
    }
}
