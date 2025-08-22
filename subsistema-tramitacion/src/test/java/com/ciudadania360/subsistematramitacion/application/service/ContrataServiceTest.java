package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.domain.handler.ContrataHandler;
import com.ciudadania360.subsistematramitacion.application.mapper.ContrataMapper;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ContrataServiceTest {

    @Mock private ContrataHandler handler;
    @Mock private ContrataMapper mapper;
    @InjectMocks private ContrataService svc;

    private Contrata buildEntity() {
        return Contrata.builder()
                .id(UUID.randomUUID())
                .nombre("Contrata 1")
                .cif("B12345678")
                .build();
    }

    private ContrataRequest toRequest(Contrata e) {
        return new ContrataRequest(e.getNombre(), e.getCif());
    }

    private ContrataResponse toResponse(Contrata e) {
        return new ContrataResponse(e.getId(), e.getNombre(), e.getCif());
    }

    @Test void listDelegatesToHandler() {
        Contrata e = buildEntity();
        when(handler.list()).thenReturn(List.of(e));
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        List<ContrataResponse> result = svc.list();

        assertThat(result).containsExactly(toResponse(e));
        verify(handler).list();
        verify(mapper).toResponse(e);
    }

    @Test void getDelegatesToHandler() {
        Contrata e = buildEntity();
        when(handler.get(e.getId())).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ContrataResponse result = svc.get(e.getId());

        assertThat(result).isEqualTo(toResponse(e));
        verify(handler).get(e.getId());
        verify(mapper).toResponse(e);
    }

    @Test
    void createDelegatesToHandler() {
        Contrata e = buildEntity();
        ContrataRequest request = toRequest(e);

        when(mapper.toEntity(request)).thenReturn(e);
        when(handler.create(any(Contrata.class))).thenReturn(e);
        when(mapper.toResponse(any(Contrata.class))).thenReturn(toResponse(e));

        ContrataResponse result = svc.create(request);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(toResponse(e));

        verify(mapper).toEntity(request);
        verify(handler).create(any(Contrata.class));
        verify(mapper).toResponse(any(Contrata.class));
    }


    @Test void updateDelegatesToHandler() {
        Contrata e = buildEntity();
        ContrataRequest request = toRequest(e);

        when(handler.get(e.getId())).thenReturn(e);
        doNothing().when(mapper).updateEntity(e, request);
        when(handler.update(eq(e.getId()), same(e))).thenReturn(e);
        when(mapper.toResponse(e)).thenReturn(toResponse(e));

        ContrataResponse result = svc.update(e.getId(), request);

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
