package com.vsm.business.service.bp.schedule;

import com.vsm.business.domain.BusinessPartner;
import com.vsm.business.domain.MallAndStall;
import com.vsm.business.repository.MallAndStallRepository;
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
public class MallAndStallSchedule {

    private final Logger log = LoggerFactory.getLogger(MallAndStallSchedule.class);

    private final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
//    private final File ExcelFolder = new File(("./temp/excel").replace("/", FileSystems.getDefault().getSeparator()));
    @Value("${bp-schedule.folder-sync:/temp/excel}")
    public String ExcelFolder;

    private final String PATH_MALLANDSTALL_FOLDER = "MallAndStall";
    private final List<String> LIST_FIELD_NAME = Arrays.asList("stt", "companyCode", "profitCenter", "profitCenterName", "businessEntity", "maKhachHang", "tenKhachHang", "tenNVKD", "soLo", "tenGianHang", "dienTich", "maHopDong", "ngayKetThucHD");

    @Autowired
    public ExcelUtils excelUtils;

    @Autowired
    public MallAndStallRepository mallAndStallRepository;

//    @Async
//    @Scheduled(fixedRate = 480000)            // 1000 * 60 * 8 = 8 minute
    public void syncMallAndStall() throws IOException, InvalidFormatException {
        File MALLANDSTALLFOLDER = new File(ExcelFolder + PATH_SEPARATOR + PATH_MALLANDSTALL_FOLDER);
        List<MallAndStall> mallAndStallList = excelUtils.readAllExcelV1(MALLANDSTALLFOLDER, LIST_FIELD_NAME, MallAndStall.class);
        AtomicInteger update = new AtomicInteger();     // số lượng update
        AtomicInteger create = new AtomicInteger();     // số lượng thêm mới
        AtomicInteger same = new AtomicInteger();       // số lượng không thay đổi so với DB
        log.info("syncMallAndStallList run: {} record.", mallAndStallList.size());
        if(mallAndStallList != null && !mallAndStallList.isEmpty()){
            mallAndStallList.forEach(ele -> {
                try {
                    List<MallAndStall> sameMallAndStall = this.mallAndStallRepository.findAllByCompanyCodeAndMaKhachHang(ele.getCompanyCode(), ele.getMaKhachHang());
                    if(sameMallAndStall != null && !sameMallAndStall.isEmpty()){
                        sameMallAndStall.forEach(ele1 -> {
                            if(!checkSame(ele, ele1)){
                                this.mallAndStallRepository.save(this.initToDB(this.copyDataBusinessPartnet(ele, ele1)));
                                update.set(update.getAndIncrement()+1);
                            }else{
                                same.set(same.getAndIncrement()+1);
                            }
                        });
                    }else{
                        this.mallAndStallRepository.save(this.initToDB(ele));
                    create.set(create.getAndIncrement()+1);
                    }
                }catch (Exception e){
                    log.debug("{}", e);
                }
            });
        }
        log.info("syncMallAndStallList run done: update: {} record, create: {} record, same: {} record.", update.getAndIncrement(), create.getAndIncrement(), same.getAndIncrement());
    }

    /**
     * Hàm thực hiện coppy giá trị từ source sang target vẫn giữ lại trường ID của target
     * @param source    : object chứa giá trị cần coppy
     * @param target    : object đích cần coppy giá trị sang
     * @return          : trả về object sau khi coppy xong (target)
     */
    private MallAndStall copyDataBusinessPartnet(MallAndStall source, MallAndStall target){
        if(source == null) return null;
        if(target == null) target = new MallAndStall();
        BeanUtils.copyProperties(source, target, "id", "isSyncFromSAP", "timeSync", "description", "createdName", "createdOrgName", "createdRankName", "createdDate", "modifiedName", "modifiedDate", "isActive", "isDelete");
        return target;
    }

    /**
     * Hàm kiểm tra xem dữ liệu của BP mới đọc được và MallAndStall trong MallAndStall có khác nhau không (ko so sánh trường ID)
     * @param mallAndStallNew     : object đọc từ file
     * @param mallAndStallOld     : object lấy từ DB
     * @return          : true nếu giống nhau, false nếu khác nhau
     */
    private boolean checkSame(MallAndStall mallAndStallNew, MallAndStall mallAndStallOld){
        if(mallAndStallNew == null && mallAndStallOld != null) return false;
        if(mallAndStallNew != null && mallAndStallOld == null) return false;
        MallAndStall mallAndStall = new MallAndStall();
        BeanUtils.copyProperties(mallAndStallOld, mallAndStall);
        mallAndStall.setId(null);
        return this.customEquals(mallAndStallNew, mallAndStall);
    }

    /**
     * Hàm thực hiện thêm dữ liệu trên Eoffice vào(vd: thời gian đồng bộ)
     * @param mallAndStall   : MallAndStall đọc được từ khi đồng bộ
     * @return : trả về BP để lưu vào trong DB
     */
    private MallAndStall initToDB(MallAndStall mallAndStall){
        mallAndStall.setTimeSync(Instant.now());
        if(mallAndStall.getCreatedDate() == null) mallAndStall.setCreatedDate(Instant.now());
        if(mallAndStall.getCreatedName() == null) mallAndStall.setCreatedName("SAP");
        if(mallAndStall.getCreatedOrgName() == null) mallAndStall.setCreatedOrgName("SAP");
        mallAndStall.setModifiedName("SAP");
        mallAndStall.setModifiedDate(Instant.now());
        mallAndStall.setIsActive(true);
        mallAndStall.setIsDelete(false);
        mallAndStall.setIsSyncFromSAP(true);
        return mallAndStall;
    }

    public boolean customEquals(MallAndStall this_, Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MallAndStall that = (MallAndStall) o;
        return Objects.equals(this_.getStt(), that.getStt()) && Objects.equals(this_.getCompanyCode(), that.getCompanyCode()) && Objects.equals(this_.getProfitCenter(), that.getProfitCenter()) && Objects.equals(this_.getProfitCenterName(), that.getProfitCenterName()) && Objects.equals(this_.getBusinessEntity(), that.getBusinessEntity()) && Objects.equals(this_.getMaKhachHang(), that.getMaKhachHang()) && Objects.equals(this_.getTenKhachHang(), that.getTenKhachHang()) && Objects.equals(this_.getTenNVKD(), that.getTenNVKD()) && Objects.equals(this_.getSoLo(), that.getSoLo()) && Objects.equals(this_.getTenGianHang(), that.getTenGianHang()) && Objects.equals(this_.getDienTich(), that.getDienTich()) && Objects.equals(this_.getMaHopDong(), that.getMaHopDong()) && Objects.equals(this_.getNgayKetThucHD(), that.getNgayKetThucHD());
    }
}
