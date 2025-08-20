package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.SugerenciaServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
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

class SugerenciaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())              // permite serializar Instant
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // fechas en formato ISO

    @Test
    void listAndCreate() throws Exception {
        SugerenciaServicio svc = mock(SugerenciaServicio.class);

        // Objeto de prueba
        UUID ciudadanoId = UUID.randomUUID();
        Sugerencia e = Sugerencia.builder()
                .id(UUID.randomUUID())
                .titulo("Ejemplo de Sugerencia")
                .descripcion("Detalle de la sugerencia")
                .ciudadanoId(ciudadanoId)
                .estado("Pendiente")
                .prioridad("Media")
                .areaResponsable("Atención Ciudadana")
                .fecha(Instant.now())
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        SugerenciaController controller = new SugerenciaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/sugerencias"))
                .andExpect(status().isOk());

        // Create
        Sugerencia newSug = Sugerencia.builder()
                .titulo("Nueva Sugerencia")
                .descripcion("Detalle de la nueva sugerencia")
                .ciudadanoId(ciudadanoId)
                .estado("Pendiente")
                .prioridad("Alta")
                .areaResponsable("Atención Ciudadana")
                .fecha(Instant.now())
                .build();

        mvc.perform(post("/api/sugerencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSug)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/sugerencias/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Sugerencia updatedSug = Sugerencia.builder()
                .titulo("Sugerencia Actualizada")
                .descripcion("Detalle actualizado")
                .ciudadanoId(ciudadanoId)
                .estado("Resuelta")
                .prioridad("Alta")
                .areaResponsable("Atención Ciudadana")
                .fecha(Instant.now())
                .build();

        mvc.perform(put("/api/sugerencias/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSug)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/sugerencias/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
