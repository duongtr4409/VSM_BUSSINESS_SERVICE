package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ManageStampInfoDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ManageStampInfo} and its DTO {@link ManageStampInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, StampMapper.class, OrganizationMapper.class, UserInfoMapper.class })
public interface ManageStampInfoMapper extends EntityMapper<ManageStampInfoDTO, ManageStampInfo> {
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "stamp", source = "stamp", qualifiedByName = "id")
    @Mapping(target = "orgReturn", source = "orgReturn", qualifiedByName = "id")
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    @Mapping(target = "orgStorages", source = "orgStorages", qualifiedByName = "idSet")
    ManageStampInfoDTO toDto(ManageStampInfo s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ManageStampInfoDTO toDtoId(ManageStampInfo manageStampInfo);

    @Mapping(target = "removeOrgStorage", ignore = true)
    ManageStampInfo toEntity(ManageStampInfoDTO manageStampInfoDTO);
}
