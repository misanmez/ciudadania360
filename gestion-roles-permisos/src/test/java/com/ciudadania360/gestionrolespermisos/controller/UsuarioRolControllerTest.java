package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.service.UsuarioRolServicio;
import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
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

class UsuarioRolControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Test
    void listAndCreate() throws Exception {
        UsuarioRolServicio svc = mock(UsuarioRolServicio.class);

        UsuarioRol e = UsuarioRol.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .rolId(UUID.randomUUID())
                .asignadoPor("admin")
                .fechaAsignacion(Instant.now())
                .origen("SSO")
                .observaciones("Asignación inicial")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        UsuarioRolController controller = new UsuarioRolController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // LIST
        mvc.perform(get("/api/usuariorols"))
                .andExpect(status().isOk());

        // CREATE
        UsuarioRol newUsuarioRol = UsuarioRol.builder()
                .ciudadanoId(UUID.randomUUID())
                .rolId(UUID.randomUUID())
                .asignadoPor("admin")
                .fechaAsignacion(Instant.now())
                .origen("OAUTH2")
                .observaciones("Asignación temporal")
                .build();

        mvc.perform(post("/api/usuariorols")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUsuarioRol)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/usuariorols/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        UsuarioRol updatedUsuarioRol = UsuarioRol.builder()
                .ciudadanoId(UUID.randomUUID())
                .rolId(UUID.randomUUID())
                .asignadoPor("supervisor")
                .fechaAsignacion(Instant.now())
                .fechaCaducidad(Instant.now().plusSeconds(3600))
                .origen("SYNC")
                .observaciones("Acceso limitado por tiempo")
                .build();

        mvc.perform(put("/api/usuariorols/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUsuarioRol)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/usuariorols/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
