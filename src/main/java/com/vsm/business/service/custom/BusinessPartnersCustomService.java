package com.vsm.business.service.custom;

import com.vsm.business.domain.BusinessPartner;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.BusinessPartnerRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.BusinessPartnerSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.BusinessPartnerDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.BusinessPartnerMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessPartnersCustomService {
    private final Logger log = LoggerFactory.getLogger(BusinessPartnersCustomService.class);

    private BusinessPartnerRepository businessPartnerRepository;

    private BusinessPartnerSearchRepository businessPartnerSearchRepository;

    private BusinessPartnerMapper businessPartnerMapper;

    public BusinessPartnersCustomService(BusinessPartnerRepository businessPartnerRepository, BusinessPartnerSearchRepository businessPartnerSearchRepository, BusinessPartnerMapper businessPartnerMapper) {
        this.businessPartnerRepository = businessPartnerRepository;
        this.businessPartnerSearchRepository = businessPartnerSearchRepository;
        this.businessPartnerMapper = businessPartnerMapper;
    }

    public List<BusinessPartnerDTO> getAll() {
        log.debug("BusinessPartnersCustomService: getAll()");
        List<BusinessPartnerDTO> result = new ArrayList<>();
        try {
            List<BusinessPartner> businessPartners = this.businessPartnerRepository.findAll();
            for (BusinessPartner businessPartner :
                businessPartners) {
                BusinessPartnerDTO stepDTO = businessPartnerMapper.toDto(businessPartner);
                result.add(stepDTO);
            }
        }catch (Exception e){
            log.error("BusinessPartnersCustomService: getAll() {}", e);
        }
        log.debug("BusinessPartnersCustomService: getAll() {}", result);
        return result;
    }

    public List<BusinessPartnerDTO> deleteAll(List<BusinessPartnerDTO> businessPartnerDTOS) {
        log.debug("BusinessPartnersCustomService: deleteAll({})", businessPartnerDTOS);
        List<Long> ids = businessPartnerDTOS.stream().map(BusinessPartnerDTO::getId).collect(Collectors.toList());
        this.businessPartnerRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            businessPartnerRepository.deleteById(id);
            businessPartnerSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("BusinessPartnersCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<BusinessPartnerDTO> saveAll(List<BusinessPartnerDTO> stepDTOList){
        List<BusinessPartnerDTO> result = businessPartnerRepository.saveAll(stepDTOList.stream().map(businessPartnerMapper::toEntity).collect(Collectors.toList())).stream().map(businessPartnerMapper::toDto).collect(Collectors.toList());
        log.info("BusinessPartnersCustomService: saveAll({}) {}");
        return result;
    }
}
