package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.NotificacionServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Notificacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NotificacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())              // permite serializar Instant
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // fechas en formato ISO

    @Test
    void listAndCreate() throws Exception {
        NotificacionServicio svc = mock(NotificacionServicio.class);

        // Notificación de ejemplo
        Notificacion n = Notificacion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .titulo("Notificación de Prueba")
                .mensaje("Contenido de la notificación")
                .canal("Email")
                .prioridad("Alta")
                .estado("Pendiente")
                .referencia("REF-123")
                .fechaEntrega(Instant.now())
                .version(0L)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(n));
        when(svc.create(any())).thenReturn(n);
        when(svc.get(n.getId())).thenReturn(n);
        when(svc.update(eq(n.getId()), any())).thenReturn(n);

        NotificacionController controller = new NotificacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/notificacions"))
                .andExpect(status().isOk());

        // Create
        Notificacion newNotificacion = Notificacion.builder()
                .ciudadanoId(UUID.randomUUID())
                .titulo("Notificación de Prueba")
                .mensaje("Contenido de la notificación")
                .canal("Email")
                .prioridad("Alta")
                .estado("Pendiente")
                .referencia("REF-123")
                .fechaEntrega(Instant.now())
                .build();

        mvc.perform(post("/api/notificacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNotificacion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/notificacions/" + n.getId()))
                .andExpect(status().isOk());

        // Update
        Notificacion updatedNotificacion = Notificacion.builder()
                .titulo("Notificación Actualizada")
                .mensaje("Contenido actualizado")
                .canal("SMS")
                .prioridad("Baja")
                .estado("Enviado")
                .referencia("REF-456")
                .fechaEntrega(Instant.now().plusSeconds(3600))
                .build();

        mvc.perform(put("/api/notificacions/" + n.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedNotificacion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/notificacions/" + n.getId()))
                .andExpect(status().isNoContent());
    }
}
