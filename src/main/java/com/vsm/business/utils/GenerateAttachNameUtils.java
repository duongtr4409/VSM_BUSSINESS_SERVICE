package com.vsm.business.utils;

import com.google.common.collect.Lists;
import com.vsm.business.domain.*;
import com.vsm.business.repository.FieldDataRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.RequestRepository;
import com.vsm.business.repository.UserInfoRepository;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GenerateAttachNameUtils {

    enum GenerateAttachNameOption {

        YYYY("YYYY", "${YYYY}"),
        MM("MM", "${MM}"),
        MON("MON", "${MON}"),
        DD("DD", "${DD}"),
        REQ_TYPE_CODE("REQ_TYPE_CODE", "${REQ_TYPE_CODE}"),
        REQ_TYPE_NAME("REQ_TYPE_NAME", "${REQ_TYPE_NAME}"),
        REQ_GROUP_CODE("REQ_GROUP_CODE", "${REQ_GROUP_CODE}"),
        REQ_GROUP_NAME("REQ_GROUP_NAME", "${REQ_GROUP_NAME}"),
        REQ_CODE("REQ_CODE", "${REQ_CODE}"),
        REQ_DATA_CODE("REQ_DATA_CODE", "${REQ_DATA_CODE}"),
        REQ_DATA_TITLE("REQ_DATA_TITLE", "${REQ_DATA_TITLE}"),
        REQ_DATA_CREATED_NAME("REQ_DATA_CREATED_NAME", "${REQ_DATA_CREATED_NAME}"),
        REQ_DATA_CREATED_ORG("REQ_DATA_CREATED_ORG", "${REQ_DATA_CREATED_ORG}"),
        STT("STT", "${STT}");

        private String name;
        private String variable;

        public String getName() {
            return name;
        }

        public String getVariable() {
            return variable;
        }

        GenerateAttachNameOption(String name, String variable) {
            this.name = name;
            this.variable = variable;
        }
    }

    @Autowired
    private VNCharacterUtils vnCharacterUtils;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestDataRepository requestDataRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private FieldDataRepository fieldDataRepository;

    public final String REQUIRED_OPTION = "${STT}";
    public static Map<String, Long> MAP_STT = new HashMap<>();
    public static Map<String, Function<RequestData, String>> generateCodeOptionFunctionMap = new HashMap<>();
    public final SimpleDateFormat YYYY_Format = new SimpleDateFormat("yyyy");
    public final SimpleDateFormat MM_Format = new SimpleDateFormat("MM");
    public final SimpleDateFormat DD_Format = new SimpleDateFormat("dd");
    public final SimpleDateFormat MON_Format = new SimpleDateFormat("MMM");

    public void loadGenerateAttachNameFunctionMap(){
        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.YYYY.getName(), (RequestData) -> {
            return YYYY_Format.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.MM.getName(), (RequestData) -> {
            return MM_Format.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.MON.getName(), (RequestData) -> {
            return MON_Format.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.DD.getName(), (RequestData) -> {
            return DD_Format.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_GROUP_CODE.getName(), (RequestData) -> {
            Request request = this.requestRepository.findById(RequestData.getRequest().getId()).get();
            return request.getRequestGroup().getRequestGroupCode();
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_GROUP_NAME.getName(), (RequestData) -> {
            Request request = this.requestRepository.findById(RequestData.getRequest().getId()).get();
            return vnCharacterUtils.removeAccent(request.getRequestGroup().getRequestGroupName());
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_TYPE_CODE.getName(), (RequestData) -> {
            Request request = this.requestRepository.findById(RequestData.getRequest().getId()).get();
            return request.getRequestType().getRequestTypeCode();
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_TYPE_NAME.getName(), (RequestData) -> {
            Request request = this.requestRepository.findById(RequestData.getRequest().getId()).get();
            return vnCharacterUtils.removeAccent(request.getRequestType().getRequestTypeName());
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_CODE.getName(), (RequestData) -> {
            Request request = this.requestRepository.findById(RequestData.getRequest().getId()).get();
            return request.getRequestCode();
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.STT.getName(), (RequestData) -> {
            //Long maxSSTByRequestId = this.requestRepository.findById(RequestData.getId()).get().getNumberRequestData();
            Long maxSSTByRequestId = RequestData.getNumberAttach() != null ? RequestData.getNumberAttach() : 0L;
            Long stt = maxSSTByRequestId + 1;
            return String.valueOf(stt);
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_DATA_CODE.getName(), (RequestData) -> {
            return RequestData.getRequestDataCode() != null ? RequestData.getRequestDataCode() : "";
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_DATA_TITLE.getName(), (RequestData) -> {
            if(Strings.isNullOrEmpty(RequestData.getTitle())){
                return "";
            }else{
                String newTitle = vnCharacterUtils.removeAccent(RequestData.getTitle());
                return newTitle/*.replaceAll("\\s", "")*/;
            }
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_DATA_CREATED_NAME.getName(), (RequestData) -> {
            if(Strings.isNullOrEmpty(RequestData.getCreatedName())){
                if(RequestData.getCreated() == null) return "";
                else{
                    if(Strings.isNullOrEmpty(RequestData.getCreated().getFullName())){
                        UserInfo created = this.userInfoRepository.findById(RequestData.getCreated().getId()).get();
                        return created.getFullName();
                    }else{
                        return RequestData.getCreated().getFullName();
                    }
                }
            }else{
                return RequestData.getCreatedName();
            }
        });

        this.generateCodeOptionFunctionMap.put(GenerateAttachNameUtils.GenerateAttachNameOption.REQ_DATA_CREATED_ORG.getName(), (RequestData) -> {
            if(RequestData.getCreated() == null) return "";
            else{
                if(RequestData.getCreated().getOrganizations() != null && !RequestData.getCreated().getOrganizations().isEmpty()){
                    return RequestData.getCreated().getOrganizations().stream().collect(Collectors.toList()).get(0).getOrganizationCode();
                }else{
                    return this.userInfoRepository.findById(RequestData.getCreated().getId()).get().getOrganizations().stream().collect(Collectors.toList()).get(0).getOrganizationCode();
                }
            }
        });

    }

    public String generateAttachFileName(RequestData requestData){
        if(this.generateCodeOptionFunctionMap.isEmpty()) this.loadGenerateAttachNameFunctionMap();
        if(Strings.isNullOrEmpty(requestData.getRuleGenerateAttachName())) return this.generateCodeOptionFunctionMap.get(GenerateAttachNameOption.STT.getName()).apply(requestData);
        else{
            String code = requestData.getRuleGenerateAttachName();
            if(!code.contains(REQUIRED_OPTION)) code += "_" + REQUIRED_OPTION;
            for(GenerateAttachNameOption ele : GenerateAttachNameOption.values()){
                if(code.contains(ele.getVariable())){
                    code = code.replace(ele.getVariable(), this.generateCodeOptionFunctionMap.get(ele.getName()).apply(requestData));
                }
            }
            List<String> ruleGenerateByFormDatas = this.getGenerateByFormData(code);
            if(!ruleGenerateByFormDatas.isEmpty()){
                List<FieldData> fieldDataList = this.fieldDataRepository.findAllByRequestDataId(requestData.getId());
                code = this.bindDataByFormDataToCode(code, ruleGenerateByFormDatas, fieldDataList);
            }
            return code;
        }
    }

    private List<String> getGenerateByFormData(String code){
        List<String> result = new ArrayList<>();
        if(Strings.isNullOrEmpty(code)) return result;
        String[] childStrings = code.split("");
        for(int i=0; i<childStrings.length;){
            if("$".equals(childStrings[i]) && "{".equals(childStrings[i+1])){
                StringBuilder stringBuilder = new StringBuilder();
                while(!"}".equals(childStrings[i])){
                    stringBuilder.append(childStrings[i]);
                    i++;
                }
                stringBuilder.append("}");
                result.add(stringBuilder.toString());
                i++;
            }else{
                i++;
            }
        }
        return result;
    }

    private String bindDataByFormDataToCode(String code, List<String> ruleGenerateByFormDatas, List<FieldData> fieldDataList){
        if(fieldDataList == null || ruleGenerateByFormDatas == null) return code;
        for(String ruleGenerateByFormData : ruleGenerateByFormDatas){
            String fieldCode = ruleGenerateByFormData.replace("${", "").replace("}", "");
            String data = fieldDataList.stream().filter(ele -> fieldCode.equals(ele.getFieldDataCode())).findFirst().get().getObjectModel();
            if(data == null) data = "";
            code = code.replace(ruleGenerateByFormData, data);
        }
        return code;
    }

    public GenerateAttachNameUtils.GenerateAttachNameOption[] getAllGenerateAttachNameOption(){
        return GenerateAttachNameUtils.GenerateAttachNameOption.values();
    }

}
