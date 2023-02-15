package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Step.
 */
@Entity
@Table(name = "step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "step")
public class Step implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "step_code")
    private String stepCode;

    @Column(name = "step_name")
    private String stepName;

    @Column(name = "processing_term")
    private Double processingTerm;

    @Column(name = "processing_term_time")
    private Instant processingTermTime;

    @Column(name = "is_required_signature")
    private Boolean isRequiredSignature;

    @Column(name = "is_back")
    private Boolean isBack;

    @Column(name = "is_authorized_approval")
    private Boolean isAuthorizedApproval;

    @Column(name = "is_examine")
    private Boolean isExamine;

    @Column(name = "is_consult")
    private Boolean isConsult;

    @Column(name = "is_edit")
    private Boolean isEdit;

    @Column(name = "is_request_modify")
    private Boolean isRequestModify;

    @Column(name = "is_send_mail")
    private Boolean isSendMail;

    @Column(name = "is_re_send")
    private Boolean isReSend;

    @Column(name = "is_auto_send_mail")
    private Boolean isAutoSendMail;

    @Column(name = "is_attach_file_pdf")
    private Boolean isAttachFilePDF;

    @Column(name = "is_send_notice_priority")
    private Boolean isSendNoticePriority;

    @Column(name = "send_notice_priority_type")
    private String sendNoticePriorityType;

    @Column(name = "is_create_otp_link")
    private Boolean isCreateOTPLink;

    @Column(name = "is_send_otp")
    private Boolean isSendOTP;

    @Column(name = "is_export_pdf")
    private Boolean isExportPDF;

    @Column(name = "is_add_qr_code")
    private Boolean isAddQRCode;

    @Column(name = "is_add_water_mark")
    private Boolean isAddWaterMark;

    @Column(name = "is_add_history")
    private Boolean isAddHistory;

    @Column(name = "is_process_save_doc")
    private Boolean isProcessSaveDoc;

    @Column(name = "is_change_process")
    private Boolean isChangeProcess;

    @Column(name = "is_save_doc")
    private Boolean isSaveDoc;

    @Column(name = "is_create_report")
    private Boolean isCreateReport;

    @Column(name = "is_create_offical_dispath")
    private Boolean isCreateOfficalDispath;

    @Column(name = "is_create_contract")
    private Boolean isCreateContract;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "process_of_contract")
    private String processOfContract;

    @Column(name = "create_contract_type")
    private String createContractType;

    @Column(name = "is_process_recover")
    private Boolean isProcessRecover;

    @Column(name = "is_recall")
    private Boolean isRecall;

    @Column(name = "is_create_tfs_task")
    private Boolean isCreateTFSTask;

    @Column(name = "is_deny")
    private Boolean isDeny;

    @Column(name = "option_deny")
    private Long optionDeny;

    @Column(name = "is_send_mail_customer")
    private Boolean isSendMailCustomer;

    @Column(name = "is_sync_sap")
    private Boolean isSyncSap;

    @Column(name = "is_approve")
    private Boolean isApprove;

    @Column(name = "is_manage_stamp")
    private Boolean isManageStamp;

    @Column(name = "is_change_status")
    private Boolean isChangeStatus;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "rank_code")
    private String rankCode;

    @Column(name = "rank_name")
    private String rankName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "created_org_name")
    private String createdOrgName;

    @Column(name = "created_rank_name")
    private String createdRankName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "version")
    private Long version;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "fields",
            "forms",
            "fieldInForms",
            "ranks",
            "roles",
            "userInfos",
            "userInSteps",
            "stepInProcesses",
            "steps",
            "processInfos",
            "templateForms",
            "requests",
            "requestTypes",
            "requestGroups",
            "processData",
            "stepData",
            "requestData",
            "statuses",
            "formData",
            "fieldData",
            "attachmentFiles",
            "fileTypes",
            "reqdataChangeHis",
            "categoryData",
            "categoryGroups",
            "resultOfSteps",
        },
        allowSetters = true
    )
    private Tennant tennant;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leader",
            "tennant",
            "created",
            "modified",
            "roles",
            "ranks",
            "organizations",
            "userInSteps",
            "createdFields",
            "modifiedFields",
            "createdForms",
            "modifiedForms",
            "createdFieldInForms",
            "modifiedFieldInForms",
            "createdRanks",
            "modifiedRanks",
            "createdRoles",
            "modifiedRoles",
            "createdUserInfos",
            "modifiedUserInfos",
            "createdUserInSteps",
            "modifiedUserInSteps",
            "createdStepInProcesses",
            "modifiedStepInProcesses",
            "createdSteps",
            "modifiedSteps",
            "createdProcessInfos",
            "modifiedProcessInfos",
            "createdTemplateForms",
            "modifiedTemplateForms",
            "createdRequests",
            "modifiedRequests",
            "createdRequestTypes",
            "modifiedRequestTypes",
            "createdRequestGroups",
            "modifiedRequestGroups",
            "createdProcessDatas",
            "modifiedProcessDatas",
            "createdStepDatas",
            "modifiedStepDatas",
            "createdRequestDatas",
            "modifiedRequestDatas",
            "approvedRequestDatas",
            "revokedRequestDatas",
            "createdStatuses",
            "modifiedStatuses",
            "createdFormDatas",
            "modifiedFormDatas",
            "createdFieldDatas",
            "modifiedFieldDatas",
            "createdAttachmentFiles",
            "modifiedAttachmentFiles",
            "createdFileTypes",
            "modifiedFileTypes",
            "createdReqdataChangeHis",
            "modifiedReqdataChangeHis",
            "createdCategoryDatas",
            "modifiedCategoryDatas",
            "createdCategoryGroups",
            "modifiedCategoryGroups",
            "signatureInfomations",
            "attachmentPermisitions",
            "createdAttachmentPermisions",
            "modifiedAttachmentPermisions",
            "signData",
            "requestData",
            "stepData",
            "userGroups",
            "offDispatchUserReads",
            "receiversHandles",
        },
        allowSetters = true
    )
    private UserInfo created;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leader",
            "tennant",
            "created",
            "modified",
            "roles",
            "ranks",
            "organizations",
            "userInSteps",
            "createdFields",
            "modifiedFields",
            "createdForms",
            "modifiedForms",
            "createdFieldInForms",
            "modifiedFieldInForms",
            "createdRanks",
            "modifiedRanks",
            "createdRoles",
            "modifiedRoles",
            "createdUserInfos",
            "modifiedUserInfos",
            "createdUserInSteps",
            "modifiedUserInSteps",
            "createdStepInProcesses",
            "modifiedStepInProcesses",
            "createdSteps",
            "modifiedSteps",
            "createdProcessInfos",
            "modifiedProcessInfos",
            "createdTemplateForms",
            "modifiedTemplateForms",
            "createdRequests",
            "modifiedRequests",
            "createdRequestTypes",
            "modifiedRequestTypes",
            "createdRequestGroups",
            "modifiedRequestGroups",
            "createdProcessDatas",
            "modifiedProcessDatas",
            "createdStepDatas",
            "modifiedStepDatas",
            "createdRequestDatas",
            "modifiedRequestDatas",
            "approvedRequestDatas",
            "revokedRequestDatas",
            "createdStatuses",
            "modifiedStatuses",
            "createdFormDatas",
            "modifiedFormDatas",
            "createdFieldDatas",
            "modifiedFieldDatas",
            "createdAttachmentFiles",
            "modifiedAttachmentFiles",
            "createdFileTypes",
            "modifiedFileTypes",
            "createdReqdataChangeHis",
            "modifiedReqdataChangeHis",
            "createdCategoryDatas",
            "modifiedCategoryDatas",
            "createdCategoryGroups",
            "modifiedCategoryGroups",
            "signatureInfomations",
            "attachmentPermisitions",
            "createdAttachmentPermisions",
            "modifiedAttachmentPermisions",
            "signData",
            "requestData",
            "stepData",
            "userGroups",
            "offDispatchUserReads",
            "receiversHandles",
        },
        allowSetters = true
    )
    private UserInfo modified;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Rank rank;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "orgParent",
            "leader",
            "requestData",
            "dispatchBooks",
            "transferHandles",
            "rankInOrgs",
            "roleOrganizations",
            "templateForms",
            "userInfos",
            "processInfos",
            "dispatchBookOrgs",
            "mailTemplates",
            "orgStorages",
        },
        allowSetters = true
    )
    private Organization organization;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "organizations",
            "processInfos",
            "stepInProcesses",
            "stepInProMailTemplateCustomers",
            "steps",
            "stepMailTemplateCustomers",
            "stepData",
            "stepDataMailTemplateCustomers",
            "attachmentFiles",
        },
        allowSetters = true
    )
    private MailTemplate mailTemplate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "organizations",
            "processInfos",
            "stepInProcesses",
            "stepInProMailTemplateCustomers",
            "steps",
            "stepMailTemplateCustomers",
            "stepData",
            "stepDataMailTemplateCustomers",
            "attachmentFiles",
        },
        allowSetters = true
    )
    private MailTemplate mailTemplateCustomer;

    @OneToMany(mappedBy = "step")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "step",
            "processInfo",
            "tennant",
            "created",
            "modified",
            "rank",
            "organization",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInSteps",
        },
        allowSetters = true
    )
    private Set<StepInProcess> stepInProcesses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Step id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepCode() {
        return this.stepCode;
    }

    public Step stepCode(String stepCode) {
        this.setStepCode(stepCode);
        return this;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getStepName() {
        return this.stepName;
    }

    public Step stepName(String stepName) {
        this.setStepName(stepName);
        return this;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Double getProcessingTerm() {
        return this.processingTerm;
    }

    public Step processingTerm(Double processingTerm) {
        this.setProcessingTerm(processingTerm);
        return this;
    }

    public void setProcessingTerm(Double processingTerm) {
        this.processingTerm = processingTerm;
    }

    public Instant getProcessingTermTime() {
        return this.processingTermTime;
    }

    public Step processingTermTime(Instant processingTermTime) {
        this.setProcessingTermTime(processingTermTime);
        return this;
    }

    public void setProcessingTermTime(Instant processingTermTime) {
        this.processingTermTime = processingTermTime;
    }

    public Boolean getIsRequiredSignature() {
        return this.isRequiredSignature;
    }

    public Step isRequiredSignature(Boolean isRequiredSignature) {
        this.setIsRequiredSignature(isRequiredSignature);
        return this;
    }

    public void setIsRequiredSignature(Boolean isRequiredSignature) {
        this.isRequiredSignature = isRequiredSignature;
    }

    public Boolean getIsBack() {
        return this.isBack;
    }

    public Step isBack(Boolean isBack) {
        this.setIsBack(isBack);
        return this;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public Boolean getIsAuthorizedApproval() {
        return this.isAuthorizedApproval;
    }

    public Step isAuthorizedApproval(Boolean isAuthorizedApproval) {
        this.setIsAuthorizedApproval(isAuthorizedApproval);
        return this;
    }

    public void setIsAuthorizedApproval(Boolean isAuthorizedApproval) {
        this.isAuthorizedApproval = isAuthorizedApproval;
    }

    public Boolean getIsExamine() {
        return this.isExamine;
    }

    public Step isExamine(Boolean isExamine) {
        this.setIsExamine(isExamine);
        return this;
    }

    public void setIsExamine(Boolean isExamine) {
        this.isExamine = isExamine;
    }

    public Boolean getIsConsult() {
        return this.isConsult;
    }

    public Step isConsult(Boolean isConsult) {
        this.setIsConsult(isConsult);
        return this;
    }

    public void setIsConsult(Boolean isConsult) {
        this.isConsult = isConsult;
    }

    public Boolean getIsEdit() {
        return this.isEdit;
    }

    public Step isEdit(Boolean isEdit) {
        this.setIsEdit(isEdit);
        return this;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public Boolean getIsRequestModify() {
        return this.isRequestModify;
    }

    public Step isRequestModify(Boolean isRequestModify) {
        this.setIsRequestModify(isRequestModify);
        return this;
    }

    public void setIsRequestModify(Boolean isRequestModify) {
        this.isRequestModify = isRequestModify;
    }

    public Boolean getIsSendMail() {
        return this.isSendMail;
    }

    public Step isSendMail(Boolean isSendMail) {
        this.setIsSendMail(isSendMail);
        return this;
    }

    public void setIsSendMail(Boolean isSendMail) {
        this.isSendMail = isSendMail;
    }

    public Boolean getIsReSend() {
        return this.isReSend;
    }

    public Step isReSend(Boolean isReSend) {
        this.setIsReSend(isReSend);
        return this;
    }

    public void setIsReSend(Boolean isReSend) {
        this.isReSend = isReSend;
    }

    public Boolean getIsAutoSendMail() {
        return this.isAutoSendMail;
    }

    public Step isAutoSendMail(Boolean isAutoSendMail) {
        this.setIsAutoSendMail(isAutoSendMail);
        return this;
    }

    public void setIsAutoSendMail(Boolean isAutoSendMail) {
        this.isAutoSendMail = isAutoSendMail;
    }

    public Boolean getIsAttachFilePDF() {
        return this.isAttachFilePDF;
    }

    public Step isAttachFilePDF(Boolean isAttachFilePDF) {
        this.setIsAttachFilePDF(isAttachFilePDF);
        return this;
    }

    public void setIsAttachFilePDF(Boolean isAttachFilePDF) {
        this.isAttachFilePDF = isAttachFilePDF;
    }

    public Boolean getIsSendNoticePriority() {
        return this.isSendNoticePriority;
    }

    public Step isSendNoticePriority(Boolean isSendNoticePriority) {
        this.setIsSendNoticePriority(isSendNoticePriority);
        return this;
    }

    public void setIsSendNoticePriority(Boolean isSendNoticePriority) {
        this.isSendNoticePriority = isSendNoticePriority;
    }

    public String getSendNoticePriorityType() {
        return this.sendNoticePriorityType;
    }

    public Step sendNoticePriorityType(String sendNoticePriorityType) {
        this.setSendNoticePriorityType(sendNoticePriorityType);
        return this;
    }

    public void setSendNoticePriorityType(String sendNoticePriorityType) {
        this.sendNoticePriorityType = sendNoticePriorityType;
    }

    public Boolean getIsCreateOTPLink() {
        return this.isCreateOTPLink;
    }

    public Step isCreateOTPLink(Boolean isCreateOTPLink) {
        this.setIsCreateOTPLink(isCreateOTPLink);
        return this;
    }

    public void setIsCreateOTPLink(Boolean isCreateOTPLink) {
        this.isCreateOTPLink = isCreateOTPLink;
    }

    public Boolean getIsSendOTP() {
        return this.isSendOTP;
    }

    public Step isSendOTP(Boolean isSendOTP) {
        this.setIsSendOTP(isSendOTP);
        return this;
    }

    public void setIsSendOTP(Boolean isSendOTP) {
        this.isSendOTP = isSendOTP;
    }

    public Boolean getIsExportPDF() {
        return this.isExportPDF;
    }

    public Step isExportPDF(Boolean isExportPDF) {
        this.setIsExportPDF(isExportPDF);
        return this;
    }

    public void setIsExportPDF(Boolean isExportPDF) {
        this.isExportPDF = isExportPDF;
    }

    public Boolean getIsAddQRCode() {
        return this.isAddQRCode;
    }

    public Step isAddQRCode(Boolean isAddQRCode) {
        this.setIsAddQRCode(isAddQRCode);
        return this;
    }

    public void setIsAddQRCode(Boolean isAddQRCode) {
        this.isAddQRCode = isAddQRCode;
    }

    public Boolean getIsAddWaterMark() {
        return this.isAddWaterMark;
    }

    public Step isAddWaterMark(Boolean isAddWaterMark) {
        this.setIsAddWaterMark(isAddWaterMark);
        return this;
    }

    public void setIsAddWaterMark(Boolean isAddWaterMark) {
        this.isAddWaterMark = isAddWaterMark;
    }

    public Boolean getIsAddHistory() {
        return this.isAddHistory;
    }

    public Step isAddHistory(Boolean isAddHistory) {
        this.setIsAddHistory(isAddHistory);
        return this;
    }

    public void setIsAddHistory(Boolean isAddHistory) {
        this.isAddHistory = isAddHistory;
    }

    public Boolean getIsProcessSaveDoc() {
        return this.isProcessSaveDoc;
    }

    public Step isProcessSaveDoc(Boolean isProcessSaveDoc) {
        this.setIsProcessSaveDoc(isProcessSaveDoc);
        return this;
    }

    public void setIsProcessSaveDoc(Boolean isProcessSaveDoc) {
        this.isProcessSaveDoc = isProcessSaveDoc;
    }

    public Boolean getIsChangeProcess() {
        return this.isChangeProcess;
    }

    public Step isChangeProcess(Boolean isChangeProcess) {
        this.setIsChangeProcess(isChangeProcess);
        return this;
    }

    public void setIsChangeProcess(Boolean isChangeProcess) {
        this.isChangeProcess = isChangeProcess;
    }

    public Boolean getIsSaveDoc() {
        return this.isSaveDoc;
    }

    public Step isSaveDoc(Boolean isSaveDoc) {
        this.setIsSaveDoc(isSaveDoc);
        return this;
    }

    public void setIsSaveDoc(Boolean isSaveDoc) {
        this.isSaveDoc = isSaveDoc;
    }

    public Boolean getIsCreateReport() {
        return this.isCreateReport;
    }

    public Step isCreateReport(Boolean isCreateReport) {
        this.setIsCreateReport(isCreateReport);
        return this;
    }

    public void setIsCreateReport(Boolean isCreateReport) {
        this.isCreateReport = isCreateReport;
    }

    public Boolean getIsCreateOfficalDispath() {
        return this.isCreateOfficalDispath;
    }

    public Step isCreateOfficalDispath(Boolean isCreateOfficalDispath) {
        this.setIsCreateOfficalDispath(isCreateOfficalDispath);
        return this;
    }

    public void setIsCreateOfficalDispath(Boolean isCreateOfficalDispath) {
        this.isCreateOfficalDispath = isCreateOfficalDispath;
    }

    public Boolean getIsCreateContract() {
        return this.isCreateContract;
    }

    public Step isCreateContract(Boolean isCreateContract) {
        this.setIsCreateContract(isCreateContract);
        return this;
    }

    public void setIsCreateContract(Boolean isCreateContract) {
        this.isCreateContract = isCreateContract;
    }

    public String getContractType() {
        return this.contractType;
    }

    public Step contractType(String contractType) {
        this.setContractType(contractType);
        return this;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getProcessOfContract() {
        return this.processOfContract;
    }

    public Step processOfContract(String processOfContract) {
        this.setProcessOfContract(processOfContract);
        return this;
    }

    public void setProcessOfContract(String processOfContract) {
        this.processOfContract = processOfContract;
    }

    public String getCreateContractType() {
        return this.createContractType;
    }

    public Step createContractType(String createContractType) {
        this.setCreateContractType(createContractType);
        return this;
    }

    public void setCreateContractType(String createContractType) {
        this.createContractType = createContractType;
    }

    public Boolean getIsProcessRecover() {
        return this.isProcessRecover;
    }

    public Step isProcessRecover(Boolean isProcessRecover) {
        this.setIsProcessRecover(isProcessRecover);
        return this;
    }

    public void setIsProcessRecover(Boolean isProcessRecover) {
        this.isProcessRecover = isProcessRecover;
    }

    public Boolean getIsRecall() {
        return this.isRecall;
    }

    public Step isRecall(Boolean isRecall) {
        this.setIsRecall(isRecall);
        return this;
    }

    public void setIsRecall(Boolean isRecall) {
        this.isRecall = isRecall;
    }

    public Boolean getIsCreateTFSTask() {
        return this.isCreateTFSTask;
    }

    public Step isCreateTFSTask(Boolean isCreateTFSTask) {
        this.setIsCreateTFSTask(isCreateTFSTask);
        return this;
    }

    public void setIsCreateTFSTask(Boolean isCreateTFSTask) {
        this.isCreateTFSTask = isCreateTFSTask;
    }

    public Boolean getIsDeny() {
        return this.isDeny;
    }

    public Step isDeny(Boolean isDeny) {
        this.setIsDeny(isDeny);
        return this;
    }

    public void setIsDeny(Boolean isDeny) {
        this.isDeny = isDeny;
    }

    public Long getOptionDeny() {
        return this.optionDeny;
    }

    public Step optionDeny(Long optionDeny) {
        this.setOptionDeny(optionDeny);
        return this;
    }

    public void setOptionDeny(Long optionDeny) {
        this.optionDeny = optionDeny;
    }

    public Boolean getIsSendMailCustomer() {
        return this.isSendMailCustomer;
    }

    public Step isSendMailCustomer(Boolean isSendMailCustomer) {
        this.setIsSendMailCustomer(isSendMailCustomer);
        return this;
    }

    public void setIsSendMailCustomer(Boolean isSendMailCustomer) {
        this.isSendMailCustomer = isSendMailCustomer;
    }

    public Boolean getIsSyncSap() {
        return this.isSyncSap;
    }

    public Step isSyncSap(Boolean isSyncSap) {
        this.setIsSyncSap(isSyncSap);
        return this;
    }

    public void setIsSyncSap(Boolean isSyncSap) {
        this.isSyncSap = isSyncSap;
    }

    public Boolean getIsApprove() {
        return this.isApprove;
    }

    public Step isApprove(Boolean isApprove) {
        this.setIsApprove(isApprove);
        return this;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public Boolean getIsManageStamp() {
        return this.isManageStamp;
    }

    public Step isManageStamp(Boolean isManageStamp) {
        this.setIsManageStamp(isManageStamp);
        return this;
    }

    public void setIsManageStamp(Boolean isManageStamp) {
        this.isManageStamp = isManageStamp;
    }

    public Boolean getIsChangeStatus() {
        return this.isChangeStatus;
    }

    public Step isChangeStatus(Boolean isChangeStatus) {
        this.setIsChangeStatus(isChangeStatus);
        return this;
    }

    public void setIsChangeStatus(Boolean isChangeStatus) {
        this.isChangeStatus = isChangeStatus;
    }

    public String getOrganizationCode() {
        return this.organizationCode;
    }

    public Step organizationCode(String organizationCode) {
        this.setOrganizationCode(organizationCode);
        return this;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Step organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankCode() {
        return this.rankCode;
    }

    public Step rankCode(String rankCode) {
        this.setRankCode(rankCode);
        return this;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getRankName() {
        return this.rankName;
    }

    public Step rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getDescription() {
        return this.description;
    }

    public Step description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Step createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Step createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Step createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Step createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Step modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Step modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Step isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Step isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getVersion() {
        return this.version;
    }

    public Step version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public Step tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public Step tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public Step tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public Step created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public Step modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Step rank(Rank rank) {
        this.setRank(rank);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Step organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public MailTemplate getMailTemplate() {
        return this.mailTemplate;
    }

    public void setMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public Step mailTemplate(MailTemplate mailTemplate) {
        this.setMailTemplate(mailTemplate);
        return this;
    }

    public MailTemplate getMailTemplateCustomer() {
        return this.mailTemplateCustomer;
    }

    public void setMailTemplateCustomer(MailTemplate mailTemplate) {
        this.mailTemplateCustomer = mailTemplate;
    }

    public Step mailTemplateCustomer(MailTemplate mailTemplate) {
        this.setMailTemplateCustomer(mailTemplate);
        return this;
    }

    public Set<StepInProcess> getStepInProcesses() {
        return this.stepInProcesses;
    }

    public void setStepInProcesses(Set<StepInProcess> stepInProcesses) {
        if (this.stepInProcesses != null) {
            this.stepInProcesses.forEach(i -> i.setStep(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setStep(this));
        }
        this.stepInProcesses = stepInProcesses;
    }

    public Step stepInProcesses(Set<StepInProcess> stepInProcesses) {
        this.setStepInProcesses(stepInProcesses);
        return this;
    }

    public Step addStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.add(stepInProcess);
        stepInProcess.setStep(this);
        return this;
    }

    public Step removeStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.remove(stepInProcess);
        stepInProcess.setStep(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Step)) {
            return false;
        }
        return id != null && id.equals(((Step) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Step{" +
            "id=" + getId() +
            ", stepCode='" + getStepCode() + "'" +
            ", stepName='" + getStepName() + "'" +
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
            ", isDelete='" + getIsDelete() + "'" +
            ", version=" + getVersion() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
