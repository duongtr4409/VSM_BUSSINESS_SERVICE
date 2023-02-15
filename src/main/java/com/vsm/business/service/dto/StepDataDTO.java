package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.vsm.business.domain.StepData} entity.
 */
public class StepDataDTO implements Serializable {

    private Long id;

    private String stepDataCode;

    private String stepDataName;

    private Long stepOrder;

    private Long roundNumber;

    private Instant timeActive;

    private Double processingTerm;

    private Instant processingTermTime;

    private Boolean isRequiredSignature;

    private Boolean isBack;

    private Boolean isAuthorizedApproval;

    private Boolean isExamine;

    private Boolean isConsult;

    private Boolean isEdit;

    private Boolean isRequestModify;

    private Boolean isSendMail;

    private Boolean isReSend;

    private Boolean isAutoSendMail;

    private Boolean isAttachFilePDF;

    private Boolean isSendNoticePriority;

    private String sendNoticePriorityType;

    private Boolean isCreateOTPLink;

    private Boolean isSendOTP;

    private Boolean isExportPDF;

    private Boolean isAddQRCode;

    private Boolean isAddWaterMark;

    private Boolean isAddHistory;

    private Boolean isProcessSaveDoc;

    private Boolean isChangeProcess;

    private Boolean isSaveDoc;

    private Boolean isCreateReport;

    private Boolean isCreateOfficalDispath;

    private Boolean isCreateContract;

    private String contractType;

    private String processOfContract;

    private String createContractType;

    private Boolean isProcessRecover;

    private Boolean isRecall;

    private Boolean isCreateTFSTask;

    private Boolean isDeny;

    private Long optionDeny;

    private Boolean isSendMailCustomer;

    private Boolean isSyncSap;

    private Boolean isApprove;

    private Boolean isManageStamp;

    private Boolean isChangeStatus;

    private String organizationCode;

    private String organizationName;

    private String rankCode;

    private String rankName;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private String tennantCode;

    private String tennantName;

    private StepDataDTO nextStep;

    private ProcessDataDTO processData;

    private RequestDataDTO requestData;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private StepInProcessDTO stepInProcess;

    private RankDTO rank;

    private MailTemplateDTO mailTemplate;

    private MailTemplateDTO mailTemplateCustomer;

    private Set<UserInfoDTO> userInfos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepDataCode() {
        return stepDataCode;
    }

    public void setStepDataCode(String stepDataCode) {
        this.stepDataCode = stepDataCode;
    }

    public String getStepDataName() {
        return stepDataName;
    }

    public void setStepDataName(String stepDataName) {
        this.stepDataName = stepDataName;
    }

    public Long getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Long stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Long getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Long roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Instant getTimeActive() {
        return timeActive;
    }

    public void setTimeActive(Instant timeActive) {
        this.timeActive = timeActive;
    }

    public Double getProcessingTerm() {
        return processingTerm;
    }

    public void setProcessingTerm(Double processingTerm) {
        this.processingTerm = processingTerm;
    }

    public Instant getProcessingTermTime() {
        return processingTermTime;
    }

    public void setProcessingTermTime(Instant processingTermTime) {
        this.processingTermTime = processingTermTime;
    }

    public Boolean getIsRequiredSignature() {
        return isRequiredSignature;
    }

    public void setIsRequiredSignature(Boolean isRequiredSignature) {
        this.isRequiredSignature = isRequiredSignature;
    }

    public Boolean getIsBack() {
        return isBack;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public Boolean getIsAuthorizedApproval() {
        return isAuthorizedApproval;
    }

    public void setIsAuthorizedApproval(Boolean isAuthorizedApproval) {
        this.isAuthorizedApproval = isAuthorizedApproval;
    }

    public Boolean getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(Boolean isExamine) {
        this.isExamine = isExamine;
    }

    public Boolean getIsConsult() {
        return isConsult;
    }

    public void setIsConsult(Boolean isConsult) {
        this.isConsult = isConsult;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public Boolean getIsRequestModify() {
        return isRequestModify;
    }

    public void setIsRequestModify(Boolean isRequestModify) {
        this.isRequestModify = isRequestModify;
    }

    public Boolean getIsSendMail() {
        return isSendMail;
    }

    public void setIsSendMail(Boolean isSendMail) {
        this.isSendMail = isSendMail;
    }

    public Boolean getIsReSend() {
        return isReSend;
    }

    public void setIsReSend(Boolean isReSend) {
        this.isReSend = isReSend;
    }

    public Boolean getIsAutoSendMail() {
        return isAutoSendMail;
    }

    public void setIsAutoSendMail(Boolean isAutoSendMail) {
        this.isAutoSendMail = isAutoSendMail;
    }

    public Boolean getIsAttachFilePDF() {
        return isAttachFilePDF;
    }

    public void setIsAttachFilePDF(Boolean isAttachFilePDF) {
        this.isAttachFilePDF = isAttachFilePDF;
    }

    public Boolean getIsSendNoticePriority() {
        return isSendNoticePriority;
    }

    public void setIsSendNoticePriority(Boolean isSendNoticePriority) {
        this.isSendNoticePriority = isSendNoticePriority;
    }

    public String getSendNoticePriorityType() {
        return sendNoticePriorityType;
    }

    public void setSendNoticePriorityType(String sendNoticePriorityType) {
        this.sendNoticePriorityType = sendNoticePriorityType;
    }

    public Boolean getIsCreateOTPLink() {
        return isCreateOTPLink;
    }

    public void setIsCreateOTPLink(Boolean isCreateOTPLink) {
        this.isCreateOTPLink = isCreateOTPLink;
    }

    public Boolean getIsSendOTP() {
        return isSendOTP;
    }

    public void setIsSendOTP(Boolean isSendOTP) {
        this.isSendOTP = isSendOTP;
    }

    public Boolean getIsExportPDF() {
        return isExportPDF;
    }

    public void setIsExportPDF(Boolean isExportPDF) {
        this.isExportPDF = isExportPDF;
    }

    public Boolean getIsAddQRCode() {
        return isAddQRCode;
    }

    public void setIsAddQRCode(Boolean isAddQRCode) {
        this.isAddQRCode = isAddQRCode;
    }

    public Boolean getIsAddWaterMark() {
        return isAddWaterMark;
    }

    public void setIsAddWaterMark(Boolean isAddWaterMark) {
        this.isAddWaterMark = isAddWaterMark;
    }

    public Boolean getIsAddHistory() {
        return isAddHistory;
    }

    public void setIsAddHistory(Boolean isAddHistory) {
        this.isAddHistory = isAddHistory;
    }

    public Boolean getIsProcessSaveDoc() {
        return isProcessSaveDoc;
    }

    public void setIsProcessSaveDoc(Boolean isProcessSaveDoc) {
        this.isProcessSaveDoc = isProcessSaveDoc;
    }

    public Boolean getIsChangeProcess() {
        return isChangeProcess;
    }

    public void setIsChangeProcess(Boolean isChangeProcess) {
        this.isChangeProcess = isChangeProcess;
    }

    public Boolean getIsSaveDoc() {
        return isSaveDoc;
    }

    public void setIsSaveDoc(Boolean isSaveDoc) {
        this.isSaveDoc = isSaveDoc;
    }

    public Boolean getIsCreateReport() {
        return isCreateReport;
    }

    public void setIsCreateReport(Boolean isCreateReport) {
        this.isCreateReport = isCreateReport;
    }

    public Boolean getIsCreateOfficalDispath() {
        return isCreateOfficalDispath;
    }

    public void setIsCreateOfficalDispath(Boolean isCreateOfficalDispath) {
        this.isCreateOfficalDispath = isCreateOfficalDispath;
    }

    public Boolean getIsCreateContract() {
        return isCreateContract;
    }

    public void setIsCreateContract(Boolean isCreateContract) {
        this.isCreateContract = isCreateContract;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getProcessOfContract() {
        return processOfContract;
    }

    public void setProcessOfContract(String processOfContract) {
        this.processOfContract = processOfContract;
    }

    public String getCreateContractType() {
        return createContractType;
    }

    public void setCreateContractType(String createContractType) {
        this.createContractType = createContractType;
    }

    public Boolean getIsProcessRecover() {
        return isProcessRecover;
    }

    public void setIsProcessRecover(Boolean isProcessRecover) {
        this.isProcessRecover = isProcessRecover;
    }

    public Boolean getIsRecall() {
        return isRecall;
    }

    public void setIsRecall(Boolean isRecall) {
        this.isRecall = isRecall;
    }

    public Boolean getIsCreateTFSTask() {
        return isCreateTFSTask;
    }

    public void setIsCreateTFSTask(Boolean isCreateTFSTask) {
        this.isCreateTFSTask = isCreateTFSTask;
    }

    public Boolean getIsDeny() {
        return isDeny;
    }

    public void setIsDeny(Boolean isDeny) {
        this.isDeny = isDeny;
    }

    public Long getOptionDeny() {
        return optionDeny;
    }

    public void setOptionDeny(Long optionDeny) {
        this.optionDeny = optionDeny;
    }

    public Boolean getIsSendMailCustomer() {
        return isSendMailCustomer;
    }

    public void setIsSendMailCustomer(Boolean isSendMailCustomer) {
        this.isSendMailCustomer = isSendMailCustomer;
    }

    public Boolean getIsSyncSap() {
        return isSyncSap;
    }

    public void setIsSyncSap(Boolean isSyncSap) {
        this.isSyncSap = isSyncSap;
    }

    public Boolean getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public Boolean getIsManageStamp() {
        return isManageStamp;
    }

    public void setIsManageStamp(Boolean isManageStamp) {
        this.isManageStamp = isManageStamp;
    }

    public Boolean getIsChangeStatus() {
        return isChangeStatus;
    }

    public void setIsChangeStatus(Boolean isChangeStatus) {
        this.isChangeStatus = isChangeStatus;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankCode() {
        return rankCode;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return createdOrgName;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return createdRankName;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getTennantCode() {
        return tennantCode;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return tennantName;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public StepDataDTO getNextStep() {
        return nextStep;
    }

    public void setNextStep(StepDataDTO nextStep) {
        this.nextStep = nextStep;
    }

    public ProcessDataDTO getProcessData() {
        return processData;
    }

    public void setProcessData(ProcessDataDTO processData) {
        this.processData = processData;
    }

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    public UserInfoDTO getCreated() {
        return created;
    }

    public void setCreated(UserInfoDTO created) {
        this.created = created;
    }

    public UserInfoDTO getModified() {
        return modified;
    }

    public void setModified(UserInfoDTO modified) {
        this.modified = modified;
    }

    public StepInProcessDTO getStepInProcess() {
        return stepInProcess;
    }

    public void setStepInProcess(StepInProcessDTO stepInProcess) {
        this.stepInProcess = stepInProcess;
    }

    public RankDTO getRank() {
        return rank;
    }

    public void setRank(RankDTO rank) {
        this.rank = rank;
    }

    public MailTemplateDTO getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(MailTemplateDTO mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public MailTemplateDTO getMailTemplateCustomer() {
        return mailTemplateCustomer;
    }

    public void setMailTemplateCustomer(MailTemplateDTO mailTemplateCustomer) {
        this.mailTemplateCustomer = mailTemplateCustomer;
    }

    public Set<UserInfoDTO> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(Set<UserInfoDTO> userInfos) {
        this.userInfos = userInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StepDataDTO)) {
            return false;
        }

        StepDataDTO stepDataDTO = (StepDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stepDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StepDataDTO{" +
            "id=" + getId() +
            ", stepDataCode='" + getStepDataCode() + "'" +
            ", stepDataName='" + getStepDataName() + "'" +
            ", stepOrder=" + getStepOrder() +
            ", roundNumber=" + getRoundNumber() +
            ", timeActive='" + getTimeActive() + "'" +
            ", processingTerm=" + getProcessingTerm() +
            ", processingTermTime='" + getProcessingTermTime() + "'" +
            ", isRequiredSignature='" + getIsRequiredSignature() + "'" +
            ", isBack='" + getIsBack() + "'" +
            ", isAuthorizedApproval='" + getIsAuthorizedApproval() + "'" +
            ", isExamine='" + getIsExamine() + "'" +
            ", isConsult='" + getIsConsult() + "'" +
            ", isEdit='" + getIsEdit() + "'" +
            ", isRequestModify='" + getIsRequestModify() + "'" +
            ", isSendMail='" + getIsSendMail() + "'" +
            ", isReSend='" + getIsReSend() + "'" +
            ", isAutoSendMail='" + getIsAutoSendMail() + "'" +
            ", isAttachFilePDF='" + getIsAttachFilePDF() + "'" +
            ", isSendNoticePriority='" + getIsSendNoticePriority() + "'" +
            ", sendNoticePriorityType='" + getSendNoticePriorityType() + "'" +
            ", isCreateOTPLink='" + getIsCreateOTPLink() + "'" +
            ", isSendOTP='" + getIsSendOTP() + "'" +
            ", isExportPDF='" + getIsExportPDF() + "'" +
            ", isAddQRCode='" + getIsAddQRCode() + "'" +
            ", isAddWaterMark='" + getIsAddWaterMark() + "'" +
            ", isAddHistory='" + getIsAddHistory() + "'" +
            ", isProcessSaveDoc='" + getIsProcessSaveDoc() + "'" +
            ", isChangeProcess='" + getIsChangeProcess() + "'" +
            ", isSaveDoc='" + getIsSaveDoc() + "'" +
            ", isCreateReport='" + getIsCreateReport() + "'" +
            ", isCreateOfficalDispath='" + getIsCreateOfficalDispath() + "'" +
            ", isCreateContract='" + getIsCreateContract() + "'" +
            ", contractType='" + getContractType() + "'" +
            ", processOfContract='" + getProcessOfContract() + "'" +
            ", createContractType='" + getCreateContractType() + "'" +
            ", isProcessRecover='" + getIsProcessRecover() + "'" +
            ", isRecall='" + getIsRecall() + "'" +
            ", isCreateTFSTask='" + getIsCreateTFSTask() + "'" +
            ", isDeny='" + getIsDeny() + "'" +
            ", optionDeny=" + getOptionDeny() +
            ", isSendMailCustomer='" + getIsSendMailCustomer() + "'" +
            ", isSyncSap='" + getIsSyncSap() + "'" +
            ", isApprove='" + getIsApprove() + "'" +
            ", isManageStamp='" + getIsManageStamp() + "'" +
            ", isChangeStatus='" + getIsChangeStatus() + "'" +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankCode='" + getRankCode() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", nextStep=" + getNextStep() +
            ", processData=" + getProcessData() +
            ", requestData=" + getRequestData() +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", stepInProcess=" + getStepInProcess() +
            ", rank=" + getRank() +
            ", mailTemplate=" + getMailTemplate() +
            ", mailTemplateCustomer=" + getMailTemplateCustomer() +
            ", userInfos=" + getUserInfos() +
            "}";
    }
}
