package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.CarpetaServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarpetaControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        CarpetaServicio svc = mock(CarpetaServicio.class);

        Carpeta e = Carpeta.builder()
                .id(UUID.randomUUID())
                .nombre("Carpeta de Prueba")
                .descripcion("Descripción de la carpeta de prueba")
                .tipo("TIPO_A")
                .ruta("/ruta/prueba")
                .permisos("{\"read\":true}")
                .numeroExpediente("EXP-001")
                .estado("ACTIVA")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        CarpetaController controller = new CarpetaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/carpetas"))
                .andExpect(status().isOk());

        // Create
        Carpeta newCarpeta = Carpeta.builder()
                .nombre("Nueva Carpeta")
                .descripcion("Descripción nueva")
                .tipo("TIPO_B")
                .ruta("/ruta/nueva")
                .permisos("{\"write\":true}")
                .numeroExpediente("EXP-002")
                .estado("ACTIVA")
                .build();

        mvc.perform(post("/api/carpetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCarpeta)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/carpetas/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Carpeta updatedCarpeta = Carpeta.builder()
                .nombre("Carpeta Actualizada")
                .descripcion("Descripción actualizada")
                .tipo("TIPO_C")
                .ruta("/ruta/actualizada")
                .permisos("{\"read\":true,\"write\":true}")
                .numeroExpediente("EXP-003")
                .estado("CERRADA")
                .build();

        mvc.perform(put("/api/carpetas/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCarpeta)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/carpetas/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verificar que el servicio fue llamado al eliminar
        verify(svc).delete(e.getId());
    }
}
