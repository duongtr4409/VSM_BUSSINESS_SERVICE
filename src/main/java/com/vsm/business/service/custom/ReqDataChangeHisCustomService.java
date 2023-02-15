package com.vsm.business.service.custom;

import com.vsm.business.domain.ReqdataChangeHis;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.ReqdataChangeHisRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.ReqdataChangeHisSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.ReqdataChangeHisDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.ReqdataChangeHisMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReqDataChangeHisCustomService {
    private final Logger log = LoggerFactory.getLogger(ReqDataChangeHisCustomService.class);

    private ReqdataChangeHisRepository reqdataChangeHisRepository;

    private ReqdataChangeHisSearchRepository reqdataChangeHisSearchRepository;

    private ReqdataChangeHisMapper reqdataChangeHisMapper;

    public ReqDataChangeHisCustomService(ReqdataChangeHisRepository reqdataChangeHisRepository, ReqdataChangeHisSearchRepository reqdataChangeHisSearchRepository, ReqdataChangeHisMapper reqdataChangeHisMapper) {
        this.reqdataChangeHisRepository = reqdataChangeHisRepository;
        this.reqdataChangeHisSearchRepository = reqdataChangeHisSearchRepository;
        this.reqdataChangeHisMapper = reqdataChangeHisMapper;
    }

    public List<ReqdataChangeHisDTO> getAll() {
        log.debug("ReqDataChangeHisCustomService: getAll()");
        List<ReqdataChangeHisDTO> result = new ArrayList<>();
        try {
            List<ReqdataChangeHis> reqdataChangeHiss = this.reqdataChangeHisRepository.findAll();
            for (ReqdataChangeHis reqdataChangeHis :
                reqdataChangeHiss) {
                ReqdataChangeHisDTO reqdataChangeHisDTO = reqdataChangeHisMapper.toDto(reqdataChangeHis);
                result.add(reqdataChangeHisDTO);
            }
        }catch (Exception e){
            log.error("ReqDataChangeHisCustomService: getAll() {}", e);
        }
        log.debug("ReqDataChangeHisCustomService: getAll() {}", result);
        return result;
    }

    public List<ReqdataChangeHisDTO> deleteAll(List<ReqdataChangeHisDTO> reqdataChangeHisDTOS) {
        log.debug("ReqDataChangeHisCustomService: deleteAll({})", reqdataChangeHisDTOS);
        List<Long> ids = reqdataChangeHisDTOS.stream().map(ReqdataChangeHisDTO::getId).collect(Collectors.toList());
        this.reqdataChangeHisRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            reqdataChangeHisRepository.deleteById(id);
            reqdataChangeHisSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ReqDataChangeHisCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ReqdataChangeHisDTO> getAllByReqDataId(Long requestDataId){
        return reqdataChangeHisRepository.findAllByRequestDataId(requestDataId).stream().map(reqdataChangeHisMapper::toDto).collect(Collectors.toList());
    }
}
