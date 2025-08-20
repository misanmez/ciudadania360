package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.handler.DocumentoHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentoServicioTest {

    @Mock
    private DocumentoHandler handler;

    @InjectMocks
    private DocumentoServicio svc;

    private Documento buildDocumento() {
        return Documento.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre del Documento")
                .tipoMime("Tipo de Documento")
                .uriAlmacenamiento("Contenido del Documento")
                .carpetaId(UUID.randomUUID())
                .hash("abc123")
                .versionNumber(1)
                .fechaSubida(Instant.now())
                .origen("Sistema")
                .firmado(false)
                .metadatos("{\"campo\":\"valor\"}")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Documento d = buildDocumento();
        when(handler.list()).thenReturn(List.of(d));

        List<Documento> result = svc.list();

        assertThat(result).containsExactly(d);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Documento d = buildDocumento();
        when(handler.get(d.getId())).thenReturn(d);

        Documento result = svc.get(d.getId());

        assertThat(result).isEqualTo(d);
        verify(handler).get(d.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Documento d = buildDocumento();
        when(handler.create(any())).thenReturn(d);

        Documento result = svc.create(d);

        assertThat(result).isEqualTo(d);
        verify(handler).create(d);
    }

    @Test
    void updateDelegatesToHandler() {
        Documento d = buildDocumento();
        when(handler.update(eq(d.getId()), any())).thenReturn(d);

        Documento result = svc.update(d.getId(), d);

        assertThat(result).isEqualTo(d);
        verify(handler).update(d.getId(), d);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
