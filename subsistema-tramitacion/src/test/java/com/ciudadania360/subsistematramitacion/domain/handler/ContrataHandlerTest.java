package com.ciudadania360.subsistematramitacion.domain.handler;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.domain.repository.ContrataRepository;
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
class ContrataHandlerTest {

    @Mock
    private ContrataRepository repo;

    @InjectMocks
    private ContrataHandler handler;

    @Test
    void listReturnsAllContratas() {
        Contrata e = Contrata.builder()
                .id(UUID.randomUUID())
                .nombre("Nombre de Contrata")
                .cif("123456789")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Contrata> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsContrataById() {
        UUID id = UUID.randomUUID();
        Contrata e = Contrata.builder().id(id).nombre("Nombre de Contrata").cif("123456789").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Contrata result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesContrata() {
        Contrata e = Contrata.builder()
                .id(UUID.randomUUID())
                .nombre("Nueva Contrata")
                .cif("987654321")
                .build();
        when(repo.save(any())).thenReturn(e);

        Contrata result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingContrata() {
        UUID id = UUID.randomUUID();
        Contrata e = Contrata.builder().id(id).nombre("Contrata Original").cif("123456789").build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Contrata result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesContrataById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
