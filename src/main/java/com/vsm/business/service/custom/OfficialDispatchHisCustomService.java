package com.vsm.business.service.custom;

import com.vsm.business.repository.OfficialDispatchHisRepository;
import com.vsm.business.repository.OfficialDispatchRepository;
import com.vsm.business.repository.search.OfficialDispatchHisSearchRepository;
import com.vsm.business.repository.search.OfficialDispatchSearchRepository;
import com.vsm.business.service.dto.OfficialDispatchDTO;
import com.vsm.business.service.dto.OfficialDispatchHisDTO;
import com.vsm.business.service.mapper.OfficialDispatchHisMapper;
import com.vsm.business.service.mapper.OfficialDispatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficialDispatchHisCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private OfficialDispatchHisRepository officialDispatchHisRepository;
    private OfficialDispatchHisSearchRepository officialDispatchHisSearchRepository;
    protected OfficialDispatchHisMapper officialDispatchHisMapper;

    public OfficialDispatchHisCustomService(OfficialDispatchHisRepository officialDispatchHisRepository, OfficialDispatchHisSearchRepository officialDispatchHisSearchRepository, OfficialDispatchHisMapper officialDispatchHisMapper) {
        this.officialDispatchHisRepository = officialDispatchHisRepository;
        this.officialDispatchHisSearchRepository = officialDispatchHisSearchRepository;
        this.officialDispatchHisMapper = officialDispatchHisMapper;
    }

    public List<OfficialDispatchHisDTO> getAll() {
        List<OfficialDispatchHisDTO> result = this.officialDispatchHisRepository.findAll().stream().map(officialDispatchHisMapper::toDto).collect(Collectors.toList());
        log.debug("OfficialDispatchHisCustomService: getAll() {}", result);
        return result;
    }

    public List<OfficialDispatchHisDTO> deleteAll(List<OfficialDispatchHisDTO> officialDispatchHisDTOS) {
        log.debug("OfficialDispatchHisCustomService: deleteAll({})", officialDispatchHisDTOS);
        List<Long> ids = officialDispatchHisDTOS.stream().map(OfficialDispatchHisDTO::getId).collect(Collectors.toList());
        this.officialDispatchHisRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            officialDispatchHisRepository.deleteById(id);
            officialDispatchHisSearchRepository.deleteById(id);
            log.debug("OfficialDispatchHisCustomService: delete({}) {}", id);
            return true;
        } catch (Exception e) {
            log.error("OfficialDispatchHisCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<OfficialDispatchHisDTO> saveAll(List<OfficialDispatchHisDTO> officialDispatchHisDTOS){
        List<OfficialDispatchHisDTO> result = officialDispatchHisRepository.saveAll(officialDispatchHisDTOS.stream().map(officialDispatchHisMapper::toEntity).collect(Collectors.toList())).stream().map(officialDispatchHisMapper::toDto).collect(Collectors.toList());
        log.debug("OfficialDispatchHisCustomService: saveAll({}) {}", officialDispatchHisDTOS, result);
        return result;
    }

    public List<OfficialDispatchHisDTO> getAllByOfficalDispatch(Long officalDispatchId){
        List<OfficialDispatchHisDTO> result = this.officialDispatchHisRepository.findAllByOfficialDispatchId(officalDispatchId).stream().map(this.officialDispatchHisMapper::toDto).collect(Collectors.toList());
        log.debug("OfficialDispatchHisCustomService: saveAll({}) {}", result, result);
        return result;
    }

}
