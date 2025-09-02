package com.ciudadania360.gestionrolespermisos.domain.handler;

import com.ciudadania360.gestionrolespermisos.domain.entity.UsuarioRol;
import com.ciudadania360.gestionrolespermisos.domain.repository.UsuarioRolRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UsuarioRolHandlerTest {

    @Mock
    private UsuarioRolRepository repo;

    @InjectMocks
    private UsuarioRolHandler handler;

    @Test
    void listReturnsAllUsuarioRoles() {
        UsuarioRol e = UsuarioRol.builder().id(UUID.randomUUID()).build();
        when(repo.findAll()).thenReturn(List.of(e));

        List<UsuarioRol> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsUsuarioRolById() {
        UUID id = UUID.randomUUID();
        UsuarioRol e = UsuarioRol.builder().id(id).build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        UsuarioRol result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesUsuarioRol() {
        UsuarioRol e = UsuarioRol.builder().id(UUID.randomUUID()).build();
        when(repo.save(any())).thenReturn(e);

        UsuarioRol result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingRol() {
        UUID id = UUID.randomUUID();
        UsuarioRol e = UsuarioRol.builder().id(id).build();

        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        UsuarioRol result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e); // solo verificamos save
    }

    @Test
    void deleteRemovesUsuarioRolById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}

