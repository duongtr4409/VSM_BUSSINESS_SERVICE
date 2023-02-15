package com.vsm.business.service.custom;

import com.vsm.business.domain.MallAndStall;
import com.vsm.business.domain.Step;
import com.vsm.business.repository.MallAndStallRepository;
import com.vsm.business.repository.StepRepository;
import com.vsm.business.repository.search.MallAndStallSearchRepository;
import com.vsm.business.repository.search.StepSearchRepository;
import com.vsm.business.service.dto.MallAndStallDTO;
import com.vsm.business.service.dto.StepDTO;
import com.vsm.business.service.mapper.MallAndStallMapper;
import com.vsm.business.service.mapper.StepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallAndStallCustomService {
    private final Logger log = LoggerFactory.getLogger(MallAndStallCustomService.class);

    private MallAndStallRepository mallAndStallRepository;

    private MallAndStallSearchRepository mallAndStallSearchRepository;

    private MallAndStallMapper mallAndStallMapper;

    public MallAndStallCustomService(MallAndStallRepository mallAndStallRepository, MallAndStallSearchRepository mallAndStallSearchRepository, MallAndStallMapper mallAndStallMapper) {
        this.mallAndStallRepository = mallAndStallRepository;
        this.mallAndStallSearchRepository = mallAndStallSearchRepository;
        this.mallAndStallMapper = mallAndStallMapper;
    }

    public List<MallAndStallDTO> getAll() {
        log.debug("MallAndStallCustomService: getAll()");
        List<MallAndStallDTO> result = new ArrayList<>();
        try {
            List<MallAndStall> mallAndStalls = this.mallAndStallRepository.findAll();
            for (MallAndStall mallAndStall :
                mallAndStalls) {
                MallAndStallDTO mallAndStallDTO = mallAndStallMapper.toDto(mallAndStall);
                result.add(mallAndStallDTO);
            }
        }catch (Exception e){
            log.error("MallAndStallCustomService: getAll() {}", e);
        }
        log.debug("MallAndStallCustomService: getAll() {}", result);
        return result;
    }

    public List<MallAndStallDTO> deleteAll(List<MallAndStallDTO> stepDTOS) {
        log.debug("MallAndStallCustomService: deleteAll({})", stepDTOS);
        List<Long> ids = stepDTOS.stream().map(MallAndStallDTO::getId).collect(Collectors.toList());
        this.mallAndStallRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            mallAndStallRepository.deleteById(id);
            mallAndStallSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("MallAndStallCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

}
