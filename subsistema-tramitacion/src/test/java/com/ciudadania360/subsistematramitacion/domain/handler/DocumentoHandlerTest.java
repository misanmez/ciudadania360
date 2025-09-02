package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Documento;
import com.ciudadania360.subsistematramitacion.domain.repository.DocumentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentoHandlerTest {

    @Mock
    private DocumentoRepository repo;

    @InjectMocks
    private DocumentoHandler handler;

    @Test
    void listReturnsAllDocumentos() {
        Documento e = Documento.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre del Documento")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/ruta/documento.pdf")
                .fechaSubida(Instant.now())
                .versionNumber(1)
                .firmado(false)
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Documento> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsDocumentoById() {
        UUID id = UUID.randomUUID();
        Documento e = Documento.builder()
                .id(id)
                .nombre("Documento Ejemplo")
                .tipoMime("application/pdf")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Documento result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesDocumento() {
        Documento e = Documento.builder()
                .id(UUID.randomUUID())
                .nombre("Nuevo Documento")
                .tipoMime("application/pdf")
                .uriAlmacenamiento("/ruta/nuevo.pdf")
                .build();

        when(repo.save(any())).thenReturn(e);

        Documento result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingDocumento() {
        UUID id = UUID.randomUUID();
        Documento e = Documento.builder()
                .id(id)
                .nombre("Documento Original")
                .tipoMime("application/pdf")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Documento result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesDocumentoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
