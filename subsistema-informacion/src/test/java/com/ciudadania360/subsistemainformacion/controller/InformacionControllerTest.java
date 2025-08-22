package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionResponse;
import com.ciudadania360.subsistemainformacion.application.service.InformacionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InformacionControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        InformacionService svc = mock(InformacionService.class);

        // Response de ejemplo
        InformacionResponse e = InformacionResponse.builder()
                .id(UUID.randomUUID())
                .titulo("Título de prueba")
                .contenido("Contenido de prueba")
                .etiquetas("etiqueta1,etiqueta2")
                .audiencia("General")
                .estadoPublicacion("Publicado")
                .propietario("Admin")
                .versionNumber(1)
                .fechaPublicacion(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(InformacionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(InformacionRequest.class))).thenReturn(e);

        // Configuramos MockMvc
        MockMvc mvc = MockMvcBuilders.standaloneSetup(new InformacionController(svc)).build();

        // List
        mvc.perform(get("/api/informaciones"))
                .andExpect(status().isOk());

        // Create
        InformacionRequest newInfo = InformacionRequest.builder()
                .titulo("Título de prueba")
                .contenido("Contenido de prueba")
                .etiquetas("etiqueta1,etiqueta2")
                .audiencia("General")
                .estadoPublicacion("Publicado")
                .propietario("Admin")
                .versionNumber(1)
                .fechaPublicacion(Instant.now())
                .build();

        mvc.perform(post("/api/informaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInfo)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/informaciones/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        InformacionRequest updatedInfo = InformacionRequest.builder()
                .titulo("Título actualizado")
                .contenido("Contenido actualizado")
                .etiquetas("etiqueta3")
                .audiencia("Privado")
                .estadoPublicacion("Borrador")
                .propietario("Admin2")
                .versionNumber(2)
                .fechaPublicacion(Instant.now())
                .build();

        mvc.perform(put("/api/informaciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/informaciones/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
