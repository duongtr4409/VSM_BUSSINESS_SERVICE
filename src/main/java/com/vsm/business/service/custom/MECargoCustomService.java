package com.vsm.business.service.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vsm.business.domain.*;
import com.vsm.business.repository.CategoryGroupRepository;
import com.vsm.business.repository.ConstructionCargoRepository;
import com.vsm.business.repository.MECargoRepository;
import com.vsm.business.repository.search.ConstructionCargoSearchRepository;
import com.vsm.business.repository.search.MECargoSearchRepository;
import com.vsm.business.service.auth.bo.MyUserDetail;
import com.vsm.business.service.dto.ConstructionCargoDTO;
import com.vsm.business.service.dto.MECargoDTO;
import com.vsm.business.service.mapper.ConstructionCargoMapper;
import com.vsm.business.service.mapper.MECargoMapper;
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
public class MECargoCustomService {
    private final Logger log = LoggerFactory.getLogger(MECargoCustomService.class);

    private MECargoRepository meCargoRepository;

    private MECargoSearchRepository meCargoSearchRepository;

    private MECargoMapper meCargoMapper;

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryGroupCustomService categoryGroupCustomService;

    private final ExcelUtils excelUtils;

    private final UserUtils userUtils;

    public final ObjectMapper objectMapper = new ObjectMapper();

    public static final String PREFIX_CODE_MECARGO = "mecargo_";

    @Value("${cargo.me_cargo_name:Hàng hóa ME}")
    private String ME_CARGO_NAME;

    @Value("${cargo.me_cargo_code:HANG_HOA_ME}")
    private String ME_CARGO_CODE;

    public MECargoCustomService(MECargoRepository meCargoRepository, MECargoSearchRepository meCargoSearchRepository, MECargoMapper meCargoMapper, CategoryGroupRepository categoryGroupRepositor, ExcelUtils excelUtils, UserUtils userUtils, CategoryGroupCustomService categoryGroupCustomService) {
        this.meCargoRepository = meCargoRepository;
        this.meCargoSearchRepository = meCargoSearchRepository;
        this.meCargoMapper = meCargoMapper;
        this.categoryGroupRepository = categoryGroupRepositor;
        this.excelUtils = excelUtils;
        this.userUtils = userUtils;
        this.categoryGroupCustomService = categoryGroupCustomService;
    }

    public CategoryGroup CATEGORYGROUP_MECARGO;

    public CategoryGroup getCATEGORYGROUP_MECARGO() {
        return CATEGORYGROUP_MECARGO;
    }

    public void setCATEGORYGROUP_MECARGO(CategoryGroup CATEGORYGROUP_MECARGO) {
        this.CATEGORYGROUP_MECARGO = CATEGORYGROUP_MECARGO;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void INIT_DANH_MUC(){
        try {
            List<CategoryGroup> categoryGroupList = this.categoryGroupRepository.findAllByCode(ME_CARGO_CODE);
            if(categoryGroupList != null && !categoryGroupList.isEmpty()){
                setCATEGORYGROUP_MECARGO( categoryGroupList.get(0));
            }else{
                CategoryGroup categoryGroup = new CategoryGroup();
                categoryGroup.setName(ME_CARGO_NAME);
                categoryGroup.setCode(ME_CARGO_CODE);
                categoryGroup.setIsActive(true);
                categoryGroup.setIsDelete(false);
                categoryGroup.setCreatedDate(Instant.now());
                categoryGroup.setModifiedDate(Instant.now());
                categoryGroup.setCreatedName("ADMIN");
                categoryGroup.setModifiedName("ADMIN");
                categoryGroup.setDescription("");
                setCATEGORYGROUP_MECARGO(this.categoryGroupRepository.save(categoryGroup));
            }
            log.info("CATEGORYGROUP_MECAGOR: {}", getCATEGORYGROUP_MECARGO());
        }catch (Exception e){
            log.error("{}", e);
        }
    }

    public List<MECargoDTO> getAll() {
        log.debug("MECargoCustomService: getAll()");
        List<MECargoDTO> result = new ArrayList<>();
        try {
            List<MECargo> meCargoList = this.meCargoRepository.findAll();
            for (MECargo meCargo :
                meCargoList) {
                MECargoDTO constructionCargoDTO = meCargoMapper.toDto(meCargo);
                result.add(constructionCargoDTO);
            }
        }catch (Exception e){
            log.error("MECargoCustomService: getAll() {}", e);
        }
        log.debug("MECargoCustomService: getAll() {}", result);
        return result;
    }

    public List<String> getAllMaHieu(){
        log.debug("MECargoCustomService: getAllMaHieu()");
        List<String> result = this.meCargoRepository.getAllMaHieu();
        return result;
    }

    public List<MECargoDTO> deleteAll(List<MECargoDTO> meCargoDTOS) {
        log.debug("MECargoCustomService: deleteAll({})", meCargoDTOS);
        List<Long> ids = meCargoDTOS.stream().map(MECargoDTO::getId).collect(Collectors.toList());
        this.meCargoRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        try {
            meCargoRepository.deleteById(id);
            meCargoSearchRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("MECargoCustomService: delete({}) {}", id, e.getStackTrace());
            return false;
        }
    }

    public List<MECargoDTO> saveAll(List<MECargoDTO> meCargoDTOS){
        List<MECargoDTO> result = meCargoRepository.saveAll(meCargoDTOS.stream().map(meCargoMapper::toEntity).collect(Collectors.toList())).stream().map(meCargoMapper::toDto).collect(Collectors.toList());
        log.debug("MECargoCustomService: saveAll({}) {}", meCargoDTOS, result);
        return result;
    }

    private final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    @Value("${bp-schedule.folder-sync:/temp/excel}")
    public String ExcelFolder;

    private List<String> LIST_FIELD_NAME = Arrays.asList("stt", "noi_dung_cong_viec", "quy_cach_ky_thuat", "ma_hieu", "don_vi_tinh", "don_gia_vat_tu_vat_lieu", "don_gia_nhan_cong");

    public boolean importCargo(MultipartFile file) throws IOException, InvalidFormatException {
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.findAndRegisterModules();

        MyUserDetail currentUser = this.userUtils.getCurrentUser();         // lấy thông tin user đang đăng nhập
        String folderName = this.ExcelFolder + this.PATH_SEPARATOR +System.currentTimeMillis();
        File folderRead = new File(folderName);
        try {
            try {
                FileUtils.writeByteArrayToFile(new File(folderName + this.PATH_SEPARATOR + file.getOriginalFilename()), file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<MECargo> meCargoList = excelUtils.readAllExcelV1(folderRead, LIST_FIELD_NAME, MECargo.class);
            log.info("MECargoCustomService: import(): {}", meCargoList.size());
            if(meCargoList != null && !meCargoList.isEmpty()){
//                List<MECargo> listInsert = new ArrayList<>();
//                List<MECargo> listUpdate = new ArrayList<>();
                List<MECargo> listNewMECargo = new ArrayList<>();
                meCargoList.forEach(ele -> {
                    try {
                        String cargoCode = ele.getMa_hieu();         // mã đang để là mã hiệu
                        List<MECargo> sameMECargo = this.meCargoRepository.findAllByCargoCode(cargoCode);
                        if(sameMECargo != null && !sameMECargo.isEmpty()){  // nếu đã có -> update thông tin
                            for(MECargo meCargo : sameMECargo){
//                                listUpdate.add(this.update(meCargo, ele, currentUser));
                                listNewMECargo.add(this.update(meCargo, ele, currentUser));
                            }
                        }else{          // nếu ko có -> thêm mới
//                            listInsert.add(this.insert(ele, currentUser));
                            listNewMECargo.add(this.insert(ele, currentUser));
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
                if(!listNewMECargo.isEmpty()){
                    this.updateToCategory(listNewMECargo, currentUser);
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

    private MECargo update(MECargo meCargoOld, MECargo meCargoNew, MyUserDetail cuMyUserDetail){
        if(meCargoOld == null || meCargoNew == null) return meCargoOld;
        meCargoOld.setNoiDungCongViec(meCargoNew.getNoi_dung_cong_viec());
        meCargoOld.setQuyCachKyThuat(meCargoNew.getQuy_cach_ky_thuat());
        meCargoOld.setDonViTinh(meCargoNew.getDon_vi_tinh());
        meCargoOld.setDonGiaVatTuVatLieu(meCargoNew.getDon_gia_vat_tu_vat_lieu());
        meCargoOld.setDonGiaNhanCong(meCargoNew.getDon_gia_nhan_cong());
        meCargoOld.setDonGiaVatTuVatLieuStr(meCargoOld.getDon_gia_vat_tu_vat_lieu() != null ? meCargoOld.getDon_gia_vat_tu_vat_lieu().toString() : "");
        meCargoOld.setDonGiaNhanCongStr(meCargoOld.getDon_gia_nhan_cong() != null ? meCargoOld.getDon_gia_nhan_cong().toString() : "");
        meCargoOld.setModifiedDate(Instant.now());
        meCargoOld.setModifiedName(cuMyUserDetail.getFullName());
        meCargoOld.setIsDelete(false);
        meCargoOld.setIsActive(true);
        return this.meCargoRepository.save(meCargoOld);
    }

    private MECargo insert(MECargo meCargo, MyUserDetail currentUser){
        if(meCargo == null) return meCargo;
        String cargoCode = meCargo.getMa_hieu();         // mã đang là mã hiệu
        meCargo.setCargoCode(cargoCode);
        meCargo.setDonGiaNhanCongStr(meCargo.getDon_gia_nhan_cong() != null ? meCargo.getDon_gia_nhan_cong().toString() : "");
        meCargo.setDonGiaVatTuVatLieuStr(meCargo.getDon_gia_vat_tu_vat_lieu() != null ? meCargo.getDon_gia_vat_tu_vat_lieu().toString() : "");
        meCargo.setCreatedName(currentUser.getFullName());
        meCargo.setCreatedDate(Instant.now());
        meCargo.setModifiedName(currentUser.getFullName());
        meCargo.setModifiedDate(Instant.now());
        meCargo.setIsDelete(false);
        meCargo.setIsActive(true);
        return this.meCargoRepository.save(meCargo);
    }

    /**
     * Cập nhật thông tin danh mục hàng hóa ME trong categoryGroup
     * @param meCargoList: dánh sách mecagoro udpate
     */
    private void updateCategoryGroup(List<MECargo> meCargoList, MyUserDetail myUserDetail){
        if(meCargoList == null || meCargoList.isEmpty()) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(myUserDetail.getId());
        meCargoList.forEach(ele -> {
            try {
                List<CategoryGroup> oldCategoryGroups = this.categoryGroupRepository.findAllByCodeAndParentId(this.PREFIX_CODE_MECARGO + ele.getMa_hieu(), getCATEGORYGROUP_MECARGO().getId());
                if(oldCategoryGroups != null && !oldCategoryGroups.isEmpty()){
                    oldCategoryGroups.forEach(ele1 -> {
                        try {
                            ele1.setModified(userInfo);
                            ele1.setModifiedName(myUserDetail.getFullName());
                            ele1.setModifiedDate(Instant.now());
                            ele1.setName(ele.getNoi_dung_cong_viec());
                            ele1.setIsActive(true);
                            ele1.setIsDelete(false);
                            ele1.setTennantCode(PREFIX_CODE_MECARGO + ele.getId());
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
     * @param meCargoList: dánh sách mecagoro udpate
     */
    private void insertCatoryGroup(List<MECargo> meCargoList, MyUserDetail myUserDetail){
        if(meCargoList == null || meCargoList.isEmpty()) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(myUserDetail.getId());
        meCargoList.forEach(ele -> {
            try {
                CategoryGroup categoryGroup = new CategoryGroup();
                categoryGroup.setName(ele.getNoi_dung_cong_viec());
                categoryGroup.setCode(this.PREFIX_CODE_MECARGO + ele.getMa_hieu());
                categoryGroup.setParent(getCATEGORYGROUP_MECARGO());
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
                categoryGroup.setTennantCode(PREFIX_CODE_MECARGO + ele.getId());
                this.categoryGroupRepository.save(categoryGroup);
            }catch (Exception ex){
                log.error("{}", ex);
            }
        });
    }

    public void updateToCategory(List<MECargo> meCargoList, MyUserDetail myUserDetail){
        if(meCargoList == null || meCargoList.isEmpty()) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setId(myUserDetail.getId());
        meCargoList.forEach(ele -> {
            try {
                String code = this.PREFIX_CODE_MECARGO + ele.getMa_hieu();
                List<CategoryGroup> categoryGroupListOld = this.categoryGroupRepository.findAllByCodeAndParentId(code, getCATEGORYGROUP_MECARGO().getId());
                if(categoryGroupListOld != null && !categoryGroupListOld.isEmpty()){
                    categoryGroupListOld.forEach(ele1 -> {
                        try {
                            ele1.setModified(userInfo);
                            ele1.setModifiedName(myUserDetail.getFullName());
                            ele1.setModifiedDate(Instant.now());
                            ele1.setName(ele.getNoi_dung_cong_viec());
                            ele1.setIsActive(true);
                            ele1.setIsDelete(false);
                            ele1.setTennantCode(PREFIX_CODE_MECARGO + ele.getId());
                            try {
                                ele1.setDescription(objectMapper.writeValueAsString(ele));
                            }catch (Exception e){log.error("{}", e);};
                            this.categoryGroupRepository.save(ele1);
                        }catch (Exception exx){
                            log.error("{}", exx);
                        }
                    });
                }else{
                    CategoryGroup categoryGroup = new CategoryGroup();
                    categoryGroup.setName(ele.getNoi_dung_cong_viec());
                    categoryGroup.setCode(this.PREFIX_CODE_MECARGO + ele.getMa_hieu());
                    categoryGroup.setParent(getCATEGORYGROUP_MECARGO());
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
                    categoryGroup.setTennantCode(this.PREFIX_CODE_MECARGO + ele.getId());
                    this.categoryGroupRepository.save(categoryGroup);
                }
            }catch (Exception ex){
                log.error("{}", ex);
            }
        });
    }
}
