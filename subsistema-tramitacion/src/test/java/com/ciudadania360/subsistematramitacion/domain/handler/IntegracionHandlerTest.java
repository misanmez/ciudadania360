package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.domain.repository.IntegracionRepository;
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
class IntegracionHandlerTest {

    @Mock
    private IntegracionRepository repo;

    @InjectMocks
    private IntegracionHandler handler;

    @Test
    void listReturnsAllIntegraciones() {
        Integracion e = Integracion.builder()
                .id(UUID.randomUUID())
                .sistema("Sistema A")
                .tipo("Tipo A")
                .endpoint("http://endpoint-a.com")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Integracion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsIntegracionById() {
        UUID id = UUID.randomUUID();
        Integracion e = Integracion.builder()
                .id(id)
                .sistema("Sistema A")
                .tipo("Tipo A")
                .endpoint("http://endpoint-a.com")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Integracion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesIntegracion() {
        Integracion e = Integracion.builder()
                .id(UUID.randomUUID())
                .sistema("Sistema B")
                .tipo("Tipo B")
                .endpoint("http://endpoint-b.com")
                .build();

        when(repo.save(any())).thenReturn(e);

        Integracion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingIntegracion() {
        UUID id = UUID.randomUUID();
        Integracion e = Integracion.builder()
                .id(id)
                .sistema("Sistema C")
                .tipo("Tipo C")
                .endpoint("http://endpoint-c.com")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Integracion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesIntegracionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
