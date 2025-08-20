package com.ciudadania360.subsistemaciudadano.controller;

import com.ciudadania360.subsistemaciudadano.application.service.UbicacionServicio;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UbicacionControllerTest {

    private ObjectMapper objectMapper;  // Se inicializa manualmente
    private MockMvc mvc;
    private UbicacionServicio svc;
    private UbicacionController controller;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper(); // Inicializamos aquí
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // opcional, ISO-8601
        svc = mock(UbicacionServicio.class);
        controller = new UbicacionController(svc);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listAndCreate() throws Exception {
        // Entidad de ejemplo
        Ubicacion e = Ubicacion.builder()
                .id(UUID.randomUUID())
                .direccion("Calle Mayor 123")
                .municipio("Valencia")
                .provincia("Valencia")
                .cp("46001")
                .lat(39.46975)
                .lon(-0.37739)
                .precision(10)
                .fuente("Registro Municipal")
                .build();

        when(svc.obtenerTodos()).thenReturn(List.of(e));
        when(svc.crear(any())).thenReturn(e);
        when(svc.obtenerPorId(e.getId())).thenReturn(e);
        when(svc.actualizar(eq(e.getId()), any())).thenReturn(e);

        // LIST
        mvc.perform(get("/api/ubicacions"))
                .andExpect(status().isOk());

        // CREATE
        Ubicacion newUbicacion = Ubicacion.builder()
                .direccion("Avenida de España 45")
                .municipio("Madrid")
                .provincia("Madrid")
                .cp("28001")
                .lat(40.41678)
                .lon(-3.70379)
                .precision(15)
                .fuente("Usuario")
                .build();

        mvc.perform(post("/api/ubicacions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUbicacion)))
                .andExpect(status().isCreated()); // mejor status REST

        // GET BY ID
        mvc.perform(get("/api/ubicacions/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        Ubicacion updatedUbicacion = Ubicacion.builder()
                .direccion("Calle Nueva 10")
                .municipio("Barcelona")
                .provincia("Barcelona")
                .cp("08001")
                .lat(41.38506)
                .lon(2.17340)
                .precision(20)
                .fuente("Actualización Manual")
                .build();

        mvc.perform(put("/api/ubicacions/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUbicacion)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/ubicacions/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
