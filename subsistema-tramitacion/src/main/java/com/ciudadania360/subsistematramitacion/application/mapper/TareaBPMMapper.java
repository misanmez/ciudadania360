// TareaBPMMapper.java
package com.ciudadania360.subsistematramitacion.application.mapper;

import com.ciudadania360.subsistematramitacion.domain.entity.TareaBPM;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMRequest;
import com.ciudadania360.subsistematramitacion.application.dto.tareabpm.TareaBPMResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TareaBPMMapper {

    TareaBPM toEntity(TareaBPMRequest request);

    TareaBPMResponse toResponse(TareaBPM entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget TareaBPM existing, TareaBPMRequest request);
}
