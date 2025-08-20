package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.service.DatasetServicio;
import com.ciudadania360.subsistemainformacion.domain.entity.Dataset;
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

class DatasetControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        DatasetServicio svc = mock(DatasetServicio.class);

        Dataset e = Dataset.builder()
                .id(UUID.randomUUID())
                .nombre("Example Dataset")
                .descripcion("Description of dataset")
                .fuente("http://example.com/dataset")
                .urlPortalDatos("http://example.com/dataset")
                .formato("CSV")
                .responsable("Admin")
                .ultimaActualizacion(Instant.now())
                .version(0L)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        DatasetController controller = new DatasetController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/datasets"))
                .andExpect(status().isOk());

        // Create
        Dataset newDataset = Dataset.builder()
                .nombre("New Dataset")
                .descripcion("New description")
                .fuente("http://example.com/new-dataset")
                .urlPortalDatos("http://example.com/new-dataset")
                .formato("JSON")
                .responsable("User")
                .ultimaActualizacion(Instant.now())
                .build();

        mvc.perform(post("/api/datasets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDataset)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/datasets/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Dataset updatedDataset = Dataset.builder()
                .nombre("Updated Dataset")
                .descripcion("Updated description")
                .fuente("http://example.com/updated-dataset")
                .urlPortalDatos("http://example.com/updated-dataset")
                .formato("XML")
                .responsable("Admin")
                .ultimaActualizacion(Instant.now())
                .build();

        mvc.perform(put("/api/datasets/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDataset)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/datasets/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
