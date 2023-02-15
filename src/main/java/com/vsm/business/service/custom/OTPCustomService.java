package com.vsm.business.service.custom;

import com.vsm.business.config.Constants;
import com.vsm.business.domain.OTP;
import com.vsm.business.domain.RequestData;
import com.vsm.business.domain.SignData;
import com.vsm.business.repository.OTPRepository;
import com.vsm.business.repository.RequestDataRepository;
import com.vsm.business.repository.search.OTPSearchRepository;
import com.vsm.business.service.dto.OTPDTO;
import com.vsm.business.service.dto.SignDataDTO;
import com.vsm.business.service.mapper.OTPMapper;
import com.vsm.business.service.mapper.SignDataMapper;
import com.vsm.business.utils.HashUtils;
import com.vsm.business.utils.OTPUtils;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OTPCustomService {
    private final Logger log = LoggerFactory.getLogger(StepCustomService.class);

    private OTPRepository otpRepository;
    private OTPSearchRepository otpSearchRepository;
    private OTPMapper otpMapper;

    private SignDataMapper signDataMapper;

    private RequestDataRepository requestDataRepository;

    @Value("${feature.hash-request:FALSE}")
    private String FEATURE_HASH_REQUEST;

    @Value("${login.max-number-login-fail:5}")
    private Long MAX_FAILED_LOGON;


    public OTPCustomService(OTPRepository otpRepository, OTPSearchRepository otpSearchRepository, OTPMapper otpMapper, RequestDataRepository requestDataRepository, SignDataMapper signDataMapper) {
        this.otpRepository = otpRepository;
        this.otpSearchRepository = otpSearchRepository;
        this.otpMapper = otpMapper;
        this.requestDataRepository = requestDataRepository;
        this.signDataMapper = signDataMapper;
    }

    public List<OTPDTO> getAll() {
//        log.debug("OTPCustomService: getAll()");
//        List<OTP> otps = this.otpRepository.findAll();
//        List<OTPDTO> result = new ArrayList<>();
//        for (OTP otp :
//            otps) {
//            OTPDTO stepInProcessDTO = otpMapper.toDto(otp);
//            result.add(stepInProcessDTO);
//        }
//        return result;
        log.debug("OTPCustomService: getAll()");
        List<OTP> otps = this.otpRepository.findAll();
        List<OTPDTO> result = new ArrayList<>();
        result = this.convertToDTO(otps);
        return result;
    }

    public List<OTPDTO> deleteAll(List<OTPDTO> otpdtos) {
        log.debug("OTPCustomService: deleteAll({})", otpdtos);
        List<Long> ids = otpdtos.stream().map(OTPDTO::getId).collect(Collectors.toList());
        this.otpRepository.deleteAllById(ids);
        this.otpSearchRepository.deleteAllById(ids);
        return this.getAll();
    }

    public boolean delete(Long id) {
        otpRepository.deleteById(id);
        otpSearchRepository.deleteById(id);
        return true;
    }

    public List<OTPDTO> saveAll(List<OTPDTO> otpdtoList) {
        List<OTPDTO> result = otpRepository.saveAll(otpdtoList.stream().map(otpMapper::toEntity).collect(Collectors.toList())).stream().map(otpMapper::toDto).collect(Collectors.toList());
        log.debug("OTPCustomService: saveAll({}) {}", otpdtoList, result);
        return result;
    }

    public List<OTPDTO> getAllByRequestData(Long requestDataId) {
        List<OTPDTO> result = this.otpRepository.findAllByRequestDataId(requestDataId).stream().map(otpMapper::toDto).collect(Collectors.toList());
        log.debug("OTPCustomService: getAllByRequestData({}), {}", requestDataId, result);
        return result;
    }

    public OTPDTO updateOTPWithSignData(SignDataDTO signDataDTO, Long requestDataId){
        try {
            List<OTP> otps = this.otpRepository.findAllByRequestDataId(requestDataId);
            otps.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
            OTP last = otps.stream().findFirst().get();
            last.setNumberView(signDataDTO.getNumberView());
            last.setNumberDownload(signDataDTO.getNumberDownload());
            last.setNumberPrint(signDataDTO.getNumberPrint());
            return otpMapper.toDto(this.otpRepository.save(last));
        }catch (Exception e){
            log.error("{}", e);
            return null;
        }
    }

    public Boolean checkOtpCode(String otp, Long requestDataId) {
        List<OTP> otps = this.otpRepository.findAllByRequestDataId(requestDataId);
        otps.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
        Instant now = Instant.now();
            // DuowngTora: 15/01/2022: sửa mail cho khách hàng sẽ bị generate lại mã OTP -> ko vào được bằng OTP cũ
        //OTP last = otps.stream().findFirst().get();
        OTP last = otps.stream().filter(ele -> ele.getoTPCode().equals(otp)).findFirst().orElse(new OTP());
            // end DuowngTora: 15/01/2022
        if (!otp.equals(last.getoTPCode())) {
            last.setNumberFail(last.getNumberFail() != null ? last.getNumberFail() + 1 : 0 + 1);
            try {
                otpRepository.save(last);
            } catch (Exception e) {
                log.error("checkOtpCode: {}", e);
            }
            return false;
        }
        if (last.getNumberFail() >= MAX_FAILED_LOGON) {
            return false;
        }
        if (last.getExpiryDate().isBefore(now)) {
            return false;
        }
        if (last.getIsActive() == null || !last.getIsActive()) {
            return false;
        }
        if(last.getIsDelete() == true){
            return false;
        }
        return last.getIsActive() == true && last.getIsDelete() != true && last.getExpiryDate().isAfter(now) && last.getNumberFail() < MAX_FAILED_LOGON;
//        boolean result = otps.stream().anyMatch(ele -> {
//            return otp.equals(ele.getoTPCode())
//                && (ele.getIsActive() != null && ele.getIsActive() == true)
//                && (ele.getIsDelete() == null || ele.getIsDelete() == false)
//                && (ele.getExpiryDate().isAfter(now));
//        });
//        return result;
    }

    public SignDataDTO getSignDataOfOtp(String otpCode, Long requestDataId){
        OTP otp = this.otpRepository.customGetAllByRequestDataIdAndOTPCode(requestDataId, otpCode).stream().filter(ele -> {
            return (ele.getIsActive() != null && ele.getIsActive() == true) && (ele.getIsDelete() == null || ele.getIsDelete() == false);
        }).findFirst().orElse(null);
        if(otp == null || otp.getSignData() == null) return null;
        return this.signDataMapper.toDto(otp.getSignData());
    }

    @Value("${system.otp.link:https://uat-eoffice.vincom.com.vn/phieu-yeu-cau/view-customer/{{id}}}")
    private String OTP_LINK;
    @Value("${system.otp.htmlCodeOTP:<br><br><p>Mã OTP: <strong>{{OPT_CODE}}</strong></p><p><a href='{{REQUESTDATA_LINK}}' rel='noopener noreferrer nofollow'>Phiếu yêu cầu: {{REQUEST_DATA_CODE}}</a></p>}")
    private String HTML_CODE_OTP;

    public OTPDTO generateOTP(Long requestDataId, String description) {
        RequestData requestData = this.requestDataRepository.findById(requestDataId).get();
        //int random_int = (int)Math.floor(Math.random()*(9999-1000+1)+1000);
        String random_otp = OTPUtils.generate(6);
        OTP otp = new OTP();
//        otp.setoTPCode(String.valueOf(random_int));
        otp.setoTPCode(random_otp);
        otp.setDescription(description);
        otp.createdDate(Instant.now());
        otp.expiryDate(Instant.now().plus(30, ChronoUnit.DAYS));
        otp.setIsDelete(false);
        otp.setIsActive(true);
        otp.setIsCustomerSign(false);
        if (Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)) {
            otp.setLink(OTP_LINK.replace("{{id}}", HashUtils.encrypt(requestData.getId().toString())));
        } else {
            otp.setLink(OTP_LINK.replace("{{id}}", requestData.getId().toString()));
        }
        otp.setRequestData(requestData);
        otp.setNumberDownload(0L);
        otp.setNumberPrint(0L);
        otp.setNumberView(0L);
        otp.setStatus("Đã gửi");
        otp.modifiedate(Instant.now());
        otp = this.otpRepository.save(otp);
        StringBuilder stringBuilder = new StringBuilder(HTML_CODE_OTP);
//        String result = stringBuilder.toString();
//        result = result.replace("{{OPT_CODE}}", String.valueOf(random_int));
//        result = result.replace("{{REQUESTDATA_LINK}}", otp.getLink());
//        result = result.replace("{{REQUEST_DATA_CODE}}", requestData.getRequestDataCode());
        return otpMapper.toDto(otp);
    }


    // ultil \\
    @Autowired
    private ObjectUtils objectUtils;

    public OTPDTO convertToDTO(OTP otp) throws IllegalAccessException {
        if (otp == null) return null;
        OTPDTO result = new OTPDTO();
        result = this.objectUtils.copyproperties(otp, result, OTPDTO.class);
        return result;
    }

    public List<OTPDTO> convertToDTO(List<OTP> listotpOtps) {
        if (listotpOtps == null) return null;
        List<OTPDTO> result = new ArrayList<>();
        listotpOtps.forEach(ele -> {
            OTPDTO otpdto = new OTPDTO();
            try {
                result.add(this.convertToDTO(ele));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
}
