package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.ProcesoBPMHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.ProcesoBPMMapper;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProcesoBPMServiceTest {

    @Mock private ProcesoBPMHandler handler;
    @Mock private ProcesoBPMMapper mapper;
    @InjectMocks private ProcesoBPMService svc;

    private ProcesoBPM buildEntity() {
        return ProcesoBPM.builder()
                .id(UUID.randomUUID())
                .engineInstanceId("engine-1")
                .definitionKey("def-key")
                .businessKey(UUID.randomUUID())
                .estado("En curso")
                .inicio(Instant.now())
                .fin(null)
                .variables("{}")
                .iniciador("usuario1")
                .version(1L)
                .build();
    }

    private ProcesoBPMRequest toRequest(ProcesoBPM e) {
        return new ProcesoBPMRequest(
                e.getEngineInstanceId(), e.getDefinitionKey(), e.getBusinessKey(),
                e.getEstado(), e.getInicio(), e.getFin(), e.getVariables(), e.getIniciador()
        );
    }

    private ProcesoBPMResponse toResponse(ProcesoBPM e) {
        return new ProcesoBPMResponse(
                e.getId(), e.getEngineInstanceId(), e.getDefinitionKey(), e.getBusinessKey(),
                e.getEstado(), e.getInicio(), e.getFin(), e.getVariables(), e.getIniciador()
        );
    }

    @Test void listDelegatesToHandler() {
        ProcesoBPM e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<ProcesoBPMResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        ProcesoBPM e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ProcesoBPMResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        ProcesoBPM e = buildEntity();
        ProcesoBPMRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(ProcesoBPM.class))).thenReturn(e);
        when(mapper.toResponse(any(ProcesoBPM.class))).thenReturn(toResponse(e));

        ProcesoBPMResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(ProcesoBPM.class));
        verify(mapper).toResponse(any(ProcesoBPM.class));
    }


    @Test void updateDelegatesToHandler() {
        ProcesoBPM e = buildEntity();
        ProcesoBPMRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ProcesoBPMResponse result = svc.update(e.getId(), request);
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
