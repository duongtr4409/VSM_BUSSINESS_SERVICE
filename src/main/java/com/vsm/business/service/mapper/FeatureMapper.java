package com.vsm.business.service.mapper;

import com.vsm.business.domain.*;
import com.vsm.business.service.dto.FeatureDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Feature} and its DTO {@link FeatureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeatureMapper extends EntityMapper<FeatureDTO, Feature> {
    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<FeatureDTO> toDtoIdSet(Set<Feature> feature);
}
