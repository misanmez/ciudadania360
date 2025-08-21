package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.UbicacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.UbicacionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.UbicacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
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

class UbicacionControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mvc;
    private UbicacionService svc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        svc = mock(UbicacionService.class);
        UbicacionController controller = new UbicacionController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        UUID ubicacionId = UUID.randomUUID();

        UbicacionResponse ubicacionResponse = UbicacionResponse.builder()
                .id(ubicacionId)
                .direccion("Calle Mayor 123")
                .municipio("Valencia")
                .provincia("Valencia")
                .cp("46001")
                .lat(39.46975)
                .lon(-0.37739)
                .precision(10)
                .fuente("Registro Municipal")
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(ubicacionResponse));
        when(svc.create(any(UbicacionRequest.class))).thenReturn(ubicacionResponse);
        when(svc.get(ubicacionId)).thenReturn(ubicacionResponse);
        when(svc.update(eq(ubicacionId), any(UbicacionRequest.class))).thenReturn(ubicacionResponse);

        // LIST
        mvc.perform(get("/api/ubicacions"))
                .andExpect(status().isOk());

        // CREATE
        UbicacionRequest newRequest = UbicacionRequest.builder()
                .direccion("Avenida de España 45")
                .municipio("Madrid")
                .provincia("Madrid")
                .cp("28001")
                .lat(40.41678)
                .lon(-3.70379)
                .precision(15)
                .fuente("Usuario")
                .build();

        mvc.perform(post("/api/ubicacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/ubicacions/" + ubicacionId))
                .andExpect(status().isOk());

        // UPDATE
        UbicacionRequest updateRequest = UbicacionRequest.builder()
                .direccion("Calle Nueva 10")
                .municipio("Barcelona")
                .provincia("Barcelona")
                .cp("08001")
                .lat(41.38506)
                .lon(2.17340)
                .precision(20)
                .fuente("Actualización Manual")
                .build();

        mvc.perform(put("/api/ubicacions/" + ubicacionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/ubicacions/" + ubicacionId))
                .andExpect(status().isNoContent());
    }
}
