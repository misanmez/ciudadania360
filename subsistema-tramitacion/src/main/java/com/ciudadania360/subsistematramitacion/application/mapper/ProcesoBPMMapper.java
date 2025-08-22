// ProcesoBPMMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.ProcesoBPM;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.procesobpm.ProcesoBPMResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProcesoBPMMapper {

    ProcesoBPM toEntity(ProcesoBPMRequest request);

    ProcesoBPMResponse toResponse(ProcesoBPM entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ProcesoBPM existing, ProcesoBPMRequest request);
}
