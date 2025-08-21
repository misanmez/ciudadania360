package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.direccion.DireccionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.DireccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DireccionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        DireccionService svc = mock(DireccionService.class);

        UUID ciudadanoId = UUID.randomUUID();

        // Dirección de ejemplo (Response DTO)
        DireccionResponse e = DireccionResponse.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(ciudadanoId)
                .via("Calle Falsa")
                .numero("123")
                .cp("46001")
                .municipio("Valencia")
                .provincia("Valencia")
                .lat(39.4699)
                .lon(-0.3763)
                .principal(true)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(DireccionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(DireccionRequest.class))).thenReturn(e);

        DireccionController controller = new DireccionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/direcciones"))
                .andExpect(status().isOk());

        // Create
        DireccionRequest newDireccion = DireccionRequest.builder()
                .ciudadanoId(ciudadanoId)
                .via("Calle Falsa")
                .numero("123")
                .cp("46001")
                .municipio("Valencia")
                .provincia("Valencia")
                .lat(39.4699)
                .lon(-0.3763)
                .principal(true)
                .build();

        mvc.perform(post("/api/direcciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDireccion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/direcciones/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        DireccionRequest updatedDireccion = DireccionRequest.builder()
                .ciudadanoId(ciudadanoId)
                .via("Avenida Nueva")
                .numero("456")
                .cp("46002")
                .municipio("Valencia")
                .provincia("Valencia")
                .lat(39.4700)
                .lon(-0.3770)
                .principal(false)
                .build();

        mvc.perform(put("/api/direcciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDireccion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/direcciones/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
