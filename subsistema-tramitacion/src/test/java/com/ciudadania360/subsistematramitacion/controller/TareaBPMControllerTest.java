package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMResponse;
import com.ciudadania360.subsistematramitacion.application.service.TareaBPMService;
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

class TareaBPMControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        TareaBPMService svc = mock(TareaBPMService.class);

        TareaBPMResponse e = TareaBPMResponse.builder()
                .id(UUID.randomUUID())
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-1")
                .nombre("Tarea 1")
                .estado("Pendiente")
                .assignee("user1")
                .candidateGroup("grupo1")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(1)
                .variables("{}")
                .created(Instant.now())
                .completed(null)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(TareaBPMRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(TareaBPMRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new TareaBPMController(svc)).build();

        mvc.perform(get("/api/tareas-bpm")).andExpect(status().isOk());

        TareaBPMRequest req = new TareaBPMRequest(
                e.getProcesoBpmId(), e.getEngineTaskId(), e.getNombre(), e.getEstado(),
                e.getAssignee(), e.getCandidateGroup(), e.getDueDate(), e.getPriority(),
                e.getVariables(), e.getCreated(), e.getCompleted()
        );

        mvc.perform(post("/api/tareas-bpm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/tareas-bpm/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/tareas-bpm/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/tareas-bpm/" + e.getId())).andExpect(status().isNoContent());
    }
}
