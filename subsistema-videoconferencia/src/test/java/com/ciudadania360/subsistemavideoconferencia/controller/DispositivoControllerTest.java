package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.service.DispositivoServicio;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DispositivoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        DispositivoServicio svc = mock(DispositivoServicio.class);

        // Mock entity
        Dispositivo e = Dispositivo.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tipo("Tipo de Dispositivo")
                .sistemaOperativo("Windows 10")
                .navegador("Chrome")
                .capacidadVideo("1080p")
                .microfono(true)
                .camara(true)
                .red("WiFi")
                .pruebaRealizada(true)
                .ultimoCheck(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        DispositivoController controller = new DispositivoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/dispositivos"))
                .andExpect(status().isOk());

        // Create
        Dispositivo newDispositivo = Dispositivo.builder()
                .ciudadanoId(UUID.randomUUID())
                .tipo("Nuevo Tipo")
                .sistemaOperativo("MacOS")
                .navegador("Firefox")
                .capacidadVideo("720p")
                .microfono(false)
                .camara(false)
                .red("4G")
                .pruebaRealizada(false)
                .ultimoCheck(Instant.now())
                .build();

        mvc.perform(post("/api/dispositivos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDispositivo)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/dispositivos/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Dispositivo updatedDispositivo = Dispositivo.builder()
                .ciudadanoId(e.getCiudadanoId())
                .tipo("Tipo Actualizado")
                .sistemaOperativo("Linux")
                .navegador("Edge")
                .capacidadVideo("4K")
                .microfono(true)
                .camara(false)
                .red("Ethernet")
                .pruebaRealizada(true)
                .ultimoCheck(Instant.now())
                .build();

        mvc.perform(put("/api/dispositivos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDispositivo)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/dispositivos/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verify delete was called
        verify(svc).delete(e.getId());
    }
}
