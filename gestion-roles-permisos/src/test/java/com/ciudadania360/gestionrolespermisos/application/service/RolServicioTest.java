package com.ciudadania360.gestionrolespermisos.application.service;

import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.domain.handler.RolHandler;
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
class RolServicioTest {

    @Mock
    private RolHandler handler;

    @InjectMocks
    private RolServicio svc;

    @Test
    void listDelegatesToHandler() {
        Rol e = Rol.builder().id(UUID.randomUUID()).build();
        when(handler.list()).thenReturn(List.of(e));

        List<Rol> result = svc.list();

        assertThat(result).containsExactly(e);
        verify(handler).list();
    }

    @Test
    void getDelegatesToHandler() {
        Rol e = Rol.builder().id(UUID.randomUUID()).build();
        when(handler.get(e.getId())).thenReturn(e);

        Rol result = svc.get(e.getId());

        assertThat(result).isEqualTo(e);
        verify(handler).get(e.getId());
    }

    @Test
    void createDelegatesToHandler() {
        Rol e = Rol.builder().id(UUID.randomUUID()).build();
        when(handler.create(any())).thenReturn(e);

        Rol result = svc.create(e);

        assertThat(result).isEqualTo(e);
        verify(handler).create(e);
    }

    @Test
    void updateDelegatesToHandler() {
        Rol e = Rol.builder().id(UUID.randomUUID()).build();
        when(handler.update(eq(e.getId()), any())).thenReturn(e);

        Rol result = svc.update(e.getId(), e);

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

