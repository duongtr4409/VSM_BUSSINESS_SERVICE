package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.TagInExchangeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TagInExchange} and its DTO {@link TagInExchangeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RequestDataMapper.class, InformationInExchangeMapper.class })
public interface TagInExchangeMapper extends EntityMapper<TagInExchangeDTO, TagInExchange> {
    @Mapping(target = "tager", source = "tager", qualifiedByName = "id")
    @Mapping(target = "tagged", source = "tagged", qualifiedByName = "id")
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "informationInExchange", source = "informationInExchange", qualifiedByName = "id")
    TagInExchangeDTO toDto(TagInExchange s);
}
