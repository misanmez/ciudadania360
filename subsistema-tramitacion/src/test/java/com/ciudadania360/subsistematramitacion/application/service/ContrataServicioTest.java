package com.ciudadania360.subsistematramitacion.application.service;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.domain.handler.ContrataHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContrataServicioTest {

    @Mock
    private ContrataHandler handler;

    @InjectMocks
    private ContrataServicio svc;

    private Contrata buildContrata() {
        return Contrata.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre de Contrata")
                .cif("123456789")
                .build();
    }

    @Test
    void listDelegatesToHandler() {
        Contrata c = buildContrata();
        when(handler.list()).thenReturn(List.of(c));

        List<Contrata> result = svc.list();

        assertThat(result).containsExactly(c);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Contrata c = buildContrata();
        when(handler.get(c.getId())).thenReturn(c);

        Contrata result = svc.get(c.getId());

        assertThat(result).isEqualTo(c);
        verify(handler).get(c.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Contrata c = buildContrata();
        when(handler.create(any())).thenReturn(c);

        Contrata result = svc.create(c);

        assertThat(result).isEqualTo(c);
        verify(handler).create(c);
    }

    @Test
    void updateDelegatesToHandler() {
        Contrata c = buildContrata();
        when(handler.update(eq(c.getId()), any())).thenReturn(c);

        Contrata result = svc.update(c.getId(), c);

        assertThat(result).isEqualTo(c);
        verify(handler).update(c.getId(), c);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}
