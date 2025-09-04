package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialResponse;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudEstadoHistorialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SolicitudEstadoHistorialControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());  // ✅ soporte para Instant

    @Test
    void listAndCrudOperations() throws Exception {
        SolicitudEstadoHistorialService svc = mock(SolicitudEstadoHistorialService.class);

        UUID solicitudId = UUID.randomUUID();

        SolicitudEstadoHistorialResponse resp = SolicitudEstadoHistorialResponse.builder()
                .id(UUID.randomUUID())
                .solicitudId(solicitudId)
                .estadoAnterior("PENDIENTE")
                .estadoNuevo("COMPLETADA")
                .fechaCambio(Instant.now())
                .agente("usuario1")
                .metadata("{\"detalle\":\"test\"}")
                .version(1L)
                .build();

        when(svc.list()).thenReturn(List.of(resp));
        when(svc.get(resp.getId())).thenReturn(resp);
        when(svc.create(any(SolicitudEstadoHistorialRequest.class))).thenReturn(resp);
        when(svc.update(eq(resp.getId()), any(SolicitudEstadoHistorialRequest.class))).thenReturn(resp);

        SolicitudEstadoHistorialController controller = new SolicitudEstadoHistorialController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // LIST
        mvc.perform(get("/api/solicitudes-estado-historial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(resp.getId().toString()))
                .andExpect(jsonPath("$[0].solicitudId").value(solicitudId.toString()))
                .andExpect(jsonPath("$[0].estadoAnterior").value("PENDIENTE"))
                .andExpect(jsonPath("$[0].estadoNuevo").value("COMPLETADA"));

        // CREATE
        SolicitudEstadoHistorialRequest createReq = new SolicitudEstadoHistorialRequest(
                solicitudId,
                "PENDIENTE",
                "COMPLETADA",
                Instant.now(),
                "usuario1",
                "{\"detalle\":\"test\"}"
        );
        mvc.perform(post("/api/solicitudes-estado-historial")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(resp.getId().toString()));

        // GET BY ID
        mvc.perform(get("/api/solicitudes-estado-historial/" + resp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resp.getId().toString()));

        // UPDATE
        SolicitudEstadoHistorialRequest updateReq = new SolicitudEstadoHistorialRequest(
                solicitudId,
                "COMPLETADA",
                "ANULADA",
                Instant.now(),
                "usuario2",
                "{\"detalle\":\"updated\"}"
        );
        mvc.perform(put("/api/solicitudes-estado-historial/" + resp.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resp.getId().toString()));

        // DELETE
        mvc.perform(delete("/api/solicitudes-estado-historial/" + resp.getId()))
                .andExpect(status().isNoContent());
    }
}
