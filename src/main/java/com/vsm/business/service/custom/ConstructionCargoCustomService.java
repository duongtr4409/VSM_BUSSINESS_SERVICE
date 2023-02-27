package com.vsm.business.service.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vsm.business.domain.CategoryGroup;
import com.vsm.business.domain.ConstructionCargo;
import com.vsm.business.domain.MECargo;
import com.vsm.business.domain.UserInfo;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.repository.search.ConstructionCargoSearchRepository;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.service.mapper.ConstructionCargoMapper;
import com.vsm.business.utils.ExcelUtils;
import com.vsm.business.utils.UserUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstructionCargoCustomService {
    private final Logger log = LoggerFactory.getLogger(ConstructionCargoCustomService.class);

    private ConstructionCargoRepository constructionCargoRepository;

    private ConstructionCargoSearchRepository constructionCargoSearchRepository;

    private ConstructionCargoMapper constructionCargoMapper;

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupCustomService categoryGroupCustomService;

    private final ExcelUtils excelUtils;

    private final UserUtils userUtils;

    public final ObjectMapper objectMapper = new ObjectMapper();

    public static final String PREFIX_CODE_CONSTRUCTIONCARGO = "constructioncargo_";

    @Value("${cargo.construction_cargo_name:Hàng hóa dịch vụ xây dựng}")
    private String CONSTRUCTION_CARGO_NAME;

    @Value("${cargo.construction_cargo_code:HANG_HOA_DICH_VU_XAY_DUNG}")
    private String CONSTRUCTION_CARGO_CODE;

    public CategoryGroup CATEGORYGROUP_CONSTRUCTIONCAGOR;

    public CategoryGroup getCATEGORYGROUP_CONSTRUCTIONCAGOR() {
        return CATEGORYGROUP_CONSTRUCTIONCAGOR;
    }

    public void setCATEGORYGROUP_CONSTRUCTIONCAGOR(CategoryGroup CATEGORYGROUP_CONSTRUCTIONCAGOR) {
        this.CATEGORYGROUP_CONSTRUCTIONCAGOR = CATEGORYGROUP_CONSTRUCTIONCAGOR;
    }

    public ConstructionCargoCustomService(ConstructionCargoRepository constructionCargoRepository, ConstructionCargoSearchRepository constructionCargoSearchRepository, ConstructionCargoMapper constructionCargoMapper, CategoryGroupRepository categoryGroupRepositor, ExcelUtils excelUtils, UserUtils userUtils, CategoryGroupCustomService categoryGroupCustomService) {
        this.constructionCargoRepository = constructionCargoRepository;
        this.constructionCargoSearchRepository = constructionCargoSearchRepository;
        this.constructionCargoMapper = constructionCargoMapper;
        this.categoryGroupRepository = categoryGroupRepositor;
        this.excelUtils = excelUtils;
        this.userUtils = userUtils;
        this.categoryGroupCustomService = categoryGroupCustomService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void INIT_DANH_MUC(){
        try {
            List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByCode(CONSTRUCTION_CARGO_CODE);
            if(categoryGroupList != null && !categoryGroupList.isEmpty()){
                setCATEGORYGROUP_CONSTRUCTIONCAGOR( categoryGroupList.get(0));
            }else{
                CategoryGroup categoryGroup = new CategoryGroup();
                categoryGroup.setName(CONSTRUCTION_CARGO_NAME);
                categoryGroup.setCode(CONSTRUCTION_CARGO_CODE);
                categoryGroup.setIsActive(true);
                categoryGroup.setIsDelete(false);
                categoryGroup.setCreatedDate(Instant.now());
                categoryGroup.setModifiedDate(Instant.now());
                categoryGroup.setCreatedName("ADMIN");
                categoryGroup.setModifiedName("ADMIN");
                categoryGroup.setDescription("");
                setCATEGORYGROUP_CONSTRUCTIONCAGOR(this.categoryGroupRepository.save(categoryGroup));
            }
            log.info("CATEGORYGROUP_CONSTRUCTIONCAGOR: {}", getCATEGORYGROUP_CONSTRUCTIONCAGOR());
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    public List<ConstructionCargoDTO> getAll() {
        log.debug("ConstructionCargoCustomService: getAll()");
        List<ConstructionCargoDTO> result = new ArrayList<>();
        try {
            List<ConstructionCargo> constructionCargos = this.constructionCargoRepository.findAll();
            for (ConstructionCargo constructionCargo :
                constructionCargos) {
                ConstructionCargoDTO constructionCargoDTO = constructionCargoMapper.toDto(constructionCargo);
                result.add(constructionCargoDTO);
            }
        }catch (Exception e){
            log.error("ConstructionCargoCustomService: getAll() {}", e);
        }
        log.debug("ConstructionCargoCustomService: getAll() {}", result);
        return result;
    }

    public List<String> getAllMaHieu(){
        log.debug("ConstructionCargoCustomService: getAllMaHieu()");
        List<String> result = this.constructionCargoRepository.getAllMaHieu();
        log.debug("ConstructionCargoCustomService: getAllMaHieu() {}", result);
        return result;
    }

    public List<ConstructionCargoDTO> deleteAll(List<ConstructionCargoDTO> constructionCargoDTOS) {
        log.debug("ConstructionCargoCustomService: deleteAll({})", constructionCargoDTOS);
        List<Long> ids = constructionCargoDTOS.stream().map(ConstructionCargoDTO::getId).collect(Collectors.toList());
        this.constructionCargoRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            constructionCargoRepository.deleteById(id);
            constructionCargoSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("ConstructionCargoCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<ConstructionCargoDTO> saveAll(List<ConstructionCargoDTO> stepDTOList){
        List<ConstructionCargoDTO> result = constructionCargoRepository.saveAll(stepDTOList.stream().map(constructionCargoMapper::toEntity).collect(Collectors.toList())).stream().map(constructionCargoMapper::toDto).collect(Collectors.toList());
        log.debug("ConstructionCargoCustomService: saveAll({}) {}", stepDTOList, result);
        return result;
    }


    private final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    @Value("${bp-schedule.folder-sync:/temp/excel}")
    public String ExcelFolder;

    private List<String> LIST_FIELD_NAME = Arrays.asList("stt", "noi_dung_cong_viec", "quy_cach_ky_thuat", "ma_hieu", "don_vi_tinh", "don_gia_ha_noi", "don_gia_hcm");

    public boolean importCargo(MultipartFile file) throws IOException, InvalidFormatException {
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.findAndRegisterModules();

        MyUserDetail currentUser = this.userUtils.getCurrentUser();         // lấy thông tin user đang đăng nhập
        String folderName = this.ExcelFolder + this.PATH_SEPARATOR + System.currentTimeMillis();
        File folderRead = new File(folderName);
        try {
            try {
                FileUtils.writeByteArrayToFile(new File(folderName + this.PATH_SEPARATOR + file.getOriginalFilename()), file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<ConstructionCargo> constructionCargos = excelUtils.readAllExcelV1(folderRead, LIST_FIELD_NAME, ConstructionCargo.class);
            log.info("MECargoCustomService: import(): {}", constructionCargos.size());
            if(constructionCargos != null && !constructionCargos.isEmpty()){
//                List<ConstructionCargo> listInsert = new ArrayList<>();
//                List<ConstructionCargo> listUpdate = new ArrayList<>();
                List<ConstructionCargo> listNewConstructionCargo = new ArrayList<>();
                constructionCargos.forEach(ele -> {
                    try {
                        String cargoCode = ele.getMa_hieu();         // mã đang để là mã hiệu
                        List<ConstructionCargo> sameConstructionCargo = this.constructionCargoRepository.findAllByCargoCode(cargoCode);
                        if(sameConstructionCargo != null && !sameConstructionCargo.isEmpty()){  // nếu đã có -> update thông tin
                            for(ConstructionCargo constructionCargo : sameConstructionCargo){
//                                listUpdate.add(this.update(constructionCargo, ele, currentUser));
                                listNewConstructionCargo.add(this.update(constructionCargo, ele, currentUser));
                            }
                        }else{          // nếu ko có -> thêm mới
//                            listInsert.add(this.insert(ele, currentUser));
                            listNewConstructionCargo.add(this.insert(ele, currentUser));
                        }
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                });

//                if(!listUpdate.isEmpty()){
//                    this.updateCategoryGroup(listUpdate, currentUser);
//                }
//                if(!listInsert.isEmpty()){
//                    this.insertCatoryGroup(listInsert, currentUser);
//                }
                if(!listNewConstructionCargo.isEmpty()){
                    updateToCategory(listNewConstructionCargo ,currentUser);
                }
                return true;
            }else{
                return false;
            }
        }catch (Exception ex){
            log.error("{}", ex);
            return false;
        }finally {
            log.info("ClearCache Category.");
            this.categoryGroupCustomService.clearCache();
            try {FileUtils.deleteDirectory(folderRead);}catch (Exception e){log.error("{}", e);}
        }

    }

    private ConstructionCargo update(ConstructionCargo constructionCargoOld, ConstructionCargo constructionCargoNew, MyUserDetail cuMyUserDetail){
        if(constructionCargoOld == null || constructionCargoNew == null) return constructionCargoOld;
        constructionCargoOld.setNoiDungCongViec(constructionCargoNew.getNoi_dung_cong_viec());
        constructionCargoOld.setQuyCachKyThuat(constructionCargoNew.getQuy_cach_ky_thuat());
        constructionCargoOld.setDonViTinh(constructionCargoNew.getDon_vi_tinh());
        constructionCargoOld.setDonGiaHaNoi(constructionCargoNew.getDon_gia_ha_noi());
        constructionCargoOld.setDonGiaHCM(constructionCargoNew.getDon_gia_hcm());
        constructionCargoOld.setDonGiaHaNoiStr(constructionCargoOld.getDon_gia_ha_noi() != null ? constructionCargoOld.getDon_gia_ha_noi().toString() : "");
        constructionCargoOld.setDonGiaHCMStr(constructionCargoOld.getDon_gia_hcm() != null ? constructionCargoOld.getDon_gia_hcm().toString() : "");
        constructionCargoOld.setModifiedDate(Instant.now());
        constructionCargoOld.setModifiedName(cuMyUserDetail.getFullName());
        constructionCargoOld.setIsDelete(false);
        constructionCargoOld.setIsActive(true);
        return this.constructionCargoRepository.save(constructionCargoOld);
    }

    private ConstructionCargo insert(ConstructionCargo constructionCargo, MyUserDetail currentUser){
        if(constructionCargo == null) return constructionCargo;
        String cargoCode = constructionCargo.getMa_hieu();         // mã đang là mã hiệu
        constructionCargo.setCargoCode(cargoCode);
        constructionCargo.setDonGiaHaNoiStr(constructionCargo.getDon_gia_ha_noi() != null ? constructionCargo.getDon_gia_ha_noi().toString() : "");
        constructionCargo.setDonGiaHCMStr(constructionCargo.getDon_gia_hcm() != null ? constructionCargo.getDon_gia_hcm().toString() : "");
        constructionCargo.setCreatedName(currentUser.getFullName());
        constructionCargo.setCreatedDate(Instant.now());
        constructionCargo.setModifiedName(currentUser.getFullName());
        constructionCargo.setModifiedDate(Instant.now());
        constructionCargo.setIsDelete(false);
        constructionCargo.setIsActive(true);
        return this.constructionCargoRepository.save(constructionCargo);
    }

    /**
     * Cập nhật thông tin danh mục hàng hóa ME trong categoryGroup
     * @param constructionCargos: dánh sách mecagoro udpate
     */
    private void updateCategoryGroup(List<ConstructionCargo> constructionCargos, MyUserDetail myUserDetail){
        if(constructionCargos == null || constructionCargos.isEmpty()) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(myUserDetail.getId());
        constructionCargos.forEach(ele -> {
            try {
                List<CategoryGroup> oldCategoryGroups = this.categoryGroupRepository.findAllByCodeAndParentId(this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getMa_hieu(), getCATEGORYGROUP_CONSTRUCTIONCAGOR().getId());
                if(oldCategoryGroups != null && !oldCategoryGroups.isEmpty()){
                    oldCategoryGroups.forEach(ele1 -> {
                        try {
                            ele1.setModified(userInfo);
                            ele1.setModifiedName(myUserDetail.getFullName());
                            ele1.setModifiedDate(Instant.now());
                            ele1.setName(ele.getNoi_dung_cong_viec());
                            ele1.setIsActive(true);
                            ele1.setIsDelete(false);
                            ele1.setTennantCode(this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getId());
                            try {
                                ele1.setDescription(objectMapper.writeValueAsString(ele));
                            }catch (Exception e){log.error("{}", e);};
                            this.categoryGroupRepository.save(ele1);
                        }catch (Exception exx){
                            log.error("{}", exx);
                        }
                    });
                }

            }catch (Exception ex){
                log.error("{}", ex);
            }
        });
    }

    /**
     * thêm mới thông tin danh mục hàng hóa ME trong categoryGroup
     * @param constructionCargos: dánh sách mecagoro udpate
     */
    private void insertCatoryGroup(List<ConstructionCargo> constructionCargos, MyUserDetail myUserDetail){
        if(constructionCargos == null || constructionCargos.isEmpty()) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(myUserDetail.getId());
        constructionCargos.forEach(ele -> {
            try {
                CategoryGroup categoryGroup = new CategoryGroup();
                categoryGroup.setName(ele.getNoi_dung_cong_viec());
                categoryGroup.setCode(this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getMa_hieu());
                categoryGroup.setParent(getCATEGORYGROUP_CONSTRUCTIONCAGOR());
                categoryGroup.setIsDelete(false);
                categoryGroup.setIsActive(true);
                try{
                    categoryGroup.setDescription(objectMapper.writeValueAsString(ele));
                }catch (Exception e){
                    log.error("{}", e);
                }
                categoryGroup.setCreatedName(myUserDetail.getFullName());
                categoryGroup.setCreatedDate(Instant.now());
                categoryGroup.setCreated(userInfo);
                categoryGroup.setModifiedName(myUserDetail.getFullName());
                categoryGroup.setModifiedDate(Instant.now());
                categoryGroup.setModified(userInfo);
                categoryGroup.setTennantCode(this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getId());
                this.categoryGroupRepository.save(categoryGroup);
            }catch (Exception ex){
                log.error("{}", ex);
            }
        });
    }

    private void updateToCategory(List<ConstructionCargo> constructionCargos, MyUserDetail myUserDetail){
        if(constructionCargos == null || constructionCargos.isEmpty()) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(myUserDetail.getId());
        constructionCargos.forEach(ele -> {
            try {
                String code = this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getMa_hieu();
                List<CategoryGroup> categoryGroupListOld = this.categoryGroupRepository.findAllByCodeAndParentId(code, this.getCATEGORYGROUP_CONSTRUCTIONCAGOR().getId());
                if(categoryGroupListOld != null && !categoryGroupListOld.isEmpty()){        // th có trong DB -> udpate
                    categoryGroupListOld.forEach(ele1 -> {
                        try {
                            ele1.setModified(userInfo);
                            ele1.setModifiedName(myUserDetail.getFullName());
                            ele1.setModifiedDate(Instant.now());
                            ele1.setName(ele.getNoi_dung_cong_viec());
                            ele1.setIsActive(true);
                            ele1.setIsDelete(false);
                            ele1.setTennantCode(this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getId());
                            try {
                                ele1.setDescription(objectMapper.writeValueAsString(ele));
                            }catch (Exception e){log.error("{}", e);};
                            this.categoryGroupRepository.save(ele1);
                        }catch (Exception exx){
                            log.error("{}", exx);
                        }
                    });
                }else {
                    CategoryGroup categoryGroup = new CategoryGroup();
                    categoryGroup.setName(ele.getNoi_dung_cong_viec());
                    categoryGroup.setCode(code);
                    categoryGroup.setParent(getCATEGORYGROUP_CONSTRUCTIONCAGOR());
                    categoryGroup.setIsDelete(false);
                    categoryGroup.setIsActive(true);
                    try{
                        categoryGroup.setDescription(objectMapper.writeValueAsString(ele));
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                    categoryGroup.setCreatedName(myUserDetail.getFullName());
                    categoryGroup.setCreatedDate(Instant.now());
                    categoryGroup.setCreated(userInfo);
                    categoryGroup.setModifiedName(myUserDetail.getFullName());
                    categoryGroup.setModifiedDate(Instant.now());
                    categoryGroup.setModified(userInfo);
                    categoryGroup.setTennantCode(this.PREFIX_CODE_CONSTRUCTIONCARGO + ele.getId());
                    this.categoryGroupRepository.save(categoryGroup);
                }
            }catch (Exception ex){
                log.error("{}", ex);
            }
        });
    }
}
