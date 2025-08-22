package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.domain.handler.TareaBPMHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.TareaBPMMapper;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TareaBPMServiceTest {

    @Mock private TareaBPMHandler handler;
    @Mock private TareaBPMMapper mapper;
    @InjectMocks private TareaBPMService svc;

    private TareaBPM buildEntity() {
        return TareaBPM.builder()
                .id(UUID.randomUUID())
                .procesoBpmId(UUID.randomUUID())
                .engineTaskId("task-1")
                .nombre("Tarea 1")
                .estado("Pendiente")
                .assignee("user1")
                .candidateGroup("grupo1")
                .dueDate(Instant.now().plusSeconds(3600))
                .priority(1)
                .variables("{}")
                .created(Instant.now())
                .completed(null)
                .version(1L)
                .build();
    }

    private TareaBPMRequest toRequest(TareaBPM e) {
        return new TareaBPMRequest(
                e.getProcesoBpmId(), e.getEngineTaskId(), e.getNombre(), e.getEstado(), e.getAssignee(),
                e.getCandidateGroup(), e.getDueDate(), e.getPriority(), e.getVariables(),
                e.getCreated(), e.getCompleted()
        );
    }

    private TareaBPMResponse toResponse(TareaBPM e) {
        return new TareaBPMResponse(
                e.getId(), e.getProcesoBpmId(), e.getEngineTaskId(), e.getNombre(), e.getEstado(), e.getAssignee(),
                e.getCandidateGroup(), e.getDueDate(), e.getPriority(), e.getVariables(), e.getCreated(), e.getCompleted()
        );
    }

    @Test void listDelegatesToHandler() {
        TareaBPM e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<TareaBPMResponse> result = svc.list();
        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        TareaBPM e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        TareaBPMResponse result = svc.get(e.getId());
        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        TareaBPM e = buildEntity();
        TareaBPMRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(TareaBPM.class))).thenReturn(e);
        when(mapper.toResponse(any(TareaBPM.class))).thenReturn(toResponse(e));

        TareaBPMResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(TareaBPM.class));
        verify(mapper).toResponse(any(TareaBPM.class));
    }


    @Test void updateDelegatesToHandler() {
        TareaBPM e = buildEntity();
        TareaBPMRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        TareaBPMResponse result = svc.update(e.getId(), request);
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
