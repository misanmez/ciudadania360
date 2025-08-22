package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Flujo;
import com.ciudadania360.subsistematramitacion.domain.handler.FlujoHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.FlujoMapper;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.flujo.FlujoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class FlujoServiceTest {

    @Mock private FlujoHandler handler;
    @Mock private FlujoMapper mapper;
    @InjectMocks private FlujoService svc;

    private Flujo buildEntity() {
        return Flujo.builder()
                .id(UUID.randomUUID())
                .nombre("Flujo 1")
                .descripcion("Descripción del flujo")
                .activo(true)
                .tipo("TIPO1")
                .slaHoras(48)
                .pasosDefinition("{\"pasos\":[\"A\",\"B\"]}")
                .version(1L)
                .build();
    }

    private FlujoRequest toRequest(Flujo e) {
        return new FlujoRequest(
                e.getNombre(), e.getDescripcion(), e.getActivo(),
                e.getTipo(), e.getSlaHoras(), e.getPasosDefinition()
        );
    }

    private FlujoResponse toResponse(Flujo e) {
        return new FlujoResponse(
                e.getId(), e.getNombre(), e.getDescripcion(),
                e.getActivo(), e.getTipo(), e.getSlaHoras(),
                e.getPasosDefinition()
        );
    }

    @Test void listDelegatesToHandler() {
        Flujo e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<FlujoResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Flujo e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        FlujoResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Flujo e = buildEntity();
        FlujoRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Flujo.class))).thenReturn(e);
        when(mapper.toResponse(any(Flujo.class))).thenReturn(toResponse(e));

        FlujoResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Flujo.class));
        verify(mapper).toResponse(any(Flujo.class));
    }


    @Test void updateDelegatesToHandler() {
        Flujo e = buildEntity();
        FlujoRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        FlujoResponse result = svc.update(e.getId(), request);

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
