package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaResponse;
import com.ciudadania360.subsistemainformacion.application.service.DudaService;
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

class DudaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        DudaService svc = mock(DudaService.class);

        DudaResponse e = DudaResponse.builder()
                .id(UUID.randomUUID())
                .pregunta("¿Pregunta?")
                .respuesta("Respuesta")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(DudaRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(DudaRequest.class))).thenReturn(e);

        var controller = new DudaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/dudas")).andExpect(status().isOk());

        DudaRequest request = new DudaRequest("¿Pregunta?", "Respuesta");
        mvc.perform(post("/api/dudas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/dudas/" + e.getId())).andExpect(status().isOk());

        DudaRequest updateRequest = new DudaRequest("Nueva pregunta", "Nueva respuesta");
        mvc.perform(put("/api/dudas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/dudas/" + e.getId())).andExpect(status().isNoContent());
    }
}
