package com.vsm.business.service.custom.sign.utils;

import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SignUtils {

    private final Logger log = LoggerFactory.getLogger(SignUtils.class);

    @Value("${system.check.ky-so-code:0}")
    public String KY_SO_CODE;

    @Value("${system.check.hide-primary-file-code:HIDE_PRIMARY_FILE}")
    public String KY_SO_CODE_HIDE;

    @Value("${system.file.customer-file-code:CUSTOMER}")
    public String CUSTOMER_FILE_CODE;

    public final String OLD_SIGN_PREFIX = "OLD_SIGN_";

    private final ProcessDataRepository processDataRepository;
    private final StepDataRepository stepDataRepository;

    private final ReqdataProcessHisRepository reqdataProcessHisRepository;

    private final RequestDataRepository requestDataRepository;

    private final AttachmentFileRepository attachmentFileRepository;

    private final SignDataRepository signDataRepository;

    public SignUtils(ProcessDataRepository processDataRepository, StepDataRepository stepDataRepository, ReqdataProcessHisRepository reqdataProcessHisRepository, RequestDataRepository requestDataRepository, AttachmentFileRepository attachmentFileRepository, SignDataRepository signDataRepository) {
        this.processDataRepository = processDataRepository;
        this.stepDataRepository = stepDataRepository;
        this.reqdataProcessHisRepository = reqdataProcessHisRepository;
        this.requestDataRepository = requestDataRepository;
        this.attachmentFileRepository = attachmentFileRepository;
        this.signDataRepository = signDataRepository;
    }

    /**
     * Hàm thực hiện kiểm tra điều kiện để ký số của 1 phiếu yêu cầu(requestData)
     *
     * @param requestData : phiếu cần kiểm tra
     * @param userInfo    : người thực hiện ký
     * @return : true: có thể ký, false: không thể ký
     */
    public boolean checkCondition(RequestData requestData, UserInfo userInfo) {
        if (!requestData.getSignTypeCode().equals(KY_SO_CODE)) {     // nếu signTypeCode != 0 -> phiếu không ký số -> false
            return false;
        }
        List<ProcessData> processDataList = processDataRepository.findAllByRequestDataId(requestData.getId());
        ProcessData currentProcess = processDataList.stream().filter(ele -> ele.getRoundNumber().equals(requestData.getCurrentRound())).findFirst().orElse(null);
        if (currentProcess == null) return false;
        List<StepData> stepDataList = stepDataRepository.findAllByProcessDataId(currentProcess.getId());
        StepData currentStepData = stepDataList.stream().filter(ele -> ele.getIsActive() == true).findFirst().orElse(null);
        if (currentStepData == null) return false;
        if (!currentStepData.getIsRequiredSignature()) {          // nếu không yêu cầu ký số ở bước -> false;
            return false;
        }
        Boolean hasPermissSign = currentStepData.getUserInfos().stream().anyMatch(ele -> ele.getId().equals(userInfo.getId()));
        if (!hasPermissSign) {                                    // nếu không là người trobng bước -> false
            return false;
        }
        return true;
    }

    public ReqdataProcessHis createReqdataProcessHis(UserInfo processer, RequestData requestData, String description, String signType) {
        ReqdataProcessHis reqdataProcessHis = new ReqdataProcessHis();
        reqdataProcessHis.setCreateDate(Instant.now());
        reqdataProcessHis.setProcessDate(Instant.now());
        reqdataProcessHis.setRequestData(requestData);
        reqdataProcessHis.setIsShowCustomer(true);
        reqdataProcessHis.setIsChild(true);
        reqdataProcessHis.setDescription(description);
        reqdataProcessHis.setStatus(signType);

        if (processer != null) {
            reqdataProcessHis.setIsShowCustomer(false);
            reqdataProcessHis.setProcesserName(processer.getFullName());
            reqdataProcessHis.setProcesser(processer);
            String organizationName = "";
            if (processer.getOrganizations() != null && !processer.getOrganizations().isEmpty()) {
                organizationName = processer.getOrganizations().stream().map(ele -> ele.getOrganizationName()).collect(Collectors.joining(", "));
            }
            reqdataProcessHis.setOrganizationName(organizationName);

            String rankName = "";
            if (processer.getRanks() != null && !processer.getRanks().isEmpty()) {
                rankName = processer.getRanks().stream().map(ele -> ele.getRankName()).collect(Collectors.joining(", "));
            }
            reqdataProcessHis.setRankName(rankName);
        }

        return this.reqdataProcessHisRepository.save(reqdataProcessHis);
    }


    /**
     * Hàm thực hiện kiểm tra xem file đã có file ký chưa
     *
     * @return true: dã có file ký, false chưa có file ký
     */
    public boolean checkHasFileSign(AttachmentFile attachmentFile, List<AttachmentFile> attachmentFileList) {
        List<String> listFileSignName = new ArrayList<>();
        if (attachmentFileList != null)
            listFileSignName = attachmentFileList.stream().map(ele -> ele.getSignOfFile()).collect(Collectors.toList());

        Boolean hasFileSign = listFileSignName.stream().anyMatch(ele -> ele != null && ele.contains(String.valueOf(attachmentFile.getId())));

        return hasFileSign;
    }

    /**
     * Hàm thực hiện kiểm tra xem file có phải là file gửi khác hàng xem hjay không (FIle có wastermark, qrcode -> fille ko cần ký)
     *
     * @attachmentFile: file cần kiểm tra
     * @return: true nếu là file chỉ view của khách hàng, false nếu không phải
     */
    public static final String CUSTOMER_FILE_VIEW = "CUSTOMER";       // cờ đánh dấu file có phải là  file gửi khách hàng xem hay không. (tenant_code trong attachment_file)

    public boolean checkFileViewCustomer(AttachmentFile attachmentFile) {
        if (attachmentFile == null) return false;
        return this.CUSTOMER_FILE_VIEW.equals(attachmentFile.getTennantCode());
    }

    /**
     * hàm thực hiện tạo tên file mới (tránh trùng tên) (kiểm tra tên file trong 1 dánh sách tên file xem có bị trùng không)
     * @param listFileName  : dánh sách tên file
     * @param fileName      : tên file
     * @return  : tên file mới
     */
    public String getFileName(List<String> listFileName, String fileName){
        if(listFileName == null || listFileName.isEmpty()) return fileName;
        String[] names;
        int stt = 1;
        String newFileName = fileName;
        while(true){
            boolean hasSameName = false;
            for(String temp : listFileName){
                if(newFileName.equals(temp)) {
                   hasSameName = true;
                   break;
                }
            }
            if(!hasSameName) return newFileName;
            names = newFileName.split("\\.");
            newFileName = String.join("", Arrays.copyOfRange(names, 0, names.length - 1)) + "_(" + stt + ")" + "." + names[names.length - 1];
        }
    }


    public List<AttachmentFile> getAllFileSign(Long requestDataId){
        List<AttachmentFile> result = new ArrayList<>();
        if(requestDataId == null) return new ArrayList<>();
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        if(requestData.getAttachmentFiles() == null || requestData.getAttachmentFiles().isEmpty()) return result;
        List<AttachmentFile> attachmentFileList = requestData.getAttachmentFiles().stream().filter(ele -> {
            return ele.getTemplateForm() != null;
        }).collect(Collectors.toList());
        if(attachmentFileList == null || attachmentFileList.isEmpty()) return result;
        for(AttachmentFile ele : attachmentFileList){
            if(checkHasFileSign(ele, attachmentFileList)) continue;        // TH đã có file ký rồi -> bỏ qua
            if(checkFileViewCustomer(ele)) continue;         // TH là file gửi kháhc hàng -> bỏ qua không ký
            result.add(ele);
        }
        return result;
    }


    /**
     * Hàm thực hiện ẩn file tài liệu chính của phiếu sau khi ký thành công
     * @param requestDataId :       id của phiếu cần ẩn
     * @return :                    true: nếu ẩn thành công | false: nếu ẩn thất bại
     */
    public boolean hideFilePrimary(Long requestDataId){
        try {
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> ele.getTemplateForm() != null).collect(Collectors.toList());
            if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                for(AttachmentFile attachmentFile : attachmentFileList){
                    try {
                        if(this.checkHasFileSign(attachmentFile, attachmentFileList)){          // nếu có file ký rồi -> ẩn file đi
                            attachmentFile.setIsDelete(true);
                            attachmentFile.setTennantCode(this.KY_SO_CODE_HIDE);                // đánh dấu là file tài liệu chính bị ẩn đi khi đã ký thành công
                            this.attachmentFileRepository.save(attachmentFile);
                        }
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }
            }
            return true;
        }catch (Exception e){ log.error("{}" ,e); return false;}
    }

    /**
     * Hàm thực hiện hiển thị lại file tài liệu chính khi bị ẩn
     * @param requestDataId : id của phiếu cần hiển thị
     * @return              : true: nếu hiển thị thành công | false: nếu hiện thị thất bại
     */
    public boolean revertFilePrimary(Long requestDataId){
        try {
            List<AttachmentFile> attachmentFileList = this.attachmentFileRepository.findAllByRequestDataId(requestDataId).stream().filter(ele -> ele.getTemplateForm() != null).collect(Collectors.toList());
            RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
            Long current_round = requestData.getCurrentRound() == null ? 0L : requestData.getCurrentRound() - 1;
            String current_round_string = "ROUND_" + current_round + "__";
            if(attachmentFileList != null && !attachmentFileList.isEmpty()){
                for(AttachmentFile attachmentFile : attachmentFileList){
                    try {
                        if(this.KY_SO_CODE_HIDE.equals(attachmentFile.getTennantCode())){           // nếu là tài liệu chính bị ẩn đi sau khi kí thành công -> hiển thị lại file
                            attachmentFile.setTennantCode("");
                            attachmentFile.setIsDelete(false);
                            this.attachmentFileRepository.save(attachmentFile);
                        }
                        if(this.CUSTOMER_FILE_CODE.equals(attachmentFile.getTennantCode())){        // nếu là file gửi cho khách hàng xem -> xóa
                            this.attachmentFileRepository.deleteById(attachmentFile.getId());
                        }
                        if(!Strings.isNullOrEmpty(attachmentFile.getSignOfFile()) && !attachmentFile.getSignOfFile().startsWith(this.OLD_SIGN_PREFIX)){
                            attachmentFile.setSignOfFile(this.OLD_SIGN_PREFIX + current_round_string + attachmentFile.getFileName());  // cập nhật lại thông tin của trường signOfFile để bỏ đánh dấu đây là file ký của file nào đó ...
                            this.attachmentFileRepository.save(attachmentFile);
                        }
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }
            }

            // reset lại số lần ký số
            List<SignData> signDataList = this.signDataRepository.findAllByRequestDataId(requestDataId);
            if(signDataList != null && !signDataList.isEmpty()){
                for(SignData signData : signDataList){
                    signData.setNumberSign(0L);
                    this.signDataRepository.save(signData);
                }
            }

            return true;
        }catch (Exception e){ log.error("{}", e); return false;}
    }

}
