package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.*;
import com.ciudadania360.subsistemaciudadano.domain.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CiudadanoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        CiudadanoServicio svc = mock(CiudadanoServicio.class);

        // Ciudadano de ejemplo
        Ciudadano e = Ciudadano.builder()
                .id(UUID.randomUUID())
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .nifNie("12345678X")
                .telefono("123456789")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .ubicacionId(UUID.randomUUID())
                .consentimientoLOPD(true)
                .estado("Activo")
                .externalId("EXT123")
                .metadata("{}")
                .solicitudes(Collections.emptyList())
                .version(0L)
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        CiudadanoController controller = new CiudadanoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/ciudadanos"))
                .andExpect(status().isOk());

        // Create
        Ciudadano newCiudadano = Ciudadano.builder()
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .nifNie("12345678X")
                .telefono("123456789")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .ubicacionId(UUID.randomUUID())
                .consentimientoLOPD(true)
                .estado("Activo")
                .externalId("EXT123")
                .metadata("{}")
                .solicitudes(Collections.emptyList())
                .build();

        mvc.perform(post("/api/ciudadanos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCiudadano)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/ciudadanos/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Ciudadano updatedCiudadano = Ciudadano.builder()
                .nombre("Juan Actualizado")
                .apellidos("Pérez Actualizado")
                .email("juan.new@example.com")
                .nifNie("87654321Y")
                .telefono("987654321")
                .canalPreferido("Teléfono")
                .direccionPostal("Avenida Nueva 456")
                .ubicacionId(UUID.randomUUID())
                .consentimientoLOPD(false)
                .estado("Inactivo")
                .externalId("EXT456")
                .metadata("{\"actualizado\":true}")
                .solicitudes(Collections.emptyList())
                .build();

        mvc.perform(put("/api/ciudadanos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCiudadano)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/ciudadanos/" + e.getId()))
                .andExpect(status().isNoContent());
    }

}