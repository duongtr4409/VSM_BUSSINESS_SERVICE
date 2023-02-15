package com.vsm.business.service.custom;

import com.vsm.business.domain.SignData;
import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.repository.search.SignDataSearchRepository;
import com.vsm.business.service.dto.SignDataDTO;
import com.vsm.business.service.mapper.SignDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignDataCustomService {
    private final Logger log = LoggerFactory.getLogger(SignDataCustomService.class);

    private SignDataRepository signDataRepository;

    private SignDataSearchRepository signDataSearchRepository;

    private SignDataMapper signDataMapper;

    public SignDataCustomService(SignDataRepository outgoingDocRepository, SignDataSearchRepository outgoingDocSearchRepository, SignDataMapper outgoingDocMapper) {
        this.signDataRepository = outgoingDocRepository;
        this.signDataSearchRepository = outgoingDocSearchRepository;
        this.signDataMapper = outgoingDocMapper;
    }

    public List<SignDataDTO> getAll() {
        log.debug("SignDataCustomService: getAll()");
        List<SignDataDTO> result = new ArrayList<>();
        try {
            List<SignData> signDataList = this.signDataRepository.findAll();
            for (SignData signData :
                signDataList) {
                SignDataDTO signDataDTO = signDataMapper.toDto(signData);
                result.add(signDataDTO);
            }
        }catch (Exception e){
            log.error("SignDataCustomService: getAll() {}", e);
        }
        log.debug("SignDataCustomService: getAll() {}", result);
        return result;
    }

    public List<SignDataDTO> deleteAll(List<SignDataDTO> signDataDTOS) {
        log.debug("SignDataCustomService: deleteAll({})", signDataDTOS);
        List<Long> ids = signDataDTOS.stream().map(SignDataDTO::getId).collect(Collectors.toList());
        this.signDataRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            signDataRepository.deleteById(id);
            signDataSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("SignDataCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<SignDataDTO> saveAll(List<SignDataDTO> incomingBookDTOList){
        List<SignDataDTO> result = signDataRepository.saveAll(incomingBookDTOList.stream().map(signDataMapper::toEntity).collect(Collectors.toList())).stream().map(signDataMapper::toDto).collect(Collectors.toList());
        log.debug("SignDataCustomService: saveAll({}) {}", incomingBookDTOList, result);
        return result;
    }

    public Long countAll(){
        Long result = signDataRepository.count();
        return result;
    }

    public List<SignDataDTO> getAllByRequetData(Long requestDataId){
        List<SignDataDTO> result = this.signDataRepository.findAllByRequestDataId(requestDataId).stream().map(this.signDataMapper::toDto).collect(Collectors.toList());
        log.debug("SignDataCustomService: getAllByRequetData({}) {}", requestDataId, result);
        return result;
    }

}
