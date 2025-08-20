package com.ciudadania360.gestionrolespermisos.domain.handler;

import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.domain.repository.RolRepositorio;
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
class RolHandlerTest {

    @Mock
    private RolRepositorio repo;

    @InjectMocks
    private RolHandler handler;

    @Test
    void listReturnsAllRoles() {
        Rol e = Rol.builder().id(UUID.randomUUID()).build();
        when(repo.findAll()).thenReturn(List.of(e));

        List<Rol> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsRolById() {
        UUID id = UUID.randomUUID();
        Rol e = Rol.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Rol result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesRol() {
        Rol e = Rol.builder().id(UUID.randomUUID()).build();
        when(repo.save(any())).thenReturn(e);

        Rol result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingRol() {
        UUID id = UUID.randomUUID();
        Rol e = Rol.builder().id(id).build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Rol result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e); // solo verificamos save
    }


    @Test
    void deleteRemovesRolById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}

