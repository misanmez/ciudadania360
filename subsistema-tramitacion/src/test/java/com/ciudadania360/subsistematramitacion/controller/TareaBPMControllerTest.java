package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.TareaBPMServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
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

class TareaBPMControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        TareaBPMServicio svc = mock(TareaBPMServicio.class);

        TareaBPM e = TareaBPM.builder()
                .id(UUID.randomUUID())
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task1")
                .nombre("Nombre de la Tarea")
                .estado("Estado Inicial")
                .assignee("usuario1")
                .candidateGroup("grupo1")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(1)
                .variables("{}")
                .created(Instant.now())
                .completed(null)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        TareaBPMController controller = new TareaBPMController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/tareabpms"))
                .andExpect(status().isOk());

        // Create
        TareaBPM newTarea = TareaBPM.builder()
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task2")
                .nombre("Nueva Tarea")
                .estado("Nuevo Estado")
                .assignee("usuario2")
                .candidateGroup("grupo2")
                .dueDate(Instant.now().plusSeconds(7200))
                .priority(2)
                .variables("{}")
                .created(Instant.now())
                .build();

        mvc.perform(post("/api/tareabpms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTarea)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/tareabpms/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        TareaBPM updatedTarea = TareaBPM.builder()
                .procesoBpmId(e.getProcesoBpmId())
                .engineTaskId(e.getEngineTaskId())
                .nombre("Nombre de la Tarea Actualizada")
                .estado("Estado Actualizado")
                .assignee(e.getAssignee())
                .candidateGroup(e.getCandidateGroup())
                .dueDate(e.getDueDate())
                .priority(e.getPriority())
                .variables("{\"var\":\"valor\"}")
                .created(e.getCreated())
                .completed(Instant.now())
                .build();

        mvc.perform(put("/api/tareabpms/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTarea)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/tareabpms/" + e.getId()))
                .andExpect(status().isNoContent());

        verify(svc).delete(e.getId());
    }
}
