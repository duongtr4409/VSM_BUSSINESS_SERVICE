package com.vsm.business.service.custom;

import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestType;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.repository.search.RequestTypeSearchRepository;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestTypeDTO;
import com.vsm.business.service.mapper.RequestTypeMapper;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RequestTypeCustomService {
    private final Logger log = LoggerFactory.getLogger(RequestTypeCustomService.class);
    private RequestTypeRepository requestTypeRepository;
    private RequestTypeSearchRepository requestTypeSearchRepository;
    private RequestTypeMapper requestTypeMapper;
    private RequestRepository requestRepository;

    private ObjectUtils objectUtils;

    public RequestTypeCustomService(RequestTypeRepository requestTypeRepository, RequestTypeSearchRepository requestTypeSearchRepository, RequestTypeMapper requestTypeMapper, RequestRepository requestRepository, ObjectUtils objectUtils) {
        this.requestTypeRepository = requestTypeRepository;
        this.requestTypeSearchRepository = requestTypeSearchRepository;
        this.requestTypeMapper = requestTypeMapper;
        this.requestRepository = requestRepository;
        this.objectUtils = objectUtils;
    }

    public List<RequestTypeDTO> getAll() {
        log.debug("RequestTypeCustomService: getAll()");
        List<RequestType> requestTypeDTOS = this.requestTypeRepository.findAll();
        List<RequestTypeDTO> result = new ArrayList<>();
        for (RequestType requestType :
            requestTypeDTOS) {
            RequestTypeDTO requestTypeDTO = requestTypeMapper.toDto(requestType);
            result.add(requestTypeDTO);
        }
        return result;
    }

    public List<RequestTypeDTO> deleteAll(List<RequestTypeDTO> requestTypeDTOS) {
        log.debug("RequestTypeCustomService: deleteAll({})", requestTypeDTOS);
        List<Long> ids = requestTypeDTOS.stream().map(RequestTypeDTO::getId).collect(Collectors.toList());
        this.requestTypeRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            requestTypeRepository.deleteById(id);
            requestTypeSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("RequestTypeCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

	public List<RequestTypeDTO> saveAll(List<RequestTypeDTO> requestTypeDTOList) {
		List<RequestTypeDTO> result = requestTypeRepository.saveAll(requestTypeDTOList.stream().map(requestTypeMapper::toEntity).collect(Collectors.toList())).stream().map(requestTypeMapper::toDto).collect(Collectors.toList());
		log.debug("RequestTypeCustomService: saveAll({}) {}", requestTypeDTOList, result);
		return result;
    }

    public RequestTypeDTO customSave(RequestTypeDTO requestTypeDTO) throws Exception {
        log.debug("RequestTypeCustomService: customSave({}) {}", requestTypeDTO);

        if(requestTypeDTO.getIsDelete()){
            Boolean isExist = checkExist(requestTypeDTO);
            if(isExist) throw new Exception("Exist Request");
        }

        // update lại RequestData
        try {
            Request request = this.requestRepository.findAllByRequestTypeId(requestTypeDTO.getId()).stream().filter(ele -> ele.getIsDelete() == null || !ele.getIsDelete()).collect(Collectors.toList()).get(0);
            if(!request.getRequestName().equals(requestTypeDTO.getRequestTypeName())){
                request.setRequestName(requestTypeDTO.getRequestTypeName());
                this.requestRepository.save(request);
            }
        }catch (Exception e){
            log.error("{}", e);
        }

        RequestType requestType = requestTypeMapper.toEntity(requestTypeDTO);
        requestType = requestTypeRepository.save(requestType);
        RequestTypeDTO result = requestTypeMapper.toDto(requestType);
        try {
            requestTypeSearchRepository.save(requestType);
        }catch (StackOverflowError e){
            log.debug(e.getMessage());
        }
        return result;
    }

    @Transactional
    public List<RequestTypeDTO> customSaveAll(List<RequestTypeDTO> requestTypeDTOList) throws Exception {
        log.debug("RequestTypeCustomService: customSaveAll({}):", requestTypeDTOList);
        if(requestTypeDTOList.stream().anyMatch(ele -> ele.getIsDelete() == true)){
            Boolean isExsit = false;
            for(RequestTypeDTO requestTypeDTO : requestTypeDTOList){
                isExsit = this.checkExist(requestTypeDTO);
                if(isExsit) throw new Exception("Exist Request");
            }
        }
        List<RequestTypeDTO> result = requestTypeRepository.saveAll(requestTypeDTOList.stream().map(requestTypeMapper::toEntity).collect(Collectors.toList())).stream().map(requestTypeMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public List<RequestTypeDTO> getAllByRequestGroupId(Long requestGroupId){
        List<RequestTypeDTO> result = this.requestTypeRepository.findAllByRequestGroupId(requestGroupId).stream().map(requestTypeMapper::toDto).collect(Collectors.toList());
        return result;
    }

    public RequestDTO checkExistRequest(Long requestTypeId){
//        Boolean result = this.requestRepository.findAllByRequestTypeId(requestTypeId).stream().anyMatch(ele -> {
//           return ele.getIsDelete() == null || ele.getIsDelete() != true;
//        });
//        return result;
        Request request = this.requestRepository.findAllByRequestTypeId(requestTypeId).stream().filter(ele -> {
            return ele.getIsDelete() == null || ele.getIsDelete() != true;
        }).findFirst().orElse(null);
        if(request == null) return null;
        else{
            RequestDTO result = new RequestDTO();
            BeanUtils.copyProperties(request, result);
            return result;
        }
    }

    private Boolean checkExist(RequestTypeDTO requestTypeDTO){
        Boolean result = requestRepository.findAllByRequestTypeId(requestTypeDTO.getId()).stream().anyMatch(ele -> ele.getIsDelete() != true);
        return result;
    }

    public List<RequestTypeDTO> getAllIgnoreField() {
        log.debug("RequestTypeCustomService: getAllIgnoreField()");
        List<RequestType> requestTypeDTOS = this.requestTypeRepository.findAll();
        List<RequestTypeDTO> result = new ArrayList<>();
        for (RequestType requestType :
            requestTypeDTOS) {
            if(requestType != null){
                RequestTypeDTO requestTypeDTO = new RequestTypeDTO();
                try {requestTypeDTO = this.objectUtils.coppySimpleType(requestType, requestTypeDTO, RequestTypeDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
                result.add(requestTypeDTO);
            }
        }
        return result;
    }
}
