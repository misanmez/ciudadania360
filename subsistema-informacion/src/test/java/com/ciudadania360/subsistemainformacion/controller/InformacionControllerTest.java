package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.InformacionServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InformacionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCreate() throws Exception {
        InformacionServicio svc = mock(InformacionServicio.class);

        // Información de ejemplo
        Informacion e = Informacion.builder()
                .id(UUID.randomUUID())
                .titulo("Información de Ejemplo")
                .contenido("Descripción de la información")
                .etiquetas("ejemplo, prueba")
                .audiencia("publico")
                .estadoPublicacion("borrador")
                .propietario("admin")
                .versionNumber(1)
                .fechaPublicacion(Instant.parse("2023-10-01T00:00:00Z"))
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        InformacionController controller = new InformacionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/informacions"))
                .andExpect(status().isOk());

        // Create
        Informacion newInfo = Informacion.builder()
                .titulo("Información de Ejemplo")
                .contenido("Descripción de la información")
                .etiquetas("ejemplo, prueba")
                .audiencia("publico")
                .estadoPublicacion("borrador")
                .propietario("admin")
                .versionNumber(1)
                .fechaPublicacion(Instant.parse("2023-10-01T00:00:00Z"))
                .build();

        mvc.perform(post("/api/informacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newInfo)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/informacions/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Informacion updatedInfo = Informacion.builder()
                .titulo("Información Actualizada")
                .contenido("Contenido actualizado")
                .etiquetas("actualizado")
                .audiencia("publico")
                .estadoPublicacion("publicado")
                .propietario("editor")
                .versionNumber(2)
                .fechaPublicacion(Instant.parse("2023-10-02T00:00:00Z"))
                .build();

        mvc.perform(put("/api/informacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/informacions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
