package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.dispositivo.DispositivoResponse;
import com.ciudadania360.subsistemavideoconferencia.application.service.DispositivoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DispositivoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void listAndCreate() throws Exception {
        DispositivoService svc = mock(DispositivoService.class);

        UUID fixedId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Instant fixedInstant = Instant.parse("2025-08-22T09:02:58.214242400Z");

        DispositivoResponse e = DispositivoResponse.builder()
                .id(fixedId)
                .ciudadanoId(UUID.randomUUID())
                .tipo("Portátil")
                .sistemaOperativo("Windows")
                .navegador("Chrome")
                .capacidadVideo("1080p")
                .microfono(true)
                .camara(true)
                .red("WiFi")
                .pruebaRealizada(true)
                .ultimoCheck(fixedInstant)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(DispositivoRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(DispositivoRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new DispositivoController(svc)).build();

        mvc.perform(get("/api/dispositivos")).andExpect(status().isOk());

        DispositivoRequest req = DispositivoRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .tipo("Portátil")
                .sistemaOperativo("Windows")
                .navegador("Chrome")
                .capacidadVideo("1080p")
                .microfono(true)
                .camara(true)
                .red("WiFi")
                .pruebaRealizada(true)
                .ultimoCheck(fixedInstant)
                .build();

        mvc.perform(post("/api/dispositivos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/dispositivos/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/dispositivos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/dispositivos/" + e.getId())).andExpect(status().isNoContent());
    }
}
