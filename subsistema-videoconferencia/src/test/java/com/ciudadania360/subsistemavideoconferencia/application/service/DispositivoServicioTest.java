package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Dispositivo;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.DispositivoHandler;
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
class DispositivoServicioTest {

    @Mock
    private DispositivoHandler handler;

    @InjectMocks
    private DispositivoServicio svc;

    private Dispositivo buildDispositivo() {
        return Dispositivo.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tipo("Tablet")
                .sistemaOperativo("iOS")
                .navegador("Safari")
                .capacidadVideo("1080p")
                .microfono(true)
                .camara(true)
                .red("WiFi")
                .pruebaRealizada(true)
                .ultimoCheck(Instant.now())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Dispositivo d = buildDispositivo();
        when(handler.list()).thenReturn(List.of(d));

        List<Dispositivo> result = svc.list();

        assertThat(result).containsExactly(d);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Dispositivo d = buildDispositivo();
        when(handler.get(d.getId())).thenReturn(d);

        Dispositivo result = svc.get(d.getId());

        assertThat(result).isEqualTo(d);
        verify(handler).get(d.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Dispositivo d = buildDispositivo();
        when(handler.create(any())).thenReturn(d);

        Dispositivo result = svc.create(d);

        assertThat(result).isEqualTo(d);
        verify(handler).create(d);
    }

    @Test
    void updateDelegatesToHandler() {
        Dispositivo d = buildDispositivo();
        when(handler.update(eq(d.getId()), any())).thenReturn(d);

        Dispositivo result = svc.update(d.getId(), d);

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
