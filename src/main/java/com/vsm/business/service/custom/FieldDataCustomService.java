package com.vsm.business.service.custom;

import com.microsoft.graph.models.DriveItem;
import com.vsm.business.common.Graph.GraphService;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.repository.search.FieldDataSearchRepository;
import com.vsm.business.service.dto.AttachmentFileDTO;
import com.vsm.business.service.dto.FieldDataDTO;
import com.vsm.business.service.dto.FormDataDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.mapper.FieldDataMapper;
import com.vsm.business.service.mapper.FieldMapper;
import com.vsm.business.utils.*;
import org.hibernate.exception.GenericJDBCException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FieldDataCustomService {
    private final Logger log = LoggerFactory.getLogger(FieldDataCustomService.class);

    private FieldDataRepository fieldDataRepository;

    private FieldDataSearchRepository fieldDataSearchRepository;

    private FieldDataMapper fieldDataMapper;

    private RequestDataRepository requestDataRepository;

    private AttachmentFileRepository attachmentFileRepository;

    private RequestDataCustomService requestDataCustomService;

    private ConditionUtils conditionUtils;

    @Autowired
    private WordXmlUtils wordXmlUtils;

    @Autowired
    private ExcelUtils excelUtils;

    @Autowired
    private GenerateAttachNameUtils generateAttachNameUtils;

    @Autowired
    private GraphService gapGraphService;

    @Autowired
    private GenerateCodeUtils generateCodeUtils;

    @Value("${system.file.customer-file-code:CUSTOMER}")
    public String CUSTOMER_FILE_CODE;

    @Value("${update-file-name:TRUE}")
    public String UPDATE_FILE_NAME;

    private SignDataRepository signDataRepository;

    private ManageStampInfoRepository manageStampInfoRepository;

    public FieldDataCustomService(FieldDataRepository fieldDataRepository,
                                  FieldDataSearchRepository fieldDataSearchRepository,
                                  FieldDataMapper fieldDataMapper,
                                  RequestDataRepository requestDataRepository,
                                  AttachmentFileRepository attachmentFileRepository,
                                  RequestDataCustomService requestDataCustomService,
                                  SignDataRepository signDataRepository,
                                  ManageStampInfoRepository manageStampInfoRepository,
                                  ConditionUtils conditionUtils,
                                  GenerateAttachNameUtils generateAttachNameUtils) {
        this.fieldDataRepository = fieldDataRepository;
        this.fieldDataSearchRepository = fieldDataSearchRepository;
        this.fieldDataMapper = fieldDataMapper;
        this.requestDataRepository = requestDataRepository;
        this.attachmentFileRepository = attachmentFileRepository;
        this.requestDataCustomService = requestDataCustomService;
        this.signDataRepository = signDataRepository;
        this.manageStampInfoRepository = manageStampInfoRepository;
        this.conditionUtils = conditionUtils;
        this.generateAttachNameUtils = generateAttachNameUtils;
    }

    public List<FieldDataDTO> getAll() {
        log.debug("FieldDataCustomService: getAll()");
        List<FieldDataDTO> result = new ArrayList<>();
        try {
            List<FieldData> fieldDatas = this.fieldDataRepository.findAll();
            for (FieldData fieldData :
                fieldDatas) {
                FieldDataDTO FieldDataDTO = fieldDataMapper.toDto(fieldData);
                result.add(FieldDataDTO);
            }
        }catch (Exception e){
            log.error("FieldDataCustomService: getAll() {}", e);
        }
        log.debug("FieldDataCustomService: getAll() {}", result);
        return result;
    }

    public List<FieldDataDTO> deleteAll(List<FieldDataDTO> FieldDataDTOS) {
        log.debug("FieldDataCustomService: deleteAll({})", FieldDataDTOS);
        List<Long> ids = FieldDataDTOS.stream().map(FieldDataDTO::getId).collect(Collectors.toList());
        this.fieldDataRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            fieldDataRepository.deleteById(id);
            fieldDataSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("FieldDataCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public Map<String, Object> getAllByReqDataId(Long requestDataId, Pageable pageable, Boolean ignoreField){
        Map<String, Object> result = new HashMap<>();
        List<FieldData> fieldData = new ArrayList<>();
        try {
            fieldData = this.fieldDataRepository.findAllByRequestDataId(requestDataId, getNewPageable(pageable)).stream().collect(Collectors.toList());
        }catch (Exception e){
            log.error("{}", e);
            fieldData = this.fieldDataRepository.findAllByRequestDataId(requestDataId, pageable).stream().collect(Collectors.toList());
        }
        List<FieldDataDTO> data = new ArrayList<>();
        if(ignoreField){
            data = fieldData.stream().map(ele -> convertToDTO(ele)).collect(Collectors.toList());
        }else{
            data = fieldData.stream().map(ele -> this.fieldDataMapper.toDto(ele)).collect(Collectors.toList());
        }
        result.put("data", data);
        Long total = this.fieldDataRepository.findAllByRequestDataId(requestDataId).stream().count();
        result.put("total", total);
        return result;
    }

    public Map<String, Object> getAllByFormDataId(Long formDataId, Pageable pageable, Boolean ignoreField){
        Map<String, Object> result = new HashMap<>();
        List<FieldData> fieldData = new ArrayList<>();
        try {
            fieldData = this.fieldDataRepository.findAllByFormDataId(formDataId, getNewPageable(pageable)).stream().collect(Collectors.toList());
        }catch (Exception e){
            log.error("{}", e);
            fieldData = this.fieldDataRepository.findAllByFormDataId(formDataId, pageable).stream().collect(Collectors.toList());
        }
        List<FieldDataDTO> data = new ArrayList<>();
        if(ignoreField){
            data = fieldData.stream().map(ele -> convertToDTO(ele)).collect(Collectors.toList());
        }else{
            data = fieldData.stream().map(ele -> this.fieldDataMapper.toDto(ele)).collect(Collectors.toList());
        }
        result.put("data", data);
        Long total = this.fieldDataRepository.findAllByFormDataId(formDataId).stream().count();
        result.put("total", total);
        return result;
    }

    public List<FieldDataDTO> customCreate(List<FieldDataDTO> fieldDataDTOList){
        log.debug("Request to save FieldData : {}", fieldDataDTOList);
        if(fieldDataDTOList == null && fieldDataDTOList.isEmpty()) return fieldDataDTOList;
        List<FieldData> fieldDataListSave = fieldDataDTOList.stream().map(ele -> this.fieldDataMapper.toEntity(ele)).collect(Collectors.toList());
        fieldDataListSave = this.fieldDataRepository.saveAll(fieldDataListSave);
        try{
//            fieldDataSearchRepository.saveAll(fieldDataListSave);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException e){

        }
        return convertToDTO(fieldDataListSave);
    }

    @Value("${request-data.contract-expire-field-name:ngay_het_han}")
    public String CONTRACT_EXPIRE_FIELD_NAME;

    private DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").parseDefaulting(ChronoField.NANO_OF_DAY, 0L).toFormatter().withZone(ZoneId.of("UTC"));

    @Transactional
    public List<FieldDataDTO> customSave(List<FieldDataDTO> fieldDataDTOS, Boolean fillIn, Boolean useTemplate, Long requestDataId){
        if(fieldDataDTOS == null || fieldDataDTOS.isEmpty()) return fieldDataDTOS;
        List<FieldData> listUpdate = new ArrayList<>();
        Instant contractExpireTime = null;
        FormData formData = null;
        RequestData requestData = null;

        for(FieldDataDTO fieldDataDTO : fieldDataDTOS){
            FieldData fieldData = this.fieldDataRepository.findById(fieldDataDTO.getId()).get();
            fieldData.setObjectModel(fieldDataDTO.getObjectModel());
            listUpdate.add(fieldData);

            if(CONTRACT_EXPIRE_FIELD_NAME.equals(fieldData.getFieldDataCode())){        // nếu là trường được đánh dấu là ngày hết hạn hợp đồng -> update lại hợp giá trị của trường ContractExpireTime trong RequestData
                try {
                    contractExpireTime = DATE_TIME_FORMATTER.parse(fieldData.getObjectModel(), Instant::from);
                    requestDataId = fieldData.getRequestData().getId();
                }catch (Exception e){log.error("{}", e);}
            }
        }
        listUpdate = this.fieldDataRepository.saveAll(listUpdate);

                    // cập nhật ngày hết hạn hợp đồng(ContractExpireTime) cho RequestData
        if(contractExpireTime != null && requestDataId != null){
            try {
                requestData = this.requestDataRepository.findById(requestDataId).get();
                formData = requestData.getFormData().stream().collect(Collectors.toList()).get(0);
                requestData.setContractExpireTime(contractExpireTime);
                requestData = this.requestDataRepository.save(requestData);
            }catch (Exception e){log.error("{}", e);}
        }

        if(fillIn != null && fillIn == true && requestDataId != null){
            List<SignData> signDataList = signDataRepository.findAllByRequestDataId(requestDataId);
            List<ManageStampInfo> manageStampInfoList = manageStampInfoRepository.findAllByRequestDataId(requestDataId);
            this.fillIntoFile(listUpdate, formData, useTemplate, requestDataId, signDataList, manageStampInfoList);

            // clear memory
            try {
                formData = null;
                signDataList = null;
                manageStampInfoList = null;
            }catch (Exception e){log.error("{}", e);}

        }

        // cập nhật lại tên file tài liệu chính \\
        if(!"FALSE".equalsIgnoreCase(this.UPDATE_FILE_NAME)){
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(requestDataId).stream().filter(ele ->
                !this.conditionUtils.checkTrueFalse(ele.getIsFolder())
                    && !this.conditionUtils.checkDelete(ele.getIsDelete())
                    && !this.CUSTOMER_FILE_CODE.equals(ele.getTennantCode())
                    && ele.getTemplateForm() != null
            ).sorted((ele1, ele2) -> (int)(ele1.getId() - ele2.getId())).collect(Collectors.toList());
            for(int i=0; i<attachmentFileList.size(); i++){
                try {
                    if(requestData == null) requestData = this.requestDataRepository.findById(requestDataId).get();
//                    String newFileName = this.generateAttachNameUtils.generateAttachFileName(requestData);
                    String newFileName = generateCodeUtils.v2_generateFileName(i + 1, attachmentFileList.get(i).getFileExtension(), requestData.getRuleGenerateAttachName(), requestData.getRequest(), requestData, listUpdate);
                    if(!attachmentFileList.get(i).equals(newFileName)){ // nếu tên mới khác với tên cũ -> update lại tên file
                        DriveItem driveItem = this.gapGraphService.reNameItem(attachmentFileList.get(i).getItemId365(), newFileName);
                        newFileName = driveItem.name;
                        attachmentFileList.get(i).setFileName(newFileName);
                        this.attachmentFileRepository.save(attachmentFileList.get(i));
                    }
                }catch (Exception e){log.error("{}", e);}
            }

            // clear memory
            try {
                attachmentFileList = null;
            }catch (Exception e){log.error("{}", e);}

        }

        return this.convertToDTO(listUpdate);
    }




    @Autowired
    private FileUtils fileUtils;
    @Value("${system.folder.TEMP_FOLDER:./temp/}")

    public String FOLDER_TEMP;

    public String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    /**
     * Hàm thực hiện fill in dữ liệu vào file tài liệu chính
     * @param fieldDataList : danh sách fieldData (trường dữ liệu)
     * @param requestDataId : id của phiếu cần fill in
     */
    public void fillIntoFile(List<FieldData> fieldDataList, FormData formData, Boolean useTemplate, Long requestDataId, List<SignData> signDataList, List<ManageStampInfo> manageStampInfoList){
        try {
            RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
            formData = formData == null ? requestData.getFormData().stream().collect(Collectors.toList()).get(0) : formData;
            List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
            String mappingInfo = requestData.getRequest().getMappingInfo();
            JSONObject jsonObject = new JSONObject(mappingInfo);
            if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                for(AttachmentFile attachmentFile : attachmentFileList){
                    if(attachmentFile.getTemplateForm() == null) continue;
                    for(AttachmentFile attachmentFileTemplate : this.attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(attachmentFile.getTemplateForm().getId(), null)){     // lấy file biểu mãu tương ứng để binding
                        try {
                            if (attachmentFileTemplate.getRequestData() == null && attachmentFile.getFileExtension() != null && attachmentFile.getFileExtension().equals(attachmentFileTemplate.getFileExtension())) {
                                JSONArray jsonMappingFileArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
                                if (useTemplate != null && useTemplate) {     // TH sử dụng file biểu mẫu để fill in
                                    this.writeDataToFile(attachmentFileTemplate, fieldDataList, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                                } else {                                      // TH sử dụng file của phiếu để fill in
                                    this.writeDataToFile(attachmentFile, fieldDataList, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                                }

                                // clear memory
                                try {
                                    jsonMappingFileArray = null;
                                }catch (Exception e){log.error("{}",e);}

                            }
                        }catch (Exception e){ log.error("{}", e);}
                    }
                }
            }

            // clear memory
            try {
                attachmentFileList = null;
            }catch (Exception e){log.error("{}", e);}

        }catch (Exception e){
            log.error("{}", e);
        }
    }

    /**
     *  Hàm thực hiện fill dữ liệu vào tài liệu đính kèm (1 file)
     * @param attachmentFileId  : id tài liệu cần fill in
     * @param useTemplate       : có sử dụng biểu mẫu hay không
     * @param requestDataId     : id của phiếu (để lấy các dữ liệu liên quan)
     */
    public void fillIntoFile(Long attachmentFileId, Boolean useTemplate, Long requestDataId){
        try {
            RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
            List<FieldData> fieldDataList = requestData.getFieldData().stream().collect(Collectors.toList());
            List<SignData> signDataList = requestData.getSignData().stream().collect(Collectors.toList());
            List<ManageStampInfo> manageStampInfoList = requestData.getManageStampInfos().stream().collect(Collectors.toList());
            FormData formData = requestData.getFormData().stream().filter(ele -> ele.getId() != null).findFirst().get();

            List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().collect(Collectors.toList());
            String mappingInfo = requestData.getRequest().getMappingInfo();
            JSONObject jsonObject = new JSONObject(mappingInfo);
            AttachmentFile attachmentFile = this.attachmentFileRepository.findById(attachmentFileId).get();
            if(attachmentFile.getTemplateForm() == null) throw new RuntimeException("Not is Primary Attachment");
            for(AttachmentFile attachmentFileTemplate : this.attachmentFileRepository.findAllByTemplateFormIdAndRequestDataId(attachmentFile.getTemplateForm().getId(), null)){     // lấy file biểu mãu tương ứng để binding
                try {
                    if (attachmentFileTemplate.getRequestData() == null && attachmentFile.getFileExtension() != null && attachmentFile.getFileExtension().equals(attachmentFileTemplate.getFileExtension())) {
                        JSONArray jsonMappingFileArray = jsonObject.getJSONArray(String.valueOf(attachmentFile.getTemplateForm().getId()));
                        if (useTemplate != null && useTemplate) {     // TH sử dụng file biểu mẫu để fill in
                            this.writeDataToFile(attachmentFileTemplate, fieldDataList, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                        } else {                                      // TH sử dụng file của phiếu để fill in
                            this.writeDataToFile(attachmentFile, fieldDataList, formData, jsonMappingFileArray, attachmentFile.getItemId365(), requestData, signDataList, manageStampInfoList);
                        }

                        // clear memory
                        try {
                            jsonMappingFileArray = null;
                        }catch (Exception e){log.error("{}", e);}

                    }
                }catch (Exception e){ log.error("{}", e);}
            }

            // clear memory
            try {
                attachmentFileList = null;
                fieldDataList = null;
                jsonObject = null;
                signDataList = null;
                manageStampInfoList = null;
            }catch (Exception e){log.error("{}", e);}

        }catch (Exception e){
            log.error("{}", e);
        }
    }

    public void writeDataToFile(AttachmentFile attachmentFile, List<FieldData> fieldDataList, FormData formData, JSONArray jsonArray, String itemId365Update, RequestData requestData, List<SignData> signDataList, List<ManageStampInfo> manageStampInfoList) throws IOException {
        String timeString = String.valueOf(System.currentTimeMillis());
        String pathTemp = FOLDER_TEMP + timeString;
        pathTemp = pathTemp.replaceAll("//", this.PATH_SEPARATOR);
        File folderTemp = new File(pathTemp);
        if (!folderTemp.exists()) folderTemp.mkdirs();
        File file = null;
        FileInputStream fileInputStream = null;
        try {

            file = this.requestDataCustomService.cloneFile365ToLocal(attachmentFile, folderTemp);
            Map<String, Object> props = this.getDataFromForm(fieldDataList, formData, attachmentFile.getTemplateForm(), jsonArray, requestData, signDataList, manageStampInfoList);
            fileInputStream = new FileInputStream(file);
            OutputStream outputStream = null;
            switch (attachmentFile.getFileExtension()){
                case "docx":
                    outputStream = this.wordXmlUtils.writeTextToFile(props, fileInputStream);
                    break;
                case "xlsx":
                    outputStream = this.excelUtils.writeTextToFile(props, fileInputStream);
                    break;
                default:
                    log.info("no support type: {}", attachmentFile.getFileExtension());
                    return;
            }
            if (outputStream != null) {
                byte[] fileContent = ((ByteArrayOutputStream) outputStream).toByteArray();
                if (fileContent == null || fileContent.length <= 0) return;
                DriveItem driveItem = this.gapGraphService.updateFile(itemId365Update, fileContent);
                attachmentFile.setFileSize(driveItem.size);
                this.attachmentFileRepository.save(attachmentFile);
                try {outputStream.close();}catch (Exception ex){log.error("{}", ex);}
            }
        }catch (Exception e){
            log.error("{}", e);
        }finally {
            fileInputStream.close();
            if(file != null){
                file.delete();
            }
            folderTemp.delete();
        }

    }

    @Autowired
    private Environment environment;
    /**
     * Hàm thực hiện lấy dữ liệu của form đẩy vào mapp để chuẩn bị fill in
     * @param fieldDataList : dữ liệu form dạng list (danh sách trường dữ liệu)
     * @param formData      : formdata lưu cấu trúc form (trong trường objectSchema)
     * @param templateForm  : thông tin biểu mẫu (lấy thông tin mapping biểu mẫu)
     * @param jsonArray     : thông tin mapping trong loại yêu cầu (thông tin mapping trong request)
     * @param requestData   : thông tin phiếu (lấy mã hợp đồng)
     * @return: dữ liệu cần fill in (dạng MAP)
     */
    private Map<String, Object> getDataFromForm(List<FieldData> fieldDataList, FormData formData, TemplateForm templateForm, JSONArray jsonArray, RequestData requestData, List<SignData> signDataList, List<ManageStampInfo> manageStampInfoList) {
        Map<String, Object> result = new HashMap<>();

        String REQ_DATA_CODE = environment.getProperty("system.fill-in.MA_HOP_DONG", "${{req_data_code}}");
        REQ_DATA_CODE = REQ_DATA_CODE.replace("\\", "");
        result.put(REQ_DATA_CODE, requestData != null ? requestData.getRequestDataCode() : "");
        String BRAND_LIST_CODE = environment.getProperty("system.fill-in.KY_HIEU_CHUOI", "${{brand_list_code}}");
        String BRAND_LIST_DELIMITER = environment.getProperty("system.fill-in.NGAN_CACH_CHUOI", System.lineSeparator());
        BRAND_LIST_CODE = BRAND_LIST_CODE.replace("\\", "");
        try{
            String brandList = Arrays.stream(Stream.concat(signDataList.stream().map(e->e.getSignName()), manageStampInfoList.stream().map(e->e.getName())).toArray(CharSequence[]::new)).collect(Collectors.joining(System.lineSeparator()));
            result.put(BRAND_LIST_CODE, brandList);
            log.info("FEATURE_FILL_SIGN_DATA: requestDataId={}, signDataList={}, brandList={}", requestData.getId(), signDataList, brandList);
        }catch (Exception e){
            log.error("FEATURE_FILL_SIGN_DATA brandList: {}", e);
            result.put(BRAND_LIST_CODE, "");
        }
        JSONArray templateMapping = new JSONArray(templateForm.getMappingInfo());           // chuyển dữ liệu mapping sang dạng map
        Map<String, String> templateMappingData = new HashMap<>();
        for (int i = 0; i < templateMapping.length(); i++) {
            try {
                JSONObject ele = templateMapping.getJSONObject(i);
                templateMappingData.put(ele.getString("name"), ele.getString("position"));
                result.put(ele.getString("position"), "");
            } catch (Exception e) {
                log.error("{}", e);
            }
        }

        Map<String, Object> dataInfo = new HashMap<>();                                     // chuyển dữ liệu của form dang dangj map

        JSONObject jsonSchemaForm = null;
        JSONObject jsonPropertities = null;
        try {
            jsonSchemaForm = new JSONObject(formData.getObjectSchema());
            jsonPropertities = jsonSchemaForm.getJSONObject("properties");
        } catch (Exception e) {
            log.error("{}", e);
            jsonPropertities = this.getJSONPropertiesFromFieldData(fieldDataList);
        }

        for (FieldData fieldData : fieldDataList) {
            try {
                if ("truong_bang".equalsIgnoreCase(fieldData.getTennantCode())) {                     // Th nếu là dạng bảng
                    JSONObject temp = jsonPropertities.getJSONObject(fieldData.getFieldDataCode());
                    if (temp.has("Headers")) {
                        RequestDataCustomService.TableFieldDuowngTora tableFieldDuowngTora = new RequestDataCustomService.TableFieldDuowngTora();
                        JSONArray headers = new JSONArray(temp.getString("Headers"));
                        for (int i = 0; i < headers.length(); i++) {
                            tableFieldDuowngTora.getHeaders().add(headers.getJSONObject(i).toString());
                        }
                        JSONArray body = new JSONArray(fieldData.getObjectModel());
                        for (int i = 0; i < body.length(); i++) {
                            tableFieldDuowngTora.getBody().add(body.getJSONObject(i).toString());
                        }
                        result.put(fieldData.getFieldDataCode(), tableFieldDuowngTora);
                    }else if(temp.has("title") && temp.getJSONObject("title").has("Headers")){
                        RequestDataCustomService.TableFieldDuowngTora tableFieldDuowngTora = new RequestDataCustomService.TableFieldDuowngTora();
                        JSONArray headers = new JSONArray(temp.getJSONObject("title").getString("Headers"));
                        for (int i = 0; i < headers.length(); i++) {
                            tableFieldDuowngTora.getHeaders().add(headers.getJSONObject(i).toString());
                        }
                        JSONArray body = new JSONArray(fieldData.getObjectModel());
                        for (int i = 0; i < body.length(); i++) {
                            tableFieldDuowngTora.getBody().add(body.getJSONObject(i).toString());
                        }
                        result.put(fieldData.getFieldDataCode(), tableFieldDuowngTora);
                    }
                } else if ("truong_file".equalsIgnoreCase(fieldData.getTennantCode())) {               // Th nếu là dạng file
                    JSONObject temp = jsonPropertities.getJSONObject(fieldData.getFieldDataCode());
                    JSONObject jsonObject = new JSONObject(fieldData.getObjectModel());
                    if ((temp.has("contentMediaType") || (temp.has("title") && temp.getJSONObject("title").has("contentMediaType"))) && jsonObject.has("data")) {
                        String base64Data = jsonObject.getString("data");
                        byte[] base64Content = Base64.getDecoder().decode(base64Data.getBytes(StandardCharsets.UTF_8));
                        dataInfo.put(fieldData.getFieldDataName(), base64Content);
                    }
                } else {
                    dataInfo.put(fieldData.getFieldDataName(), fieldData.getObjectModel());
                }
            } catch (Exception e) {
                log.error("{}", e);
            }
        }


        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObjectEle = jsonArray.getJSONObject(i);
                String key = templateMappingData.get(jsonObjectEle.getString("position"));
                Object value = dataInfo.get(jsonObjectEle.getString("name"));
                if (key != null && value != null) {
                    result.put(key, value);
                }
            } catch (Exception e) {
                log.error("{}", e);
            }
        }

        return result;
    }

    private JSONObject getJSONPropertiesFromFieldData(List<FieldData> fieldDataList){
        JSONObject jsonObjectProperties = new JSONObject();
        if(fieldDataList != null && !fieldDataList.isEmpty()){
            try {
                fieldDataList.forEach(ele -> {
                    try {
                        jsonObjectProperties.put(ele.getFieldDataCode(), new JSONObject(ele.getObjectSchema()));
                    }catch (Exception e){ log.error("{}", e); }
                });
            }catch (Exception e){ log.error("{}", e); };
        }
        return jsonObjectProperties;
    }

            // utils \\
    /**
     * Convert sang DTO
     * @param fieldData : fieldData cần convert
     * @return: DTO
     */
    private FieldDataDTO convertToDTO(FieldData fieldData){
        if(fieldData == null) return null;
        FieldDataDTO result = new FieldDataDTO();
        BeanUtils.copyProperties(fieldData, result);
        if(fieldData.getRequestData() != null){
            RequestDataDTO requestDataDTO = new RequestDataDTO();
            requestDataDTO.setId(fieldData.getRequestData().getId());
            result.setRequestData(requestDataDTO);
        }
        if(fieldData.getFormData() != null){
            FormDataDTO formDataDTO = new FormDataDTO();
            formDataDTO.setId(fieldData.getFormData().getId());
            result.setFormData(formDataDTO);
        }
        return result;
    }

    private List<FieldDataDTO> convertToDTO(List<FieldData> fieldDataList){
        List<FieldDataDTO> result = new ArrayList<>();
        if(fieldDataList == null || fieldDataList.isEmpty()) return result;
        for(FieldData fieldData : fieldDataList){
            result.add(this.convertToDTO(fieldData));
        }
        return result;
    }

    /**
     * tạo ra pageble có sort
     * @param pageable
     * @return
     */
    private Pageable getNewPageable(Pageable pageable){
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").ascending());
        return newPageable;
    }
}
