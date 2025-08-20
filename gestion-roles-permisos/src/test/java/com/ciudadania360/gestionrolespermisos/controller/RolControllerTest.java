package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.service.RolServicio;
import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.fasterxml.jackson.databind.ObjectMapper;
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

class RolControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        RolServicio svc = mock(RolServicio.class);
        Rol e = Rol.builder()
                .id(UUID.randomUUID())
                .codigo("AGENTE_TRAMITACION")
                .nombre("Nombre del Rol")
                .descripcion("Descripción del Rol")
                .nivel("ADMIN")
                .activo(true)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        RolController controller = new RolController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // LIST
        mvc.perform(get("/api/rols"))
                .andExpect(status().isOk());

        // CREATE
        Rol newRol = Rol.builder()
                .codigo("NUEVO_ROL")
                .nombre("Nombre del Rol")
                .descripcion("Descripción del Rol")
                .nivel("AGENTE")
                .activo(true)
                .build();

        mvc.perform(post("/api/rols")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRol)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/rols/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        Rol updatedRol = Rol.builder()
                .codigo("ROL_ACTUALIZADO")
                .nombre("Nombre del Rol Actualizado")
                .descripcion("Descripción del Rol Actualizada")
                .nivel("ADMIN")
                .activo(true)
                .build();

        mvc.perform(put("/api/rols/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRol)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/rols/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}
