package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.AttachmentPermisitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentPermisition} and its DTO {@link AttachmentPermisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, AttachmentFileMapper.class })
public interface AttachmentPermisitionMapper extends EntityMapper<AttachmentPermisitionDTO, AttachmentPermisition> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    @Mapping(target = "attachmentFile", source = "attachmentFile", qualifiedByName = "id")
    @Mapping(target = "created", source = "created", qualifiedByName = "id")
    @Mapping(target = "modified", source = "modified", qualifiedByName = "id")
    AttachmentPermisitionDTO toDto(AttachmentPermisition s);
}
