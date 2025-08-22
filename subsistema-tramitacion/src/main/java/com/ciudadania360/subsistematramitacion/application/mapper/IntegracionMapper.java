// IntegracionMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.Integracion;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionRequest;
import com.ciudadania360.subsistematramitacion.application.dto.integracion.IntegracionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IntegracionMapper {

    Integracion toEntity(IntegracionRequest request);

    IntegracionResponse toResponse(Integracion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Integracion existing, IntegracionRequest request);
}
