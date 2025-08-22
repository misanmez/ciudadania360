package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetRequest;
import com.ciudadania360.subsistemainformacion.application.dto.dataset.DatasetResponse;
import com.ciudadania360.subsistemainformacion.application.service.DatasetService;
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

class DatasetControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        DatasetService svc = mock(DatasetService.class);

        DatasetResponse e = DatasetResponse.builder()
                .id(UUID.randomUUID())
                .nombre("Dataset 1")
                .descripcion("Descripción del dataset")
                .fuente("Fuente 1")
                .esquema("{\"campo\":\"tipo\"}")
                .periodicidad("Mensual")
                .licencia("MIT")
                .urlPortalDatos("http://datos.example.com")
                .formato("CSV")
                .responsable("Responsable")
                .ultimaActualizacion(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(DatasetRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(DatasetRequest.class))).thenReturn(e);

        var controller = new DatasetController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/datasets")).andExpect(status().isOk());

        DatasetRequest request = new DatasetRequest(
                "Dataset 1", "Descripción del dataset", "Fuente 1", "{\"campo\":\"tipo\"}",
                "Mensual", "MIT", "http://datos.example.com", "CSV", "Responsable", Instant.now()
        );

        mvc.perform(post("/api/datasets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/datasets/" + e.getId())).andExpect(status().isOk());

        DatasetRequest updateRequest = new DatasetRequest(
                "Dataset actualizado", "Nueva descripción", "Fuente 2", "{\"campo\":\"tipo2\"}",
                "Semanal", "GPL", "http://nuevoportal.com", "JSON", "Nuevo responsable", Instant.now()
        );

        mvc.perform(put("/api/datasets/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/datasets/" + e.getId())).andExpect(status().isNoContent());
    }
}
