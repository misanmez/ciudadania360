package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaRequest;
import com.ciudadania360.subsistematramitacion.application.dto.carpeta.CarpetaResponse;
import com.ciudadania360.subsistematramitacion.application.service.CarpetaService;
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

class CarpetaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        CarpetaService svc = mock(CarpetaService.class);

        CarpetaResponse e = CarpetaResponse.builder()
                .id(UUID.randomUUID())
                .nombre("Carpeta 1")
                .descripcion("Descripción")
                .tipo("Tipo A")
                .ruta("/ruta/1")
                .numeroExpediente("EXP-001")
                .estado("Activo")
                .permisos("{}")
                .solicitudId(UUID.randomUUID())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(CarpetaRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(CarpetaRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new CarpetaController(svc)).build();

        mvc.perform(get("/api/carpetas")).andExpect(status().isOk());

        CarpetaRequest req = CarpetaRequest.builder()
                .solicitudId(UUID.randomUUID())
                .nombre("Carpeta 1")
                .descripcion("Descripción")
                .tipo("Tipo A")
                .ruta("/ruta/1")
                .permisos("{}")
                .numeroExpediente("EXP-001")
                .estado("Activo")
                .build();

        mvc.perform(post("/api/carpetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/carpetas/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/carpetas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/carpetas/" + e.getId())).andExpect(status().isNoContent());
    }
}
