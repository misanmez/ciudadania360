package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.domain.handler.PasoHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.PasoMapper;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class PasoServiceTest {

    @Mock private PasoHandler handler;
    @Mock private PasoMapper mapper;
    @InjectMocks private PasoService svc;

    private Paso buildEntity() {
        return Paso.builder()
                .id(UUID.randomUUID())
                .flujoId(UUID.randomUUID())
                .nombre("Paso 1")
                .orden(1)
                .tipo("Manual")
                .responsableRole("Admin")
                .slaHoras(24)
                .build();
    }

    private PasoRequest toRequest(Paso e) {
        return new PasoRequest(e.getFlujoId(), e.getNombre(), e.getOrden(), e.getTipo(), e.getResponsableRole(), e.getSlaHoras());
    }

    private PasoResponse toResponse(Paso e) {
        return new PasoResponse(e.getId(), e.getFlujoId(), e.getNombre(), e.getOrden(), e.getTipo(), e.getResponsableRole(), e.getSlaHoras());
    }

    @Test void listDelegatesToHandler() {
        Paso e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<PasoResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Paso e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        PasoResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Paso e = buildEntity();
        PasoRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Paso.class))).thenReturn(e);
        when(mapper.toResponse(any(Paso.class))).thenReturn(toResponse(e));

        PasoResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Paso.class));
        verify(mapper).toResponse(any(Paso.class));
    }


    @Test void updateDelegatesToHandler() {
        Paso e = buildEntity();
        PasoRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        PasoResponse result = svc.update(e.getId(), request);
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
