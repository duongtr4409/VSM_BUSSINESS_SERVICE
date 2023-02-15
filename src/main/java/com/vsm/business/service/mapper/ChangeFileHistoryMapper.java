package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.ChangeFileHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChangeFileHistory} and its DTO {@link ChangeFileHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { AttachmentFileMapper.class })
public interface ChangeFileHistoryMapper extends EntityMapper<ChangeFileHistoryDTO, ChangeFileHistory> {
    @Mapping(target = "attachmentFile", source = "attachmentFile", qualifiedByName = "id")
    ChangeFileHistoryDTO toDto(ChangeFileHistory s);
}
