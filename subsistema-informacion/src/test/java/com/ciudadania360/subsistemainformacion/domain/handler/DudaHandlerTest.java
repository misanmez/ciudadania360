package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.domain.repository.DudaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DudaHandlerTest {

    @Mock
    private DudaRepository repo;

    @InjectMocks
    private DudaHandler handler;

    @Test
    void listReturnsAllDudas() {
        Duda e = Duda.builder()
                .id(UUID.randomUUID())
                .pregunta("¿Cómo puedo obtener mi certificado de nacimiento?")
                .respuesta("Necesito ayuda con el proceso.")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Duda> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsDudaById() {
        UUID id = UUID.randomUUID();
        Duda e = Duda.builder()
                .id(id)
                .pregunta("Pregunta ejemplo")
                .respuesta("Respuesta ejemplo")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Duda result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesDuda() {
        Duda e = Duda.builder()
                .id(UUID.randomUUID())
                .pregunta("Nueva pregunta")
                .respuesta("Nueva respuesta")
                .build();
        when(repo.save(any())).thenReturn(e);

        Duda result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingDuda() {
        UUID id = UUID.randomUUID();
        Duda e = Duda.builder()
                .id(id)
                .pregunta("Pregunta original")
                .respuesta("Respuesta original")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Duda result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesDudaById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
