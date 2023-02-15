package com.vsm.business.service.custom;

import com.vsm.business.domain.ConsultReply;
import com.vsm.business.repository.ConsultReplyRepository;
import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.repository.search.ConsultReplySearchRepository;
import com.vsm.business.repository.search.ConsultSearchRepository;
import com.vsm.business.service.dto.ConsultDTO;
import com.vsm.business.service.dto.ConsultReplyDTO;
import com.vsm.business.service.mapper.ConsultMapper;
import com.vsm.business.service.mapper.ConsultReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultReplyCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private ConsultReplyRepository consultReplyRepository;
    private ConsultReplySearchRepository consultReplySearchRepository;
    private ConsultReplyMapper consultReplyMapper;

    public ConsultReplyCustomService(ConsultReplyRepository consultReplyRepository, ConsultReplySearchRepository consultReplySearchRepository, ConsultReplyMapper consultReplyMapper) {
        this.consultReplyRepository = consultReplyRepository;
        this.consultReplySearchRepository = consultReplySearchRepository;
        this.consultReplyMapper = consultReplyMapper;
    }

    public List<ConsultReplyDTO> getAll() {
        log.debug("ConsultReplyCustomService: getAll()");
        List<ConsultReply> consultReplies = this.consultReplyRepository.findAll();
        List<ConsultReplyDTO> result = new ArrayList<>();
        for (ConsultReply consultReply :
            consultReplies) {
            ConsultReplyDTO consultReplyDTO = consultReplyMapper.toDto(consultReply);
            result.add(consultReplyDTO);
        }
        return result;
    }

    public List<ConsultReplyDTO> deleteAll(List<ConsultReplyDTO> consultDTOS) {
        log.debug("ConsultReplyCustomService: deleteAll({})", consultDTOS);
        List<Long> ids = consultDTOS.stream().map(ConsultReplyDTO::getId).collect(Collectors.toList());
        this.consultReplyRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            consultReplyRepository.deleteById(id);
            consultReplySearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ConsultReplyCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<ConsultReplyDTO> saveAll(List<ConsultReplyDTO> rankDTOList){
        List<ConsultReplyDTO> result = consultReplyRepository.saveAll(rankDTOList.stream().map(consultReplyMapper::toEntity).collect(Collectors.toList())).stream().map(consultReplyMapper::toDto).collect(Collectors.toList());
        log.debug("ConsultReplyCustomService: saveAll({}): {}", rankDTOList, result);
        return result;
    }

}
