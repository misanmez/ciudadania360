package com.ciudadania360.subsistemacomunicaciones.application.mapper;

import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.encuesta.EncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.Encuesta;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EncuestaMapper {

    // Request -> Entity
    Encuesta toEntity(EncuestaRequest request);

    // Entity -> Response
    EncuestaResponse toResponse(Encuesta entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Encuesta existing, EncuestaRequest request);
}

