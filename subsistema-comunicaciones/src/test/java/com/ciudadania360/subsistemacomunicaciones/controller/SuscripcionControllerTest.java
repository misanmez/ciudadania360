package com.ciudadania360.subsistemacomunicaciones.controller;

import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.suscripcion.SuscripcionResponse;
import com.ciudadania360.subsistemacomunicaciones.application.service.SuscripcionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SuscripcionControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        SuscripcionService svc = mock(SuscripcionService.class);

        SuscripcionResponse e = SuscripcionResponse.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tema("Noticias")
                .activo(true)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(SuscripcionRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(SuscripcionRequest.class))).thenReturn(e);

        SuscripcionController controller = new SuscripcionController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        mvc.perform(get("/api/suscripciones")).andExpect(status().isOk());

        SuscripcionRequest newSub = SuscripcionRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .tema("Noticias")
                .activo(true)
                .build();

        mvc.perform(post("/api/suscripciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSub)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/suscripciones/" + e.getId())).andExpect(status().isOk());

        SuscripcionRequest updatedSub = SuscripcionRequest.builder()
                .ciudadanoId(UUID.randomUUID())
                .tema("Promociones")
                .activo(false)
                .build();

        mvc.perform(put("/api/suscripciones/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSub)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/suscripciones/" + e.getId())).andExpect(status().isNoContent());
    }
}
