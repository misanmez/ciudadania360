package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.SolicitudServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
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
    private SolicitudServicio svc;
    private SolicitudController controller;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // opcional, ISO-8601
        svc = mock(SolicitudServicio.class);
        controller = new SolicitudController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        // Entidad de ejemplo
        Solicitud e = Solicitud.builder()
                .id(UUID.randomUUID())
                .titulo("Solicitud Inicial")
                .descripcion("Descripción de la solicitud inicial")
                .tipo("Tipo A")
                .canalEntrada("Email")
                .estado("Abierta")
                .prioridad("Alta")
                .numeroExpediente("EXP-123")
                .fechaRegistro(Instant.now())
                .fechaLimiteSLA(Instant.now().plusSeconds(86400))
                .fechaCierre(null)
                .scoreRelevancia(BigDecimal.valueOf(0.75))
                .origen("Interno")
                .adjuntosCount(0)
                .encuestaEnviada(false)
                .referenciaExterna("REF-001")
                .metadata("{\"campo\":\"valor\"}")
                .build();

        when(svc.obtenerTodos()).thenReturn(List.of(e));
        when(svc.crear(any())).thenReturn(e);
        when(svc.obtenerPorId(e.getId())).thenReturn(e);
        when(svc.actualizar(eq(e.getId()), any())).thenReturn(e);

        // LIST
        mvc.perform(get("/api/solicituds"))
                .andExpect(status().isOk());

        // CREATE
        Solicitud newSolicitud = Solicitud.builder()
                .titulo("Solicitud Nueva")
                .descripcion("Descripción de la nueva solicitud")
                .tipo("Tipo B")
                .canalEntrada("Web")
                .estado("Pendiente")
                .prioridad("Media")
                .numeroExpediente("EXP-456")
                .fechaRegistro(Instant.now())
                .fechaLimiteSLA(Instant.now().plusSeconds(172800))
                .fechaCierre(null)
                .scoreRelevancia(BigDecimal.valueOf(0.9))
                .origen("Externo")
                .adjuntosCount(2)
                .encuestaEnviada(false)
                .referenciaExterna("REF-002")
                .metadata("{\"campo\":\"nuevo_valor\"}")
                .build();

        mvc.perform(post("/api/solicituds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSolicitud)))
                .andExpect(status().isCreated()); // mejor status REST

        // GET BY ID
        mvc.perform(get("/api/solicituds/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        Solicitud updatedSolicitud = Solicitud.builder()
                .titulo("Solicitud Actualizada")
                .descripcion("Descripción actualizada")
                .tipo("Tipo C")
                .canalEntrada("Teléfono")
                .estado("Cerrada")
                .prioridad("Baja")
                .numeroExpediente("EXP-789")
                .fechaRegistro(Instant.now())
                .fechaLimiteSLA(Instant.now().plusSeconds(3600))
                .fechaCierre(Instant.now())
                .scoreRelevancia(BigDecimal.valueOf(1.0))
                .origen("Interno")
                .adjuntosCount(1)
                .encuestaEnviada(true)
                .referenciaExterna("REF-003")
                .metadata("{\"campo\":\"actualizado\"}")
                .build();

        mvc.perform(put("/api/solicituds/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSolicitud)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/solicituds/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
