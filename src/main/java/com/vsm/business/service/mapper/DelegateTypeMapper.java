package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.DelegateTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DelegateType} and its DTO {@link DelegateTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DelegateTypeMapper extends EntityMapper<DelegateTypeDTO, DelegateType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DelegateTypeDTO toDtoId(DelegateType delegateType);
}
