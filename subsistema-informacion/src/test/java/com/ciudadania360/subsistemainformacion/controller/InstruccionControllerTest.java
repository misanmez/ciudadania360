package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionResponse;
import com.ciudadania360.subsistemainformacion.application.service.InstruccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InstruccionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        InstruccionService svc = mock(InstruccionService.class);

        InstruccionResponse e = new InstruccionResponse(
                UUID.randomUUID(),
                "Título instrucción",
                "Paso1, Paso2"
        );

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(InstruccionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(InstruccionRequest.class))).thenReturn(e);

        var controller = new InstruccionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/instrucciones")).andExpect(status().isOk());

        InstruccionRequest request = new InstruccionRequest(e.getTitulo(), e.getPasos());

        mvc.perform(post("/api/instrucciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/instrucciones/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/instrucciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/instrucciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
