package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.InteraccionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InteraccionControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mvc;
    private InteraccionServicio svc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Permite serializar Instant

        svc = mock(InteraccionServicio.class);
        InteraccionController controller = new InteraccionController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        Ciudadano ciudadano = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .build();

        Solicitud solicitud = Solicitud.builder()
                .id(UUID.randomUUID())
                .build();

        Interaccion e = Interaccion.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("Email")
                .fecha(Instant.now())
                .agente("Agente 1")
                .mensaje("Mensaje inicial")
                .adjuntoUri("http://example.com/adjunto")
                .visibilidad("PUBLICA")
                .build();

        when(svc.obtenerTodos()).thenReturn(List.of(e));
        when(svc.crear(any())).thenReturn(e);
        when(svc.obtenerPorId(e.getId())).thenReturn(e);
        when(svc.actualizar(eq(e.getId()), any())).thenReturn(e);

        // LIST
        mvc.perform(get("/api/interaccions"))
                .andExpect(status().isOk());

        // CREATE
        Interaccion newInteraccion = Interaccion.builder()
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("Chat")
                .fecha(Instant.now())
                .agente("Agente 2")
                .mensaje("Nuevo mensaje")
                .adjuntoUri(null)
                .visibilidad("PRIVADA")
                .build();

        mvc.perform(post("/api/interaccions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInteraccion)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/interaccions/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        Interaccion updatedInteraccion = Interaccion.builder()
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("Teléfono")
                .fecha(Instant.now())
                .agente("Agente 3")
                .mensaje("Mensaje actualizado")
                .adjuntoUri("http://example.com/nuevo-adjunto")
                .visibilidad("PUBLICA")
                .build();

        mvc.perform(put("/api/interaccions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInteraccion)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/interaccions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
