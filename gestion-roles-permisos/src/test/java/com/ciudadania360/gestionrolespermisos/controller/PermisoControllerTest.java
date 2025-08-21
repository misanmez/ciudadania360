package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoResponse;
import com.ciudadania360.gestionrolespermisos.application.service.PermisoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

class PermisoControllerTest {

    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private PermisoResponse ejemplo;

    @BeforeEach
    void setup() {
        PermisoService svc = mock(PermisoService.class);

        ejemplo = PermisoResponse.builder()
                .id(UUID.randomUUID())
                .codigo("PERMISO_EJEMPLO")
                .nombre("Permiso de ejemplo")
                .descripcion("Descripción del permiso")
                .activo(true)
                .build();

        when(svc.list()).thenReturn(List.of(ejemplo));
        when(svc.create(any(PermisoRequest.class))).thenReturn(ejemplo);
        when(svc.get(ejemplo.getId())).thenReturn(ejemplo);
        when(svc.update(eq(ejemplo.getId()), any(PermisoRequest.class))).thenReturn(ejemplo);

        // Configuramos MockMvc de forma standalone (no ApplicationContext)
        mvc = MockMvcBuilders.standaloneSetup(new PermisoController(svc)).build();
    }

    @Test
    void listAndCreate() throws Exception {
        // List
        mvc.perform(get("/api/permisos"))
                .andExpect(status().isOk());

        // Create
        PermisoRequest newPermiso = PermisoRequest.builder()
                .codigo("PERMISO_NUEVO")
                .nombre("Nuevo permiso")
                .descripcion("Descripción del nuevo permiso")
                .activo(true)
                .build();

        mvc.perform(post("/api/permisos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPermiso)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/permisos/" + ejemplo.getId()))
                .andExpect(status().isOk());

        // Update
        PermisoRequest updatedPermiso = PermisoRequest.builder()
                .codigo("PERMISO_ACTUALIZADO")
                .nombre("Permiso actualizado")
                .descripcion("Descripción actualizada")
                .activo(false)
                .build();

        mvc.perform(put("/api/permisos/" + ejemplo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPermiso)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/permisos/" + ejemplo.getId()))
                .andExpect(status().isNoContent());
    }

}
