package com.ciudadania360.subsistemavideoconferencia.application.mapper;

import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionRequest;
import com.ciudadania360.subsistemavideoconferencia.application.dto.planificacion.PlanificacionResponse;
import com.ciudadania360.subsistemavideoconferencia.domain.entity.Planificacion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlanificacionMapper {

    // Request -> Entity
    Planificacion toEntity(PlanificacionRequest request);

    // Entity -> Response
    PlanificacionResponse toResponse(Planificacion entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Planificacion existing, PlanificacionRequest request);
}
