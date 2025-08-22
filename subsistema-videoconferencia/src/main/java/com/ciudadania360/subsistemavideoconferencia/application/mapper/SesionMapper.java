package com.ciudadania360.subsistemavideoconferencia.application.mapper;

import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.sesion.SesionResponse;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Sesion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SesionMapper {

    // Request -> Entity
    Sesion toEntity(SesionRequest request);

    // Entity -> Response
    SesionResponse toResponse(Sesion entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Sesion existing, SesionRequest request);
}