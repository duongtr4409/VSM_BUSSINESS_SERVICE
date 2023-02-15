package com.vsm.business.service.mule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.service.custom.CategoryGroupCustomService;
import com.vsm.business.service.dto.CategoryGroupDTO;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerService {

    private final Logger log = LoggerFactory.getLogger(PartnerService.class);

    @Value("${system.sync-sap.PARTNER_CODE:KHACHHANG}")
    public String CATEGORY_PARTNER_CODE;

    @Value("${system.sync-sap.PARTNER_NAME:Khách Hàng}")
    public String CATEGORY_PARTNER_NAME;

    @Value("${system.sync-sap.PARTNER.is-concat-address:TRUE}")
    public String IS_CONCAT_ADDRESS;

    public final String CREATE_NAME = "Sync with SAP";

    public final String PARTNER_PREFIX_KEY = "partner_";

    @Autowired
    public CategoryGroupRepository categoryGroupRepository;

    @Autowired
    public CategoryGroupCustomService categoryGroupCustomService;

    public CategoryGroup PARTNER_PARENT_CATEGORYGROUP;

    public final ObjectMapper objectMapper = new ObjectMapper();

    public List<?> sync(List<PartnerDTO> partnerDTOs){
        List<CategoryGroupDTO> results = new ArrayList<>();
        initParent();
        log.info("BP_SAP_SYNC partnerDTOs.size()={}", partnerDTOs.size());
        int success = 0;
        int fail = 0;
        Gson gson = new Gson();
        for(PartnerDTO partnerDTO : partnerDTOs){
            log.info("BP_SAP_SYNC partnerDTO={}", gson.toJson(partnerDTO));
            try {
                String partnerKey = PARTNER_PREFIX_KEY + partnerDTO.getPartnerCode();
                List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByTennantCode(partnerKey);
                if(categoryGroupList == null || categoryGroupList.isEmpty()){           // TH ko có -> tạo mới
                    results.add(convertToDTO(createCategory(partnerDTO)));
                }else{              // TH đã có -> update
                    for(CategoryGroup categoryGroup : categoryGroupList){
                        results.add(convertToDTO(updateCategory(partnerDTO, categoryGroup)));
                    }
                }
                success++;
            }catch (Exception e){
                fail++;
                log.error("BP_SAP_SYNC partnerDTO={}, error={}", gson.toJson(partnerDTO), e);
            }
        }
        log.info("BP_SAP_SYNC result: success={}, fail={}", success, fail);
        this.PARTNER_PARENT_CATEGORYGROUP = null;
        this.categoryGroupCustomService.clearCache();       // clear cache sau khi đồng bộ với SAP
        return results;
    }

    private CategoryGroup createCategory(PartnerDTO partnerDTO){
        CategoryGroup categoryGroup = new CategoryGroup();
        categoryGroup.setName(partnerDTO.getPartnerName());
        categoryGroup.setCode(partnerDTO.getPartnerName());
        Instant now = Instant.now();
        categoryGroup.setCreatedName(CREATE_NAME);
        categoryGroup.setCreatedDate(now);
        categoryGroup.setModifiedName(CREATE_NAME);
        categoryGroup.setModifiedDate(now);
        categoryGroup.setTennantCode(this.PARTNER_PREFIX_KEY + partnerDTO.getPartnerCode());

        if("TRUE".equalsIgnoreCase(this.IS_CONCAT_ADDRESS)){
            String address = this.concatCustom("", partnerDTO.getStreet(), partnerDTO.getStreet4(), partnerDTO.getStreet5(), partnerDTO.getDistrict(), partnerDTO.getCity());
            partnerDTO.setAddress(address);
        }

        try {
            categoryGroup.setDescription(objectMapper.writeValueAsString(partnerDTO));
        }catch (Exception e){log.error("{}", e);}
        categoryGroup.setIsActive(true);
        categoryGroup.setIsDelete(false);
        categoryGroup.setParent(this.PARTNER_PARENT_CATEGORYGROUP);
        categoryGroup = this.categoryGroupRepository.save(categoryGroup);
        return categoryGroup;
    }

    private CategoryGroup updateCategory(PartnerDTO partnerDTO, CategoryGroup categoryGroup){
        PartnerDTO partnerDTOOld;
        try {
            partnerDTOOld = objectMapper.readValue(categoryGroup.getDescription(), PartnerDTO.class);
        }catch (Exception e){
            log.error("{}", e);
            partnerDTOOld = null;
        }
        if(checkSame(partnerDTO, partnerDTOOld)) return categoryGroup;
        categoryGroup.setName(partnerDTO.getPartnerName());
        categoryGroup.setCode(partnerDTO.getPartnerName());
        categoryGroup.setModifiedDate(Instant.now());
        categoryGroup.setModifiedName(CREATE_NAME);
        categoryGroup.setIsDelete(false);
        categoryGroup.setIsActive(true);
        try {
            categoryGroup.setDescription(objectMapper.writeValueAsString(partnerDTO));
        }catch (Exception e){log.error("{}", e);}
        categoryGroup = this.categoryGroupRepository.save(categoryGroup);
        return categoryGroup;
    }


    /**
     * Hàm kiểm tra xem 2 PartnerDTO giống nhau ko
     * @param partnerDTONew :
     * @param partnerDTOOld :
     * @return  true nếu giống nhau, false nếu khác nhau
     */
    private boolean checkSame(PartnerDTO partnerDTONew, PartnerDTO partnerDTOOld){
        if(partnerDTONew == null || partnerDTOOld == null) return false;
        try {
            PartnerDTO partnerDTOCompareNew = new PartnerDTO();
            BeanUtils.copyProperties(partnerDTONew, partnerDTOCompareNew);
            PartnerDTO partnerDTOCompateOld = new PartnerDTO();
            BeanUtils.copyProperties(partnerDTOOld, partnerDTOCompateOld);
            partnerDTOCompateOld.setSerial("");
            partnerDTOCompareNew.setSerial("");
            return partnerDTOCompareNew.equals(partnerDTOCompateOld);
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
    }

    private void initParent() {
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.findAndRegisterModules();
        if (this.PARTNER_PARENT_CATEGORYGROUP == null) {
            List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByNameAndParent(CATEGORY_PARTNER_NAME, null);
            if (categoryGroupList == null || categoryGroupList.isEmpty()) {
                CategoryGroup categoryGroupVibdro = new CategoryGroup();
                categoryGroupVibdro.setName(CATEGORY_PARTNER_NAME);
                categoryGroupVibdro.setCode(CATEGORY_PARTNER_CODE);
                Instant now = Instant.now();
                categoryGroupVibdro.setCreatedDate(now);
                categoryGroupVibdro.setCreatedName(CREATE_NAME);
                categoryGroupVibdro.setModifiedDate(now);
                categoryGroupVibdro.setModifiedName(CREATE_NAME);
                categoryGroupVibdro.setIsActive(true);
                categoryGroupVibdro.setIsDelete(false);
                this.PARTNER_PARENT_CATEGORYGROUP = this.categoryGroupRepository.save(categoryGroupVibdro);
            } else {
                this.PARTNER_PARENT_CATEGORYGROUP = categoryGroupList.get(0);
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

    private String concatCustom(String source, String ...params){
        if(source == null) return "";
        if(params == null || params.length == 0) return source;
        for(String param : params){
            if(!Strings.isNullOrEmpty(param))
                source += ", " + param;
        }
        return source;
    }
}
