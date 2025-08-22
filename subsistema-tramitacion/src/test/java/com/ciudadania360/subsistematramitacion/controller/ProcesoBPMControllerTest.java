package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMResponse;
import com.ciudadania360.subsistematramitacion.application.service.ProcesoBPMService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProcesoBPMControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        ProcesoBPMService svc = mock(ProcesoBPMService.class);

        ProcesoBPMResponse e = ProcesoBPMResponse.builder()
                .id(UUID.randomUUID())
                .engineInstanceId("engine-1")
                .definitionKey("def-key")
                .businessKey(UUID.randomUUID())
                .estado("En curso")
                .inicio(Instant.now())
                .fin(null)
                .variables("{}")
                .iniciador("usuario1")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(ProcesoBPMRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(ProcesoBPMRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new ProcesoBPMController(svc)).build();

        mvc.perform(get("/api/procesos-bpm")).andExpect(status().isOk());

        ProcesoBPMRequest req = new ProcesoBPMRequest(e.getEngineInstanceId(), e.getDefinitionKey(),
                e.getBusinessKey(), e.getEstado(), e.getInicio(), e.getFin(), e.getVariables(), e.getIniciador());

        mvc.perform(post("/api/procesos-bpm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/procesos-bpm/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/procesos-bpm/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/procesos-bpm/" + e.getId())).andExpect(status().isNoContent());
    }
}
