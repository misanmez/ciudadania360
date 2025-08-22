package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.handler.DocumentoHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.DocumentoMapper;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.documento.DocumentoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DocumentoServiceTest {

    @Mock private DocumentoHandler handler;
    @Mock private DocumentoMapper mapper;
    @InjectMocks private DocumentoService svc;

    private Documento buildEntity() {
        return Documento.builder()
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
                .metadatos("{\"key\":\"value\"}")
                .version(1L)
                .build();
    }

    private DocumentoRequest toRequest(Documento e) {
        return new DocumentoRequest(
                e.getCarpetaId(), e.getNombre(), e.getTipoMime(),
                e.getUriAlmacenamiento(), e.getHash(), e.getVersionNumber(),
                e.getFechaSubida(), e.getOrigen(), e.getFirmado(), e.getMetadatos()
        );
    }

    private DocumentoResponse toResponse(Documento e) {
        return new DocumentoResponse(
                e.getId(), e.getCarpetaId(), e.getNombre(), e.getTipoMime(),
                e.getUriAlmacenamiento(), e.getHash(), e.getVersionNumber(),
                e.getFechaSubida(), e.getOrigen(), e.getFirmado(), e.getMetadatos()
        );
    }

    @Test void listDelegatesToHandler() {
        Documento e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<DocumentoResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Documento e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DocumentoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Documento e = buildEntity();
        DocumentoRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Documento.class))).thenReturn(e);
        when(mapper.toResponse(any(Documento.class))).thenReturn(toResponse(e));

        DocumentoResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Documento.class));
        verify(mapper).toResponse(any(Documento.class));
    }


    @Test void updateDelegatesToHandler() {
        Documento e = buildEntity();
        DocumentoRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DocumentoResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
