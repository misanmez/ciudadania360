package com.ciudadania360.subsistemainformacion.controller;

import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaResponse;
import com.ciudadania360.subsistemainformacion.application.service.SugerenciaService;
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

class SugerenciaControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listAndCreate() throws Exception {
        SugerenciaService svc = mock(SugerenciaService.class);

        SugerenciaResponse e = SugerenciaResponse.builder()
                .id(UUID.randomUUID())
                .titulo("Título")
                .descripcion("Descripción")
                .ciudadanoId(UUID.randomUUID())
                .estado("Pendiente")
                .prioridad("Alta")
                .areaResponsable("TI")
                .fecha(Instant.now())
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(SugerenciaRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(SugerenciaRequest.class))).thenReturn(e);

        SugerenciaController controller = new SugerenciaController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/sugerencias")).andExpect(status().isOk());

        SugerenciaRequest request = SugerenciaRequest.builder()
                .titulo("Título")
                .descripcion("Descripción")
                .ciudadanoId(UUID.randomUUID())
                .estado("Pendiente")
                .prioridad("Alta")
                .areaResponsable("TI")
                .fecha(Instant.now())
                .build();

        mvc.perform(post("/api/sugerencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/sugerencias/" + e.getId())).andExpect(status().isOk());

        SugerenciaRequest updateRequest = SugerenciaRequest.builder()
                .titulo("Nuevo título")
                .descripcion("Nuevo detalle")
                .ciudadanoId(UUID.randomUUID())
                .estado("Resuelta")
                .prioridad("Media")
                .areaResponsable("TI")
                .fecha(Instant.now())
                .build();

        mvc.perform(put("/api/sugerencias/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/sugerencias/" + e.getId())).andExpect(status().isNoContent());
    }
}
