package com.vsm.business.service.custom;

import com.vsm.business.domain.Role;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.FeatureRepository;
import com.vsm.business.repository.RoleRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.repository.search.FeatureSearchRepository;
import com.vsm.business.service.dto.FeatureDTO;
import com.vsm.business.service.mapper.FeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeatureCustomService {
    private final Logger log = LoggerFactory.getLogger(FeatureCustomService.class);

    private final FeatureRepository featureRepository;

    private final FeatureMapper featureMapper;

    private final RoleRepository roleRepository;

    private final FeatureSearchRepository featureSearchRepository;

    private final UserInfoRepository userInfoRepository;

    public FeatureCustomService(FeatureRepository featureRepository, FeatureMapper featureMapper, RoleRepository roleRepository, FeatureSearchRepository featureSearchRepository, UserInfoRepository userInfoRepository) {
        this.featureRepository = featureRepository;
        this.featureMapper = featureMapper;
        this.roleRepository = roleRepository;
        this.featureSearchRepository = featureSearchRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public List<FeatureDTO> getAll(){
        List<FeatureDTO> result = this.featureRepository.findAll().stream().map(this.featureMapper::toDto).collect(Collectors.toList());
        log.debug("FeatureCustomService getAll(): {}", result);
        return result;
    }

    public List<FeatureDTO> deleteAll(List<FeatureDTO> featureDTOS){
        log.debug("FeatureCustomService deleteAll({})", featureDTOS);
        List<Long> ids = featureDTOS.stream().map(FeatureDTO::getId).collect(Collectors.toList());
        this.featureRepository.deleteAllById(ids);
        return this.getAll();
    }

    public List<FeatureDTO> saveAll(List<FeatureDTO> featureDTOS){
        List<FeatureDTO> result = this.featureRepository.saveAll(featureDTOS.stream().map(this.featureMapper::toEntity).collect(Collectors.toList())).stream().map(this.featureMapper::toDto).collect(Collectors.toList());
        log.debug("FeatureCustomService saveAll({}): {}", featureDTOS, result);
        return result;
    }

    public List<FeatureDTO> getAllByRole(Long roleId){
        Role role = this.roleRepository.findById(roleId).get();
        List<FeatureDTO> result = role.getFeatures().stream().map(this.featureMapper::toDto).collect(Collectors.toList());
        log.debug("FeatureCustomService getAllByRole({}): {}", roleId, result);
        return result;
    }

    public List<FeatureDTO> getAllByUser(Long userId){
        UserInfo userInfo = this.userInfoRepository.findById(userId).get();
        List<FeatureDTO> result = new ArrayList<>();
        userInfo.getRoles().forEach(ele -> {
            result.addAll(ele.getFeatures().stream().map(this.featureMapper::toDto).collect(Collectors.toList()));
        });
        log.debug("FeatureCustomService getAllByUser({}): {}", userId, result);
        return result;
    }
}
