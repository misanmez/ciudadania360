package com.ciudadania360.gestionrolespermisos.domain.handler;

import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.domain.repository.PermisoRepositorio;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermisoHandlerTest {

    @Mock
    private PermisoRepositorio repo;

    @InjectMocks
    private PermisoHandler handler;

    @Test
    void listReturnsAllPermisos() {
        Permiso e = Permiso.builder().id(UUID.randomUUID()).build();
        when(repo.findAll()).thenReturn(List.of(e));

        List<Permiso> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsPermisoById() {
        UUID id = UUID.randomUUID();
        Permiso e = Permiso.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Permiso result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesPermiso() {
        Permiso e = Permiso.builder().id(UUID.randomUUID()).build();
        when(repo.save(any())).thenReturn(e);

        Permiso result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingPermiso() {
        UUID id = UUID.randomUUID();
        Permiso e = Permiso.builder().id(id).build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Permiso result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesPermisoById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}

