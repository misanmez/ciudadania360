package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasificacionHandlerTest {

    @Mock
    private ClasificacionRepositorio repository; // Suponiendo que tu handler usa un repositorio

    @InjectMocks
    private ClasificacionHandler handler;

    private Clasificacion clasificacion;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        clasificacion = Clasificacion.builder()
                .id(id)
                .codigo("X01")
                .nombre("Test Clasificacion")
                .descripcion("Descripción de prueba")
                .tipo("TIPO")
                .build();
    }

    @Test
    void testObtenerTodos() {
        when(repository.findAll()).thenReturn(List.of(clasificacion));

        List<Clasificacion> resultado = handler.list();

        assertEquals(1, resultado.size());
        assertEquals(clasificacion.getId(), resultado.get(0).getId());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId() {
        when(repository.findById(id)).thenReturn(Optional.of(clasificacion));

        Clasificacion resultado = handler.get(id);

        assertEquals(clasificacion.getId(), resultado.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testCrear() {
        when(repository.save(any(Clasificacion.class))).thenReturn(clasificacion);

        Clasificacion resultado = handler.create(clasificacion);

        assertEquals(clasificacion.getId(), resultado.getId());
        verify(repository, times(1)).save(clasificacion);
    }



    @Test
    void testActualizar() {
        when(repository.findById(id)).thenReturn(Optional.of(clasificacion));
        when(repository.save(any(Clasificacion.class))).thenReturn(clasificacion);

        Clasificacion resultado = handler.update(id, clasificacion);

        assertEquals(clasificacion.getId(), resultado.getId());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(clasificacion);
    }



    @Test
    void testEliminar() {
        doNothing().when(repository).deleteById(id);

        handler.delete(id);

        // Solo verificamos que deleteById se haya llamado
        verify(repository, times(1)).deleteById(id);
    }
}
