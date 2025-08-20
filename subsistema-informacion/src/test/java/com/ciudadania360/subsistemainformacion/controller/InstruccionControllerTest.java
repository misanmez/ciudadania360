package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.InstruccionServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
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

class InstruccionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        InstruccionServicio svc = mock(InstruccionServicio.class);

        // Objeto de prueba
        Instruccion e = Instruccion.builder()
                .id(UUID.randomUUID())
                .titulo("Ejemplo de Instrucción")
                .pasos("Paso 1, Paso 2, Paso 3")
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        InstruccionController controller = new InstruccionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/instruccions"))
                .andExpect(status().isOk());

        // Create
        Instruccion newInst = Instruccion.builder()
                .titulo("Ejemplo de Instrucción")
                .pasos("Paso 1, Paso 2, Paso 3")
                .build();

        mvc.perform(post("/api/instruccions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInst)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/instruccions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Instruccion updatedInst = Instruccion.builder()
                .titulo("Instrucción Actualizada")
                .pasos("Nuevo paso 1, Nuevo paso 2")
                .build();

        mvc.perform(put("/api/instruccions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInst)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/instruccions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
