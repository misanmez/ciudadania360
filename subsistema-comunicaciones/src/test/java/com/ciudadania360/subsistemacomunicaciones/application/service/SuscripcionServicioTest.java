package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Suscripcion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.SuscripcionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuscripcionServicioTest {

    @Mock
    private SuscripcionHandler handler;

    @InjectMocks
    private SuscripcionServicio svc;

    private Suscripcion buildSuscripcion() {
        return Suscripcion.builder()
                .id(UUID.randomUUID())
                .ciudadanoId(UUID.randomUUID())
                .tema("Suscripción de Ejemplo")
                .activo(true)
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        when(handler.list()).thenReturn(List.of(e));

        List<Suscripcion> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        when(handler.get(e.getId())).thenReturn(e);

        Suscripcion result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        when(handler.create(any())).thenReturn(e);

        Suscripcion result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Suscripcion e = buildSuscripcion();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        Suscripcion result = svc.update(e.getId(), e);

        assertThat(result).isEqualTo(e);
        verify(handler).update(e.getId(), e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
