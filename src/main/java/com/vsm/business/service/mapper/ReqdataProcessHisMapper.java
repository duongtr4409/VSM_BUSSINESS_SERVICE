package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ReqdataProcessHisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReqdataProcessHis} and its DTO {@link ReqdataProcessHisDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, StepDataMapper.class, UserInfoMapper.class })
public interface ReqdataProcessHisMapper extends EntityMapper<ReqdataProcessHisDTO, ReqdataProcessHis> {
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "stepData", source = "stepData", qualifiedByName = "id")
    @Mapping(target = "processer", source = "processer", qualifiedByName = "id")
    ReqdataProcessHisDTO toDto(ReqdataProcessHis s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReqdataProcessHisDTO toDtoId(ReqdataProcessHis reqdataProcessHis);
}
