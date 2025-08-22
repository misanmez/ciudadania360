// IncidenciaContrataMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.IncidenciaContrata;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.incidenciacontrata.IncidenciaContrataResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IncidenciaContrataMapper {

    // Request -> Entity
    IncidenciaContrata toEntity(IncidenciaContrataRequest request);

    // Entity -> Response
    IncidenciaContrataResponse toResponse(IncidenciaContrata entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget IncidenciaContrata existing, IncidenciaContrataRequest request);
}
