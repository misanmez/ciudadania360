package com.ciudadania360.subsistematramitacion.controller;

import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoResponse;
import com.ciudadania360.subsistematramitacion.application.service.DocumentoService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentoControllerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 🔹 Esto permite serializar Instant
    }

    @Test
    void listAndCreate() throws Exception {
        DocumentoService svc = mock(DocumentoService.class);

        DocumentoResponse e = DocumentoResponse.builder()
                .id(UUID.randomUUID())
                .carpetaId(UUID.randomUUID())
                .nombre("Documento 1")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/docs/doc1.pdf")
                .hash("abc123")
                .versionNumber(1)
                .fechaSubida(Instant.now())
                .origen("ORIGEN")
                .firmado(true)
                .metadatos("{}")
                .build();

        when(svc.list()).thenReturn(List.of(e));
        when(svc.create(any(DocumentoRequest.class))).thenReturn(e);
        when(svc.get(e.getId())).thenReturn(e);
        when(svc.update(eq(e.getId()), any(DocumentoRequest.class))).thenReturn(e);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new DocumentoController(svc)).build();

        mvc.perform(get("/api/documentos")).andExpect(status().isOk());

        DocumentoRequest req = DocumentoRequest.builder()
                .carpetaId(UUID.randomUUID())
                .nombre("Documento 1")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/docs/doc1.pdf")
                .hash("abc123")
                .versionNumber(1)
                .fechaSubida(Instant.now())
                .origen("ORIGEN")
                .firmado(true)
                .metadatos("{}")
                .build();

        mvc.perform(post("/api/documentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        mvc.perform(get("/api/documentos/" + e.getId())).andExpect(status().isOk());

        mvc.perform(put("/api/documentos/" + e.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        mvc.perform(delete("/api/documentos/" + e.getId())).andExpect(status().isNoContent());
    }
}
