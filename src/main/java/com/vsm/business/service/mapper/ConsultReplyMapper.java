package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ConsultReplyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsultReply} and its DTO {@link ConsultReplyDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, ConsultMapper.class })
public interface ConsultReplyMapper extends EntityMapper<ConsultReplyDTO, ConsultReply> {
    @Mapping(target = "sender", source = "sender", qualifiedByName = "id")
    @Mapping(target = "replier", source = "replier", qualifiedByName = "id")
    @Mapping(target = "consult", source = "consult", qualifiedByName = "id")
    ConsultReplyDTO toDto(ConsultReply s);
}
