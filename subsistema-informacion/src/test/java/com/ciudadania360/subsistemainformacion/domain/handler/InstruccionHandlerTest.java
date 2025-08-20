package com.ciudadania360.subsistemainformacion.domain.handler;

import com.ciudadania360.subsistemainformacion.domain.entity.Instruccion;
import com.ciudadania360.subsistemainformacion.domain.repository.InstruccionRepositorio;
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
class InstruccionHandlerTest {

    @Mock
    private InstruccionRepositorio repo;

    @InjectMocks
    private InstruccionHandler handler;

    @Test
    void listReturnsAllInstrucciones() {
        Instruccion e = Instruccion.builder()
                .id(UUID.randomUUID())
                .titulo("Ejemplo de Instrucción")
                .pasos("Descripción de la instrucción")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Instruccion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsInstruccionById() {
        UUID id = UUID.randomUUID();
        Instruccion e = Instruccion.builder()
                .id(id)
                .titulo("Ejemplo")
                .pasos("Pasos ejemplo")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));

        Instruccion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesInstruccion() {
        Instruccion e = Instruccion.builder()
                .id(UUID.randomUUID())
                .titulo("Nueva instrucción")
                .pasos("Pasos nuevos")
                .build();
        when(repo.save(any())).thenReturn(e);

        Instruccion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingInstruccion() {
        UUID id = UUID.randomUUID();
        Instruccion e = Instruccion.builder()
                .id(id)
                .titulo("Original")
                .pasos("Pasos originales")
                .build();
        when(repo.findById(id)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(e);

        Instruccion result = handler.update(id, e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void deleteRemovesInstruccionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}
