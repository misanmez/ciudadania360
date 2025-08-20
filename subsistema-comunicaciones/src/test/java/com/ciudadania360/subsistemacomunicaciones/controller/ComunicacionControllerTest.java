package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.service.ComunicacionServicio;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
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

class ComunicacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())              // permite serializar Instant
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // fechas en formato ISO

    @Test
    void listAndCreate() throws Exception {
        ComunicacionServicio svc = mock(ComunicacionServicio.class);

        // Comunicacion de ejemplo
        Comunicacion e = Comunicacion.builder()
                .id(UUID.randomUUID())
                .canal("Email")
                .asunto("Asunto de prueba")
                .cuerpo("Contenido de prueba")
                .estado("Pendiente")
                .destinatario("juan@example.com")
                .ciudadanoId(UUID.randomUUID())
                .programadaPara(Instant.now())
                .version(0L)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        ComunicacionController controller = new ComunicacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/comunicacions"))
                .andExpect(status().isOk());

        // Create
        Comunicacion newComunicacion = Comunicacion.builder()
                .canal("Email")
                .asunto("Asunto de prueba")
                .cuerpo("Contenido de prueba")
                .estado("Pendiente")
                .destinatario("juan@example.com")
                .ciudadanoId(e.getCiudadanoId())
                .programadaPara(Instant.now())
                .build();

        mvc.perform(post("/api/comunicacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newComunicacion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/comunicacions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Comunicacion updatedComunicacion = Comunicacion.builder()
                .canal("SMS")
                .asunto("Asunto actualizado")
                .cuerpo("Contenido actualizado")
                .estado("Enviado")
                .destinatario("juan.new@example.com")
                .ciudadanoId(e.getCiudadanoId())
                .programadaPara(Instant.now())
                .build();

        mvc.perform(put("/api/comunicacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedComunicacion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/comunicacions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
