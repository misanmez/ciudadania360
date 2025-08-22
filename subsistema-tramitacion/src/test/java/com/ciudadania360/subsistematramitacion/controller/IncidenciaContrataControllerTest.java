package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataResponse;
import com.ciudadania360.subsistematramitacion.application.service.IncidenciaContrataService;
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

class IncidenciaContrataControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        IncidenciaContrataService svc = mock(IncidenciaContrataService.class);

        IncidenciaContrataResponse e = IncidenciaContrataResponse.builder()
                .id(UUID.randomUUID())
                .contrataId(UUID.randomUUID())
                .descripcion("Incidencia prueba")
                .estado("ABIERTA")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(IncidenciaContrataRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(IncidenciaContrataRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new IncidenciaContrataController(svc)).build();

        mvc.perform(get("/api/incidencias-contrata")).andExpect(status().isOk());

        IncidenciaContrataRequest req = IncidenciaContrataRequest.builder()
                .contrataId(UUID.randomUUID())
                .descripcion("Incidencia prueba")
                .estado("ABIERTA")
                .build();

        mvc.perform(post("/api/incidencias-contrata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/incidencias-contrata/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/incidencias-contrata/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/incidencias-contrata/" + e.getId())).andExpect(status().isNoContent());
    }
}
