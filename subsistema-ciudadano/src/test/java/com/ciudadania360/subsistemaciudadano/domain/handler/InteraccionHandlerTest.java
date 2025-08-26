package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.repository.InteraccionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteraccionHandlerTest {

    @Mock
    private InteraccionRepository repo;

    @InjectMocks
    private InteraccionHandler handler;

    @Test
    void listReturnsAllInteracciones() {
        Ciudadano ciudadano = new Ciudadano();
        Solicitud solicitud = new Solicitud();

        Interaccion e = Interaccion.builder()
                .id(UUID.randomUUID())
                .ciudadano(ciudadano)
                .solicitud(solicitud)
                .canal("Email")
                .fecha(Instant.now())
                .agente("Agente X")
                .mensaje("Mensaje de prueba")
                .adjuntoUri("uri/adjunto")
                .visibilidad("Publica")
                .build();

        when(repo.findAll()).thenReturn(List.of(e));

        List<Interaccion> result = handler.list();

        assertThat(result).containsExactly(e);
        verify(repo).findAll();
    }

    @Test
    void getReturnsInteraccionById() {
        UUID id = UUID.randomUUID();
        Interaccion e = Interaccion.builder()
                .id(id)
                .canal("Email")
                .fecha(Instant.now())
                .agente("Agente X")
                .mensaje("Mensaje de prueba")
                .adjuntoUri("uri/adjunto")
                .visibilidad("Publica")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(e));

        Interaccion result = handler.get(id);

        assertThat(result).isEqualTo(e);
        verify(repo).findById(id);
    }

    @Test
    void createSavesInteraccion() {
        Interaccion e = Interaccion.builder()
                .id(UUID.randomUUID())
                .canal("Email")
                .fecha(Instant.now())
                .agente("Agente X")
                .mensaje("Mensaje de prueba")
                .adjuntoUri("uri/adjunto")
                .visibilidad("Publica")
                .build();

        when(repo.save(any())).thenReturn(e);

        Interaccion result = handler.create(e);

        assertThat(result).isEqualTo(e);
        verify(repo).save(e);
    }

    @Test
    void updateSavesExistingInteraccion() {
        UUID id = UUID.randomUUID();

        // Interacción existente en la "BD"
        Interaccion existente = Interaccion.builder()
                .id(id)
                .canal("Email")
                .fecha(Instant.parse("2024-01-01T10:00:00Z"))
                .agente("Agente X")
                .mensaje("Mensaje original")
                .adjuntoUri("uri/antiguo")
                .visibilidad("Privada")
                .build();

        // Cambios que vienen en la request
        Interaccion cambios = Interaccion.builder()
                .id(id)
                .canal("Chat")
                .fecha(Instant.parse("2024-01-02T12:00:00Z"))
                .agente("Agente Y")
                .mensaje("Mensaje actualizado")
                .adjuntoUri("uri/nuevo")
                .visibilidad("Pública")
                .build();

        when(repo.findById(id)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Interaccion result = handler.update(id, cambios);

        // Verificar que los cambios se aplicaron en la entidad existente
        assertThat(result.getCanal()).isEqualTo("Chat");
        assertThat(result.getFecha()).isEqualTo(Instant.parse("2024-01-02T12:00:00Z"));
        assertThat(result.getAgente()).isEqualTo("Agente Y");
        assertThat(result.getMensaje()).isEqualTo("Mensaje actualizado");
        assertThat(result.getAdjuntoUri()).isEqualTo("uri/nuevo");
        assertThat(result.getVisibilidad()).isEqualTo("Pública");

        // Verificar que se guarda la entidad existente (modificada)
        verify(repo).save(existente);
    }


    @Test
    void deleteRemovesInteraccionById() {
        UUID id = UUID.randomUUID();

        handler.delete(id);

        verify(repo).deleteById(id);
    }
}