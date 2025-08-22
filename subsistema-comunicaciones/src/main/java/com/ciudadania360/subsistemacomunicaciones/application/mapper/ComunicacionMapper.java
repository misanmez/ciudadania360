package com.ciudadania360.subsistemacomunicaciones.application.mapper;

import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.comunicacion.ComunicacionResponse;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Comunicacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComunicacionMapper {

    // Request -> Entity
    Comunicacion toEntity(ComunicacionRequest request);

    // Entity -> Response
    ComunicacionResponse toResponse(Comunicacion entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Comunicacion existing, ComunicacionRequest request);
}
