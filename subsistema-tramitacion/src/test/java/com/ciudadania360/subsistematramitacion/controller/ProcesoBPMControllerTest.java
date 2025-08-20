package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.ProcesoBPMServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProcesoBPMControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        ProcesoBPMServicio svc = mock(ProcesoBPMServicio.class);

        ProcesoBPM e = ProcesoBPM.builder()
                .id(UUID.randomUUID())
                .engineInstanceId("engine1")
                .definitionKey("def1")
                .businessKey(UUID.randomUUID())
                .estado("Estado Inicial")
                .inicio(Instant.now())
                .fin(null)
                .variables("{}")
                .iniciador("usuario1")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        ProcesoBPMController controller = new ProcesoBPMController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/procesobpms"))
                .andExpect(status().isOk());

        // Create
        ProcesoBPM newProceso = ProcesoBPM.builder()
                .engineInstanceId("engine2")
                .definitionKey("def2")
                .businessKey(UUID.randomUUID())
                .estado("Nuevo Estado")
                .inicio(Instant.now())
                .variables("{}")
                .iniciador("usuario2")
                .build();

        mvc.perform(post("/api/procesobpms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProceso)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/procesobpms/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        ProcesoBPM updatedProceso = ProcesoBPM.builder()
                .engineInstanceId(e.getEngineInstanceId())
                .definitionKey(e.getDefinitionKey())
                .businessKey(e.getBusinessKey())
                .estado("Estado Actualizado")
                .inicio(e.getInicio())
                .fin(Instant.now())
                .variables("{\"var\":\"valor\"}")
                .iniciador(e.getIniciador())
                .build();

        mvc.perform(put("/api/procesobpms/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProceso)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/procesobpms/" + e.getId()))
                .andExpect(status().isNoContent());

        verify(svc).delete(e.getId());
    }
}
