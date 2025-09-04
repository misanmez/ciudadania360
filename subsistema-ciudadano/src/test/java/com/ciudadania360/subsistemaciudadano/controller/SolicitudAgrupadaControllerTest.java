package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaResponse;
import com.ciudadania360.subsistemaciudadano.application.service.SolicitudAgrupadaService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SolicitudAgrupadaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());  // ✅ soporte para Instant

    @Test
    void listAndCrudOperations() throws Exception {
        SolicitudAgrupadaService svc = mock(SolicitudAgrupadaService.class);

        UUID padreId = UUID.randomUUID();
        UUID hijaId = UUID.randomUUID();

        SolicitudAgrupadaResponse resp = SolicitudAgrupadaResponse.builder()
                .id(UUID.randomUUID())
                .solicitudPadreId(padreId)
                .solicitudHijaId(hijaId)
                .metadata("{\"key\":\"value\"}")
                .version(1L)
                .build();

        when(svc.list()).thenReturn(List.of(resp));
        when(svc.get(resp.getId())).thenReturn(resp);
        when(svc.create(any(SolicitudAgrupadaRequest.class))).thenReturn(resp);
        when(svc.update(eq(resp.getId()), any(SolicitudAgrupadaRequest.class))).thenReturn(resp);

        SolicitudAgrupadaController controller = new SolicitudAgrupadaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // LIST
        mvc.perform(get("/api/solicitudes-agrupadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(resp.getId().toString()))
                .andExpect(jsonPath("$[0].solicitudPadreId").value(padreId.toString()))
                .andExpect(jsonPath("$[0].solicitudHijaId").value(hijaId.toString()));

        // CREATE
        SolicitudAgrupadaRequest createReq = new SolicitudAgrupadaRequest(padreId, hijaId, "{\"key\":\"value\"}");
        mvc.perform(post("/api/solicitudes-agrupadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createReq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(resp.getId().toString()));

        // GET BY ID
        mvc.perform(get("/api/solicitudes-agrupadas/" + resp.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resp.getId().toString()));

        // UPDATE
        SolicitudAgrupadaRequest updateReq = new SolicitudAgrupadaRequest(padreId, hijaId, "{\"key\":\"updated\"}");
        mvc.perform(put("/api/solicitudes-agrupadas/" + resp.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resp.getId().toString()));

        // DELETE
        mvc.perform(delete("/api/solicitudes-agrupadas/" + resp.getId()))
                .andExpect(status().isNoContent());
    }
}
