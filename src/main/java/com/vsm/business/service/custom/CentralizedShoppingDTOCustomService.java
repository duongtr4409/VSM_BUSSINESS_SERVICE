package com.vsm.business.service.custom;

import com.vsm.business.domain.CentralizedShopping;
import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.repository.search.CentralizedShoppingSearchRepository;
import com.vsm.business.service.dto.CentralizedShoppingDTO;
import com.vsm.business.service.mapper.CentralizedShoppingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CentralizedShoppingDTOCustomService {
    private final Logger log = LoggerFactory.getLogger(CentralizedShoppingDTOCustomService.class);

    private CentralizedShoppingRepository centralizedShoppingRepository;

    private CentralizedShoppingSearchRepository centralizedShoppingSearchRepository;

    private CentralizedShoppingMapper centralizedShoppingMapper;

    public CentralizedShoppingDTOCustomService(CentralizedShoppingRepository centralizedShoppingRepository, CentralizedShoppingSearchRepository centralizedShoppingSearchRepository, CentralizedShoppingMapper centralizedShoppingMapper) {
        this.centralizedShoppingRepository = centralizedShoppingRepository;
        this.centralizedShoppingSearchRepository = centralizedShoppingSearchRepository;
        this.centralizedShoppingMapper = centralizedShoppingMapper;
    }

    public List<CentralizedShoppingDTO> getAll() {
        log.debug("CentralizedShoppingDTOCustomService: getAll()");
        List<CentralizedShoppingDTO> result = new ArrayList<>();
        try {
            List<CentralizedShopping> centralizedShoppings = this.centralizedShoppingRepository.findAll();
            for (CentralizedShopping centralizedShopping :
                centralizedShoppings) {
                CentralizedShoppingDTO stcentralizedShoppingDTOpDTO = centralizedShoppingMapper.toDto(centralizedShopping);
                result.add(stcentralizedShoppingDTOpDTO);
            }
        }catch (Exception e){
            log.error("CentralizedShoppingDTOCustomService: getAll() {}", e);
        }
        log.debug("CentralizedShoppingDTOCustomService: getAll() {}", result);
        return result;
    }

    public List<CentralizedShoppingDTO> deleteAll(List<CentralizedShoppingDTO> stepDTOS) {
        log.debug("CentralizedShoppingDTOCustomService: deleteAll({})", stepDTOS);
        List<Long> ids = stepDTOS.stream().map(CentralizedShoppingDTO::getId).collect(Collectors.toList());
        this.centralizedShoppingRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            centralizedShoppingRepository.deleteById(id);
            centralizedShoppingSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("CentralizedShoppingDTOCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<CentralizedShoppingDTO> saveAll(List<CentralizedShoppingDTO> stepDTOList){
        List<CentralizedShoppingDTO> result = centralizedShoppingRepository.saveAll(stepDTOList.stream().map(centralizedShoppingMapper::toEntity).collect(Collectors.toList())).stream().map(centralizedShoppingMapper::toDto).collect(Collectors.toList());
        log.debug("CentralizedShoppingDTOCustomService: saveAll({}) {}", stepDTOList, result);
        return result;
    }
}
