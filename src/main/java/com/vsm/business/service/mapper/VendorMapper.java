package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.VendorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendor} and its DTO {@link VendorDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface VendorMapper extends EntityMapper<VendorDTO, Vendor> {
    @Mapping(target = "creater", source = "creater", qualifiedByName = "id")
    @Mapping(target = "modifier", source = "modifier", qualifiedByName = "id")
    VendorDTO toDto(Vendor s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VendorDTO toDtoId(Vendor vendor);
}
