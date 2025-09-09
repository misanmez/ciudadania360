package com.ciudadania360.gestionrolespermisos.controller;

import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolResponse;
import com.ciudadania360.gestionrolespermisos.application.service.RolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class RolControllerTest {

    private MockMvc mvc;

    @Mock
    private RolService rolService;

    @InjectMocks
    private RolController rolController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(rolController).build();
    }

    @Test
    void listAndCreateUpdateDelete() throws Exception {
        RolResponse sample = RolResponse.builder()
                .id(UUID.randomUUID())
                .nombre("ADMIN")
                .descripcion("Administrador del sistema")
                .build();

        when(rolService.list()).thenReturn(List.of(sample));
        when(rolService.create(any(RolRequest.class))).thenReturn(sample);
        when(rolService.update(eq(sample.getId()), any(RolRequest.class))).thenReturn(sample);

        // LIST
        mvc.perform(get("/api/roles"))
                .andExpect(status().isOk());

        // CREATE
        RolRequest newRol = RolRequest.builder()
                .nombre("USER")
                .descripcion("Usuario normal")
                .build();

        mvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRol)))
                .andExpect(status().isCreated());

        // UPDATE
        RolRequest updatedRol = RolRequest.builder()
                .nombre("SUPERUSER")
                .descripcion("Usuario con permisos extendidos")
                .build();

        mvc.perform(put("/api/roles/" + sample.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRol)))
                .andExpect(status().isOk());

        // DELETE
        mvc.perform(delete("/api/roles/" + sample.getId()))
                .andExpect(status().isNoContent());
    }
}
