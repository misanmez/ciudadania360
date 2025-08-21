package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.application.dto.ConsentimientoRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.ConsentimientoResponse;
import com.ciudadania360.subsistemaciudadano.domain.entity.Consentimiento;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsentimientoMapper {

    Consentimiento toEntity(ConsentimientoRequest request);

    ConsentimientoResponse toResponse(Consentimiento entity);
}
