package com.ciudadania360.subsistemacomunicaciones.application.service;

import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import com.ciudadania360.subsistemacomunicaciones.domain.handler.ComunicacionHandler;
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
class ComunicacionServicioTest {

    @Mock
    private ComunicacionHandler handler;

    @InjectMocks
    private ComunicacionServicio svc;

    private Comunicacion buildComunicacion() {
        return Comunicacion.builder()
                .id(UUID.randomUUID())
                .canal("Email")
                .asunto("Asunto de prueba")
                .cuerpo("Cuerpo de prueba")
                .estado("PENDIENTE")
                .destinatario("destinatario@example.com")
                .ciudadanoId(UUID.randomUUID())
                .programadaPara(Instant.now())
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Comunicacion c = buildComunicacion();
        when(handler.list()).thenReturn(List.of(c));

        List<Comunicacion> result = svc.list();

        assertThat(result).containsExactly(c);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Comunicacion c = buildComunicacion();
        when(handler.get(c.getId())).thenReturn(c);

        Comunicacion result = svc.get(c.getId());

        assertThat(result).isEqualTo(c);
        verify(handler).get(c.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Comunicacion c = buildComunicacion();
        when(handler.create(any())).thenReturn(c);

        Comunicacion result = svc.create(c);

        assertThat(result).isEqualTo(c);
        verify(handler).create(c);
    }

    @Test
    void updateDelegatesToHandler() {
        Comunicacion c = buildComunicacion();
        when(handler.update(eq(c.getId()), any())).thenReturn(c);

        Comunicacion result = svc.update(c.getId(), c);

        assertThat(result).isEqualTo(c);
        verify(handler).update(c.getId(), c);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
