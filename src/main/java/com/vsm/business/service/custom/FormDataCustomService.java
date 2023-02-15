package com.vsm.business.service.custom;

import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.repository.search.FormDataSearchRepository;
import com.vsm.business.service.dto.FormDataDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.dto.UserInfoDTO;
import com.vsm.business.service.mapper.FormDataMapper;
import org.hibernate.exception.GenericJDBCException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormDataCustomService {
    private final Logger log = LoggerFactory.getLogger(FormDataCustomService.class);

    private FormDataRepository formDataRepository;

    private FormDataSearchRepository formDataSearchRepository;

    private FormDataMapper formDataMapper;

    private RequestDataCustomService requestDataCustomService;

    private AttachmentFileRepository attachmentFileRepository;

    private RequestDataRepository requestDataRepository;

    private SignDataRepository signDataRepository;

    private ManageStampInfoRepository manageStampInfoRepository;

    public FormDataCustomService(FormDataRepository formDataRepository, FormDataSearchRepository formDataSearchRepository, FormDataMapper formDataMapper, RequestDataCustomService requestDataCustomService, RequestDataRepository requestDataRepository, AttachmentFileRepository attachmentFileRepository, SignDataRepository signDataRepository, ManageStampInfoRepository manageStampInfoRepository) {
        this.formDataRepository = formDataRepository;
        this.formDataSearchRepository = formDataSearchRepository;
        this.formDataMapper = formDataMapper;
        this.requestDataCustomService = requestDataCustomService;
        this.requestDataRepository = requestDataRepository;
        this.attachmentFileRepository = attachmentFileRepository;
        this.signDataRepository = signDataRepository;
        this.manageStampInfoRepository = manageStampInfoRepository;
    }

    public List<FormDataDTO> getAll() {
        log.debug("FormDataCustomService: getAll()");
        List<FormDataDTO> result = new ArrayList<>();
        try {
            List<FormData> formDatas = this.formDataRepository.findAll();
            for (FormData formData :
                formDatas) {
                FormDataDTO formDataDTO = formDataMapper.toDto(formData);
                result.add(formDataDTO);
            }
        }catch (Exception e){
            log.error("FormDataCustomService: getAll() {}", e);
        }
        log.debug("FormDataCustomService: getAll() {}", result);
        return result;
    }

    public List<FormDataDTO> deleteAll(List<FormDataDTO> formDataDTO) {
        log.debug("FormDataCustomService: deleteAll({})", formDataDTO);
        List<Long> ids = formDataDTO.stream().map(FormDataDTO::getId).collect(Collectors.toList());
        this.formDataRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            formDataRepository.deleteById(id);
            formDataSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("FormDataCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<FormDataDTO> getAllByReqDataId(Long requestDataId, Boolean ignoreFiled){
        if(ignoreFiled){
            List<FormDataDTO> listFormData = this.formDataRepository.findAllByRequestDataId(requestDataId).stream().map(ele -> {
                return this.convertToDTO(ele);
            }).collect(Collectors.toList());
            return listFormData;
        }else{
            return formDataRepository.findAllByRequestDataId(requestDataId).stream().map(formDataMapper::toDto).collect(Collectors.toList());
        }
    }


    public FormDataDTO customSave(FormDataDTO formDataDTO, Boolean fillIn) {
        log.debug("Request to save FormData : {}", formDataDTO);
        FormData formData = formDataMapper.toEntity(formDataDTO);
        formData = formDataRepository.save(formData);
        FormDataDTO result = formDataMapper.toDto(formData);
        try{
//            formDataSearchRepository.save(formData);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }

        try {

                // bind dữ liệu từ form vào file
            if(fillIn != null && fillIn == true){
            try {
                // bind dữ liệu từ form vào file
//            FormData formData = this.formDataRepository.findById(formDataDTO.getId()).get();
//            RequestData requestData = this.requestDataRepository.findById(formDataDTO.getRequestData().getId()).get();
//            List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
//            String mappingInfo = requestData.getRequest().getMappingInfo();
//            JSONObject jsonObject = new JSONObject(mappingInfo);
//            if(attachmentFileList != null && !attachmentFileList.isEmpty()){
//                for(AttachmentFile attachmentFile : attachmentFileList){
//                    try{
//                        JSONArray jsonMappingFileArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
//                        this.requestDataCustomService.writeDataToFile(attachmentFile, formData, jsonMappingFileArray);
//                    }catch (Exception e){
//                        log.info("{}", e);
//                    }
//                 }
//            }
                RequestData requestData = this.requestDataRepository.findById(formData.getRequestData().getId()).get();
                List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
                List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestData.getId());
                List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestData.getId());
                String mappingInfo = requestData.getRequest().getMappingInfo();
                JSONObject jsonObject = new JSONObject(mappingInfo);
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        //for(AttachmentFile attachmentFileTemplate : attachmentFile.getTemplateForm().getAttachmentFiles()) {
                        if(attachmentFile.getTemplateForm() == null) continue;
                        for(AttachmentFile attachmentFileTemplate :  this.attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(attachmentFile.getTemplateForm().getId(), null)) {       // lấy file biểu mãu tương ứng để binding
                            try {
                                if (attachmentFileTemplate.getRequestData() == null && attachmentFile.getFileExtension() != null && attachmentFile.getFileExtension().equals(attachmentFileTemplate.getFileExtension())) {
                                    JSONArray jsonMappingFileArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
                                    this.requestDataCustomService.writeDataToFile(attachmentFileTemplate, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                                }
                            } catch (Exception e) {
                                log.error("{}", e);
                            }
                        }
                    }
                }

            }catch (Exception e){
                log.error("{}", e);
            }
        }

        }catch (Exception e){
            log.error("{}", e);
        }
        return result;
    }

    public boolean saveDataOfForm(FormDataDTO formDataDTO, Boolean fillIn, Boolean useTemplate){
        if(formDataDTO.getId() == null) return false;
        FormData formData = this.formDataRepository.findById(formDataDTO.getId()).get();
        formData.setObjectModel(formDataDTO.getObjectModel());
        this.formDataRepository.save(formData);

        if(fillIn != null && fillIn == true){
            try {

                // bind dữ liệu từ form vào file
//            FormData formData = this.formDataRepository.findById(formDataDTO.getId()).get();
//            RequestData requestData = this.requestDataRepository.findById(formDataDTO.getRequestData().getId()).get();
//            List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
//            String mappingInfo = requestData.getRequest().getMappingInfo();
//            JSONObject jsonObject = new JSONObject(mappingInfo);
//            if(attachmentFileList != null && !attachmentFileList.isEmpty()){
//                for(AttachmentFile attachmentFile : attachmentFileList){
//                    try{
//                        JSONArray jsonMappingFileArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
//                        this.requestDataCustomService.writeDataToFile(attachmentFile, formData, jsonMappingFileArray);
//                    }catch (Exception e){
//                        log.info("{}", e);
//                    }
//                 }
//            }

                RequestData requestData = this.requestDataRepository.findById(formData.getRequestData().getId()).get();
                List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
                List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestData.getId());
                List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestData.getId());
                String mappingInfo = requestData.getRequest().getMappingInfo();
                JSONObject jsonObject = new JSONObject(mappingInfo);
                if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                    for(AttachmentFile attachmentFile : attachmentFileList){
                        //for(AttachmentFile attachmentFileTemplate : attachmentFile.getTemplateForm().getAttachmentFiles()) {
                        if(attachmentFile.getTemplateForm() == null) continue;
                        for(AttachmentFile attachmentFileTemplate :  this.attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(attachmentFile.getTemplateForm().getId(), null)) {       // lấy file biểu mãu tương ứng để binding
                            try {
                                if (attachmentFileTemplate.getRequestData() == null && attachmentFile.getFileExtension() != null && attachmentFile.getFileExtension().equals(attachmentFileTemplate.getFileExtension())) {
                                    JSONArray jsonMappingFileArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
                                    if(useTemplate == true)     // TH sử dụng file template để fill in
                                        this.requestDataCustomService.writeDataToFile(attachmentFileTemplate, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                                    else                        // Th sử dụng file của phiếu để fill in
                                        this.requestDataCustomService.writeDataToFile(attachmentFile, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                                }
                            } catch (Exception e) {
                                log.error("{}", e);
                            }
                        }
                    }
                }

            }catch (Exception e){
                log.error("{}", e);
            }
        }

        return true;
    }

    private FormDataDTO convertToDTO(FormData formData){
        if(formData == null) return null;
        FormDataDTO result = this.formDataMapper.toDto(formData);
        RequestDataDTO requestDataDTO = new RequestDataDTO();
        try {requestDataDTO.setId(formData.getRequestData().getId());}catch (Exception e){};
        result.setRequestData(requestDataDTO);

        UserInfoDTO created = new UserInfoDTO();
        try {created.setId(formData.getCreated().getId());}catch (Exception e){};
        result.setCreated(created);

        UserInfoDTO modified = new UserInfoDTO();
        try {modified.setId(formData.getModified().getId());}catch (Exception e){};
        result.setCreated(modified);

        return result;
    }
}
