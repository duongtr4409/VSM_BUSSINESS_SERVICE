package com.vsm.business.service.mule;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.vsm.business.config.Constants;
import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.service.custom.CategoryGroupCustomService;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vsm.business.service.dto.CategoryGroupDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Đồng bộ thông tin Gian hàng từ SAP, -> tên lấy từ trường 'businessEntity' lấy giá trị sau dấu '-'
*                                         key đồng bộ là trường 'roKey' sẽ lưu trong tennant code của category-group
 *
 *
 *
 *  Khi đồng bộ được Gian Hàng -> sẽ thêm luôn vào danh mục TTTM (do )
 */
@Service
public class VibdroService {
    private final Logger log = LoggerFactory.getLogger(VibdroDTO.class);

    @Value("${feature.mule.vibdro.store-full-name-business:FALSE}")
    public String STORE_FULL_NAME_BUSINESS;

    @Value("${system.sync-sap.VIBDRO_CODE:GIANHANG}")
    public String CATEGORY_VIBDRO_CODE;

    @Value("${system.sync-sap.VIBDRO_NAME:Gian Hàng}")
    public String CATEGORY_VIBDRO_NAME;

    @Value("${system.sync-sap.TTTM_CODE:TTTM}")
    public String CATEGORY_TTTM_CODE;

    @Value("${system.sync-sap.TTTM_NAME:Trung Tâm Thương Mại}")
    public String CATEGORY_TTTM_NAME;

    @Value("${feature.mule.vibdro.store-rental-object-name:false}")
    public String STORE_RENTAL_OBJECT_NAME;

    public final String CREATE_NAME = "Sync with SAP";

    public final String VIBDRO_PREFIX_KEY = "vibdro_";

    public final String TTTM_PREFIX_KEY = "tttm_";

    @Autowired
    public CategoryGroupRepository categoryGroupRepository;

    @Autowired
    public CategoryGroupCustomService categoryGroupCustomService;

    public CategoryGroup VIBDRO_PARENT_CATEGORYGROUP;

    public CategoryGroup TTTM_PARENT_CATEGORYGROUP;

    public final ObjectMapper objectMapper = new ObjectMapper();

    public List<?> sync(List<VibdroDTO> vibdroDTOs) {
        List<CategoryGroupDTO> results = new ArrayList<>();
        initParent();
        log.info("VIBDRO_SAP_SYNC vibdroDTOs.size()={}", vibdroDTOs.size());
        int success = 0;
        int fail = 0;
        Gson gson = new Gson();
        for (VibdroDTO vibdroDTO : vibdroDTOs) {
            log.info("VIBDRO_SAP_SYNC vibdroDTO={}", gson.toJson(vibdroDTO));
            try {
                // generate ObjectId
                vibdroDTO.setObjectId(generateObjectId(vibdroDTO));
                String vibdroKey = VIBDRO_PREFIX_KEY + vibdroDTO.getRoKey();
                List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByTennantCode(vibdroKey);
                if(categoryGroupList == null || categoryGroupList.isEmpty()){           // TH ko có -> tạo mới
                    results.add(convertToDTO(createCategory(vibdroDTO)));
                }else{              // TH đã có -> update
                    for(CategoryGroup categoryGroup : categoryGroupList){
                        results.add(convertToDTO(updateCategory(vibdroDTO, categoryGroup)));
                    }
                }
                success++;
            }catch (Exception e){
                fail++;
                log.error("VIBDRO_SAP_SYNC vibdroDTO={}, error={}", gson.toJson(vibdroDTO), e);
            }
        }
        log.info("VIBDRO_SAP_SYNC result: success={}, fail={}", success, fail);
        this.VIBDRO_PARENT_CATEGORYGROUP = null;
        this.TTTM_PARENT_CATEGORYGROUP = null;
        this.categoryGroupCustomService.clearCache();               // clear cached sau khi đồng bộ với SAP
        return results;
    }


    private CategoryGroup createCategory(VibdroDTO vibdroDTO){
        CategoryGroup categoryGroup = new CategoryGroup();
        String name = vibdroDTO.getBusinessEntity() == null ? "" : vibdroDTO.getBusinessEntity().substring(vibdroDTO.getBusinessEntity().indexOf("-") + 1);
        if(!Constants.TRUE.equalsIgnoreCase(STORE_RENTAL_OBJECT_NAME)){
            if(!"false".equalsIgnoreCase(this.STORE_FULL_NAME_BUSINESS)){
                categoryGroup.setName(vibdroDTO.getBusinessEntity());
                categoryGroup.setCode(vibdroDTO.getBusinessEntity());
            }else{
                categoryGroup.setName(name);
                categoryGroup.setCode(name);
            }
        }else{
            categoryGroup.setName(vibdroDTO.getRentalObjectName());
            categoryGroup.setCode(vibdroDTO.getRoKey());
        }
        Instant now = Instant.now();
        categoryGroup.setCreatedName(CREATE_NAME);
        categoryGroup.setCreatedDate(now);
        categoryGroup.setModifiedName(CREATE_NAME);
        categoryGroup.setModifiedDate(now);
        categoryGroup.tennantCode(this.VIBDRO_PREFIX_KEY + vibdroDTO.getRoKey());
        try {
            categoryGroup.setDescription(objectMapper.writeValueAsString(vibdroDTO));
        }catch (Exception e){log.error("{}", e);}
        categoryGroup.setIsActive(true);
        categoryGroup.setIsDelete(false);
        categoryGroup.setParent(this.VIBDRO_PARENT_CATEGORYGROUP);
        categoryGroup = this.categoryGroupRepository.save(categoryGroup);

//        try {       // update TTTM
//            CategoryGroup categoryGroupTTTM = new CategoryGroup();
//            BeanUtils.copyProperties(categoryGroup, categoryGroupTTTM);
//            categoryGroupTTTM.setId(null);
//            categoryGroupTTTM.setTennantCode(this.TTTM_PREFIX_KEY + vibdroDTO.getRoKey());
//            categoryGroupTTTM.setParent(this.TTTM_PARENT_CATEGORYGROUP);
//            categoryGroupTTTM = this.categoryGroupRepository.save(categoryGroupTTTM);
//        }catch (Exception e){log.error("{}", e);}

        return categoryGroup;
    }


    private CategoryGroup updateCategory(VibdroDTO vibdroDTO, CategoryGroup categoryGroup){
        VibdroDTO vibdroDTOOld;
        try {
            vibdroDTOOld = this.objectMapper.readValue(categoryGroup.getDescription(), VibdroDTO.class);
        }catch (Exception e){
            log.error("{}", e);
            vibdroDTOOld = null;
        }
        if(checkSame(vibdroDTO, vibdroDTOOld)) return categoryGroup;          // nếu giống nhau -> ko update
        if(!Constants.TRUE.equalsIgnoreCase(STORE_RENTAL_OBJECT_NAME)){
            String name = vibdroDTO.getBusinessEntity() == null ? "" : (vibdroDTO.getBusinessEntity().substring(vibdroDTO.getBusinessEntity().indexOf("-") + 1)).trim();
            categoryGroup.setName(name);
            categoryGroup.setCode(name);
        }else{
            categoryGroup.setName(vibdroDTO.getRentalObjectName());
            categoryGroup.setCode(vibdroDTO.getRoKey());
        }
        categoryGroup.setModifiedName(CREATE_NAME);
        categoryGroup.setModifiedDate(Instant.now());
        categoryGroup.setIsActive(true);
        categoryGroup.setIsDelete(false);
        try {
            categoryGroup.setDescription(objectMapper.writeValueAsString(vibdroDTO));
        }catch (Exception e){
            log.error("{}", e);
        }

//        try {       // cập nhật TTTM
//            String key = this.TTTM_PREFIX_KEY + vibdroDTO.getRoKey();
//            List<CategoryGroup> categoryGroupTTTMList = this.categoryGroupRepository.findAllByTennantCode(key);
//            if(categoryGroupTTTMList != null && !categoryGroupTTTMList.isEmpty()){
//                for(CategoryGroup ele : categoryGroupTTTMList){
//                    ele.setName(name);
//                    ele.setCode(name);
//                    categoryGroup.setModifiedName(CREATE_NAME);
//                    categoryGroup.setModifiedDate(Instant.now());
//                    categoryGroup.setIsActive(true);
//                    categoryGroup.setIsDelete(false);
//                    ele.setParent(TTTM_PARENT_CATEGORYGROUP);
//                    try {
//                        ele.setDescription(objectMapper.writeValueAsString(vibdroDTO));
//                    }catch (Exception se){log.error("{}", se);}
//                    ele = this.categoryGroupRepository.save(ele);
//                }
//            }
//        }catch (Exception e){log.error("{}", e);}

        categoryGroup = this.categoryGroupRepository.save(categoryGroup);
        return categoryGroup;
    }


    /**
     * Hàm kiểm tra xem 2 VibdroDTO giống nhau ko
     * @param vibdroDTONew :
     * @param vibdroDTOOld :
     * @return  true nếu giống nhau, false nếu khác nhau
     */
    private boolean checkSame(VibdroDTO vibdroDTONew, VibdroDTO vibdroDTOOld){
        if(vibdroDTONew == null || vibdroDTOOld == null) return false;
        try {
            VibdroDTO vibdroDTOCompareNew = new VibdroDTO();
            BeanUtils.copyProperties(vibdroDTONew, vibdroDTOCompareNew);
            VibdroDTO vibdroDTOCompareOld = new VibdroDTO();
            BeanUtils.copyProperties(vibdroDTOOld, vibdroDTOCompareOld);
            vibdroDTOCompareNew.setSerial("");
            vibdroDTOCompareOld.setSerial("");
            return vibdroDTOCompareNew.equals(vibdroDTOCompareOld);
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
    }

    private void initParent(){
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.findAndRegisterModules();
        if(this.VIBDRO_PARENT_CATEGORYGROUP == null){
            List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByNameAndParent(CATEGORY_VIBDRO_NAME, null);
            if(categoryGroupList == null || categoryGroupList.isEmpty()){
                CategoryGroup categoryGroupVibdro = new CategoryGroup();
                categoryGroupVibdro.setName(CATEGORY_VIBDRO_NAME);
                categoryGroupVibdro.setCode(CATEGORY_VIBDRO_CODE);
                Instant now = Instant.now();
                categoryGroupVibdro.setCreatedDate(now);
                categoryGroupVibdro.setCreatedName(CREATE_NAME);
                categoryGroupVibdro.setModifiedDate(now);
                categoryGroupVibdro.setModifiedName(CREATE_NAME);
                categoryGroupVibdro.setIsActive(true);
                categoryGroupVibdro.setIsDelete(false);
                this.VIBDRO_PARENT_CATEGORYGROUP = this.categoryGroupRepository.save(categoryGroupVibdro);
            }else{
                this.VIBDRO_PARENT_CATEGORYGROUP = categoryGroupList.get(0);
            }
        }
        if(this.TTTM_PARENT_CATEGORYGROUP == null){
            List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByNameAndParent(CATEGORY_TTTM_NAME, null);
            if(categoryGroupList == null || categoryGroupList.isEmpty()){
                CategoryGroup categoryGroupTTTM = new CategoryGroup();
                categoryGroupTTTM.setName(CATEGORY_TTTM_NAME);
                categoryGroupTTTM.setCode(CATEGORY_TTTM_CODE);
                Instant now = Instant.now();
                categoryGroupTTTM.setCreatedDate(now);
                categoryGroupTTTM.setCreatedName(CREATE_NAME);
                categoryGroupTTTM.setModifiedDate(now);
                categoryGroupTTTM.setModifiedName(CREATE_NAME);
                categoryGroupTTTM.setIsActive(true);
                categoryGroupTTTM.setIsDelete(false);
                this.TTTM_PARENT_CATEGORYGROUP = this.categoryGroupRepository.save(categoryGroupTTTM);
            }else{
                this.TTTM_PARENT_CATEGORYGROUP = categoryGroupList.get(0);
            }
        }
    }


    private CategoryGroupDTO convertToDTO(CategoryGroup categoryGroup){
        if(categoryGroup == null) return null;
        CategoryGroupDTO result = new CategoryGroupDTO();
        result.setDescription("");
        result.setCreatedOrgName("");
        result.setCreatedRankName("");
        result.setName(categoryGroup.getName());
        result.setCode(categoryGroup.getCode());
        result.setCreatedDate(categoryGroup.getCreatedDate());
        result.setCreatedName(categoryGroup.getCreatedName());
        result.setModifiedDate(categoryGroup.getModifiedDate());
        result.setModifiedName(categoryGroup.getModifiedName());
        result.setIsActive(categoryGroup.getIsActive());
        result.setIsDelete(categoryGroup.getIsDelete());
        return result;
    }

    /**
     * Hàm thực hiện generate ObjectId theo thong tin các trường trong vibdriDTO
     * @param vibdroDTO: thông tin vibdroDTO
     * @return          : ObjectId tương ứng
     */
    private String generateObjectId(VibdroDTO vibdroDTO){
        try {
            if(vibdroDTO == null) return null;
            StringBuilder result = new StringBuilder();

            String companyCode = vibdroDTO.getCompanyCode() != null ? vibdroDTO.getCompanyCode().replace(" ", "") : "";
            String rentalObject = vibdroDTO.getRentalObject() != null ? vibdroDTO.getRentalObject() : "";
            String businessEntity = vibdroDTO.getBusinessEntity() != null ? vibdroDTO.getBusinessEntity() : "";

            int index = companyCode.indexOf('-') < 0 ? companyCode.length() : companyCode.indexOf('-');
            result.append(companyCode.substring( (index - 4) < 0 ? 0 : (index-4), index));
            result.append("/");
            result.append(find(businessEntity, '0', businessEntity.indexOf('-')));
            result.append("/");
            result.append(find(rentalObject, '0', rentalObject.indexOf('-')));

            return result.toString();
        }catch (Exception e){
            log.error("{}",  e);
            return null;
        }
    }

    /**
     * hàm thực hiện tách lấy chuỗi cần thiết
     * @param str               : chuỗi gốc
     * @param findChar          : char cần kiểm tra (dừng lại ở vị trí khác char này)
     * @param endIndexSubString : vị trí cuối cắt chuỗi
     * @return
     */
    public String find(String str, char findChar, int endIndexSubString){
        if(Strings.isNullOrEmpty(str)) return "";
        int n = str.length();
        for(int i=0; i<n; i++){
            char c = str.charAt(i);
            if(c != findChar){
                if(endIndexSubString < 0)
                    return str.substring(i);
                else
                    return str.substring(i, endIndexSubString);
            }
        }
        return str.substring(0, endIndexSubString);
    }
}
