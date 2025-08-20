package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.handler.CiudadanoHandler;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CiudadanoServicioTest {

    @Mock
    private CiudadanoHandler handler;

    @InjectMocks
    private CiudadanoServicio svc;

    private Ciudadano buildCiudadano() {
        return Ciudadano.builder()
                .id(UUID.randomUUID())
                .nifNie("12345678A")
                .nombre("Juan")
                .apellidos("Pérez Gómez")
                .email("juan@example.com")
                .telefono("600123456")
                .canalPreferido("Email")
                .direccionPostal("Calle Falsa 123")
                .ubicacionId(UUID.randomUUID())
                .consentimientoLOPD(true)
                .estado("ACTIVO")
                .externalId("EXT123")
                .metadata("{\"key\":\"value\"}")
                .solicitudes(List.of()) // lista vacía por defecto
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        when(handler.list()).thenReturn(List.of(e));

        List<Ciudadano> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        when(handler.get(e.getId())).thenReturn(e);

        Ciudadano result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        when(handler.create(any())).thenReturn(e);

        Ciudadano result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Ciudadano e = buildCiudadano();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        Ciudadano result = svc.update(e.getId(), e);

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
