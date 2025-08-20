package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.domain.handler.InstruccionHandler;
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
class InstruccionServicioTest {

    @Mock
    private InstruccionHandler handler;

    @InjectMocks
    private InstruccionServicio svc;

    private Instruccion buildInstruccion() {
        return Instruccion.builder()
                .id(UUID.randomUUID())
                .titulo("Ejemplo de Instrucción")
                .pasos("Paso 1, Paso 2, Paso 3")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Instruccion i = buildInstruccion();
        when(handler.list()).thenReturn(List.of(i));

        List<Instruccion> result = svc.list();

        assertThat(result).containsExactly(i);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Instruccion i = buildInstruccion();
        when(handler.get(i.getId())).thenReturn(i);

        Instruccion result = svc.get(i.getId());

        assertThat(result).isEqualTo(i);
        verify(handler).get(i.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Instruccion i = buildInstruccion();
        when(handler.create(any())).thenReturn(i);

        Instruccion result = svc.create(i);

        assertThat(result).isEqualTo(i);
        verify(handler).create(i);
    }

    @Test
    void updateDelegatesToHandler() {
        Instruccion i = buildInstruccion();
        when(handler.update(eq(i.getId()), any())).thenReturn(i);

        Instruccion result = svc.update(i.getId(), i);

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
