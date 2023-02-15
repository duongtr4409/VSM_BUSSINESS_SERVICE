package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.OTPDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OTP} and its DTO {@link OTPDTO}.
 */
@Mapper(componentModel = "spring", uses = { RequestDataMapper.class, SignDataMapper.class })
public interface OTPMapper extends EntityMapper<OTPDTO, OTP> {
    @Mapping(target = "requestData", source = "requestData", qualifiedByName = "id")
    @Mapping(target = "signData", source = "signData", qualifiedByName = "id")
    OTPDTO toDto(OTP s);
}
