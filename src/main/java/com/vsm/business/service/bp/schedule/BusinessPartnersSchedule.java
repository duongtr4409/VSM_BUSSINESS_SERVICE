package com.vsm.business.service.bp.schedule;

import com.vsm.business.domain.BusinessPartner;
import com.vsm.business.repository.BusinessPartnerRepository;
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
public class BusinessPartnersSchedule {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnersSchedule.class);

    private final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();
//    private final File ExcelFolder = new File(("./temp/excel").replace("/", FileSystems.getDefault().getSeparator()));
    @Value("${bp-schedule.folder-sync:/temp/excel}")
    public String ExcelFolder;

    private final String PATH_BUSINESS_FOLDER = "BusinessPartner";
    private final List<String> LIST_FIELD_NAME = Arrays.asList("maChuoi" , "tenChuoi" , "addressNumber" , "customer" , "name" , "street" , "telephone" , "vatTegistrationNo" , "eMailAddress1" , "eMailAddress2");

    @Autowired
    public ExcelUtils excelUtils;

    @Autowired
    public BusinessPartnerRepository businessPartnerRepository;

//    @Async
//    @Scheduled(fixedRate = 480000)            // 1000 * 60 * 8 = 8 minute
    public void syncBusinessPartner() throws IOException, InvalidFormatException {
        File BUSINESSFOLDER = new File(ExcelFolder + PATH_SEPARATOR + PATH_BUSINESS_FOLDER);
        List<BusinessPartner> businessPartnerList = excelUtils.readAllExcelV1(BUSINESSFOLDER, LIST_FIELD_NAME, BusinessPartner.class);
        AtomicInteger update = new AtomicInteger();     // số lượng update
        AtomicInteger create = new AtomicInteger();     // số lượng thêm mới
        AtomicInteger same = new AtomicInteger();       // số lượng không thay đổi so với DB
        log.info("syncBusinessPartner run: {} record.", businessPartnerList.size());
        if(businessPartnerList != null && !businessPartnerList.isEmpty()){
            businessPartnerList.forEach(ele -> {
                try {
                    List<BusinessPartner> sameBusinessPartner = this.businessPartnerRepository.findAllByMaChuoiAndCustomer(ele.getMaChuoi(), ele.getCustomer());
                    if(sameBusinessPartner != null && !sameBusinessPartner.isEmpty()){
                        sameBusinessPartner.forEach(ele1 -> {
                            if(!checkSame(ele, ele1)){
                                this.businessPartnerRepository.save(this.initToDB(this.copyDataBusinessPartnet(ele, ele1)));
                                update.set(update.getAndIncrement()+1);
                            }else{
                                same.set(same.getAndIncrement()+1);
                            }
                        });
                    }else{
                        this.businessPartnerRepository.save(this.initToDB(ele));
                        create.set(create.getAndIncrement()+1);
                    }
                }catch (Exception e){
                    log.debug("{}", e);
                }
            });
        }
        log.info("syncBusinessPartner run done: update: {} record, create: {} record, same: {} record.", update.getAndIncrement(), create.getAndIncrement(), same.getAndIncrement());
    }

    /**
     * Hàm thực hiện coppy giá trị từ source sang target vẫn giữ lại trường ID của target
     * @param source    : object chứa giá trị cần coppy
     * @param target    : object đích cần coppy giá trị sang
     * @return          : trả về object sau khi coppy xong (target)
     */
    private BusinessPartner copyDataBusinessPartnet(BusinessPartner source, BusinessPartner target){
        if(source == null) return null;
        if(target == null) target = new BusinessPartner();
        BeanUtils.copyProperties(source, target, "id", "isSyncFromSAP", "timeSync", "description", "createdName", "createdOrgName", "createdRankName", "createdDate", "modifiedName", "modifiedDate", "isActive", "isDelete");
        return target;
    }

    /**
     * Hàm kiểm tra xem dữ liệu của BP mới đọc được và BP trong DB có khác nhau không (ko so sánh trường ID)
     * @param bpNew     : object đọc từ file
     * @param bpOld     : object lấy từ DB
     * @return          : true nếu giống nhau, false nếu khác nhau
     */
    private boolean checkSame(BusinessPartner bpNew, BusinessPartner bpOld){
        if(bpNew == null && bpOld != null) return false;
        if(bpNew != null && bpOld == null) return false;
        BusinessPartner businessPartner = new BusinessPartner();
        BeanUtils.copyProperties(bpOld, businessPartner);
        businessPartner.setId(null);
        return this.customEquals(bpNew, businessPartner);
    }

    /**
     * Hàm thực hiện thêm dữ liệu trên Eoffice vào(vd: thời gian đồng bộ)
     * @param businessPartner   : DP đọc được từ khi đồng bộ
     * @return : trả về BP để lưu vào trong DB
     */
    private BusinessPartner initToDB(BusinessPartner businessPartner){
        businessPartner.setTimeSync(Instant.now());
        if(businessPartner.getCreatedDate() == null) businessPartner.setCreatedDate(Instant.now());
        if(businessPartner.getCreatedName() == null) businessPartner.setCreatedName("SAP");
        if(businessPartner.getCreatedOrgName() == null) businessPartner.setCreatedOrgName("SAP");
        businessPartner.setModifiedName("SAP");
        businessPartner.setModifiedDate(Instant.now());
        businessPartner.setIsActive(true);
        businessPartner.setIsDelete(false);
        businessPartner.setIsSyncFromSAP(true);
        return businessPartner;
    }

    public boolean customEquals(BusinessPartner this_, Object o) {
        if (this_ == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessPartner that = (BusinessPartner) o;
        return Objects.equals(this_.getMaChuoi(), that.getMaChuoi()) && Objects.equals(this_.getTenChuoi(), that.getTenChuoi()) && Objects.equals(this_.getAddressNumber(), that.getAddressNumber()) && Objects.equals(this_.getCustomer(), that.getCustomer()) && Objects.equals(this_.getName(), that.getName()) && Objects.equals(this_.getStreet(), that.getStreet()) && Objects.equals(this_.getTelephone(), that.getTelephone()) && Objects.equals(this_.getVatTegistrationNo(), that.getVatTegistrationNo()) && Objects.equals(this_.geteMailAddress1(), that.geteMailAddress1()) && Objects.equals(this_.geteMailAddress2(), that.geteMailAddress2());
    }
}
