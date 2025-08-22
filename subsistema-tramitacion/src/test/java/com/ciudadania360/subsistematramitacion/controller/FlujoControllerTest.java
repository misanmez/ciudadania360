package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse;
import com.ciudadania360.subsistematramitacion.application.service.FlujoService;
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

class FlujoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        FlujoService svc = mock(FlujoService.class);

        FlujoResponse e = FlujoResponse.builder()
                .id(UUID.randomUUID())
                .nombre("Flujo 1")
                .descripcion("Descripción del flujo")
                .activo(true)
                .tipo("TIPO1")
                .slaHoras(48)
                .pasosDefinition("{\"pasos\":[\"A\",\"B\"]}")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(FlujoRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(FlujoRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new FlujoController(svc)).build();

        mvc.perform(get("/api/flujos")).andExpect(status().isOk());

        FlujoRequest req = FlujoRequest.builder()
                .nombre("Flujo 1")
                .descripcion("Descripción del flujo")
                .activo(true)
                .tipo("TIPO1")
                .slaHoras(48)
                .pasosDefinition("{\"pasos\":[\"A\",\"B\"]}")
                .build();

        mvc.perform(post("/api/flujos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/flujos/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/flujos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/flujos/" + e.getId())).andExpect(status().isNoContent());
    }
}
