package com.ciudadania360.subsistemavideoconferencia.application.mapper;

import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.citavideollamada.CitaVideollamadaResponse;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.CitaVideollamada;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CitaVideollamadaMapper {

    // Request -> Entity
    CitaVideollamada toEntity(CitaVideollamadaRequest request);

    // Entity -> Response
    CitaVideollamadaResponse toResponse(CitaVideollamada entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget CitaVideollamada existing, CitaVideollamadaRequest request);
}