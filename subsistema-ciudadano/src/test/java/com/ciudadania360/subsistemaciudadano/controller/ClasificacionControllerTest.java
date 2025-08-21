package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.dto.clasificacion.ClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.clasificacion.ClasificacionResponse;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .nombre("Test")
                .descripcion("Desc")
                .version(0L)
                .build();
    }

    @Test
    void listAndCrudOperations() throws Exception {
        // LIST
        when(service.list()).thenReturn(List.of(response));
        mvc.perform(get("/api/clasificaciones"))
                .andExpect(status().isOk());

        // CREATE
        ClasificacionRequest createRequest = new ClasificacionRequest("Nueva", "Desc");
        when(service.create(createRequest)).thenReturn(response);
        mvc.perform(post("/api/clasificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        // GET BY ID
        when(service.get(id)).thenReturn(response);
        mvc.perform(get("/api/clasificaciones/" + id))
                .andExpect(status().isOk());

        // UPDATE
        ClasificacionRequest updateRequest = new ClasificacionRequest("Updated", "Desc");
        when(service.update(id, updateRequest)).thenReturn(response);
        mvc.perform(put("/api/clasificaciones/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        // DELETE
        doNothing().when(service).delete(id);
        mvc.perform(delete("/api/clasificaciones/" + id))
                .andExpect(status().isNoContent());
    }
}
