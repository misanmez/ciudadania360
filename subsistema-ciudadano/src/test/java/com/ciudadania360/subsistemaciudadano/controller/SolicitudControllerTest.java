package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudService;
import com.ciudadania360.shared.exception.GlobalExceptionHandler;
import com.ciudadania360.shared.exception.BadRequestException;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void listAndCreateAndHandleError() throws Exception {
        UUID id = UUID.randomUUID();

        // Response de ejemplo
        SolicitudResponse response = new SolicitudResponse();
        response.setId(id);
        response.setTitulo("Solicitud Inicial");
        response.setDescripcion("Descripción de la solicitud inicial");
        response.setTipo("Tipo A");
        response.setCanalEntrada("Email");
        response.setEstado("Abierta");
        response.setPrioridad("ALTA");
        response.setNumeroExpediente("EXP-123");
        response.setFechaRegistro(Instant.now());
        response.setFechaLimiteSLA(Instant.now().plusSeconds(86400));
        response.setScoreRelevancia(BigDecimal.valueOf(0.75));
        response.setOrigen("Interno");

        // Mock de servicio
        when(svc.list()).thenReturn(List.of(response));
        when(svc.create(any(SolicitudRequest.class))).thenReturn(response);
        when(svc.get(id)).thenReturn(response);
        when(svc.update(eq(id), any(SolicitudRequest.class))).thenReturn(response);

        // LIST
        mvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()));

        // CREATE válido
        SolicitudRequest newRequest = new SolicitudRequest();
        newRequest.setTitulo("Solicitud Nueva");
        newRequest.setDescripcion("Descripción de la nueva solicitud");
        newRequest.setTipo("Tipo B");
        newRequest.setCanalEntrada("Web");
        newRequest.setEstado("Pendiente");
        newRequest.setPrioridad("MEDIA");
        newRequest.setNumeroExpediente("EXP-456");
        newRequest.setOrigen("Externo");

        mvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // CREATE inválido: prioridad inválida
        SolicitudRequest badRequest = new SolicitudRequest();
        badRequest.setTitulo("Solicitud Mal");
        badRequest.setPrioridad("INVALIDA");

        when(svc.create(any(SolicitudRequest.class)))
                .thenThrow(new BadRequestException("Prioridad inválida, debe ser ALTA, MEDIA o BAJA"));

        mvc.perform(post("/api/solicitudes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Prioridad inválida, debe ser ALTA, MEDIA o BAJA"))
                .andExpect(header().exists("X-Request-Id"));

        // GET by ID
        mvc.perform(get("/api/solicitudes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // UPDATE
        SolicitudRequest updateRequest = new SolicitudRequest();
        updateRequest.setTitulo("Solicitud Actualizada");
        updateRequest.setDescripcion("Descripción actualizada");
        updateRequest.setTipo("Tipo C");
        updateRequest.setCanalEntrada("Teléfono");
        updateRequest.setEstado("Cerrada");
        updateRequest.setPrioridad("BAJA");
        updateRequest.setNumeroExpediente("EXP-789");
        updateRequest.setOrigen("Interno");

        mvc.perform(put("/api/solicitudes/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // DELETE
        mvc.perform(delete("/api/solicitudes/" + id))
                .andExpect(status().isNoContent());
    }
}
