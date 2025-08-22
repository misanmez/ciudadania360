package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorRequest;
import com.ciudadania360.subsistemainformacion.application.dto.indicador.IndicadorResponse;
import com.ciudadania360.subsistemainformacion.application.service.IndicadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndicadorControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        IndicadorService svc = mock(IndicadorService.class);

        IndicadorResponse e = IndicadorResponse.builder()
                .id(UUID.randomUUID())
                .codigo("IND001")
                .nombre("Indicador de prueba")
                .descripcion("Descripción")
                .definicionCalculo("Suma")
                .frecuencia("Mensual")
                .unidad("Unidad")
                .responsable("Admin")
                .kpi(true)
                .origenDatos("Sistema")
                .datasetId(UUID.randomUUID())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(IndicadorRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(IndicadorRequest.class))).thenReturn(e);

        var controller = new IndicadorController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/indicadores")).andExpect(status().isOk());

        IndicadorRequest request = new IndicadorRequest(
                e.getCodigo(), e.getNombre(), e.getDescripcion(), e.getDefinicionCalculo(),
                e.getFrecuencia(), e.getUnidad(), e.getResponsable(), e.getKpi(),
                e.getOrigenDatos(), e.getDatasetId()
        );

        mvc.perform(post("/api/indicadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/indicadores/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/indicadores/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/indicadores/" + e.getId())).andExpect(status().isNoContent());
    }
}
