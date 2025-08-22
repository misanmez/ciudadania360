package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionRequest;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionResponse;
import com.ciudadania360.subsistematramitacion.application.service.IntegracionService;
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

class IntegracionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        IntegracionService svc = mock(IntegracionService.class);

        IntegracionResponse e = IntegracionResponse.builder()
                .id(UUID.randomUUID())
                .sistema("Sistema X")
                .tipo("REST")
                .endpoint("/api/test")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(IntegracionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(IntegracionRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new IntegracionController(svc)).build();

        mvc.perform(get("/api/integraciones")).andExpect(status().isOk());

        IntegracionRequest req = new IntegracionRequest("Sistema X", "REST", "/api/test");

        mvc.perform(post("/api/integraciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/integraciones/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/integraciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/integraciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
