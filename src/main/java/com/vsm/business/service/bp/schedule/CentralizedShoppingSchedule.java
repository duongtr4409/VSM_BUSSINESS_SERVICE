package com.vsm.business.service.bp.schedule;

import com.vsm.business.domain.CentralizedShopping;
import com.vsm.business.repository.CentralizedShoppingRepository;
import com.vsm.business.utils.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@EnableAsync
@Component
public class CentralizedShoppingSchedule {
    private final Logger log = LoggerFactory.getLogger(CentralizedShoppingSchedule.class);

    private final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
    //private final File ExcelFolder = new File(("./temp/excel").replace("/", FileSystems.getDefault().getSeparator()));
    @Value("${bp-schedule.folder-sync:/temp/excel}")
    public String ExcelFolder;
    private final String PATH_CENTRALIZEDSHOPPING_FOLDER = "CentralizedShopping";
    private final List<String> LIST_FIELD_NAME = Arrays.asList("stt", "bangSo", "level1", "tenNhomLevel1", "level2", "tenNhomLevel2", "level3", "tenNhomLevel3", "level4", "tenNhomLevel4", "maChungPnL", "shortName", "dVTSAP", "doDaiShortName", "fullName", "pLSuDung", "pICYeuCauTaoMa", "cBLDPnLPheDuyet", "noteKhoaMa", "ngayTaoMa", "ghiChuKhac", "iDGuiITXuLy");

    @Autowired
    public ExcelUtils excelUtils;

    @Autowired
    public CentralizedShoppingRepository centralizedShoppingRepository;

//    @Async
//    @Scheduled(fixedRate = 480000)            // 1000 * 60 * 8 = 8 minute
    public void syncCentralizedShopping() throws IOException, InvalidFormatException {
        File CENTRALIZEDSHOPPINGFOLDER = new File(ExcelFolder + PATH_SEPARATOR + PATH_CENTRALIZEDSHOPPING_FOLDER);
        List<CentralizedShopping> centralizedShoppings = excelUtils.readAllExcelV1(CENTRALIZEDSHOPPINGFOLDER, LIST_FIELD_NAME, CentralizedShopping.class);
        AtomicInteger update = new AtomicInteger();     // số lượng update
        AtomicInteger create = new AtomicInteger();     // số lượng thêm mới
        AtomicInteger same = new AtomicInteger();       // số lượng không thay đổi so với DB
        log.info("syncCentralizedShopping run: {} record.", centralizedShoppings.size());
        if(centralizedShoppings != null && !centralizedShoppings.isEmpty()){
            centralizedShoppings.forEach(ele -> {
                try {
                    List<CentralizedShopping> sameCentralizedShopping = this.centralizedShoppingRepository.findAllByMaChungPnL(ele.getMaChungPnL());
                    if(sameCentralizedShopping != null && !sameCentralizedShopping.isEmpty()){
                        sameCentralizedShopping.forEach(ele1 -> {
                            if(!checkSame(ele, ele1)){
                                this.centralizedShoppingRepository.save(this.initToDB(this.copyDataBusinessPartnet(ele, ele1)));
                                update.set(update.getAndIncrement()+1);
                            }else{
                                same.set(same.getAndIncrement()+1);
                            }
                        });
                    }else{
                        this.centralizedShoppingRepository.save(this.initToDB(ele));
                        create.set(create.getAndIncrement()+1);
                    }
                }catch (Exception e){
                    log.debug("{}", e);
                }
            });
        }
        log.info("syncCentralizedShopping run done: update: {} record, create: {} record, same: {} record.", update.getAndIncrement(), create.getAndIncrement(), same.getAndIncrement());
    }

    /**
     * Hàm thực hiện coppy giá trị từ source sang target vẫn giữ lại trường ID của target
     * @param source    : object chứa giá trị cần coppy
     * @param target    : object đích cần coppy giá trị sang
     * @return          : trả về object sau khi coppy xong (target)
     */
    private CentralizedShopping copyDataBusinessPartnet(CentralizedShopping source, CentralizedShopping target){
        if(source == null) return null;
        if(target == null) target = new CentralizedShopping();
        BeanUtils.copyProperties(source, target, "id", "isSyncFromSAP", "timeSync", "description", "createdName", "createdOrgName", "createdRankName", "createdDate", "modifiedName", "modifiedDate", "isActive", "isDelete");
        return target;
    }

    /**
     * Hàm kiểm tra xem dữ liệu của BP mới đọc được và CentralizedShopping trong MallAndStall có khác nhau không (ko so sánh trường ID)
     * @param centralizedShoppingNew     : object đọc từ file
     * @param centralizedShoppingOld     : object lấy từ DB
     * @return          : true nếu giống nhau, false nếu khác nhau
     */
    private boolean checkSame(CentralizedShopping centralizedShoppingNew, CentralizedShopping centralizedShoppingOld){
        if(centralizedShoppingNew == null && centralizedShoppingOld != null) return false;
        if(centralizedShoppingNew != null && centralizedShoppingOld == null) return false;
        CentralizedShopping centralizedShopping = new CentralizedShopping();
        BeanUtils.copyProperties(centralizedShoppingOld, centralizedShopping);
        centralizedShopping.setId(null);
        return this.customEquals(centralizedShoppingNew, centralizedShopping);
    }

    /**
     * Hàm thực hiện thêm dữ liệu trên Eoffice vào(vd: thời gian đồng bộ, ...)
     * @param centralizedShopping   : CentralizedShopping đọc được từ khi đồng bộ
     * @return : trả về BP để lưu vào trong DB
     */
    private CentralizedShopping initToDB(CentralizedShopping centralizedShopping){
        centralizedShopping.setTimeSync(Instant.now());
        if(centralizedShopping.getCreatedDate() == null) centralizedShopping.setCreatedDate(Instant.now());
        if(centralizedShopping.getCreatedName() == null) centralizedShopping.setCreatedName("SAP");
        if(centralizedShopping.getCreatedOrgName() == null) centralizedShopping.setCreatedOrgName("SAP");
        centralizedShopping.setModifiedName("SAP");
        centralizedShopping.setModifiedDate(Instant.now());
        centralizedShopping.setIsActive(true);
        centralizedShopping.setIsDelete(false);
        centralizedShopping.setIsSyncFromSAP(true);
        return centralizedShopping;
    }

    public boolean customEquals(CentralizedShopping this_, Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CentralizedShopping that = (CentralizedShopping) o;
        return Objects.equals(this_.getStt(), that.getStt()) && Objects.equals(this_.getBangSo(), that.getBangSo()) && Objects.equals(this_.getLevel1(), that.getLevel1()) && Objects.equals(this_.getTenNhomLevel1(), that.getTenNhomLevel1()) && Objects.equals(this_.getLevel2(), that.getLevel2()) && Objects.equals(this_.getTenNhomLevel2(), that.getTenNhomLevel2()) && Objects.equals(this_.getLevel3(), that.getLevel3()) && Objects.equals(this_.getTenNhomLevel3(), that.getTenNhomLevel3()) && Objects.equals(this_.getLevel4(), that.getLevel4()) && Objects.equals(this_.getTenNhomLevel4(), that.getTenNhomLevel4()) && Objects.equals(this_.getMaChungPnL(), that.getMaChungPnL()) && Objects.equals(this_.getShortName(), that.getShortName()) && Objects.equals(this_.getdVTSAP(), that.getdVTSAP()) && Objects.equals(this_.getDoDaiShortName(), that.getDoDaiShortName()) && Objects.equals(this_.getFullName(), that.getFullName()) && Objects.equals(this_.getpLSuDung(), that.getpLSuDung()) && Objects.equals(this_.getpICYeuCauTaoMa(), that.getpICYeuCauTaoMa()) && Objects.equals(this_.getcBLDPnLPheDuyet(), that.getcBLDPnLPheDuyet()) && Objects.equals(this_.getNoteKhoaMa(), that.getNoteKhoaMa()) && Objects.equals(this_.getNgayTaoMa(), that.getNgayTaoMa()) && Objects.equals(this_.getGhiChuKhac(), that.getGhiChuKhac()) && Objects.equals(this_.getiDGuiITXuLy(), that.getiDGuiITXuLy());
    }
}
