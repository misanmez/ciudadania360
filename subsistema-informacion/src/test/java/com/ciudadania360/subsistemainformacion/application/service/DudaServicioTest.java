package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.domain.handler.DudaHandler;
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
class DudaServicioTest {

    @Mock
    private DudaHandler handler;

    @InjectMocks
    private DudaServicio svc;

    private Duda buildDuda() {
        return Duda.builder()
                .id(UUID.randomUUID())
                .pregunta("¿Cómo puedo obtener mi certificado de nacimiento?")
                .respuesta("Necesito ayuda con el proceso.")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Duda d = buildDuda();
        when(handler.list()).thenReturn(List.of(d));

        List<Duda> result = svc.list();

        assertThat(result).containsExactly(d);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Duda d = buildDuda();
        when(handler.get(d.getId())).thenReturn(d);

        Duda result = svc.get(d.getId());

        assertThat(result).isEqualTo(d);
        verify(handler).get(d.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Duda d = buildDuda();
        when(handler.create(any())).thenReturn(d);

        Duda result = svc.create(d);

        assertThat(result).isEqualTo(d);
        verify(handler).create(d);
    }

    @Test
    void updateDelegatesToHandler() {
        Duda d = buildDuda();
        when(handler.update(eq(d.getId()), any())).thenReturn(d);

        Duda result = svc.update(d.getId(), d);

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
