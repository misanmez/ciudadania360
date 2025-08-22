package com.ciudadania360.subsistemainformacion.application.service;

import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.duda.DudaResponse;
import com.ciudadania360.subsistemainformacion.application.mapper.DudaMapper;
import com.ciudadania360.subsistemainformacion.domain.entity.Duda;
import com.ciudadania360.subsistemainformacion.domain.handler.DudaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class DudaServiceTest {

    @Mock
    private DudaHandler handler;

    @Mock
    private DudaMapper mapper;

    @InjectMocks
    private DudaService svc;

    private Duda buildEntity() {
        return Duda.builder()
                .id(UUID.randomUUID())
                .pregunta("¿Pregunta?")
                .respuesta("Respuesta")
                .build();
    }

    private DudaRequest toRequest(Duda e) {
        return new DudaRequest(e.getPregunta(), e.getRespuesta());
    }

    private DudaResponse toResponse(Duda e) {
        return new DudaResponse(e.getId(), e.getPregunta(), e.getRespuesta());
    }

    @Test
    void listDelegatesToHandler() {
        Duda e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<DudaResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test
    void getDelegatesToHandler() {
        Duda e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DudaResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Duda e = Duda.builder()
                .id(UUID.randomUUID())
                .pregunta("Pregunta 1")
                .respuesta("Respuesta 1")
                .build();

        DudaRequest request = new DudaRequest(e.getPregunta(), e.getRespuesta());

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Duda.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Duda.class))).thenAnswer(invocation -> {
            Duda d = invocation.getArgument(0);
            return new DudaResponse(d.getId(), d.getPregunta(), d.getRespuesta());
        });

        DudaResponse result = svc.create(request);

        assertThat(result.getPregunta()).isEqualTo(e.getPregunta());
        assertThat(result.getRespuesta()).isEqualTo(e.getRespuesta());
        assertThat(result.getId()).isNotNull();

        verify(mapper).toEntity(request);
        verify(handler).create(any(Duda.class));
        verify(mapper).toResponse(any(Duda.class));
    }


    @Test
    void updateDelegatesToHandler() {
        Duda e = buildEntity();
        DudaRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(e.getId(), e)).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        DudaResponse result = svc.update(e.getId(), request);
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
