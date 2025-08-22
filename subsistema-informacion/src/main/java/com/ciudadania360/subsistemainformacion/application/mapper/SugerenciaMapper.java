package com.ciudadania360.subsistemainformacion.application.mapper;

import com.ciudadania360.subsistemainformacion.domain.entity.Sugerencia;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaRequest;
import com.ciudadania360.subsistemainformacion.application.dto.sugerencia.SugerenciaResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SugerenciaMapper {

    Sugerencia toEntity(SugerenciaRequest request);

    SugerenciaResponse toResponse(Sugerencia entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Sugerencia existing, SugerenciaRequest request);
}
