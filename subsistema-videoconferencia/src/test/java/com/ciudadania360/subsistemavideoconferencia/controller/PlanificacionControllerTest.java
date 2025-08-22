package com.ciudadania360.subsistemavideoconferencia.controller;

import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionResponse;
import com.ciudadania360.subsistemavideoconferencia.application.service.PlanificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlanificacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        PlanificacionService svc = mock(PlanificacionService.class);

        UUID fixedId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        PlanificacionResponse e = PlanificacionResponse.builder()
                .id(fixedId)
                .nombre("Planificación 1")
                .descripcion("Descripción de prueba")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(PlanificacionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(PlanificacionRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new PlanificacionController(svc)).build();

        mvc.perform(get("/api/planificaciones")).andExpect(status().isOk());

        PlanificacionRequest req = PlanificacionRequest.builder()
                .nombre("Planificación 1")
                .descripcion("Descripción de prueba")
                .build();

        mvc.perform(post("/api/planificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/planificaciones/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/planificaciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/planificaciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
