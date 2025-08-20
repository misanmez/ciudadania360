package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Carpeta;
import com.ciudadania360.subsistematramitacion.domain.handler.CarpetaHandler;
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
class CarpetaServicioTest {

    @Mock
    private CarpetaHandler handler;

    @InjectMocks
    private CarpetaServicio svc;

    private Carpeta buildCarpeta() {
        return Carpeta.builder()
                .id(UUID.randomUUID())
                .nombre("Carpeta de Prueba")
                .descripcion("Descripción de la carpeta de prueba")
                .solicitudId(UUID.randomUUID())
                .tipo("Tipo A")
                .ruta("/ruta/prueba")
                .permisos("{\"read\":true}")
                .numeroExpediente("EXP-123")
                .estado("Abierta")
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Carpeta c = buildCarpeta();
        when(handler.list()).thenReturn(List.of(c));

        List<Carpeta> result = svc.list();

        assertThat(result).containsExactly(c);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Carpeta c = buildCarpeta();
        when(handler.get(c.getId())).thenReturn(c);

        Carpeta result = svc.get(c.getId());

        assertThat(result).isEqualTo(c);
        verify(handler).get(c.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Carpeta c = buildCarpeta();
        when(handler.create(any())).thenReturn(c);

        Carpeta result = svc.create(c);

        assertThat(result).isEqualTo(c);
        verify(handler).create(c);
    }

    @Test
    void updateDelegatesToHandler() {
        Carpeta c = buildCarpeta();
        when(handler.update(eq(c.getId()), any())).thenReturn(c);

        Carpeta result = svc.update(c.getId(), c);

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
