package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.RequestRecallDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RequestRecall} and its DTO {@link RequestRecallDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RequestDataMapper.class, StepDataMapper.class })
public interface RequestRecallMapper extends EntityMapper<RequestRecallDTO, RequestRecall> {
    @Mapping(target = "recaller", source = "recaller", qualifiedByName = "id")
    @Mapping(target = "processer", source = "processer", qualifiedByName = "id")
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    RequestRecallDTO toDto(RequestRecall s);
}
