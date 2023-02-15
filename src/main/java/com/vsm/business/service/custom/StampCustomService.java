package com.vsm.business.service.custom;

import com.vsm.business.domain.Stamp;
import com.vsm.business.repository.StampRepository;
import com.vsm.business.repository.search.StampSearchRepository;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.dto.StampDTO;
import com.vsm.business.service.mapper.StampMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StampCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private StampRepository stampRepository;
    private StampSearchRepository stampSearchRepository;
    private StampMapper stampMapper;

    public StampCustomService(StampRepository stampRepository, StampSearchRepository stampSearchRepository, StampMapper stampMapper) {
        this.stampRepository = stampRepository;
        this.stampSearchRepository = stampSearchRepository;
        this.stampMapper = stampMapper;
    }

    public List<StampDTO> getAll() {
        log.debug("StampCustomService: getAll()");
        List<Stamp> ranks = this.stampRepository.findAll();
        List<StampDTO> result = new ArrayList<>();
        for (Stamp rank :
            ranks) {
            StampDTO stampDTO = stampMapper.toDto(rank);
            result.add(stampDTO);
        }
        return result;
    }

    public List<StampDTO> deleteAll(List<StampDTO> rankDTOS) {
        log.debug("StampCustomService: deleteAll({})", rankDTOS);
        List<Long> ids = rankDTOS.stream().map(StampDTO::getId).collect(Collectors.toList());
        this.stampRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            stampRepository.deleteById(id);
            stampSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("StampCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<StampDTO> saveAll(List<StampDTO> rankDTOList){
        List<StampDTO> result = stampRepository.saveAll(rankDTOList.stream().map(stampMapper::toEntity).collect(Collectors.toList())).stream().map(stampMapper::toDto).collect(Collectors.toList());
        log.debug("StampCustomService: saveAll({}): {}", rankDTOList, result);
        return result;
    }

}
