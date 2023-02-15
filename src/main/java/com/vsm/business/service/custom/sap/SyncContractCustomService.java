package com.vsm.business.service.custom.sap;

import com.vsm.business.domain.FieldData;
import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.FieldDataRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.service.custom.sap.bo.SyncContractDTO;
import com.vsm.business.utils.CallAPIUtils;
import org.elasticsearch.common.Strings;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SyncContractCustomService {

    private final Logger log = LoggerFactory.getLogger(SyncContractCustomService.class);

    @Autowired
    private CallAPIUtils callAPIUtils;

    @Autowired
    private RequestDataRepository requestDataRepository;

    @Autowired
    private FieldDataRepository fieldDataRepository;

    @Value("${sync-contract-sap.api_key:eOPh9QMopl95HuxtZFo2TJaZw1erjHeor}")
    private String API_KEY;

    @Value("${sync-contract-sap.client_id:cd71e2507d2c4812bbaecb25ec377610}")
    private String CLIENT_ID;

    @Value("${sync-contract-sap.client_secret:C02808548cb74BB0858F5417BE525aBc}")
    private String CLIENT_SECRET;

    @Value("${sync-contract-sap.url:https://api.vincom.com.vn/api/vcr/v1/sap-re/contract}")
    private String URL_SAP_API;

    @Value("${sync-contract-sap.field-format.date}")
    private String[] FIELD_FORMAT_TYPE_DATE;

    @Value("${sync-contract-sap.field-format.date-format-regex-db:dd/MM/yyyy}")
    private String FORMAT_DATE_REGEX_DB;            // format dữ liệu dạng date trong DB
    @Value("${sync-contract-sap.field-format.date-format-regex-sap:dd/MM/yyyy}")
    private String FORMAT_DATE_REGEX_SAP;           // format dữ liệu dạng date bên SAP

    @Value("${sync-contract-sap.field-format.number}")
    private String[] FIELD_FORMAT_TYPE_NUMBER;

    @Value("${sync-contract-sap.field-format.string}")
    private String[] FIELD_FORMAT_TYPE_STRING;

    public String callSAPAPI(SyncContractDTO syncContractDto) throws Exception {
        HttpMethod method = HttpMethod.POST;
        if("POST".equalsIgnoreCase(syncContractDto.getHttpMethod())){
            method = HttpMethod.POST;
        }else if("GET".equalsIgnoreCase(syncContractDto.getHttpMethod())){
            method = HttpMethod.GET;
        }else if("PUT".equalsIgnoreCase(syncContractDto.getHttpMethod())){
            method = HttpMethod.PUT;
        }
        String result = callAPIUtils.createRestTemplateCallSAP(syncContractDto.getUrl(), syncContractDto.getBody(), method, syncContractDto.getHeaders(), String.class);
        log.debug("SyncContractCustomService: callSAPAPI({}): {}", syncContractDto, result);
        return result;
    }


    public Map<String, Object> syncDataToSAP(Long requestDataId){
        Map<String, Object> result = new HashMap<>();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        List<FieldData> fieldDataList = requestData.getFieldData() != null ? requestData.getFieldData().stream().collect(Collectors.toList()) : this.fieldDataRepository.findAllByRequestDataId(requestDataId);
        DateTimeFormatter formatterDB;
        try {
            formatterDB = DateTimeFormatter.ofPattern(this.FORMAT_DATE_REGEX_DB);
        }catch (Exception e){
            log.error("{}", e);
            formatterDB = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        DateTimeFormatter formatterSAP;
        try {
            formatterSAP = DateTimeFormatter.ofPattern(this.FORMAT_DATE_REGEX_SAP);
        }catch (Exception e){
            log.error("{}", e);
            formatterSAP = DateTimeFormatter.ofPattern("yyyyMMdd");
        }
        log.info("SAP_MAPPING:{}", requestData.getSapMapping());
        if(Strings.isNullOrEmpty(requestData.getContractNumber()) && !Strings.isNullOrEmpty(requestData.getSapMapping())) { // nếu chưa đồng bộ sang SAP và có thông tin mapping -> xử lý
            try {
                JSONObject jsonMapping = new JSONObject(requestData.getSapMapping());       // lấy thông tin mapping
                Map<String, Object> bodyMap = new HashMap<>();
                Iterator<String> keys = jsonMapping.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        String keyData = jsonMapping.getJSONObject(key).getString("value");
                        FieldData fieldData = fieldDataList.stream().filter(ele -> ele.getFieldDataCode().equals(keyData)).findFirst().orElse(null);
                        String dataOfField = fieldData == null ? null : fieldData.getObjectModel();
                        log.info("key(tên trường SAP): {} || keydata(tên trường trong form): {}", key, keyData);
                        if (dataOfField == null) {
                            bodyMap.put(key, fieldData == null ? keyData : null);
                        } else {
                            if (Arrays.stream(this.FIELD_FORMAT_TYPE_DATE).anyMatch(ele -> ele.equals(key))) {        // TH nếu bên SAP là type DATE
                                try {
                                    LocalDate dateVaLue = LocalDate.parse(dataOfField, formatterDB);
                                    bodyMap.put(key, dateVaLue.format(formatterSAP));
                                } catch (Exception ex1) {
                                    log.error("{}", ex1);
                                    bodyMap.put(key, null);
                                }
                            } else if (Arrays.stream(this.FIELD_FORMAT_TYPE_NUMBER).anyMatch(ele -> ele.equals(key))) {    // TH nếu bên SAP là type NUMBER
                                // trường số trong DB đang lưu dạng (VD: 10.000.000,1 tương đương 10000000.1)
                                dataOfField = dataOfField.replace(".", "");
                                dataOfField = dataOfField.replace(",", ".");
                                try {
                                    bodyMap.put(key, Double.parseDouble(dataOfField));
                                } catch (Exception ex1) {
                                    log.error("{}", ex1);
                                    bodyMap.put(key, 0D);
                                }
                            } else if (Arrays.stream(this.FIELD_FORMAT_TYPE_STRING).anyMatch(ele -> ele.equals(key))) {     // TH nếu bên SAP là type STRING
                                bodyMap.put(key, dataOfField);      // dataOfField vốn là dạng String -> không cần parse
                            } else {
                                bodyMap.put(key, dataOfField);      // không có trong các list Field  cần format -> put luôn vào
                            }
                        }
                    } catch (Exception ex) {
                        log.error("{}", ex);
                        bodyMap.put(key, null);
                    }
                }
                String responseOfSAP = this.syncDataToSAP(bodyMap);
                JSONObject responseOfSAPJSON = new JSONObject(responseOfSAP);
                if (Strings.isNullOrEmpty(responseOfSAPJSON.getString("ev_contract"))) {      // nếu đồng bộ faild;
                    result.put("res", false);
                    result.put("mess", responseOfSAPJSON.getString("ev_message"));
                    return result;
                }
                requestData.setContractNumber(responseOfSAPJSON.getString("ev_contract"));
                requestData.setModifiedDate(Instant.now());
                this.requestDataRepository.save(requestData);
                result.put("res", true);
                result.put("mess", requestData.getContractNumber());
                return result;
            } catch (Exception e) {
                log.error("{}", e);
                result.put("res", false);
                result.put("mess", "Lỗi khi đồng bộ dữ liệu với SAP");
                return result;
            }
        }
        return result;
    }

    public String syncDataToSAP(Object obj) throws Exception {
        Map<String, Object> headers = new HashMap<>();
        headers.put("api_key", this.API_KEY);
        headers.put("client_id", this.CLIENT_ID);
        headers.put("client_secret", this.CLIENT_SECRET);
        String result = callAPIUtils.createRestTemplateCallSAP(this.URL_SAP_API, obj, HttpMethod.POST, headers, String.class);
        return result;
    }

}
