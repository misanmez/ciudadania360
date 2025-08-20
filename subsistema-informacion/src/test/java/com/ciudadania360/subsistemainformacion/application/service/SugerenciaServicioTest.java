package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.domain.handler.SugerenciaHandler;
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
class SugerenciaServicioTest {

    @Mock
    private SugerenciaHandler handler;

    @InjectMocks
    private SugerenciaServicio svc;

    private Sugerencia buildSugerencia() {
        return Sugerencia.builder()
                .id(UUID.randomUUID())
                .titulo("Ejemplo de Sugerencia")
                .descripcion("Descripción de la sugerencia")
                .ciudadanoId(UUID.randomUUID())
                .estado("Pendiente")
                .prioridad("Alta")
                .areaResponsable("Atención al Ciudadano")
                .fecha(Instant.now())
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Sugerencia s = buildSugerencia();
        when(handler.list()).thenReturn(List.of(s));

        List<Sugerencia> result = svc.list();

        assertThat(result).containsExactly(s);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Sugerencia s = buildSugerencia();
        when(handler.get(s.getId())).thenReturn(s);

        Sugerencia result = svc.get(s.getId());

        assertThat(result).isEqualTo(s);
        verify(handler).get(s.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Sugerencia s = buildSugerencia();
        when(handler.create(any())).thenReturn(s);

        Sugerencia result = svc.create(s);

        assertThat(result).isEqualTo(s);
        verify(handler).create(s);
    }

    @Test
    void updateDelegatesToHandler() {
        Sugerencia s = buildSugerencia();
        when(handler.update(eq(s.getId()), any())).thenReturn(s);

        Sugerencia result = svc.update(s.getId(), s);

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
