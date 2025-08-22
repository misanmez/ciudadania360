package com.ciudadania360.subsistemacomunicaciones.application.mapper;

import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaRequest;
import com.ciudadania360.subsistemacomunicaciones.application.dto.respuestaencuesta.RespuestaEncuestaResponse;
import com.ciudadania360.subsistemacomunicaciones.domain.entity.RespuestaEncuesta;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RespuestaEncuestaMapper {

    // Request -> Entity
    RespuestaEncuesta toEntity(RespuestaEncuestaRequest request);

    // Entity -> Response
    RespuestaEncuestaResponse toResponse(RespuestaEncuesta entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget RespuestaEncuesta existing, RespuestaEncuestaRequest request);
}
