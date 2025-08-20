package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.domain.handler.PermisoHandler;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermisoServicioTest {

    @Mock
    private PermisoHandler handler;

    @InjectMocks
    private PermisoServicio svc;

    @Test
    void listDelegatesToHandler() {
        Permiso e = Permiso.builder().id(UUID.randomUUID()).build();
        when(handler.list()).thenReturn(List.of(e));

        List<Permiso> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Permiso e = Permiso.builder().id(UUID.randomUUID()).build();
        when(handler.get(e.getId())).thenReturn(e);

        Permiso result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Permiso e = Permiso.builder().id(UUID.randomUUID()).build();
        when(handler.create(any())).thenReturn(e);

        Permiso result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Permiso e = Permiso.builder().id(UUID.randomUUID()).build();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        Permiso result = svc.update(e.getId(), e);

        assertThat(result).isEqualTo(e);
        verify(handler).update(e.getId(), e);
    }

    @Test
    void deleteDelegatesToHandler() {
        UUID id = UUID.randomUUID();

        svc.delete(id);

        verify(handler).delete(id);
    }
}

