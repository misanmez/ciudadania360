package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitud.SolicitudResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Clasificacion;
import com.ciudadania360.subsistemaciudadano.domain.entity.Ubicacion;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolicitudMapper {

    // Request -> Entity
    @Mapping(target = "ciudadano",     source = "ciudadanoId")
    @Mapping(target = "clasificacion", source = "clasificacionId")
    @Mapping(target = "ubicacion",     source = "ubicacionId")
    Solicitud toEntity(SolicitudRequest request);

    // Entity -> Response
    @Mapping(target = "ciudadanoId",     source = "ciudadano.id")
    @Mapping(target = "clasificacionId", source = "clasificacion.id")
    @Mapping(target = "ubicacionId",     source = "ubicacion.id")
    SolicitudResponse toResponse(Solicitud entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "ciudadano",     source = "ciudadanoId")
    @Mapping(target = "clasificacion", source = "clasificacionId")
    @Mapping(target = "ubicacion",     source = "ubicacionId")
    void updateEntity(@MappingTarget Solicitud existing, SolicitudRequest request);

    /* ===== Helpers UUID -> Entidad ===== */

    default Ciudadano toCiudadano(UUID id) {
        if (id == null) return null;
        Ciudadano c = new Ciudadano();
        c.setId(id);
        return c;
    }

    default Clasificacion toClasificacion(UUID id) {
        if (id == null) return null;
        Clasificacion cl = new Clasificacion();
        cl.setId(id);
        return cl;
    }

    default Ubicacion toUbicacion(UUID id) {
        if (id == null) return null;
        Ubicacion u = new Ubicacion();
        u.setId(id);
        return u;
    }

}
