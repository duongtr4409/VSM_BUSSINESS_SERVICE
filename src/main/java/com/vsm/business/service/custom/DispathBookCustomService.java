package com.vsm.business.service.custom;

import com.vsm.business.domain.DispatchBook;
import com.vsm.business.domain.Rank;
import com.vsm.business.repository.DispatchBookRepository;
import com.vsm.business.repository.RankRepository;
import com.vsm.business.repository.search.DispatchBookSearchRepository;
import com.vsm.business.repository.search.RankSearchRepository;
import com.vsm.business.service.custom.RankInOrgCustomService;
import com.vsm.business.service.custom.StepCustomService;
import com.vsm.business.service.dto.DispatchBookDTO;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.mapper.DispatchBookMapper;
import com.vsm.business.service.mapper.RankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispathBookCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private DispatchBookRepository dispatchBookRepository;
    private DispatchBookSearchRepository dispatchBookSearchRepository;
    private DispatchBookMapper dispatchBookMapper;

    public DispathBookCustomService(DispatchBookRepository dispatchBookRepository, DispatchBookSearchRepository dispatchBookSearchRepository, DispatchBookMapper dispatchBookMapper) {
        this.dispatchBookRepository = dispatchBookRepository;
        this.dispatchBookSearchRepository = dispatchBookSearchRepository;
        this.dispatchBookMapper = dispatchBookMapper;
    }

    public List<DispatchBookDTO> getAll() {
        log.debug("DispathBookCustomService: getAll()");
        List<DispatchBook> ranks = this.dispatchBookRepository.findAll();
        List<DispatchBookDTO> result = new ArrayList<>();
        for (DispatchBook dispatchBook :
            ranks) {
            DispatchBookDTO dispatchBookDTO = dispatchBookMapper.toDto(dispatchBook);
            result.add(dispatchBookDTO);
        }
        return result;
    }

    public List<DispatchBookDTO> deleteAll(List<DispatchBookDTO> dispatchBookDTOS) {
        log.debug("DispathBookCustomService: deleteAll({})", dispatchBookDTOS);
        List<Long> ids = dispatchBookDTOS.stream().map(DispatchBookDTO::getId).collect(Collectors.toList());
        this.dispatchBookRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            dispatchBookRepository.deleteById(id);
            dispatchBookSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("DispathBookCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<DispatchBookDTO> saveAll(List<DispatchBookDTO> rankDTOList){
        List<DispatchBookDTO> result = dispatchBookRepository.saveAll(rankDTOList.stream().map(dispatchBookMapper::toEntity).collect(Collectors.toList())).stream().map(dispatchBookMapper::toDto).collect(Collectors.toList());
        log.debug("DispathBookCustomService: saveAll({}): {}", rankDTOList, result);
        return result;
    }

}
