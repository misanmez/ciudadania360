package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ReglaClasificacionMapper;
import com.ciudadania360.subsistemaciudadano.application.validator.ReglaClasificacionValidator;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.domain.handler.ReglaClasificacionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReglaClasificacionServiceTest {

    @Mock
    private ReglaClasificacionHandler handler;

    @Mock
    private ReglaClasificacionMapper mapper;

    @Mock
    private ReglaClasificacionValidator validator;

    @InjectMocks
    private ReglaClasificacionService svc;

    private ReglaClasificacion buildRegla() {
        Clasificacion clasificacion = Clasificacion.builder()
                .id(UUID.randomUUID())
                .build();

        return ReglaClasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Regla de prueba")
                .expresion("x > 10")
                .prioridad(1)
                .activa(true)
                .clasificacion(clasificacion)
                .condiciones("{}")
                .fuente("Sistema")
                .vigenciaDesde(Instant.now())
                .vigenciaHasta(Instant.now().plusSeconds(3600))
                .version(1L)
                .build();
    }

    private ReglaClasificacionRequest toRequest(ReglaClasificacion e) {
        return new ReglaClasificacionRequest(
                e.getNombre(),
                e.getExpresion(),
                e.getPrioridad(),
                e.getActiva(),
                e.getClasificacion().getId(),
                e.getCondiciones(),
                e.getFuente(),
                e.getVigenciaDesde(),
                e.getVigenciaHasta()
        );
    }

    private ReglaClasificacionResponse toResponse(ReglaClasificacion e) {
        return new ReglaClasificacionResponse(
                e.getId(),
                e.getNombre(),
                e.getExpresion(),
                e.getPrioridad(),
                e.getActiva(),
                e.getClasificacion().getId(),
                e.getCondiciones(),
                e.getFuente(),
                e.getVigenciaDesde(),
                e.getVigenciaHasta(),
                e.getVersion()
        );
    }

    @Test
    void listDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        ReglaClasificacionResponse r = toResponse(e);

        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(r);

        List<ReglaClasificacionResponse> result = svc.list();

        assertThat(result).containsExactly(r);
        verify(handler).list();
        verify(mapper).toResponse(e);
        verifyNoMoreInteractions(handler, mapper);
    }

    @Test
    void getDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        ReglaClasificacionResponse r = toResponse(e);

        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(r);

        ReglaClasificacionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(r);
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
        verifyNoMoreInteractions(handler, mapper);
    }

    @Test
    void createDelegatesToHandler() {
        ReglaClasificacion e = buildRegla();
        ReglaClasificacionRequest request = toRequest(e);
        ReglaClasificacionResponse r = toResponse(e);

        // Validación de negocio
        doNothing().when(validator).validateCreate(request);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(r);

        ReglaClasificacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(r);
        verify(validator).validateCreate(request);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
        verifyNoMoreInteractions(handler, mapper, validator);
    }

    @Test
    void updateDelegatesToHandler() {
        ReglaClasificacion existing = buildRegla();

        ReglaClasificacionRequest request = new ReglaClasificacionRequest(
                "Regla actualizada",
                "y < 5",
                2,
                false,
                existing.getClasificacion().getId(),
                "{\"k\":\"v\"}",
                "Manual",
                Instant.now(),
                Instant.now().plusSeconds(7200)
        );

        ReglaClasificacion updated = ReglaClasificacion.builder()
                .id(existing.getId())
                .nombre(request.getNombre())
                .expresion(request.getExpresion())
                .prioridad(request.getPrioridad())
                .activa(request.getActiva())
                .clasificacion(existing.getClasificacion())
                .condiciones(request.getCondiciones())
                .fuente(request.getFuente())
                .vigenciaDesde(request.getVigenciaDesde())
                .vigenciaHasta(request.getVigenciaHasta())
                .version(existing.getVersion())
                .build();

        ReglaClasificacionResponse r = toResponse(updated);

        // Flujo típico: get -> validator -> mapper.updateEntity -> handler.update -> mapper.toResponse
        when(handler.get(existing.getId())).thenReturn(existing);
        doNothing().when(validator).validateUpdate(existing.getId(), request);
        doNothing().when(mapper).updateEntity(existing, request);
        when(handler.update(existing.getId(), existing)).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(r);

        ReglaClasificacionResponse result = svc.update(existing.getId(), request);

        assertThat(result.getNombre()).isEqualTo("Regla actualizada");
        assertThat(result.getExpresion()).isEqualTo("y < 5");
        assertThat(result.getPrioridad()).isEqualTo(2);
        assertThat(result.getActiva()).isFalse();
        assertThat(result.getFuente()).isEqualTo("Manual");

        verify(handler).get(existing.getId());
        verify(validator).validateUpdate(existing.getId(), request);
        verify(mapper).updateEntity(existing, request);
        verify(handler).update(existing.getId(), existing);
        verify(mapper).toResponse(updated);
        verifyNoMoreInteractions(handler, mapper, validator);
    }

    @Test
    void deleteDelegatesToHandler() {
        ReglaClasificacion regla = buildRegla();

        // Stub para que handler devuelva la regla al hacer get(id)
        when(handler.get(regla.getId())).thenReturn(regla);

        // Validación de borrado
        doNothing().when(validator).validateForDelete(regla);

        svc.delete(regla.getId());

        verify(handler).get(regla.getId());
        verify(validator).validateForDelete(regla);
        verify(handler).delete(regla.getId());
        verifyNoMoreInteractions(handler, mapper, validator);
    }

}
