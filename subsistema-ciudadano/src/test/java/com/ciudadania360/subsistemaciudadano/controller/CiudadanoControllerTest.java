package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoRequest;
import com.ciudadania360.shared.application.dto.ciudadano.CiudadanoResponse;
import com.ciudadania360.shared.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.shared.application.dto.direccion.DireccionResponse;
import com.ciudadania360.shared.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.application.service.CiudadanoService;
import com.ciudadania360.shared.exception.GlobalExceptionHandler;
import com.ciudadania360.shared.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CiudadanoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreateAndHandleError() throws Exception {
        CiudadanoService svc = mock(CiudadanoService.class);

        // Ciudadano de ejemplo (Response DTO)
        CiudadanoResponse e = CiudadanoResponse.builder()
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
                .direcciones(List.of(DireccionResponse.builder().id(UUID.randomUUID()).build()))
                .consentimientos(List.of(ConsentimientoResponse.builder().id(UUID.randomUUID()).build()))
                .solicitudes(List.of(SolicitudResponse.builder().id(UUID.randomUUID()).build()))
                .build();

        // Mock del servicio
        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(CiudadanoRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(CiudadanoRequest.class))).thenReturn(e);

        CiudadanoController controller = new CiudadanoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        // List
        mvc.perform(get("/api/ciudadanos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(e.getId().toString()))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].direcciones[0].id").exists())
                .andExpect(jsonPath("$[0].consentimientos[0].id").exists())
                .andExpect(jsonPath("$[0].solicitudes[0].id").exists());

        // Create (válido)
        CiudadanoRequest newCiudadano = CiudadanoRequest.builder()
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
                .direcciones(List.of())
                .consentimientos(List.of())
                .build();

        mvc.perform(post("/api/ciudadanos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCiudadano)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(e.getId().toString()))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.direcciones").isArray())
                .andExpect(jsonPath("$.consentimientos").isArray());

        // Simular error de validación usando BadRequestException
        CiudadanoRequest badRequest = CiudadanoRequest.builder()
                .nifNie("") // campo obligatorio vacío
                .build();

        when(svc.create(any(CiudadanoRequest.class)))
                .thenThrow(new BadRequestException("NIF/NIE es obligatorio"));

        mvc.perform(post("/api/ciudadanos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("NIF/NIE es obligatorio"))
                .andExpect(header().exists("X-Request-Id"));

        // Get by ID
        mvc.perform(get("/api/ciudadanos/" + e.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(e.getId().toString()));

        // Update
        CiudadanoRequest updatedCiudadano = CiudadanoRequest.builder()
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
                .direcciones(List.of())
                .consentimientos(List.of())
                .build();

        mvc.perform(put("/api/ciudadanos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCiudadano)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(e.getId().toString()));

        // Delete
        mvc.perform(delete("/api/ciudadanos/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
