package com.vsm.business.service.custom;

import com.vsm.business.domain.Consult;
import com.vsm.business.domain.Rank;
import com.vsm.business.domain.StepData;
import com.vsm.business.repository.ConsultRepository;
import com.vsm.business.repository.RankRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.repository.search.ConsultSearchRepository;
import com.vsm.business.repository.search.RankSearchRepository;
import com.vsm.business.service.dto.ConsultDTO;
import com.vsm.business.service.dto.RankDTO;
import com.vsm.business.service.mapper.ConsultMapper;
import com.vsm.business.service.mapper.RankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private ConsultRepository consultRepository;
    private ConsultSearchRepository consultSearchRepository;
    private ConsultMapper consultMapper;

    private StepDataRepository stepDataRepository;

    public ConsultCustomService(ConsultRepository consultRepository, ConsultSearchRepository consultSearchRepository, ConsultMapper consultMapper, StepDataRepository stepDataRepository) {
        this.consultRepository = consultRepository;
        this.consultSearchRepository = consultSearchRepository;
        this.consultMapper = consultMapper;
        this.stepDataRepository = stepDataRepository;
    }

    public List<ConsultDTO> getAll() {
        log.debug("ConsultCustomService: getAll()");
        List<Consult> consults = this.consultRepository.findAll();
        List<ConsultDTO> result = new ArrayList<>();
        for (Consult rank :
            consults) {
            ConsultDTO consultDTO = consultMapper.toDto(rank);
            result.add(consultDTO);
        }
        return result;
    }

    public List<ConsultDTO> deleteAll(List<ConsultDTO> consultDTOS) {
        log.debug("ConsultCustomService: deleteAll({})", consultDTOS);
        List<Long> ids = consultDTOS.stream().map(ConsultDTO::getId).collect(Collectors.toList());
        this.consultRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            consultRepository.deleteById(id);
            consultSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ConsultCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<ConsultDTO> saveAll(List<ConsultDTO> rankDTOList){
        List<ConsultDTO> result = consultRepository.saveAll(rankDTOList.stream().map(consultMapper::toEntity).collect(Collectors.toList())).stream().map(consultMapper::toDto).collect(Collectors.toList());
        log.debug("ConsultCustomService: saveAll({}): {}", rankDTOList, result);
        return result;
    }

    public List<ConsultDTO> getAllByUserAndRequestData(Long userId, Long requestDataId){
        List<StepData> stepDataList = this.stepDataRepository.findAllByRequestDataId(requestDataId);
        if(stepDataList == null || stepDataList.isEmpty()) return new ArrayList<>();
        List<ConsultDTO> result = new ArrayList<>();
        stepDataList.forEach(ele ->{
            result.addAll(this.consultRepository.findAllByReceiverIdAndStepDataId(userId, ele.getId()).stream().map(this.consultMapper::toDto).collect(Collectors.toList()));
        });
        return result;
    }
}
