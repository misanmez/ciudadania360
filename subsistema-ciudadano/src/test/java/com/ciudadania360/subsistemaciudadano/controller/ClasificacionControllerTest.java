package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.shared.application.dto.clasificacion.ClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.service.ClasificacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClasificacionControllerTest {

    private MockMvc mvc;
    private ClasificacionService service;
    private ObjectMapper objectMapper;

    private UUID id;
    private ClasificacionResponse response;

    @BeforeEach
    void setUp() {
        service = mock(ClasificacionService.class);
        ClasificacionController controller = new ClasificacionController(service);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();

        id = UUID.randomUUID();
        response = ClasificacionResponse.builder()
                .id(id)
                .codigo("C001")
                .nombre("Test")
                .descripcion("Desc")
                .tipo("Tipo1")
                .padreId(null)
                .hijos(List.of())
                .version(0L)
                .build();
    }

    @Test
    void listAndCrudOperations() throws Exception {
        // LIST
        when(service.list()).thenReturn(List.of(response));
        mvc.perform(get("/api/clasificaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].codigo").value("C001"))
                .andExpect(jsonPath("$[0].nombre").value("Test"))
                .andExpect(jsonPath("$[0].hijos").isArray());

        // CREATE
        ClasificacionRequest createRequest = ClasificacionRequest.builder()
                .codigo("C002")
                .nombre("Nueva")
                .descripcion("Desc")
                .tipo("Tipo2")
                .padreId(null)
                .build();
        when(service.create(any(ClasificacionRequest.class))).thenReturn(response);
        mvc.perform(post("/api/clasificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.codigo").value("C001"));

        // GET BY ID
        when(service.get(id)).thenReturn(response);
        mvc.perform(get("/api/clasificaciones/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // UPDATE
        ClasificacionRequest updateRequest = ClasificacionRequest.builder()
                .codigo("C003")
                .nombre("Updated")
                .descripcion("Desc Updated")
                .tipo("Tipo1")
                .padreId(null)
                .build();
        when(service.update(eq(id), any(ClasificacionRequest.class))).thenReturn(response);
        mvc.perform(put("/api/clasificaciones/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));

        // DELETE
        doNothing().when(service).delete(id);
        mvc.perform(delete("/api/clasificaciones/" + id))
                .andExpect(status().isNoContent());
    }
}
