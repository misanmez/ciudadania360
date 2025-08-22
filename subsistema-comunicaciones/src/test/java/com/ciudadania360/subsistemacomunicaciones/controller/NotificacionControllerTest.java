package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.notificacion.NotificacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.NotificacionService;
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

class NotificacionControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        NotificacionService svc = mock(NotificacionService.class);

        NotificacionResponse e = NotificacionResponse.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .titulo("Notificación prueba")
                .mensaje("Mensaje prueba")
                .canal("Email")
                .prioridad("ALTA")
                .estado("PENDIENTE")
                .referencia("REF123")
                .fechaEntrega(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(NotificacionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(NotificacionRequest.class))).thenReturn(e);

        NotificacionController controller = new NotificacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/notificaciones")).andExpect(status().isOk());

        NotificacionRequest newNot = NotificacionRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .titulo("Notificación prueba")
                .mensaje("Mensaje prueba")
                .canal("Email")
                .prioridad("ALTA")
                .estado("PENDIENTE")
                .referencia("REF123")
                .fechaEntrega(Instant.now())
                .build();

        mvc.perform(post("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNot)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/notificaciones/" + e.getId())).andExpect(status().isOk());

        NotificacionRequest updatedNot = NotificacionRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .titulo("Notificación actualizada")
                .mensaje("Mensaje actualizado")
                .canal("SMS")
                .prioridad("MEDIA")
                .estado("ENVIADO")
                .referencia("REF456")
                .fechaEntrega(Instant.now())
                .build();

        mvc.perform(put("/api/notificaciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedNot)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/notificaciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
