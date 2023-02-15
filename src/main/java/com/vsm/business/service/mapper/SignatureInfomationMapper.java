package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.SignatureInfomationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SignatureInfomation} and its DTO {@link SignatureInfomationDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface SignatureInfomationMapper extends EntityMapper<SignatureInfomationDTO, SignatureInfomation> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    SignatureInfomationDTO toDto(SignatureInfomation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SignatureInfomationDTO toDtoId(SignatureInfomation signatureInfomation);
}
