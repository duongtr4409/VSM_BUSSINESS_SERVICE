package com.vsm.business.web.rest.custom.customer;

import com.microsoft.graph.models.ItemPreviewInfo;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailCreateMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailLoadMessage;
import com.vsm.business.common.ReponseMessage.ErrorMessage.FailUpdateMessage;
import com.vsm.business.common.ReponseMessage.IResponseMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.CreatedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.LoadedMessage;
import com.vsm.business.common.ReponseMessage.SuccessMessage.UpdatedMessage;
import com.vsm.business.config.Constants;
import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.SignData;
import com.vsm.business.repository.AttachmentFileRepository;
import com.vsm.business.repository.SignDataRepository;
import com.vsm.business.repository.StepDataRepository;
import com.vsm.business.service.InformationInExchangeService;
import com.vsm.business.service.SignDataService;
import com.vsm.business.service.custom.*;
import com.vsm.business.service.custom.file.UploadFile365CustomService;
import com.vsm.business.service.custom.file.bo.Office365.Download365Option;
import com.vsm.business.service.custom.processRequest.ProcessRequestCustomService;
import com.vsm.business.service.custom.processRequest.bo.ApproveOption;
import com.vsm.business.service.custom.processRequest.bo.CustomerApproveOption;
import com.vsm.business.service.custom.sign.SimSign;
import com.vsm.business.service.custom.sign.SoftSign;
import com.vsm.business.service.custom.sign.TokenSign;
import com.vsm.business.service.custom.sign.bo.SignDTO;
import com.vsm.business.service.dto.*;
import com.vsm.business.utils.HashUtils;
import com.vsm.business.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customer/api")
public class CustomerController {


    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private static final String ENTITY_NAME = "vsmBusinessServiceRequestData";

    private final String NO_TYPE_SIGN_ERROR = "No Type Sign";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UploadFile365CustomService uploadFile365CustomService;

    private final OTPCustomService optCustomService;

    private final RequestDataCustomService requestDataCustomService;

    private final InformationInExchangeCustomService informationInExchangeCustomService;

    private final InformationInExchangeService informationInExchangeService;

    private final AttachmentFileCustomService attachmentFileCustomService;

    private final SignDataCustomService signDataCustomService;

    private final SignDataService signDataService;

    private final SignDataRepository signDataRepository;

    private final AttachmentFileRepository attachmentFileRepository;

    private final SoftSign softSign;

    private final SimSign simSign;

    private final TokenSign tokenSign;

    private final StepDataRepository stepDataRepository;

    private final StepDataCustomService stepDataCustomService;

    private final ProcessRequestCustomService processRequestCustomService;

    @Value("${feature.hash-request:TRUE}")
    private String FEATURE_HASH_REQUEST;

    public CustomerController(UploadFile365CustomService uploadFile365CustomService, OTPCustomService optCustomService, RequestDataCustomService requestDataCustomService, InformationInExchangeCustomService informationInExchangeCustomService, InformationInExchangeService informationInExchangeService, AttachmentFileCustomService attachmentFileCustomService, SignDataCustomService signDataCustomService, SignDataService signDataService, SignDataRepository signDataRepository, SoftSign softSign, SimSign simSign, TokenSign tokenSign, AttachmentFileRepository attachmentFileRepository, StepDataRepository stepDataRepository, StepDataCustomService stepDataCustomService, ProcessRequestCustomService processRequestCustomService) {
        this.uploadFile365CustomService = uploadFile365CustomService;
        this.optCustomService = optCustomService;
        this.requestDataCustomService = requestDataCustomService;
        this.informationInExchangeCustomService = informationInExchangeCustomService;
        this.informationInExchangeService = informationInExchangeService;
        this.attachmentFileCustomService = attachmentFileCustomService;
        this.signDataCustomService = signDataCustomService;
        this.signDataService = signDataService;
        this.signDataRepository = signDataRepository;
        this.softSign = softSign;
        this.simSign = simSign;
        this.tokenSign = tokenSign;
        this.attachmentFileRepository = attachmentFileRepository;
        this.stepDataRepository = stepDataRepository;
        this.stepDataCustomService = stepDataCustomService;
        this.processRequestCustomService = processRequestCustomService;
    }

    @GetMapping("/file/office365/preview/{id}")
    public ResponseEntity<IResponseMessage> previewFile(@PathVariable("id") Long id, @RequestParam("otp") String otp){

                // check otp \\
        this.checkPermission(this.attachmentFileRepository.findById(id).get().getRequestData().getId(), otp);

        ItemPreviewInfo itemPreviewInfo = this.uploadFile365CustomService.previewFile(id);
        String urlPreview = itemPreviewInfo.getUrl;
        return ResponseEntity.ok().body(new LoadedMessage(urlPreview));
    }

    @GetMapping("/_check/otps")
    public ResponseEntity<IResponseMessage> checkOTPCode(@RequestParam("opt") String otp, @RequestParam("requestDataId") String hashedId){
        log.debug("OTPCustomRest: checkOTPCode({}, {}): {}", otp, hashedId);
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        Boolean result = this.optCustomService.checkOtpCode(otp, requestDataId);
        if(!result){
            return ResponseEntity.ok().body(new FailLoadMessage("NOT ACCESS"));
        }
        return ResponseEntity.ok().body(new LoadedMessage("SUCESS"));
    }

    @GetMapping("/request-data/{id}")
    public ResponseEntity<IResponseMessage> getRequestData(@PathVariable("id") String hashedId, @RequestParam("otp") String otp) {
        Long id;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            id = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            id = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(id, otp);

        log.debug("REST request to get RequestData : {}", id);
//        Optional<RequestDataDTO> requestDataDTO = requestDataService.findOne(id);
        Optional<RequestDataDTO> requestDataDTO = this.requestDataCustomService.customFindOne(id);
        if (!requestDataDTO.isPresent()) {
            return ResponseEntity.ok().body(new FailLoadMessage(id));
        }
        return ResponseEntity.ok().body(new LoadedMessage(requestDataDTO.get()));
    }

    @GetMapping("/request-data/{requestDataId}/_all/information-in-exchanges")
    public ResponseEntity<IResponseMessage> getAllByRequestDataId(@PathVariable("requestDataId") String hashedId, @RequestParam("otp") String otp) {
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(requestDataId, otp);

        List<InformationInExchangeDTO> result = this.informationInExchangeCustomService.getAllByRequestDataId(requestDataId);
        log.debug("InformationInExchangeCustomRest: getAllByRequestDataId({}) {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PostMapping("/information-in-exchanges")
    public ResponseEntity<IResponseMessage> createInformationInExchange(
        @RequestBody InformationInExchangeDTO informationInExchangeDTO,
        @RequestParam("otp") String otp
    ) throws URISyntaxException {

        // check otp \\
        this.checkPermission(informationInExchangeDTO.getRequestData().getId(), otp);

        log.debug("REST request to save InformationInExchange : {}", informationInExchangeDTO);
        if (informationInExchangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new informationInExchange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformationInExchangeDTO result = this.informationInExchangeService.save(informationInExchangeDTO);
        if (result == null) {
            return ResponseEntity.ok().body(new FailCreateMessage(informationInExchangeDTO));
        }
        return ResponseEntity.ok().body(new CreatedMessage(result));
    }

    @GetMapping("/_view_file_customer/{requestDataId}/request-data")
    public ResponseEntity<IResponseMessage> getFileCustomer(@PathVariable("requestDataId") String hashedId, @RequestParam("otp") String otp) throws Exception {
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(requestDataId, otp);

        List<AttachmentFileDTO> result = this.requestDataCustomService.viewFileCustomer(requestDataId);
        log.debug("REST request to getFileCustomer({})", requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/attachment-files")
    public ResponseEntity<IResponseMessage> findAllByRequestDataId(@PathVariable("requestDataId") String hashedId, @RequestParam("otp") String otp){
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(requestDataId, otp);

        List<AttachmentFileDTO> result = attachmentFileCustomService.findAllByRequestData(requestDataId);
        log.debug("REST request to findAllByRequestDataId({}): {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/request-data/{requestDataId}/_all/sign-data")
    public ResponseEntity<IResponseMessage> getAllByRequestData(@PathVariable("requestDataId") String hashedId, @RequestParam("otp") String otp){
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(requestDataId, otp);

        log.debug("SignDataCustomRest: getAll()");
//        List<SignDataDTO> result = this.signDataCustomService.getAllByRequetData(requestDataId);
        SignDataDTO result = this.optCustomService.getSignDataOfOtp(otp, requestDataId);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @PutMapping("/sign-data/{id}")
    public ResponseEntity<IResponseMessage> updateSignData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SignDataDTO signDataDTO,
        @RequestParam("otp") String otp
    ) throws URISyntaxException {

        // check otp \\
        SignData signData = this.signDataRepository.findById(id).get();
        log.info("id={} requestData={}", id, signData.getRequestData());
        this.checkPermission(signData.getRequestData().getId(), otp);

        log.debug("REST request to update SignData : {}, {}", id, signDataDTO);
        if (signDataDTO.getId() == null) {
            return ResponseEntity.ok().body(FailUpdateMessage.MissingParameter(signDataDTO));
        }
        if (!Objects.equals(id, signDataDTO.getId())) {
            return ResponseEntity.ok().body(FailUpdateMessage.MatchingFail(signDataDTO));
        }

        if (!signDataRepository.existsById(id)) {
            return ResponseEntity.ok().body(FailUpdateMessage.DidNotExist(signDataDTO));
        }

                // không cho customer cập nhật số lần ký \\
        signDataDTO.setNumberSign(signData.getNumberSign());

        SignDataDTO result = signDataService.save(signDataDTO);
        // cập nhật thêm thông tin OTP
        optCustomService.updateOTPWithSignData(signDataDTO, signData.getRequestData().getId());


        if (result == null) {
            return ResponseEntity.ok().body(new FailUpdateMessage(signDataDTO));
        }
        return ResponseEntity.ok().body(new UpdatedMessage(result));
    }

    @GetMapping("/file/office365/download/{fileName}.{fileExtension}")
    public void downloadFile(HttpServletResponse response, @PathVariable("fileName") String fileName , @PathVariable("fileExtension") String fileExtension, Download365Option download365Option, @RequestParam("otp") String otp) throws Exception {

        // check otp \\
        Long requestDataId;
        if(download365Option.getAttachmentFileId() != null){
            requestDataId = this.attachmentFileRepository.findById(download365Option.getAttachmentFileId()).get().getRequestData().getId();
        }else{
            requestDataId = this.attachmentFileRepository.findAllByItemId365(download365Option.getItemId()).get(0).getRequestData().getId();
        }
        this.checkPermission(requestDataId, otp);

        this.uploadFile365CustomService.downloadFile(response, fileName + "." + fileExtension, download365Option);
    }

    @GetMapping("/file/office365/hash-file/{id}")
    public ResponseEntity<IResponseMessage> hashPDFFile(@PathVariable("id") Long id, @RequestParam("otp") String otp) throws Exception {

        // check otp \\
        this.checkPermission(this.attachmentFileRepository.findById(id).get().getRequestData().getId(), otp);

        String hashFile = this.uploadFile365CustomService.hashFile(id);
        return ResponseEntity.ok(new LoadedMessage(hashFile));
    }

    @PostMapping("/sign/anonymous")
    public ResponseEntity<IResponseMessage> anonymouseSign(@RequestBody SignDTO signDTO, @RequestParam("otp") String otp) throws Exception {

        // check otp \\
        this.checkPermissionSign(signDTO, otp);

        Object result = false;
        switch (signDTO.getSignType()){
            case Sim:
                result = simSign.anonymousSignOne(signDTO, otp);
                break;
            case Soft:
                result = softSign.signOne(signDTO);
                break;
            case Token:
                result = tokenSign.anonymousSign(signDTO, otp);
                break;
            default:
                throw new RuntimeException(this.NO_TYPE_SIGN_ERROR);
        }
        if(result instanceof Boolean && !(Boolean) result)
            return ResponseEntity.ok().body(new FailLoadMessage(result));
        else{
            // Th kí thành công -> cập nhật lại thông tin ký ở sign data
            //updateSignDataAfterSign(signDTO);
            return ResponseEntity.ok().body(new LoadedMessage(result));
        }

    }

    @GetMapping("/sign/_all/{requestDataId}/file_sign")
    public ResponseEntity<IResponseMessage> getAllFileSign(@PathVariable("requestDataId") String hashedId, @RequestParam("otp") String otp){
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(requestDataId, otp);

        List<AttachmentFile> result = this.tokenSign.getAllFileSign(requestDataId);
        log.debug("SignCustomRest: getAllFileSign({}): {}", requestDataId, result);
        return ResponseEntity.ok().body(new LoadedMessage(result));
    }

    @GetMapping("/check_has_sign/{requestDataId}")          // thực hiện kiểm tra xem có hieemrn thị nút ký số ở màn khách hàng đối tác
    public ResponseEntity<IResponseMessage> getIsShowSign(@PathVariable("requestDataId") String hashedId, @RequestParam("otp") String otp){
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }
        // check otp \\
        this.checkPermission(requestDataId, otp);

        StepDataDTO stepData = this.stepDataCustomService.getCurrentStepData(requestDataId, true).stream().filter(ele -> ele.getIsActive()).findFirst().orElse(null);
        if(stepData == null) return ResponseEntity.ok().body(new FailLoadMessage(false));
        else return ResponseEntity.ok().body(new LoadedMessage(stepData.getIsRequiredSignature()));
    }

    @PostMapping("/process/request_data")
    public ResponseEntity<IResponseMessage> process(@RequestBody CustomerApproveOption customerApproveOption, @RequestParam("otp") String otp){
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt((customerApproveOption.getRequestData())));
        }else{
            requestDataId = Long.valueOf(customerApproveOption.getRequestData());
        }
        customerApproveOption.setRequestDataId(requestDataId);
        customerApproveOption.setOtp(otp);
        // check otp \\
        this.checkPermission(requestDataId, otp);
        Boolean result = this.processRequestCustomService.customerActionRequestData(customerApproveOption);
        if(!result) return ResponseEntity.ok().body(new FailLoadMessage(customerApproveOption.getErrorMessageReturn()));
        return ResponseEntity.ok().body(new LoadedMessage(customerApproveOption.getSuccessMessageReturn()));
    }

//    @GetMapping("/send-otp")
    public ResponseEntity<IResponseMessage> regenerateOtp(@RequestParam("hash") String hashedId){
        Long requestDataId;
        if(Constants.TRUE.equalsIgnoreCase(FEATURE_HASH_REQUEST)){
            requestDataId = Long.valueOf(HashUtils.decrypt(hashedId));
        }else{
            requestDataId = Long.valueOf(hashedId);
        }

        return ResponseEntity.ok().body(null);
    }

    private void updateSignDataAfterSign(SignDTO signDTO){
        if(signDTO == null) return;
        List<Long> requestDataIds = new ArrayList<>();
        switch (signDTO.getSignType()){
            case Token:
                requestDataIds.add(signDTO.getRequestDataId());
                break;
            default:
                requestDataIds = signDTO.getRequestDataList();
                if(signDTO.getRequestDataId() != null && signDTO.getRequestDataId() != null) requestDataIds.add(signDTO.getRequestDataId());
        }
        requestDataIds.forEach(ele -> {
            try {
                List<SignData> signDatas = this.signDataRepository.findAllByRequestDataId(ele);
                for(SignData signData : signDatas){
                    try {
                        signData.setNumberSign(signData.getNumberSign() == null ? 1L : signData.getNumberSign() + 1);
                        this.signDataRepository.save(signData);
                    }catch (Exception e){log.error("{}", e);}
                }
            }catch (Exception e){log.error("{}", e);}
        });
    }

    /**
     * Hàm thực hiện kiểm tra quyền truy cập dữ liệu ở màn khách hàng (dựa vào OTP)
     * @param requestDataId : id của phiếu (dữ liệu sẽ liên quan đến phiếu yêu cầu)
     * @param otp           : mã OTP
     * @return:     true: nếu được phép truy cập | ResponseStatusException nếu ko có quyền truy cập
     */
    private boolean checkPermission(Long requestDataId, String otp){
        Boolean result = this.optCustomService.checkOtpCode(otp, requestDataId);
        if(result != null && result == true){
            return true;
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT AUTHORITY");
        }
    }

    /**
     * Hàm thực hiện kiểm tra xem có quyền ký hay không (dựa vào mã OTP)
     * @param signDTO   : thông tin yêu cầu ký (có chứa id phiếu)
     * @param otp       : mã OTP
     * @return:     true: nếu được ký | ResponseStatusException nếu ko được ký
     */
    private boolean checkPermissionSign(SignDTO signDTO, String otp){
        Long requestDataId;
        switch (signDTO.getSignType()){
            case Token:
                requestDataId = signDTO.getRequestDataId();
                break;
            default:
                requestDataId = signDTO.getRequestDataList().get(0);
        }
        return this.checkPermission(requestDataId, otp);
    }
}
