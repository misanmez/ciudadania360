package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.domain.handler.IncidenciaContrataHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.IncidenciaContrataMapper;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class IncidenciaContrataServiceTest {

    @Mock private IncidenciaContrataHandler handler;
    @Mock private IncidenciaContrataMapper mapper;
    @InjectMocks private IncidenciaContrataService svc;

    private IncidenciaContrata buildEntity() {
        return IncidenciaContrata.builder()
                .id(UUID.randomUUID())
                .contrataId(UUID.randomUUID())
                .descripcion("Incidencia prueba")
                .estado("ABIERTA")
                .build();
    }

    private IncidenciaContrataRequest toRequest(IncidenciaContrata e) {
        return new IncidenciaContrataRequest(e.getContrataId(), e.getDescripcion(), e.getEstado());
    }

    private IncidenciaContrataResponse toResponse(IncidenciaContrata e) {
        return new IncidenciaContrataResponse(e.getId(), e.getContrataId(), e.getDescripcion(), e.getEstado());
    }

    @Test void listDelegatesToHandler() {
        IncidenciaContrata e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<IncidenciaContrataResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        IncidenciaContrata e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        IncidenciaContrataResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        IncidenciaContrata e = buildEntity();
        IncidenciaContrataRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(IncidenciaContrata.class))).thenReturn(e);
        when(mapper.toResponse(any(IncidenciaContrata.class))).thenReturn(toResponse(e));

        IncidenciaContrataResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison() // 🔹 importante
                .ignoringFields("id")       // 🔹 ignoramos el UUID generado dinámicamente
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(IncidenciaContrata.class));
        verify(mapper).toResponse(any(IncidenciaContrata.class));
    }


    @Test void updateDelegatesToHandler() {
        IncidenciaContrata e = buildEntity();
        IncidenciaContrataRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        IncidenciaContrataResponse result = svc.update(e.getId(), request);

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).updateEntity(e, request);
        verify(handler).update(e.getId(), e);
        verify(mapper).toResponse(e);
    }

    @Test void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();
        svc.delete(id);
        verify(handler).delete(id);
        verifyNoInteractions(mapper);
    }
}
