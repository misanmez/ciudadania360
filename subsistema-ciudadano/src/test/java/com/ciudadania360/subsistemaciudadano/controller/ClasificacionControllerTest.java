package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.ClasificacionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        ClasificacionServicio svc = mock(ClasificacionServicio.class);

        // Clasificacion de ejemplo
        Clasificacion e = Clasificacion.builder()
                .id(UUID.randomUUID())
                .codigo("X")
                .nombre("Nombre X")
                .descripcion("Descripción X")
                .tipo("Tipo X")
                .padre(null)
                .hijos(List.of())
                .build();

        // Mock del servicio
        when(svc.obtenerTodos()).thenReturn(List.of(e));
        when(svc.crear(any())).thenReturn(e);
        when(svc.obtenerPorId(e.getId())).thenReturn(e);
        when(svc.actualizar(eq(e.getId()), any())).thenReturn(e);

        ClasificacionController controller = new ClasificacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/clasificacions"))
                .andExpect(status().isOk());

        // Create
        Clasificacion newClasificacion = Clasificacion.builder()
                .codigo("X")
                .nombre("Nombre X")
                .descripcion("Descripción X")
                .tipo("Tipo X")
                .padre(null)
                .hijos(List.of())
                .build();

        mvc.perform(post("/api/clasificacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newClasificacion)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/clasificacions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Clasificacion updatedClasificacion = Clasificacion.builder()
                .codigo("Y")
                .nombre("Nombre Y")
                .descripcion("Descripción Y")
                .tipo("Tipo Y")
                .padre(null)
                .hijos(List.of())
                .build();

        mvc.perform(put("/api/clasificacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClasificacion)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/clasificacions/" + e.getId()))
                .andExpect(status().isNoContent());
    }

}
