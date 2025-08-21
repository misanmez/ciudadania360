package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.Ciudadano;
import com.ciudadania360.subsistemaciudadano.domain.entity.Interaccion;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.interaccion.InteraccionResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Solicitud;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InteraccionMapper {

    // Request -> Entity
    @Mapping(target = "solicitud", source = "solicitudId")
    @Mapping(target = "ciudadano", source = "ciudadanoId")
    Interaccion toEntity(InteraccionRequest request);

    // Entity -> Response
    @Mapping(target = "solicitudId", source = "solicitud.id")
    @Mapping(target = "ciudadanoId", source = "ciudadano.id")
    InteraccionResponse toResponse(Interaccion entity);

    // Update parcial (ignora nulls)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "solicitud", source = "solicitudId")
    @Mapping(target = "ciudadano", source = "ciudadanoId")
    void updateEntity(@MappingTarget Interaccion existing, InteraccionRequest request);

    /* ==== Helpers para mapear UUID <-> Entidades ==== */
    default Solicitud map(UUID id) {
        if (id == null) return null;
        Solicitud s = new Solicitud();
        s.setId(id);
        return s;
    }

    default Ciudadano mapCiudadano(UUID id) {
        if (id == null) return null;
        Ciudadano c = new Ciudadano();
        c.setId(id);
        return c;
    }

}

