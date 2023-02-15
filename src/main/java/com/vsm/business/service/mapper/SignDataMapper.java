package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.SignDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SignData} and its DTO {@link SignDataDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, SignatureInfomationMapper.class, UserInfoMapper.class })
public interface SignDataMapper extends EntityMapper<SignDataDTO, SignData> {
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "signatureInfomation", source = "signatureInfomation", qualifiedByName = "id")
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    SignDataDTO toDto(SignData s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SignDataDTO toDtoId(SignData signData);
}
