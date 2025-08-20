package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.ReglaClasificacionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
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
    private ReglaClasificacionServicio svc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // permite serializar Instant

        svc = mock(ReglaClasificacionServicio.class);
        ReglaClasificacionController controller = new ReglaClasificacionController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {

        // Entidad de ejemplo
        ReglaClasificacion e = ReglaClasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Regla Inicial")
                .expresion("expresion_inicial")
                .prioridad(1)
                .activa(true)
                .clasificacionId(UUID.randomUUID())
                .condiciones("{}")
                .fuente("Sistema")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(3600))
                .build();

        when(svc.obtenerTodos()).thenReturn(List.of(e));
        when(svc.crear(any())).thenReturn(e);
        when(svc.obtenerPorId(e.getId())).thenReturn(e);
        when(svc.actualizar(eq(e.getId()), any())).thenReturn(e);

        // LIST
        mvc.perform(get("/api/reglaclasificacions"))
                .andExpect(status().isOk());

        // CREATE
        ReglaClasificacion newRegla = ReglaClasificacion.builder()
                .nombre("Regla Nueva")
                .expresion("expresion_nueva")
                .prioridad(2)
                .activa(false)
                .clasificacionId(UUID.randomUUID())
                .condiciones("{\"campo\":\"valor\"}")
                .fuente("Usuario")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(7200))
                .build();

        mvc.perform(post("/api/reglaclasificacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRegla)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/reglaclasificacions/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        ReglaClasificacion updatedRegla = ReglaClasificacion.builder()
                .nombre("Regla Actualizada")
                .expresion("expresion_actualizada")
                .prioridad(3)
                .activa(true)
                .clasificacionId(UUID.randomUUID())
                .condiciones("{\"campo\":\"nuevo_valor\"}")
                .fuente("Sistema")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(10800))
                .build();

        mvc.perform(put("/api/reglaclasificacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRegla)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/reglaclasificacions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
