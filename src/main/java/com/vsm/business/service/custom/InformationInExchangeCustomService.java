package com.vsm.business.service.custom;

import com.vsm.business.domain.InformationInExchange;
import com.vsm.business.repository.InformationInExchangeRepository;
import com.vsm.business.repository.search.InformationInExchangeSearchRepository;
import com.vsm.business.service.dto.InformationInExchangeDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.mapper.InformationInExchangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InformationInExchangeCustomService {
    private final Logger log = LoggerFactory.getLogger(InformationInExchangeCustomService.class);

    private InformationInExchangeRepository informationInExchangeRepository;

    private InformationInExchangeSearchRepository informationInExchangeSearchRepository;

    private InformationInExchangeMapper informationInExchangeMapper;

    public InformationInExchangeCustomService(InformationInExchangeRepository informationInExchangeRepository, InformationInExchangeSearchRepository informationInExchangeSearchRepository, InformationInExchangeMapper informationInExchangeMapper) {
        this.informationInExchangeRepository = informationInExchangeRepository;
        this.informationInExchangeSearchRepository = informationInExchangeSearchRepository;
        this.informationInExchangeMapper = informationInExchangeMapper;
    }

    public List<InformationInExchangeDTO> getAll() {
        log.debug("InformationInExchangeServiceImpl: getAll()");
        List<InformationInExchangeDTO> result = new ArrayList<>();
        try {
            List<InformationInExchange> informationInExchanges = this.informationInExchangeRepository.findAll();
            for (InformationInExchange informationInExchange :
                informationInExchanges) {
                InformationInExchangeDTO informationInExchangeDTO = informationInExchangeMapper.toDto(informationInExchange);
                result.add(informationInExchangeDTO);
            }
        }catch (Exception e){
            log.error("InformationInExchangeServiceImpl: getAll() {}", e);
        }
        log.debug("InformationInExchangeServiceImpl: getAll() {}", result);
        return result;
    }

    public List<InformationInExchangeDTO> deleteAll(List<InformationInExchangeDTO> informationInExchangeDTOS) {
        log.debug("InformationInExchangeServiceImpl: deleteAll({})", informationInExchangeDTOS);
        List<Long> ids = informationInExchangeDTOS.stream().map(InformationInExchangeDTO::getId).collect(Collectors.toList());
        this.informationInExchangeRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            informationInExchangeRepository.deleteById(id);
            informationInExchangeSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("InformationInExchangeServiceImpl: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<InformationInExchangeDTO> getAllByRequestDataId(Long requestDataId){
        //return informationInExchangeRepository.findAllByRequestDataId(requestDataId).stream().map(informationInExchangeMapper::toDto).collect(Collectors.toList());
        return informationInExchangeRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> {
            InformationInExchangeDTO informationInExchangeDTO = new InformationInExchangeDTO();
            informationInExchangeDTO = informationInExchangeMapper.toDto(ele);
            if(ele.getRequestData() != null){
                RequestDataDTO requestDataDTO = new RequestDataDTO();
                requestDataDTO.setId(ele.getRequestData().getId());
                informationInExchangeDTO.setRequestData(requestDataDTO);
            }
            return informationInExchangeDTO;
        }).collect(Collectors.toList());
    }
}
