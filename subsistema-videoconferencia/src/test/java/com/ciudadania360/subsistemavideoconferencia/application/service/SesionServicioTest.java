package com.ciudadania360.subsistemavideoconferencia.application.service;

import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import com.ciudadania360.subsistemavideoconferencia.domain.handler.SesionHandler;
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
class SesionServicioTest {

    @Mock
    private SesionHandler handler;

    @InjectMocks
    private SesionServicio svc;

    private Sesion buildSesion() {
        return Sesion.builder()
                .id(UUID.randomUUID())
                .solicitudId(UUID.randomUUID())
                .fechaInicio(Instant.now())
                .fechaFin(Instant.now().plusSeconds(3600))
                .estado("EN_CURSO")
                .plataforma("Zoom")
                .enlace("https://zoom.example.com/sesion")
                .codigoAcceso("123456")
                .grabacionUri("https://storage.example.com/grabacion.mp4")
                .motivo("Prueba de Sesión")
                .operadorId("operador-01")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Sesion s = buildSesion();
        when(handler.list()).thenReturn(List.of(s));

        List<Sesion> result = svc.list();

        assertThat(result).containsExactly(s);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Sesion s = buildSesion();
        when(handler.get(s.getId())).thenReturn(s);

        Sesion result = svc.get(s.getId());

        assertThat(result).isEqualTo(s);
        verify(handler).get(s.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Sesion s = buildSesion();
        when(handler.create(any())).thenReturn(s);

        Sesion result = svc.create(s);

        assertThat(result).isEqualTo(s);
        verify(handler).create(s);
    }

    @Test
    void updateDelegatesToHandler() {
        Sesion s = buildSesion();
        when(handler.update(eq(s.getId()), any())).thenReturn(s);

        Sesion result = svc.update(s.getId(), s);

        assertThat(result).isEqualTo(s);
        verify(handler).update(s.getId(), s);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
