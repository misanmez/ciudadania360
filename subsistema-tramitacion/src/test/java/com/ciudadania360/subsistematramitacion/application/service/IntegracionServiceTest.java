package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.domain.handler.IntegracionHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.IntegracionMapper;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionRequest;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class IntegracionServiceTest {

    @Mock private IntegracionHandler handler;
    @Mock private IntegracionMapper mapper;
    @InjectMocks private IntegracionService svc;

    private Integracion buildEntity() {
        return Integracion.builder()
                .id(UUID.randomUUID())
                .sistema("Sistema X")
                .tipo("REST")
                .endpoint("/api/test")
                .build();
    }

    private IntegracionRequest toRequest(Integracion e) {
        return new IntegracionRequest(e.getSistema(), e.getTipo(), e.getEndpoint());
    }

    private IntegracionResponse toResponse(Integracion e) {
        return new IntegracionResponse(e.getId(), e.getSistema(), e.getTipo(), e.getEndpoint());
    }

    @Test void listDelegatesToHandler() {
        Integracion e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<IntegracionResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Integracion e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        IntegracionResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Integracion e = buildEntity();
        IntegracionRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Integracion.class))).thenReturn(e);
        when(mapper.toResponse(any(Integracion.class))).thenReturn(toResponse(e));

        IntegracionResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison() // 🔹 importante
                .ignoringFields("id")       // 🔹 ignoramos el UUID generado dinámicamente
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Integracion.class));
        verify(mapper).toResponse(any(Integracion.class));
    }

    @Test void updateDelegatesToHandler() {
        Integracion e = buildEntity();
        IntegracionRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        IntegracionResponse result = svc.update(e.getId(), request);

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
