package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.InteraccionService;
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
    private InteraccionService svc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        svc = mock(InteraccionService.class);
        InteraccionController controller = new InteraccionController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        UUID interaccionId = UUID.randomUUID();
        UUID ciudadanoId = UUID.randomUUID();
        UUID solicitudId = UUID.randomUUID();

        InteraccionResponse interaccionResponse = InteraccionResponse.builder()
                .id(interaccionId)
                .ciudadanoId(ciudadanoId)
                .solicitudId(solicitudId)
                .canal("Email")
                .fecha(Instant.now())
                .agente("Agente 1")
                .mensaje("Mensaje inicial")
                .adjuntoUri("http://example.com/adjunto")
                .visibilidad("PUBLICA")
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(interaccionResponse));
        when(svc.create(any(InteraccionRequest.class))).thenReturn(interaccionResponse);
        when(svc.get(interaccionId)).thenReturn(interaccionResponse);
        when(svc.update(eq(interaccionId), any(InteraccionRequest.class))).thenReturn(interaccionResponse);

        // LIST
        mvc.perform(get("/api/interaccions"))
                .andExpect(status().isOk());

        // CREATE
        InteraccionRequest newRequest = InteraccionRequest.builder()
                .ciudadanoId(ciudadanoId)
                .solicitudId(solicitudId)
                .canal("Chat")
                .fecha(Instant.now())
                .agente("Agente 2")
                .mensaje("Nuevo mensaje")
                .adjuntoUri(null)
                .visibilidad("PRIVADA")
                .build();

        mvc.perform(post("/api/interaccions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/interaccions/" + interaccionId))
                .andExpect(status().isOk());

        // UPDATE
        InteraccionRequest updateRequest = InteraccionRequest.builder()
                .ciudadanoId(ciudadanoId)
                .solicitudId(solicitudId)
                .canal("Teléfono")
                .fecha(Instant.now())
                .agente("Agente 3")
                .mensaje("Mensaje actualizado")
                .adjuntoUri("http://example.com/nuevo-adjunto")
                .visibilidad("PUBLICA")
                .build();

        mvc.perform(put("/api/interaccions/" + interaccionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/interaccions/" + interaccionId))
                .andExpect(status().isNoContent());
    }
}
