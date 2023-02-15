package com.vsm.business.service.custom.sign;

import com.vsm.business.common.Sign.token.TokenSignService;
import com.vsm.business.domain.*;
import com.vsm.business.repository.*;
import com.vsm.business.service.custom.sign.bo.SignDTO;
import com.vsm.business.service.custom.sign.bo.SignResponseDTO;
import com.vsm.business.service.custom.sign.utils.SignUtils;
import com.vsm.business.utils.FileUtils;
import com.vsm.business.utils.PDFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenSign {

    private final Logger log = LoggerFactory.getLogger(TokenSign.class);

    @Value("${system.qr-code.link:https://vcr.mobifone.ai/phieu-yeu-cau/chi-tiet/{{id}}}")
    public String QRCODE_LINK;

    private final TokenSignService tokenSignService;

    private final SignUtils signUtils;

    private final RequestDataRepository requestDataRepository;

    private final UserInfoRepository userInfoRepository;

    private final AttachmentFileRepository attachmentFileRepository;
    private final SignDataRepository signDataRepository;
    private final OTPRepository otpRepository;

    private final FileUtils fileUtils;

    private final PDFUtils pdfUtils;

    public TokenSign(TokenSignService tokenSignService, SignUtils signUtils, RequestDataRepository requestDataRepository, UserInfoRepository userInfoRepository, AttachmentFileRepository attachmentFileRepository, SignDataRepository signDataRepository, FileUtils fileUtils, PDFUtils pdfUtils, OTPRepository otpRepository) {
        this.tokenSignService = tokenSignService;
        this.signUtils = signUtils;
        this.requestDataRepository = requestDataRepository;
        this.userInfoRepository = userInfoRepository;
        this.attachmentFileRepository = attachmentFileRepository;
        this.signDataRepository = signDataRepository;
        this.fileUtils = fileUtils;
        this.pdfUtils = pdfUtils;
        this.otpRepository = otpRepository;
    }

    public List<SignResponseDTO> sign(SignDTO signDTO){
        List<SignResponseDTO> result = new ArrayList<>();
        if(signDTO.getRequestDataList()!= null){
            for(Long requestDataId : signDTO.getRequestDataList()){
                SignResponseDTO signResponseDTO = new SignResponseDTO();
                signResponseDTO.setRequestDataId(signDTO.getRequestDataId());
                if(!checkKYSO(signDTO, requestDataId)){
                    signResponseDTO.setResultSign(false);
                }else{
                    signResponseDTO.setResultSign(_signOne(signDTO));
                }
                result.add(signResponseDTO);
                if(signResponseDTO.isResultSign()){     // nếu kí thành công -> ghi lịch sử
                    writeSignHis(signDTO.getRequestDataId(), signDTO);
                    // ký thành công -> ẩn file tài liệu chính đã có file ký
                    this.signUtils.hideFilePrimary(requestDataId);
                }
            }
        }
        return result;
    }

    public SignResponseDTO anonymousSign(SignDTO signDTO, String otpCode){
        SignResponseDTO signResponseDTO = new SignResponseDTO();
        signResponseDTO.setRequestDataId(signDTO.getRequestDataId());
        signResponseDTO.setResultSign(_signOne(signDTO, true));
        if(signResponseDTO.isResultSign()){     // nếu kí thành công -> ghi lịch sử
            try{
                // ký thành công -> ẩn file tài liệu chính đã có file ký
                this.signUtils.hideFilePrimary(signDTO.getRequestDataId());

                // ký thành công -> cập nhật thông tin ký của khách hàng: sửa lại để chỉ cập nhật thông tin ký của signData tương ứng với mã OTP (20230131: nếu có truyền OTP còn ko thì như cũ )
                if(otpCode != null) {
                    OTP otp = this.otpRepository.customGetAllByRequestDataIdAndOTPCode(signDTO.getRequestDataId(), otpCode).stream().filter(ele -> {
                        return (ele.getIsActive() != null && ele.getIsActive() == true) && (ele.getIsDelete() == null || ele.getIsDelete() == false);
                    }).findFirst().get();
                    SignData signData = this.signDataRepository.findById(otp.getSignData().getId()).get();
                    signData.setNumberSign(signData.getNumberSign() + 1);
                    this.signDataRepository.save(signData);
                    writeSignHisAnonymous(signDTO.getRequestDataId(), signDTO, true, signData);
                }else{
                    List<SignData> signDataList = signDataRepository.findAllByRequestDataId(signDTO.getRequestDataId());
                    if(signDataList != null && signDataList.size() > 0){
                        for (SignData signData: signDataList){
                            signData.setNumberSign(signData.getNumberSign() + 1);
                        }
                        signDataRepository.saveAll(signDataList);
                        writeSignHis(signDTO.getRequestDataId(), signDTO, true);
                    }
                }

            }catch (Exception e){
                signResponseDTO.setResultSign(false);
            }
        }
        return signResponseDTO;
    }

    public SignResponseDTO signOne(SignDTO signDTO){
        SignResponseDTO signResponseDTO = new SignResponseDTO();
        signResponseDTO.setRequestDataId(signDTO.getRequestDataId());
        signResponseDTO.setResultSign(_signOne(signDTO));
        if(signResponseDTO.isResultSign()){     // nếu kí thành công -> ghi lịch sử
            writeSignHis(signDTO.getRequestDataId(), signDTO);
            // ký thành công -> ẩn file tài liệu chính đã có file ký
            this.signUtils.hideFilePrimary(signDTO.getRequestDataId());
        }
        return signResponseDTO;
    }

    public boolean _signOne(SignDTO signDTO){
        RequestData requestData = requestDataRepository.findById(signDTO.getRequestDataId()).get();
        UserInfo userInfo = userInfoRepository.findById(signDTO.getUserId()).get();
        AttachmentFile attachmentFile = attachmentFileRepository.findById(signDTO.getAttachmentFileId()).get();
        List<String> listFileName = requestData.getAttachmentFiles() != null ? requestData.getAttachmentFiles().stream().map(ele -> ele.getFileName()).collect(Collectors.toList()) : null;
        if(!this.signUtils.checkCondition(requestData, userInfo)){      // nếu không đủ điều kiện thực hiện ký số
            return false;
        }
        return tokenSignService.sign(signDTO.getFileDataBase64(), requestData.getIdDirectoryPath(), attachmentFile, userInfo, listFileName);
    }

    public boolean _signOne(SignDTO signDTO, boolean skipUser){
        RequestData requestData = requestDataRepository.findById(signDTO.getRequestDataId()).get();
        AttachmentFile attachmentFile = attachmentFileRepository.findById(signDTO.getAttachmentFileId()).get();
        List<String> listFileName = requestData.getAttachmentFiles() != null ? requestData.getAttachmentFiles().stream().map(ele -> ele.getFileName()).collect(Collectors.toList()) : null;
        UserInfo userInfo = null;
        if(!skipUser){
            userInfo = userInfoRepository.findById(signDTO.getUserId()).get();
            if(!this.signUtils.checkCondition(requestData, userInfo)){      // nếu không đủ điều kiện thực hiện ký số
                return false;
            }
        }
        return tokenSignService.sign(signDTO.getFileDataBase64(), requestData.getIdDirectoryPath(), attachmentFile, userInfo, listFileName);
    }

    /**
     * Hàm thực hiện kiểm tra xem phiếu có được phép ký số hay không
     * @return
     */
    private boolean checkKYSO(SignDTO signDTO, Long requestDataId){
        try {
            RequestData requestData = requestDataRepository.findById(requestDataId).get();
            UserInfo userInfo = userInfoRepository.findById(signDTO.getUserId()).get();
            return signUtils.checkCondition(requestData, userInfo);
        }catch (Exception e){
            log.error("{}", e);
            return false;
        }
    }

    /**
     * Hàm thực hiện ghi lịch sửa ký
     */
    private void writeSignHis(Long requestDataId, SignDTO signDTO){
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        UserInfo userInfo = new UserInfo();
        if(signDTO.getUserId() != null){
            userInfo = userInfoRepository.findById(signDTO.getUserId()).orElse(null);
            if(userInfo == null) {
                userInfo = new UserInfo();
                try {
                    SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
                    userInfo.setFullName(signData.getSignName());
                }catch (Exception e){
                    log.error("{}", e);
                }
            }
        }
        String description = signDTO.getReason();
        String signType = "";
        switch (signDTO.getSignType()){
            case Sim:
                signType = "Ký SIM";
                break;
            case Soft:
                signType = "Ký MỀM";
                break;
            case Token:
                signType = "Ký TOKEN";
                break;
            default:
                break;
        }
        signUtils.createReqdataProcessHis(userInfo, requestData, description, signType);
    }

    /**
     * Hàm thực hiện ghi lịch sửa ký
     */
    private void writeSignHis(Long requestDataId, SignDTO signDTO, boolean skipUser){
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        UserInfo userInfo = null;
//        if(!skipUser){
//            userInfo = new UserInfo();
//            try {
//                SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
//                userInfo.setFullName(signData.getSignName());
//            }catch (Exception e){
//                log.info("{}", e);
//            }
//        }
        String description = signDTO.getReason();
        String signType = "";
        switch (signDTO.getSignType()){
            case Sim:
                signType = "Ký SIM";
                break;
            case Soft:
                signType = "Ký MỀM";
                break;
            case Token:
                signType = "Ký TOKEN";
                break;
            default:
                break;
        }
        signUtils.createReqdataProcessHis(userInfo, requestData, description, signType);
    }

    private void writeSignHisAnonymous(Long requestDataId, SignDTO signDTO, boolean skipUser, SignData signData){
        RequestData requestData = requestDataRepository.findById(requestDataId).get();
        UserInfo userInfo = null;
//        if(!skipUser){
//            userInfo = new UserInfo();
//            try {
//                SignData signData = this.signDataRepository.findAllByRequestDataId(requestDataId).get(0);
//                userInfo.setFullName(signData.getSignName());
//            }catch (Exception e){
//                log.info("{}", e);
//            }
//        }
        String description = signDTO.getReason();
        String signType = "";
        switch (signDTO.getSignType()){
            case Sim:
                signType = "Ký SIM";
                break;
            case Soft:
                signType = "Ký MỀM";
                break;
            case Token:
                signType = "Ký TOKEN";
                break;
            default:
                break;
        }
        if(signData != null) signType = (signData.getSignName() != null ? signData.getSignName() + " " : "") + signType;
        signUtils.createReqdataProcessHis(userInfo, requestData, description, signType);
    }

    public List<AttachmentFile> getAllFileSign(Long requestDataId){
        return this.signUtils.getAllFileSign(requestDataId);
    }

}
