package com.ciudadania360.subsistemaciudadano.application.mapper;

import com.ciudadania360.subsistemaciudadano.domain.entity.ReglaClasificacion;
import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionRequest;
import com.ciudadania360.subsistemaciudadano.application.dto.reglaclasificacion.ReglaClasificacionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReglaClasificacionMapper {

    ReglaClasificacion toEntity(ReglaClasificacionRequest request);

    ReglaClasificacionResponse toResponse(ReglaClasificacion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ReglaClasificacion existing, ReglaClasificacionRequest request);
}
