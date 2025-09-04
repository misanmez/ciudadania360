package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.SolicitudAgrupada;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.solicitudagrupada.SolicitudAgrupadaResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SolicitudAgrupadaMapper {

    SolicitudAgrupadaResponse toResponse(SolicitudAgrupada entity);

    SolicitudAgrupada toEntity(SolicitudAgrupadaRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget SolicitudAgrupada entity, SolicitudAgrupadaRequest request);
}
