package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.StatusTransferHandleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatusTransferHandle} and its DTO {@link StatusTransferHandleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatusTransferHandleMapper extends EntityMapper<StatusTransferHandleDTO, StatusTransferHandle> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StatusTransferHandleDTO toDtoId(StatusTransferHandle statusTransferHandle);
}
