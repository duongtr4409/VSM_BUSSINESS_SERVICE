package com.vsm.business.service.custom;

import com.vsm.business.domain.ProcessInfo;
import com.vsm.business.domain.Request;
import com.vsm.business.domain.RequestType;
import com.vsm.business.repository.ProcessInfoRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.RequestTypeRepository;
import com.vsm.business.repository.search.ProcessInfoSearchRepository;
import com.vsm.business.service.dto.ProcessInfoDTO;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestTypeDTO;
import com.vsm.business.service.mapper.ProcessInfoMapper;
import com.vsm.business.utils.ConditionUtils;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessInfoCustomService {
    private final Logger log = LoggerFactory.getLogger(ProcessInfoCustomService.class);
    private ProcessInfoRepository processInfoRepository;

    private ProcessInfoSearchRepository processInfoSearchRepository;

    private ProcessInfoMapper processInfoMapper;

    private RequestRepository requestRepository;

    private RequestTypeRepository requestTypeRepository;

    private ObjectUtils objectUtils;

    @Autowired
    private ConditionUtils conditionUtils;

    public ProcessInfoCustomService(ProcessInfoRepository processInfoRepository, ProcessInfoSearchRepository processInfoSearchRepository, ProcessInfoMapper processInfoMapper, RequestRepository requestRepositor, RequestTypeRepository requestTypeRepository, ObjectUtils objectUtils) {
        this.processInfoRepository = processInfoRepository;
        this.processInfoSearchRepository = processInfoSearchRepository;
        this.processInfoMapper = processInfoMapper;
        this.requestRepository = requestRepositor;
        this.requestTypeRepository = requestTypeRepository;
        this.objectUtils = objectUtils;
    }

    public List<ProcessInfoDTO> getAll(Boolean ignoreField) {
//        log.debug("ProcessInfoCustomService: getAll()");
//        List<ProcessInfoDTO> result = processInfoRepository.findAll().stream().map(processInfoMapper::toDto).collect(Collectors.toList());
//        log.info("ProcessInfoCustomService: getAll() {}", result);
//        return result;
        log.debug("ProcessInfoCustomService: getAll()");
        List<ProcessInfoDTO> result = new ArrayList<>();
        if (ignoreField) {
            result = processInfoRepository.findAll().stream().map(ele -> {
                ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();
                BeanUtils.copyProperties(ele, processInfoDTO);
                return processInfoDTO;
            }).collect(Collectors.toList());
        } else {
            result = processInfoRepository.findAll().stream().map(ele -> {
                ProcessInfoDTO processInfoDTO = this.processInfoMapper.toDto(ele);
                processInfoDTO.setRequestDTOS(ele.getRequests().stream().map(ele1 -> {
                    RequestDTO requestDTO = new RequestDTO();
                    BeanUtils.copyProperties(ele1, requestDTO);
                    return requestDTO;
                }).collect(Collectors.toList()));
                return processInfoDTO;
            }).collect(Collectors.toList());
        }

        log.debug("ProcessInfoCustomService: getAll() {}", result);
        return result;
    }

    public Page<ProcessInfoDTO> getPage(Boolean ignoreField, Pageable pageable) {
//        log.debug("ProcessInfoCustomService: getAll()");
//        List<ProcessInfoDTO> result = processInfoRepository.findAll().stream().map(processInfoMapper::toDto).collect(Collectors.toList());
//        log.info("ProcessInfoCustomService: getAll() {}", result);
//        return result;
        log.debug("ProcessInfoCustomService: getAll()");
        List<ProcessInfoDTO> result = new ArrayList<>();
        if (ignoreField) {
            result = processInfoRepository.findAllByIsDeleteIsNot(pageable, true).stream().map(ele -> {
                ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();
                BeanUtils.copyProperties(ele, processInfoDTO);
                return processInfoDTO;
            }).collect(Collectors.toList());
        } else {
            result = processInfoRepository.findAllByIsDeleteIsNot(pageable, true).stream().map(ele -> {
                ProcessInfoDTO processInfoDTO = this.processInfoMapper.toDto(ele);
                processInfoDTO.setRequestDTOS(ele.getRequests().stream().map(ele1 -> {
                    RequestDTO requestDTO = new RequestDTO();
                    BeanUtils.copyProperties(ele1, requestDTO);
                    return requestDTO;
                }).collect(Collectors.toList()));
                return processInfoDTO;
            }).collect(Collectors.toList());
        }
        log.debug("ProcessInfoCustomService: getAll() {}", result);
        return new PageImpl<>(result);
    }

    public Optional<ProcessInfoDTO> getOne(Long id) {
        log.debug("ProcessInfoCustomService: getOne({})", id);
        ProcessInfo processInfo = this.processInfoRepository.findById(id).get();
        ProcessInfoDTO result = this.processInfoMapper.toDto(processInfo);
        result.setRequestDTOS(processInfo.getRequests().stream().map(ele -> {
            RequestDTO requestDTO = new RequestDTO();
            //BeanUtils.copyProperties(ele, requestDTO);
            requestDTO.setId(ele.getId());
            requestDTO.setRequestName(ele.getRequestName());
            requestDTO.setRequestCode(ele.getRequestCode());
            return requestDTO;
        }).collect(Collectors.toList()));
        return Optional.of(result);
    }

    public List<ProcessInfoDTO> deleteAll(List<ProcessInfoDTO> processInfoDTOS) {
        log.debug("ProcessInfoCustomService: deleteAll({})", processInfoDTOS);
        this.processInfoRepository.deleteAllById(processInfoDTOS.stream().map(ProcessInfoDTO::getId).collect(Collectors.toList()));
        return this.getAll(false);
    }

    public boolean delete(Long id) {
        try {
            processInfoRepository.deleteById(id);
            processInfoSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ProcessInfoCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ProcessInfoDTO> saveAll(List<ProcessInfoDTO> processInfoDTOList) {
        List<ProcessInfoDTO> result = processInfoRepository.saveAll(processInfoDTOList.stream().map(processInfoMapper::toEntity).collect(Collectors.toList())).stream().map(processInfoMapper::toDto).collect(Collectors.toList());
        log.debug("ProcessInfoCustomService saveAll({}) {}", processInfoDTOList, result);
        return result;
    }

    //    @Transactional
    public ProcessInfoDTO customSave(ProcessInfoDTO processInfoDTO) throws Exception {

        if (processInfoDTO.getIsDelete())
            if (checkExist(processInfoDTO)) throw new Exception("Exist Request");

        if (processInfoDTO.getId() != null) {         // TH là update phải kiểm tra xem có xóa đi Request hay không
            ProcessInfo processInfoOld = this.processInfoRepository.findById(processInfoDTO.getId()).get();         // lấy ra ProcessInfo hiện tại trong DB
            // lấy ra danh sách nhưng request bị loại khỏi ProcessInfo
            if (processInfoOld.getRequests() != null && !processInfoOld.getRequests().isEmpty()) {
                List<Request> listRequestRemoveProcessInfo = processInfoOld.getRequests().stream().filter(ele -> {
                    return !processInfoDTO.getRequestDTOS().stream().anyMatch(ele1 -> ele.getId().equals(ele1.getId()));
                }).collect(Collectors.toList());

                if (listRequestRemoveProcessInfo != null && !listRequestRemoveProcessInfo.isEmpty()) {
                    List<Request> listRequestUpdate = new ArrayList<>();
                    listRequestRemoveProcessInfo.forEach(ele -> {
                        Set<ProcessInfo> processInfoSet = ele.getProcessInfos();
                        if (processInfoSet != null) {
                            processInfoSet.remove(processInfoOld);
                            ele.setProcessInfos(processInfoSet);
                            listRequestUpdate.add(ele);
                        }
                    });
                    this.requestRepository.saveAll(listRequestUpdate);
                }
            }
        }


        ProcessInfo processInfo = processInfoMapper.toEntity(processInfoDTO);


        processInfo = processInfoRepository.save(processInfo);

        // lưu danh sách requestType tương ứng \\
        List<Request> requestList = new ArrayList<>();
        ProcessInfo finalProcessInfo = processInfo;
        processInfoDTO.getRequestDTOS().forEach(ele -> {
            Request requestType = this.requestRepository.findById(ele.getId()).get();
            Set<ProcessInfo> processInfoSet = requestType.getProcessInfos() == null ? new HashSet<>() : requestType.getProcessInfos();
            processInfoSet.add(finalProcessInfo);
            requestType.setProcessInfos(processInfoSet);
            requestList.add(requestType);
        });
        this.requestRepository.saveAll(requestList);


        ProcessInfoDTO result = processInfoMapper.toDto(processInfo);
        processInfo = this.processInfoRepository.findById(processInfo.getId()).get();
        result.setRequestDTOS(processInfo.getRequests().stream().map(ele -> {
            RequestDTO requestDTO = new RequestDTO();
            BeanUtils.copyProperties(ele, requestDTO);
            return requestDTO;
        }).collect(Collectors.toList()));

        try {
            //processInfoSearchRepository.save(processInfo);
        } catch (StackOverflowError e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    public List<ProcessInfoDTO> customSaveAll(List<ProcessInfoDTO> processInfoDTOList) throws Exception {
        if (processInfoDTOList.stream().anyMatch(ele -> ele.getIsDelete() == true)) {
            Boolean isExist = false;
            for (ProcessInfoDTO processInfoDTO : processInfoDTOList) {
                isExist = checkExist(processInfoDTO);
                if (isExist) throw new Exception("Exist Request");
            }
        }
        List<ProcessInfoDTO> result = processInfoRepository.saveAll(processInfoDTOList.stream().map(processInfoMapper::toEntity).collect(Collectors.toList())).stream().map(processInfoMapper::toDto).collect(Collectors.toList());
        log.debug("ProcessInfoCustomService saveAll({}) {}", processInfoDTOList, result);
        return result;
    }

    public List<ProcessInfoDTO> getAllByRequestIdWithRole(Long requestId, Long userId, boolean ignoreField){
        List<ProcessInfoDTO> result = new ArrayList<>();
        if(ignoreField){
            result = processInfoRepository.getAllProcessByRequestWithRole(requestId, userId).stream().map(ele -> {
                ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();
                try {
                    BeanUtils.copyProperties(ele, processInfoDTO);
                }catch (Exception e){
                    log.error("{}", e);
                }
                return processInfoDTO;
            }).collect(Collectors.toList());
        }else{
            result = processInfoRepository.getAllProcessByRequestWithRole(requestId, userId).stream().map(ele -> processInfoMapper.toDto(ele)).collect(Collectors.toList());
        }
        log.debug("ProcessInfoCustomService getAllByRequestIdWithRole(requestId: {}, userId: {}, {}): ignoreField: {}", requestId, userId, ignoreField, result);
        return result;
    }
	
    private Boolean checkExist(ProcessInfoDTO processInfoDTO) {
        ProcessInfo processInfo = this.processInfoRepository.findById(processInfoDTO.getId()).orElse(null);
        Boolean result = processInfo.getRequests().stream().anyMatch(ele -> !this.conditionUtils.checkTrueFalse(ele.getIsDelete()));
        return result;
//        ProcessInfo processInfo = this.processInfoRepository.findById(processInfoDTO.getId()).orElse(null);
//        boolean result = processInfo.getRequestTypes().stream().anyMatch(ele -> {
//            return ele.getRequests().stream().anyMatch(ele1 -> !this.conditionUtils.checkDelete(ele.getIsDelete()));
//        });
//        return result;
    }

    public List<ProcessInfoDTO> getAllIgnoreField(){
        List<ProcessInfoDTO> result = this.processInfoRepository.findAll().stream().map(ele -> {
            ProcessInfoDTO processInfoDTO  = new ProcessInfoDTO();
            if(ele != null){
                try {processInfoDTO = this.objectUtils.coppySimpleType(ele, processInfoDTO, ProcessInfoDTO.class);} catch (IllegalAccessException e) {log.error("{}", e);}
            }
            return processInfoDTO;
        }).collect(Collectors.toList());
        return result;
    }
}
