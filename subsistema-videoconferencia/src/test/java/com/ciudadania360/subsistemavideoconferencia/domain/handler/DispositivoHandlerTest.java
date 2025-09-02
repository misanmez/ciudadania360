package com.ciudadania360.subsistemavideoconferencia.domain.handler;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import com.ciudadania360.subsistemavideoconferencia.domain.repository.DispositivoRepository;
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
class DispositivoHandlerTest {

    @Mock
    private DispositivoRepository repo;

    @InjectMocks
    private DispositivoHandler handler;

    @Test
    void listReturnsAllDispositivos() {
        Dispositivo e = Dispositivo.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tipo("Laptop")
                .sistemaOperativo("Windows 11")
                .navegador("Chrome")
                .capacidadVideo("1080p")
                .microfono(true)
                .camara(true)
                .red("WiFi")
                .pruebaRealizada(false)
                .ultimoCheck(Instant.now())
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Dispositivo> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsDispositivoById() {
        UUID id = UUID.randomUUID();
        Dispositivo e = Dispositivo.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Dispositivo result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesDispositivo() {
        Dispositivo e = Dispositivo.builder()
                .id(UUID.randomUUID())
                .build();
        when(repo.save(any())).thenReturn(e);

        Dispositivo result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingDispositivo() {
        UUID id = UUID.randomUUID();
        Dispositivo e = Dispositivo.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Dispositivo result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesDispositivoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
