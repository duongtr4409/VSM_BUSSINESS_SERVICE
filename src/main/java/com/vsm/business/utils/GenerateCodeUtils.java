package com.vsm.business.utils;

import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.dto.RequestDTO;
import com.vsm.business.service.dto.RequestDataDTO;
import com.vsm.business.service.mapper.RequestMapper;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GenerateCodeUtils {

    public static final String REQDATACODE_TYPE = "REQDATACODE_TYPE";       // mã loại generate cho Mã Phiếu Yêu Cầu

    enum GenerateCodeOption {

        YYYY("YYYY", "${YYYY}", GenerateCodeUtils.REQDATACODE_TYPE),
        MM("MM", "${MM}", GenerateCodeUtils.REQDATACODE_TYPE),
        MON("MON", "${MON}", GenerateCodeUtils.REQDATACODE_TYPE),
        DD("DD", "${DD}", GenerateCodeUtils.REQDATACODE_TYPE),
        REQ_TYPE_CODE("REQ_TYPE_CODE", "${REQ_TYPE_CODE}", GenerateCodeUtils.REQDATACODE_TYPE),
        REQ_GROUP_CODE("REQ_GROUP_CODE", "${REQ_GROUP_CODE}", GenerateCodeUtils.REQDATACODE_TYPE),
        REQ_CODE("REQ_CODE", "${REQ_CODE}", GenerateCodeUtils.REQDATACODE_TYPE),
        STT("STT", "${STT}", GenerateCodeUtils.REQDATACODE_TYPE),
        ORG("ORG", "${ORG}", GenerateCodeUtils.REQDATACODE_TYPE),
        REQUEST_DATA_TITLE("Tiêu đề", "${REQUEST_TITLE}"),
        REQUEST_DATA_CODE("Số phiếu", "${REQUEST_CODE}"),

        REQ_TYPE_NAME("Tên loại yêu cầu", "${REQ_TYPE_NAME}");

        private String name;
        private String variable;

        private String type;


        public String getName() {
            return name;
        }

        public String getVariable() {
            return variable;
        }

        private String getType() {
            return type;
        }

        GenerateCodeOption(String name, String variable, String type) {
            this.name = name;
            this.variable = variable;
            this.type = type;
        }

        GenerateCodeOption(String name, String variable) {
            this.name = name;
            this.variable = variable;
        }
    }

    public final String REQUIRED_OPTION = "${STT}";
    public static Map<String, Long> MAP_STT = new HashMap<>();
    public static Map<String, Function<RequestDTO, String>> generateCodeOptionFunctionMap = new HashMap<>();

    public static Map<String, Function<RequestData, String>> generateFileNameOptionFunctionMap = new HashMap<>();
    public final SimpleDateFormat YYYY_Fomat = new SimpleDateFormat("yyyy");
    public final SimpleDateFormat MM_Fomat = new SimpleDateFormat("MM");
    public final SimpleDateFormat DD_Fomat = new SimpleDateFormat("dd");
    public final SimpleDateFormat MON_Fomat = new SimpleDateFormat("MMM");

    @Autowired
    private VNCharacterUtils vnCharacterUtils;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private RequestDataRepository requestDataRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private FieldInFormRepository fieldInFormRepository;
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private RequestMapper requestMapper;

    public void loadGenerateCodeOptionFunctionMap() {
        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.YYYY.getName(), (RequestDTO) -> {
            return YYYY_Fomat.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.MM.getName(), (RequestDTO) -> {
            return MM_Fomat.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.MON.getName(), (RequestDTO) -> {
            return MON_Fomat.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.DD.getName(), (RequestDTO) -> {
            return DD_Fomat.format(new Date());
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.REQ_GROUP_CODE.getName(), (RequestDto) -> {
            Request request = this.requestRepository.findById(RequestDto.getId()).get();
            return request.getRequestGroup().getRequestGroupCode();
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.REQ_TYPE_CODE.getName(), (RequestDto) -> {
            Request request = this.requestRepository.findById(RequestDto.getId()).get();
            return request.getRequestType().getRequestTypeCode();
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.REQ_CODE.getName(), (RequestDto) -> {
            Request request = this.requestRepository.findById(RequestDto.getId()).get();
            return request.getRequestCode();
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.STT.getName(), (RequestDto) -> {
            //Long maxSSTByRequestId = this.requestDataRepository.getMaxSTTByRequetId(RequestDto.getId());
            Long maxSSTByRequestId = this.requestRepository.findById(RequestDto.getId()).get().getNumberRequestData();
            maxSSTByRequestId = maxSSTByRequestId != null ? maxSSTByRequestId : 0L;
            Long stt = maxSSTByRequestId + 1;
            if (String.valueOf(stt).length() > 4) {
                return String.valueOf(stt);
            }
            return String.format("%04d", stt);
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.ORG.getName(), (RequestDTO) -> {
            UserInfo userInfo = new UserInfo();
            try {
                userInfo = this.userInfoRepository.findById(RequestDTO.getCreated().getId()).get();
            } catch (Exception e) {
                userInfo = new UserInfo();
            }
            ;
            List<Organization> organizations = userInfo.getOrganizations().stream().collect(Collectors.toList());
            if (organizations != null && !organizations.isEmpty()) {
                return organizations.get(0).getOrganizationCode();
            } else {
                return "";
            }
        });

        this.generateCodeOptionFunctionMap.put(GenerateCodeOption.REQ_TYPE_NAME.getName(), (RequestDTO) -> {
            String requestTypeName = "";
            if(RequestDTO.getRequestType() != null && !Strings.isNullOrEmpty(RequestDTO.getRequestType().getRequestTypeName())){
                requestTypeName = RequestDTO.getRequestType().getRequestTypeName();
            }else{
                requestTypeName = this.requestRepository.findById(RequestDTO.getId()).get().getRequestType().getRequestTypeName();
            }
            return vnCharacterUtils.removeAccent(requestTypeName);
        });

        this.generateFileNameOptionFunctionMap.put(GenerateCodeOption.REQUEST_DATA_CODE.getVariable(), (RequestData) -> vnCharacterUtils.removeAccent(RequestData.getRequestDataCode()));

        this.generateFileNameOptionFunctionMap.put(GenerateCodeOption.REQUEST_DATA_TITLE.getVariable(), (RequestData) -> vnCharacterUtils.removeAccent(RequestData.getTitle()));
    }

    @Autowired
    EntityManager entityManager;

    public void loadSTTMap() {
        Query query = entityManager.createNativeQuery("");
    }

    public String getCode(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        if (name != null) {
            String[] split = name.split(" ");
            int n = split != null ? split.length : 0;
            for (int i = 0; i < n; i++) {
                if (split[i].length() > 0) {
                    try {
                        Integer.valueOf(split[i]);
                        stringBuilder.append(split[i]);
                    } catch (NumberFormatException e) {
                        stringBuilder.append(String.valueOf(split[i].charAt(0)));
                    }
                }
            }
        }
        return stringBuilder.toString().toUpperCase();
    }

    public Integer getNumber(String code) {
        if (code == null) return 0;
        int n = code.length();
        StringBuilder builder = new StringBuilder();
        for (int i = n - 1; i >= 0; i--) {
            try {
                Integer.valueOf(String.valueOf(code.charAt(i)));
                builder.append(code.charAt(i));
            } catch (NumberFormatException e) {
                break;
            }
        }
        return builder.toString().isEmpty() ? 0 : Integer.valueOf(builder.reverse().toString());
    }

    public String getCharactorFormCode(String code) {
        if (code == null) return null;
        int n = code.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            try {
                Integer.valueOf(String.valueOf(code.charAt(i)));
                break;
            } catch (NumberFormatException e) {
                builder.append(code.charAt(i));
            }
        }
        return builder.toString();
    }

    public <T> String generateCode(String name, Map<String, T> map, Class<T> tClass, String getCodeNameMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        name = vnCharacterUtils.removeAccent(name);
        String code = this.getCode(name);
        T temp = map.get(code);
        if (temp == null) {
            return code;
        } else {
            code = this.getCharactorFormCode(code);
            Integer stt = 0;
            while (temp != null) {
                Method getCodeMethod = tClass.getMethod(getCodeNameMethod);
                String codeTemp = (String) getCodeMethod.invoke(temp);
                stt = this.getNumber(codeTemp);
                temp = map.get(code + ++stt);
            }
            code += (stt != 0) ? stt : "";
            return code;
        }
    }

    public RequestDataDTO generateRequestDataCode(RequestDataDTO requestDataDTO) {
        if (this.generateCodeOptionFunctionMap.size() == 0) this.loadGenerateCodeOptionFunctionMap();
        String code = requestDataDTO.getRequest().getRuleGenerateCode();
        if (code == null || code.isEmpty()) {
            code = this.requestRepository.findById(requestDataDTO.getRequest().getId()).get().getRuleGenerateCode();
        }
        if (!code.contains(REQUIRED_OPTION)) code += REQUIRED_OPTION;
        for (GenerateCodeOption ele : GenerateCodeOption.values()) {
            if (code.contains(ele.getVariable())) {
                code = code.replace(ele.getVariable(), GenerateCodeUtils.generateCodeOptionFunctionMap.get(ele.getName()).apply(requestDataDTO.getRequest()));
            }
        }
        requestDataDTO.setRequestDataCode(code);
        return requestDataDTO;
    }

    public GenerateCodeOption[] getAllGenerateCodeOption() {
        return Arrays.stream(GenerateCodeOption.values()).filter(ele -> GenerateCodeUtils.REQDATACODE_TYPE.equals(ele.getType())).toArray(GenerateCodeOption[]::new);
    }

    public List<Options> v2_getAllGenerateCodeOption(Long requestId) {
        List<Options> result = new ArrayList<>();
        for (GenerateCodeOption generateCodeOption : GenerateCodeOption.values()) {
            Options option = new Options(generateCodeOption.getName(), generateCodeOption.getVariable());
            result.add(option);
        }
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (!optionalRequest.isPresent()) {
            return result;
        }
        Set<Request> requests = new HashSet<>();
        requests.add(optionalRequest.get());
        Form form = formRepository.findAllByRequestsIn(requests).stream().findFirst().get();
        List<FieldInForm> fieldInForms = fieldInFormRepository.findAllByFormId(form.getId());
        for (FieldInForm fieldInForm : fieldInForms) {
            Options option = new Options(fieldInForm.getFieldInFormName(), String.format("${%s}", fieldInForm.getFieldInFormCode()));
            result.add(option);
        }
        return result;
    }

    public String v2_generateFileName(Integer stt, String extension, String rules, Request request, RequestData requestData, List<FieldData> fieldDataList) {
        String temp = rules;
        if (Strings.isNullOrEmpty(rules)) {
            temp = String.format("%s_%s", GenerateCodeOption.REQUEST_DATA_CODE.getVariable(), GenerateCodeOption.REQUEST_DATA_TITLE.getVariable());
        }
        String fileName = temp;
        GenerateCodeOption[] generateCodeOptions = GenerateCodeOption.values();
        if (generateCodeOptionFunctionMap.size() == 0 || generateFileNameOptionFunctionMap.size() == 0) {
            this.loadGenerateCodeOptionFunctionMap();
        }
        for (GenerateCodeOption generateCodeOption : generateCodeOptions) {
            if (generateCodeOptionFunctionMap.containsKey(generateCodeOption.getName())) {
                fileName = fileName.replace(generateCodeOption.getVariable(), generateCodeOptionFunctionMap.get(generateCodeOption.getName()).apply(requestMapper.toDto(request)));
            }
            if (generateFileNameOptionFunctionMap.containsKey(generateCodeOption.getVariable())) {
                fileName = fileName.replace(generateCodeOption.getVariable(), generateFileNameOptionFunctionMap.get(generateCodeOption.getVariable()).apply(requestData));
            }
        }
        String search = "";
        for (FieldData fieldData : fieldDataList) {
            search = String.format("${%s}", fieldData.getFieldDataCode());
            if(fieldData.getObjectModel() == null) continue;
            fileName = fileName.replace(search, vnCharacterUtils.removeAccent(fieldData.getObjectModel()));
        }
        return String.format("%s_%s.%s", fileName, stt, extension);
    }

    public class Options {
        private String name;
        private String variable;

        public Options(String name, String variable) {
            this.name = name;
            this.variable = variable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVariable() {
            return variable;
        }

        public void setVariable(String variable) {
            this.variable = variable;
        }
    }
}
