package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.consentimiento.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsentimientoMapper {

    Consentimiento toEntity(ConsentimientoRequest request);

    ConsentimientoResponse toResponse(Consentimiento entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Consentimiento existing, ConsentimientoRequest request);
}
