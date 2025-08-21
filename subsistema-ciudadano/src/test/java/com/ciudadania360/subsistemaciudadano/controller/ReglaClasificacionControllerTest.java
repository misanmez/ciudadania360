package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ReglaClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.ReglaClasificacionService;
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

class ReglaClasificacionControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mvc;
    private ReglaClasificacionService svc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        svc = mock(ReglaClasificacionService.class);
        ReglaClasificacionController controller = new ReglaClasificacionController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        UUID reglaId = UUID.randomUUID();
        UUID clasificacionId = UUID.randomUUID();

        ReglaClasificacionResponse reglaResponse = ReglaClasificacionResponse.builder()
                .id(reglaId)
                .nombre("Regla Inicial")
                .expresion("expresion_inicial")
                .prioridad(1)
                .activa(true)
                .clasificacionId(clasificacionId)
                .condiciones("{}")
                .fuente("Sistema")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(3600))
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(reglaResponse));
        when(svc.create(any(ReglaClasificacionRequest.class))).thenReturn(reglaResponse);
        when(svc.get(reglaId)).thenReturn(reglaResponse);
        when(svc.update(eq(reglaId), any(ReglaClasificacionRequest.class))).thenReturn(reglaResponse);

        // LIST
        mvc.perform(get("/api/reglaclasificacions"))
                .andExpect(status().isOk());

        // CREATE
        ReglaClasificacionRequest newRequest = ReglaClasificacionRequest.builder()
                .nombre("Regla Nueva")
                .expresion("expresion_nueva")
                .prioridad(2)
                .activa(false)
                .clasificacionId(clasificacionId)
                .condiciones("{\"campo\":\"valor\"}")
                .fuente("Usuario")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(7200))
                .build();

        mvc.perform(post("/api/reglaclasificacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/reglaclasificacions/" + reglaId))
                .andExpect(status().isOk());

        // UPDATE
        ReglaClasificacionRequest updateRequest = ReglaClasificacionRequest.builder()
                .nombre("Regla Actualizada")
                .expresion("expresion_actualizada")
                .prioridad(3)
                .activa(true)
                .clasificacionId(clasificacionId)
                .condiciones("{\"campo\":\"nuevo_valor\"}")
                .fuente("Sistema")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(10800))
                .build();

        mvc.perform(put("/api/reglaclasificacions/" + reglaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/reglaclasificacions/" + reglaId))
                .andExpect(status().isNoContent());
    }
}
