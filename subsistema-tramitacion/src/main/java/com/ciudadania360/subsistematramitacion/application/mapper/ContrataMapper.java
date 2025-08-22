// ContrataMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.Contrata;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataRequest;
import com.ciudadania360.subsistematramitacion.application.dto.contrata.ContrataResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContrataMapper {

    // Request -> Entity
    Contrata toEntity(ContrataRequest request);

    // Entity -> Response
    ContrataResponse toResponse(Contrata entity);

    // Update parcial (ignora nulls en el request)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Contrata existing, ContrataRequest request);
}
