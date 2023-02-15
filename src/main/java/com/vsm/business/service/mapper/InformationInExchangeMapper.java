package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.InformationInExchangeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InformationInExchange} and its DTO {@link InformationInExchangeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RequestDataMapper.class })
public interface InformationInExchangeMapper extends EntityMapper<InformationInExchangeDTO, InformationInExchange> {
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "requestData", source = "requestData")
//    @Mapping(target = "reply", source = "reply", qualifiedByName = "id")
    InformationInExchangeDTO toDto(InformationInExchange s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InformationInExchangeDTO toDtoId(InformationInExchange informationInExchange);
}
