package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.IndicadorServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Indicador;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndicadorControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCreate() throws Exception {
        IndicadorServicio svc = mock(IndicadorServicio.class);

        // Indicador de ejemplo
        Indicador e = Indicador.builder()
                .id(UUID.randomUUID())
                .nombre("Indicador de Ejemplo")
                .descripcion("Descripción del indicador")
                .codigo("IND-001")
                .definicionCalculo("Valor calculado")
                .frecuencia("Mensual")
                .unidad("Unidad")
                .responsable("Responsable")
                .kpi(true)
                .origenDatos("Sistema X")
                .datasetId(UUID.randomUUID())
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        IndicadorController controller = new IndicadorController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/indicadors"))
                .andExpect(status().isOk());

        // Create
        Indicador newIndicador = Indicador.builder()
                .nombre("Indicador de Ejemplo")
                .descripcion("Descripción del indicador")
                .codigo("IND-001")
                .definicionCalculo("Valor calculado")
                .frecuencia("Mensual")
                .unidad("Unidad")
                .responsable("Responsable")
                .kpi(true)
                .origenDatos("Sistema X")
                .datasetId(UUID.randomUUID())
                .build();

        mvc.perform(post("/api/indicadors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newIndicador)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/indicadors/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Indicador updatedIndicador = Indicador.builder()
                .nombre("Indicador Actualizado")
                .descripcion("Descripción actualizada")
                .codigo("IND-001")
                .definicionCalculo("Valor recalculado")
                .frecuencia("Semanal")
                .unidad("Unidad")
                .responsable("Nuevo Responsable")
                .kpi(true)
                .origenDatos("Sistema Y")
                .datasetId(UUID.randomUUID())
                .build();

        mvc.perform(put("/api/indicadors/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIndicador)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/indicadors/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
