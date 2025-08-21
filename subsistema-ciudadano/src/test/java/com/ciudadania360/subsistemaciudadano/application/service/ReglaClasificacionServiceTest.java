package com.ciudadania360.subsistemaciudadano.application.service;

import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionResponse;
import com.ciudadania360.subsistemaciudadano.application.mapper.ReglaClasificacionMapper;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReglaClasificacionServiceTest {

    @Mock
    private ReglaClasificacionHandler handler;

    @Mock
    private ReglaClasificacionMapper mapper; // 🔑 Mock del mapper

    @InjectMocks
    private ReglaClasificacionService svc;

    private ReglaClasificacion buildRegla() {
        return ReglaClasificacion.builder()
                .id(UUID.randomUUID())
                .nombre("Regla de prueba")
                .expresion("x > 10")
                .prioridad(1)
                .activa(true)
                .clasificacionId(UUID.randomUUID())
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
                e.getClasificacionId(),
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
                e.getClasificacionId(),
                e.getCondiciones(),
                e.getFuente(),
                e.getVigenciaDesde(),
                e.getVigenciaHasta()
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

        when(mapper.toEntity(request)).thenReturn(e); // 🔑 mapping request -> entity
        when(handler.create(e)).thenReturn(e);        // 🔑 persiste
        when(mapper.toResponse(e)).thenReturn(r);     // 🔑 mapping entity -> response

        ReglaClasificacionResponse result = svc.create(request);

        assertThat(result).isEqualTo(r);
        verify(mapper).toEntity(request);
        verify(handler).create(e);
        verify(mapper).toResponse(e);
        verifyNoMoreInteractions(handler, mapper);
    }

    @Test
    void updateDelegatesToHandler() {
        ReglaClasificacion existing = buildRegla();

        // Creamos un request con cambios para poder asertar el resultado
        ReglaClasificacionRequest request = new ReglaClasificacionRequest(
                "Regla actualizada",                 // 🔁 nombre
                "y < 5",                             // 🔁 expresión
                2,                                   // 🔁 prioridad
                false,                               // 🔁 activa
                existing.getClasificacionId(),       // mismo clasificacionId
                "{\"k\":\"v\"}",                     // 🔁 condiciones
                "Manual",                            // 🔁 fuente
                Instant.now(),                       // 🔁 vigenciaDesde
                Instant.now().plusSeconds(7200)      // 🔁 vigenciaHasta
        );

        // Lo que devuelve el handler tras actualizar
        ReglaClasificacion updated = ReglaClasificacion.builder()
                .id(existing.getId())
                .nombre("Regla actualizada")
                .expresion("y < 5")
                .prioridad(2)
                .activa(false)
                .clasificacionId(existing.getClasificacionId())
                .condiciones("{\"k\":\"v\"}")
                .fuente("Manual")
                .vigenciaDesde(request.getVigenciaDesde())
                .vigenciaHasta(request.getVigenciaHasta())
                .version(existing.getVersion())
                .build();

        ReglaClasificacionResponse r = toResponse(updated);

        // Flujo típico: get -> mapper.updateEntity -> handler.update -> mapper.toResponse
        when(handler.get(existing.getId())).thenReturn(existing);
        doNothing().when(mapper).updateEntity(same(existing), eq(request));
        when(handler.update(eq(existing.getId()), same(existing))).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(r);

        ReglaClasificacionResponse result = svc.update(existing.getId(), request);

        assertThat(result.getNombre()).isEqualTo("Regla actualizada");
        assertThat(result.getExpresion()).isEqualTo("y < 5");
        assertThat(result.getPrioridad()).isEqualTo(2);
        assertThat(result.getActiva()).isFalse();
        assertThat(result.getFuente()).isEqualTo("Manual");

        verify(handler).get(existing.getId());
        verify(mapper).updateEntity(same(existing), eq(request));
        verify(handler).update(eq(existing.getId()), same(existing));
        verify(mapper).toResponse(updated);
        verifyNoMoreInteractions(handler, mapper);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
        verifyNoInteractions(mapper); // el mapper no participa en delete
    }
}
