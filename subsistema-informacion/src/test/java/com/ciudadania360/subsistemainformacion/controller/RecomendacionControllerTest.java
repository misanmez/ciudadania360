package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.recomendacion.RecomendacionResponse;
import com.ciudadania360.subsistemainformacion.application.service.RecomendacionService;
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

class RecomendacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        RecomendacionService svc = mock(RecomendacionService.class);

        RecomendacionResponse e = RecomendacionResponse.builder()
                .id(UUID.randomUUID())
                .titulo("Título")
                .detalle("Detalle")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(RecomendacionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(RecomendacionRequest.class))).thenReturn(e);

        RecomendacionController controller = new RecomendacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/recomendaciones")).andExpect(status().isOk());

        RecomendacionRequest request = RecomendacionRequest.builder()
                .titulo("Título")
                .detalle("Detalle")
                .build();

        mvc.perform(post("/api/recomendaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/recomendaciones/" + e.getId())).andExpect(status().isOk());

        RecomendacionRequest updateRequest = RecomendacionRequest.builder()
                .titulo("Nuevo título")
                .detalle("Nuevo detalle")
                .build();

        mvc.perform(put("/api/recomendaciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/recomendaciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
