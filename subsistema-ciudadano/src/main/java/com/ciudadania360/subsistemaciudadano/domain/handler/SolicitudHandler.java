package com.ciudadania360.subsistemaciudadano.domain.handler;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudSearchFilter;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.repository.ClasificacionRepository;
import com.ciudadania360.subsistemaciudadano.domain.repository.SolicitudRepository;
import com.ciudadania360.shared.exception.BadRequestException;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SolicitudHandler {

    private final SolicitudRepository repository;
    private final ClasificacionRepository clasificacionRepository;

    public SolicitudHandler(SolicitudRepository repository,
                            ClasificacionRepository clasificacionRepository) {
        this.repository = repository;
        this.clasificacionRepository = clasificacionRepository;
    }

    public List<Solicitud> list() {
        return repository.findAll();
    }

    public Solicitud get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Solicitud no encontrada con id: " + id));
    }

    public Solicitud create(Solicitud solicitud) {
        // Garantizar clasificación
        if (solicitud.getClasificacion() == null) {
            solicitud.setClasificacion(getDefaultClasificacion());
        }
        return repository.save(solicitud);
    }

    public Solicitud update(UUID id, Solicitud solicitud) {
        if (!repository.existsById(id)) {
            throw new BadRequestException("Solicitud no encontrada con id: " + id);
        }

        solicitud.setId(id);

        if (solicitud.getClasificacion() == null) {
            solicitud.setClasificacion(getDefaultClasificacion());
        }

        return repository.save(solicitud);
    }

    public boolean exists(UUID id) {
        return repository.existsById(id);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new BadRequestException("Solicitud no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    public List<Solicitud> search(SolicitudSearchFilter filter) {
        return repository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCiudadanoId() != null) {
                predicates.add(cb.equal(root.get("ciudadano").get("id"), filter.getCiudadanoId()));
            }
            if (filter.getEstado() != null) {
                predicates.add(cb.equal(root.get("estado"), filter.getEstado()));
            }
            if (filter.getPrioridad() != null) {
                predicates.add(cb.equal(root.get("prioridad"), filter.getPrioridad()));
            }
            if (filter.getFechaDesde() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fechaRegistro"), filter.getFechaDesde()));
            }
            if (filter.getFechaHasta() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fechaRegistro"), filter.getFechaHasta()));
            }
            if (filter.getAgenteAsignado() != null) {
                predicates.add(cb.equal(root.get("agenteAsignado"), filter.getAgenteAsignado()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    /**
     * Devuelve la Clasificación por defecto ("GENERICA").
     * Si no existe en BD, la crea automáticamente.
     */
    public Clasificacion getDefaultClasificacion() {
        return clasificacionRepository.findByNombreIgnoreCase("GENERICA")
                .orElseGet(() -> {
                    Clasificacion defaultClasificacion = new Clasificacion();
                    defaultClasificacion.setId(UUID.randomUUID());
                    defaultClasificacion.setNombre("GENERICA");
                    defaultClasificacion.setDescripcion("Clasificación por defecto");
                    defaultClasificacion.setVersion(0L);
                    return clasificacionRepository.save(defaultClasificacion);
                });
    }
}
