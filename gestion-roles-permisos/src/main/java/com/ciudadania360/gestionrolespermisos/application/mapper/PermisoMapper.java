package com.ciudadania360.gestionrolespermisos.application.mapper;

import com.ciudadania360.gestionrolespermisos.domain.entity.Permiso;
import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.permiso.PermisoResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermisoMapper {

    PermisoResponse toResponse(Permiso entity);

    Permiso toEntity(PermisoRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Permiso entity, PermisoRequest request);
}
