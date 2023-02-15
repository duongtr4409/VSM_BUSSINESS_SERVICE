package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ExamineReplyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExamineReply} and its DTO {@link ExamineReplyDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, ExamineMapper.class })
public interface ExamineReplyMapper extends EntityMapper<ExamineReplyDTO, ExamineReply> {
    @Mapping(target = "sender", source = "sender", qualifiedByName = "id")
    @Mapping(target = "replier", source = "replier", qualifiedByName = "id")
    @Mapping(target = "examine", source = "examine", qualifiedByName = "id")
    ExamineReplyDTO toDto(ExamineReply s);
}
