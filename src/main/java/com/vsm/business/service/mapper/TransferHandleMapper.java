package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.TransferHandleDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferHandle} and its DTO {@link TransferHandleDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        StatusTransferHandleMapper.class, OrganizationMapper.class, DispatchBookMapper.class, StepDataMapper.class, UserInfoMapper.class,
    }
)
public interface TransferHandleMapper extends EntityMapper<TransferHandleDTO, TransferHandle> {
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    @Mapping(target = "statusTransferHandle", source = "statusTransferHandle", qualifiedByName = "id")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "id")
    @Mapping(target = "dispatchBook", source = "dispatchBook", qualifiedByName = "id")
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    @Mapping(target = "receiversHandles", source = "receiversHandles", qualifiedByName = "idSet")
    TransferHandleDTO toDto(TransferHandle s);

    @Mapping(target = "removeReceiversHandle", ignore = true)
    TransferHandle toEntity(TransferHandleDTO transferHandleDTO);
}
