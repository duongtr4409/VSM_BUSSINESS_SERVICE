package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.MailLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MailLog} and its DTO {@link MailLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MailLogMapper extends EntityMapper<MailLogDTO, MailLog> {}
