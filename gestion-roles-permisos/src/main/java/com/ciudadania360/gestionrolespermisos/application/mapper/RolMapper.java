package com.ciudadania360.gestionrolespermisos.application.mapper;

import com.ciudadania360.gestionrolespermisos.domain.entity.Rol;
import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolRequest;
import com.ciudadania360.gestionrolespermisos.application.dto.rol.RolResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolMapper {

    RolResponse toResponse(Rol entity);

    Rol toEntity(RolRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Rol entity, RolRequest request);
}
