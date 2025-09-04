package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudEstadoHistorial;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudestadohistorial.SolicitudEstadoHistorialResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolicitudEstadoHistorialMapper {

    SolicitudEstadoHistorialResponse toResponse(SolicitudEstadoHistorial entity);

    SolicitudEstadoHistorial toEntity(SolicitudEstadoHistorialRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget SolicitudEstadoHistorial entity, SolicitudEstadoHistorialRequest request);
}
