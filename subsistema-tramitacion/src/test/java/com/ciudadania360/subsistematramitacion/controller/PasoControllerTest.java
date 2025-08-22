package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoResponse;
import com.ciudadania360.subsistematramitacion.application.service.PasoService;
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

class PasoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        PasoService svc = mock(PasoService.class);

        PasoResponse e = PasoResponse.builder()
                .id(UUID.randomUUID())
                .flujoId(UUID.randomUUID())
                .nombre("Paso 1")
                .orden(1)
                .tipo("Manual")
                .responsableRole("Admin")
                .slaHoras(24)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(PasoRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(PasoRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new PasoController(svc)).build();

        mvc.perform(get("/api/pasos")).andExpect(status().isOk());

        PasoRequest req = new PasoRequest(e.getFlujoId(), e.getNombre(), e.getOrden(), e.getTipo(), e.getResponsableRole(), e.getSlaHoras());
        mvc.perform(post("/api/pasos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/pasos/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/pasos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/pasos/" + e.getId())).andExpect(status().isNoContent());
    }
}
