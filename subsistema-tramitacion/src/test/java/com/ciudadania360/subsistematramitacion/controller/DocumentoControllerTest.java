package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.service.DocumentoServicio;
import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void listAndCrudOperations() throws Exception {
        DocumentoServicio svc = mock(DocumentoServicio.class);

        Documento e = Documento.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre del Documento")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/documentos/doc1.pdf")
                .hash("hash123")
                .versionNumber(1)
                .fechaSubida(Instant.now())
                .origen("sistema")
                .firmado(false)
                .metadatos("{\"clave\":\"valor\"}")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any())).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any())).thenReturn(e);

        DocumentoController controller = new DocumentoController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        // List
        mvc.perform(get("/api/documentos"))
                .andExpect(status().isOk());

        // Create
        Documento newDocumento = Documento.builder()
                .nombre("Nuevo Documento")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/documentos/doc2.pdf")
                .hash("hash456")
                .versionNumber(1)
                .fechaSubida(Instant.now())
                .origen("usuario")
                .firmado(true)
                .metadatos("{\"info\":\"dato\"}")
                .build();

        mvc.perform(post("/api/documentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDocumento)))
                .andExpect(status().isCreated());

        // Get by ID
        mvc.perform(get("/api/documentos/" + e.getId()))
                .andExpect(status().isOk());

        // Update
        Documento updatedDocumento = Documento.builder()
                .nombre("Documento Actualizado")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/documentos/doc1_v2.pdf")
                .hash("hash789")
                .versionNumber(2)
                .fechaSubida(Instant.now())
                .origen("sistema")
                .firmado(true)
                .metadatos("{\"clave\":\"nuevo_valor\"}")
                .build();

        mvc.perform(put("/api/documentos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDocumento)))
                .andExpect(status().isOk());

        // Delete
        mvc.perform(delete("/api/documentos/" + e.getId()))
                .andExpect(status().isNoContent());

        // Verificar que el servicio fue llamado al eliminar
        verify(svc).delete(e.getId());
    }
}
