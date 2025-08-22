package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataResponse;
import com.ciudadania360.subsistematramitacion.application.service.ContrataService;
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

class ContrataControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        ContrataService svc = mock(ContrataService.class);

        ContrataResponse e = ContrataResponse.builder()
                .id(UUID.randomUUID())
                .nombre("Contrata 1")
                .cif("B12345678")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(ContrataRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(ContrataRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new ContrataController(svc)).build();

        mvc.perform(get("/api/contratas")).andExpect(status().isOk());

        ContrataRequest req = ContrataRequest.builder()
                .nombre("Contrata 1")
                .cif("B12345678")
                .build();

        mvc.perform(post("/api/contratas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/contratas/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/contratas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/contratas/" + e.getId())).andExpect(status().isNoContent());
    }
}
