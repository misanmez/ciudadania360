package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.ComunicacionService;
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

class ComunicacionControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        ComunicacionService svc = mock(ComunicacionService.class);

        ComunicacionResponse e = ComunicacionResponse.builder()
                .id(UUID.randomUUID())
                .asunto("Prueba")
                .cuerpo("Cuerpo de prueba")
                .canal("Email")
                .estado("PENDIENTE")
                .destinatario("usuario@example.com")
                .ciudadanoId(UUID.randomUUID())
                .programadaPara(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(ComunicacionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(ComunicacionRequest.class))).thenReturn(e);

        ComunicacionController controller = new ComunicacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/comunicaciones")).andExpect(status().isOk());

        ComunicacionRequest newCom = ComunicacionRequest.builder()
                .asunto("Prueba")
                .cuerpo("Cuerpo de prueba")
                .canal("Email")
                .estado("PENDIENTE")
                .destinatario("usuario@example.com")
                .ciudadanoId(UUID.randomUUID())
                .programadaPara(Instant.now())
                .build();

        mvc.perform(post("/api/comunicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCom)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/comunicaciones/" + e.getId())).andExpect(status().isOk());

        ComunicacionRequest updatedCom = ComunicacionRequest.builder()
                .asunto("Actualizado")
                .cuerpo("Cuerpo actualizado")
                .canal("SMS")
                .estado("ENVIADO")
                .destinatario("usuario2@example.com")
                .ciudadanoId(UUID.randomUUID())
                .programadaPara(Instant.now())
                .build();

        mvc.perform(put("/api/comunicaciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCom)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/comunicaciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
