package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.domain.handler.InformacionHandler;
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
class InformacionServicioTest {

    @Mock
    private InformacionHandler handler;

    @InjectMocks
    private InformacionServicio svc;

    private Informacion buildInformacion() {
        return Informacion.builder()
                .id(UUID.randomUUID())
                .titulo("Información de Ejemplo")
                .contenido("Descripción de la información")
                .etiquetas("ejemplo, prueba")
                .audiencia("Todos")
                .estadoPublicacion("Publicado")
                .propietario("Departamento Ejemplo")
                .versionNumber(1)
                .fechaPublicacion(Instant.parse("2023-10-01T00:00:00Z"))
                .version(1L)
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Informacion i = buildInformacion();
        when(handler.list()).thenReturn(List.of(i));

        List<Informacion> result = svc.list();

        assertThat(result).containsExactly(i);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Informacion i = buildInformacion();
        when(handler.get(i.getId())).thenReturn(i);

        Informacion result = svc.get(i.getId());

        assertThat(result).isEqualTo(i);
        verify(handler).get(i.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Informacion i = buildInformacion();
        when(handler.create(any())).thenReturn(i);

        Informacion result = svc.create(i);

        assertThat(result).isEqualTo(i);
        verify(handler).create(i);
    }

    @Test
    void updateDelegatesToHandler() {
        Informacion i = buildInformacion();
        when(handler.update(eq(i.getId()), any())).thenReturn(i);

        Informacion result = svc.update(i.getId(), i);

        assertThat(result).isEqualTo(i);
        verify(handler).update(i.getId(), i);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
