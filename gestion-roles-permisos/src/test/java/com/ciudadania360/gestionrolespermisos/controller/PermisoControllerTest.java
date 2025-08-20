package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.service.PermisoServicio;
import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
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

class PermisoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAndCreate() throws Exception {
        PermisoServicio svc = mock(PermisoServicio.class);

        Permiso e = Permiso.builder()
                .id(UUID.randomUUID())
                .codigo("PERMISO_TEST")
                .nombre("Permiso de prueba")
                .descripcion("Descripción del permiso de prueba")
                .scope("read")
                .recurso("usuario")
                .accion("listar")
                .activo(true)
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        PermisoController controller = new PermisoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // LIST
        mvc.perform(get("/api/permisos"))
                .andExpect(status().isOk());

        // CREATE
        Permiso newPermiso = Permiso.builder()
                .codigo("PERMISO_NEW")
                .nombre("Permiso nuevo")
                .descripcion("Descripción del permiso nuevo")
                .scope("write")
                .recurso("rol")
                .accion("crear")
                .activo(true)
                .build();

        mvc.perform(post("/api/permisos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPermiso)))
                .andExpect(status().isCreated());

        // GET BY ID
        mvc.perform(get("/api/permisos/" + e.getId()))
                .andExpect(status().isOk());

        // UPDATE
        Permiso updatedPermiso = Permiso.builder()
                .codigo("PERMISO_UPDATED")
                .nombre("Permiso actualizado")
                .descripcion("Descripción del permiso actualizada")
                .scope("delete")
                .recurso("rol")
                .accion("eliminar")
                .activo(false)
                .build();

        mvc.perform(put("/api/permisos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPermiso)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/permisos/" + e.getId()))
                .andExpect(status().isNoContent());
    }
}

