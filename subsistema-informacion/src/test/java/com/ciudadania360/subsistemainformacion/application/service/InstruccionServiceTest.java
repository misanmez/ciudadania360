package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.instruccion.InstruccionResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.InstruccionMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.domain.handler.InstruccionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class InstruccionServiceTest {

    @Mock
    private InstruccionHandler handler;

    @Mock
    private InstruccionMapper mapper;

    @InjectMocks
    private InstruccionService svc;

    private Instruccion buildEntity() {
        return Instruccion.builder()
                .id(UUID.randomUUID())
                .titulo("Título instrucción")
                .pasos("Paso1, Paso2")
                .build();
    }

    private InstruccionRequest toRequest(Instruccion e) {
        return new InstruccionRequest(e.getTitulo(), e.getPasos());
    }

    private InstruccionResponse toResponse(Instruccion e) {
        return new InstruccionResponse(e.getId(), e.getTitulo(), e.getPasos());
    }

    @Test
    void listDelegatesToHandler() {
        Instruccion e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<InstruccionResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Instruccion e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        InstruccionResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Instruccion e = Instruccion.builder()
                .id(UUID.randomUUID())
                .titulo("Título instrucción")
                .pasos("Paso 1, Paso 2")
                .build();

        InstruccionRequest request = new InstruccionRequest(e.getTitulo(), e.getPasos());

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Instruccion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Instruccion.class))).thenAnswer(invocation -> toResponse(invocation.getArgument(0)));


        InstruccionResponse result = svc.create(request);

        assertThat(result.getTitulo()).isEqualTo(e.getTitulo());
        assertThat(result.getPasos()).isEqualTo(e.getPasos());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Instruccion.class));
        verify(mapper).toResponse(any(Instruccion.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Instruccion e = buildEntity();
        InstruccionRequest request = toRequest(e);
        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        InstruccionResponse result = svc.update(e.getId(), request);
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
