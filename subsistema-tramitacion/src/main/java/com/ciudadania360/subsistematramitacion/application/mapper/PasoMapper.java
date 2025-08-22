// PasoMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.Paso;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoRequest;
import com.ciudadania360.subsistematramitacion.application.dto.paso.PasoResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PasoMapper {

    Paso toEntity(PasoRequest request);

    PasoResponse toResponse(Paso entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Paso existing, PasoRequest request);
}
