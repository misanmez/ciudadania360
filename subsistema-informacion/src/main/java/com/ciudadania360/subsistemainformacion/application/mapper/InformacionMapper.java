package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Informacion;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionRequest;
import com.ciudadania360.subsistemainformacion.application.dto.informacion.InformacionResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InformacionMapper {

    Informacion toEntity(InformacionRequest request);

    InformacionResponse toResponse(Informacion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Informacion existing, InformacionRequest request);
}
