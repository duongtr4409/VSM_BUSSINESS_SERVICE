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
 * A StepData.
 */
@Entity
@Table(name = "step_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stepdata")
public class StepData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "step_data_code")
    private String stepDataCode;

    @Column(name = "step_data_name")
    private String stepDataName;

    @Column(name = "step_order")
    private Long stepOrder;

    @Column(name = "round_number")
    private Long roundNumber;

    @Column(name = "time_active")
    private Instant timeActive;

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

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @JsonIgnoreProperties(
        value = {
            "nextStep",
            "processData",
            "requestData",
            "tennant",
            "created",
            "modified",
            "stepInProcess",
            "rank",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInfos",
            "previousStep",
            "reqdataProcessHis",
            "examines",
            "consults",
            "attachmentInSteps",
            "requestRecalls",
            "transferHandles",
            "resultOfSteps",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private StepData nextStep;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified", "stepData" }, allowSetters = true)
    private ProcessData processData;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "request",
            "status",
            "tennant",
            "requestType",
            "requestGroup",
            "organization",
            "created",
            "modified",
            "subStatus",
            "reqDataConcerned",
            "approver",
            "revoker",
            "userInfos",
            "formData",
            "attachmentFiles",
            "reqdataProcessHis",
            "reqdataChangeHis",
            "processData",
            "stepData",
            "fieldData",
            "informationInExchanges",
            "tagInExchanges",
            "requestRecalls",
            "oTPS",
            "signData",
            "manageStampInfos",
        },
        allowSetters = true
    )
    private RequestData requestData;

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
    private StepInProcess stepInProcess;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Rank rank;

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

    @ManyToMany
    @JoinTable(
        name = "rel_step_data__user_info",
        joinColumns = @JoinColumn(name = "step_data_id"),
        inverseJoinColumns = @JoinColumn(name = "user_info_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<UserInfo> userInfos = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "nextStep",
            "processData",
            "requestData",
            "tennant",
            "created",
            "modified",
            "stepInProcess",
            "rank",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInfos",
            "previousStep",
            "reqdataProcessHis",
            "examines",
            "consults",
            "attachmentInSteps",
            "requestRecalls",
            "transferHandles",
            "resultOfSteps",
        },
        allowSetters = true
    )
    @OneToOne(mappedBy = "nextStep")
    private StepData previousStep;

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "stepData", "processer", "attachmentFiles" }, allowSetters = true)
    private Set<ReqdataProcessHis> reqdataProcessHis = new HashSet<>();

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "stepData", "sender", "receiver", "examineReplies" }, allowSetters = true)
    private Set<Examine> examines = new HashSet<>();

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "stepData", "sender", "receiver", "consultReplies" }, allowSetters = true)
    private Set<Consult> consults = new HashSet<>();

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attachmentInStepType", "creater", "modifier", "stepData" }, allowSetters = true)
    private Set<AttachmentInStep> attachmentInSteps = new HashSet<>();

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recaller", "processer", "requestData", "stepData" }, allowSetters = true)
    private Set<RequestRecall> requestRecalls = new HashSet<>();

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "statusTransferHandle", "organization", "dispatchBook", "stepData", "transfer", "creater", "modifier", "receiversHandles",
        },
        allowSetters = true
    )
    private Set<TransferHandle> transferHandles = new HashSet<>();

    @OneToMany(mappedBy = "stepData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "excutor", "stepData", "tennant" }, allowSetters = true)
    private Set<ResultOfStep> resultOfSteps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StepData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepDataCode() {
        return this.stepDataCode;
    }

    public StepData stepDataCode(String stepDataCode) {
        this.setStepDataCode(stepDataCode);
        return this;
    }

    public void setStepDataCode(String stepDataCode) {
        this.stepDataCode = stepDataCode;
    }

    public String getStepDataName() {
        return this.stepDataName;
    }

    public StepData stepDataName(String stepDataName) {
        this.setStepDataName(stepDataName);
        return this;
    }

    public void setStepDataName(String stepDataName) {
        this.stepDataName = stepDataName;
    }

    public Long getStepOrder() {
        return this.stepOrder;
    }

    public StepData stepOrder(Long stepOrder) {
        this.setStepOrder(stepOrder);
        return this;
    }

    public void setStepOrder(Long stepOrder) {
        this.stepOrder = stepOrder;
    }

    public Long getRoundNumber() {
        return this.roundNumber;
    }

    public StepData roundNumber(Long roundNumber) {
        this.setRoundNumber(roundNumber);
        return this;
    }

    public void setRoundNumber(Long roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Instant getTimeActive() {
        return this.timeActive;
    }

    public StepData timeActive(Instant timeActive) {
        this.setTimeActive(timeActive);
        return this;
    }

    public void setTimeActive(Instant timeActive) {
        this.timeActive = timeActive;
    }

    public Double getProcessingTerm() {
        return this.processingTerm;
    }

    public StepData processingTerm(Double processingTerm) {
        this.setProcessingTerm(processingTerm);
        return this;
    }

    public void setProcessingTerm(Double processingTerm) {
        this.processingTerm = processingTerm;
    }

    public Instant getProcessingTermTime() {
        return this.processingTermTime;
    }

    public StepData processingTermTime(Instant processingTermTime) {
        this.setProcessingTermTime(processingTermTime);
        return this;
    }

    public void setProcessingTermTime(Instant processingTermTime) {
        this.processingTermTime = processingTermTime;
    }

    public Boolean getIsRequiredSignature() {
        return this.isRequiredSignature;
    }

    public StepData isRequiredSignature(Boolean isRequiredSignature) {
        this.setIsRequiredSignature(isRequiredSignature);
        return this;
    }

    public void setIsRequiredSignature(Boolean isRequiredSignature) {
        this.isRequiredSignature = isRequiredSignature;
    }

    public Boolean getIsBack() {
        return this.isBack;
    }

    public StepData isBack(Boolean isBack) {
        this.setIsBack(isBack);
        return this;
    }

    public void setIsBack(Boolean isBack) {
        this.isBack = isBack;
    }

    public Boolean getIsAuthorizedApproval() {
        return this.isAuthorizedApproval;
    }

    public StepData isAuthorizedApproval(Boolean isAuthorizedApproval) {
        this.setIsAuthorizedApproval(isAuthorizedApproval);
        return this;
    }

    public void setIsAuthorizedApproval(Boolean isAuthorizedApproval) {
        this.isAuthorizedApproval = isAuthorizedApproval;
    }

    public Boolean getIsExamine() {
        return this.isExamine;
    }

    public StepData isExamine(Boolean isExamine) {
        this.setIsExamine(isExamine);
        return this;
    }

    public void setIsExamine(Boolean isExamine) {
        this.isExamine = isExamine;
    }

    public Boolean getIsConsult() {
        return this.isConsult;
    }

    public StepData isConsult(Boolean isConsult) {
        this.setIsConsult(isConsult);
        return this;
    }

    public void setIsConsult(Boolean isConsult) {
        this.isConsult = isConsult;
    }

    public Boolean getIsEdit() {
        return this.isEdit;
    }

    public StepData isEdit(Boolean isEdit) {
        this.setIsEdit(isEdit);
        return this;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public Boolean getIsRequestModify() {
        return this.isRequestModify;
    }

    public StepData isRequestModify(Boolean isRequestModify) {
        this.setIsRequestModify(isRequestModify);
        return this;
    }

    public void setIsRequestModify(Boolean isRequestModify) {
        this.isRequestModify = isRequestModify;
    }

    public Boolean getIsSendMail() {
        return this.isSendMail;
    }

    public StepData isSendMail(Boolean isSendMail) {
        this.setIsSendMail(isSendMail);
        return this;
    }

    public void setIsSendMail(Boolean isSendMail) {
        this.isSendMail = isSendMail;
    }

    public Boolean getIsReSend() {
        return this.isReSend;
    }

    public StepData isReSend(Boolean isReSend) {
        this.setIsReSend(isReSend);
        return this;
    }

    public void setIsReSend(Boolean isReSend) {
        this.isReSend = isReSend;
    }

    public Boolean getIsAutoSendMail() {
        return this.isAutoSendMail;
    }

    public StepData isAutoSendMail(Boolean isAutoSendMail) {
        this.setIsAutoSendMail(isAutoSendMail);
        return this;
    }

    public void setIsAutoSendMail(Boolean isAutoSendMail) {
        this.isAutoSendMail = isAutoSendMail;
    }

    public Boolean getIsAttachFilePDF() {
        return this.isAttachFilePDF;
    }

    public StepData isAttachFilePDF(Boolean isAttachFilePDF) {
        this.setIsAttachFilePDF(isAttachFilePDF);
        return this;
    }

    public void setIsAttachFilePDF(Boolean isAttachFilePDF) {
        this.isAttachFilePDF = isAttachFilePDF;
    }

    public Boolean getIsSendNoticePriority() {
        return this.isSendNoticePriority;
    }

    public StepData isSendNoticePriority(Boolean isSendNoticePriority) {
        this.setIsSendNoticePriority(isSendNoticePriority);
        return this;
    }

    public void setIsSendNoticePriority(Boolean isSendNoticePriority) {
        this.isSendNoticePriority = isSendNoticePriority;
    }

    public String getSendNoticePriorityType() {
        return this.sendNoticePriorityType;
    }

    public StepData sendNoticePriorityType(String sendNoticePriorityType) {
        this.setSendNoticePriorityType(sendNoticePriorityType);
        return this;
    }

    public void setSendNoticePriorityType(String sendNoticePriorityType) {
        this.sendNoticePriorityType = sendNoticePriorityType;
    }

    public Boolean getIsCreateOTPLink() {
        return this.isCreateOTPLink;
    }

    public StepData isCreateOTPLink(Boolean isCreateOTPLink) {
        this.setIsCreateOTPLink(isCreateOTPLink);
        return this;
    }

    public void setIsCreateOTPLink(Boolean isCreateOTPLink) {
        this.isCreateOTPLink = isCreateOTPLink;
    }

    public Boolean getIsSendOTP() {
        return this.isSendOTP;
    }

    public StepData isSendOTP(Boolean isSendOTP) {
        this.setIsSendOTP(isSendOTP);
        return this;
    }

    public void setIsSendOTP(Boolean isSendOTP) {
        this.isSendOTP = isSendOTP;
    }

    public Boolean getIsExportPDF() {
        return this.isExportPDF;
    }

    public StepData isExportPDF(Boolean isExportPDF) {
        this.setIsExportPDF(isExportPDF);
        return this;
    }

    public void setIsExportPDF(Boolean isExportPDF) {
        this.isExportPDF = isExportPDF;
    }

    public Boolean getIsAddQRCode() {
        return this.isAddQRCode;
    }

    public StepData isAddQRCode(Boolean isAddQRCode) {
        this.setIsAddQRCode(isAddQRCode);
        return this;
    }

    public void setIsAddQRCode(Boolean isAddQRCode) {
        this.isAddQRCode = isAddQRCode;
    }

    public Boolean getIsAddWaterMark() {
        return this.isAddWaterMark;
    }

    public StepData isAddWaterMark(Boolean isAddWaterMark) {
        this.setIsAddWaterMark(isAddWaterMark);
        return this;
    }

    public void setIsAddWaterMark(Boolean isAddWaterMark) {
        this.isAddWaterMark = isAddWaterMark;
    }

    public Boolean getIsAddHistory() {
        return this.isAddHistory;
    }

    public StepData isAddHistory(Boolean isAddHistory) {
        this.setIsAddHistory(isAddHistory);
        return this;
    }

    public void setIsAddHistory(Boolean isAddHistory) {
        this.isAddHistory = isAddHistory;
    }

    public Boolean getIsProcessSaveDoc() {
        return this.isProcessSaveDoc;
    }

    public StepData isProcessSaveDoc(Boolean isProcessSaveDoc) {
        this.setIsProcessSaveDoc(isProcessSaveDoc);
        return this;
    }

    public void setIsProcessSaveDoc(Boolean isProcessSaveDoc) {
        this.isProcessSaveDoc = isProcessSaveDoc;
    }

    public Boolean getIsChangeProcess() {
        return this.isChangeProcess;
    }

    public StepData isChangeProcess(Boolean isChangeProcess) {
        this.setIsChangeProcess(isChangeProcess);
        return this;
    }

    public void setIsChangeProcess(Boolean isChangeProcess) {
        this.isChangeProcess = isChangeProcess;
    }

    public Boolean getIsSaveDoc() {
        return this.isSaveDoc;
    }

    public StepData isSaveDoc(Boolean isSaveDoc) {
        this.setIsSaveDoc(isSaveDoc);
        return this;
    }

    public void setIsSaveDoc(Boolean isSaveDoc) {
        this.isSaveDoc = isSaveDoc;
    }

    public Boolean getIsCreateReport() {
        return this.isCreateReport;
    }

    public StepData isCreateReport(Boolean isCreateReport) {
        this.setIsCreateReport(isCreateReport);
        return this;
    }

    public void setIsCreateReport(Boolean isCreateReport) {
        this.isCreateReport = isCreateReport;
    }

    public Boolean getIsCreateOfficalDispath() {
        return this.isCreateOfficalDispath;
    }

    public StepData isCreateOfficalDispath(Boolean isCreateOfficalDispath) {
        this.setIsCreateOfficalDispath(isCreateOfficalDispath);
        return this;
    }

    public void setIsCreateOfficalDispath(Boolean isCreateOfficalDispath) {
        this.isCreateOfficalDispath = isCreateOfficalDispath;
    }

    public Boolean getIsCreateContract() {
        return this.isCreateContract;
    }

    public StepData isCreateContract(Boolean isCreateContract) {
        this.setIsCreateContract(isCreateContract);
        return this;
    }

    public void setIsCreateContract(Boolean isCreateContract) {
        this.isCreateContract = isCreateContract;
    }

    public String getContractType() {
        return this.contractType;
    }

    public StepData contractType(String contractType) {
        this.setContractType(contractType);
        return this;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getProcessOfContract() {
        return this.processOfContract;
    }

    public StepData processOfContract(String processOfContract) {
        this.setProcessOfContract(processOfContract);
        return this;
    }

    public void setProcessOfContract(String processOfContract) {
        this.processOfContract = processOfContract;
    }

    public String getCreateContractType() {
        return this.createContractType;
    }

    public StepData createContractType(String createContractType) {
        this.setCreateContractType(createContractType);
        return this;
    }

    public void setCreateContractType(String createContractType) {
        this.createContractType = createContractType;
    }

    public Boolean getIsProcessRecover() {
        return this.isProcessRecover;
    }

    public StepData isProcessRecover(Boolean isProcessRecover) {
        this.setIsProcessRecover(isProcessRecover);
        return this;
    }

    public void setIsProcessRecover(Boolean isProcessRecover) {
        this.isProcessRecover = isProcessRecover;
    }

    public Boolean getIsRecall() {
        return this.isRecall;
    }

    public StepData isRecall(Boolean isRecall) {
        this.setIsRecall(isRecall);
        return this;
    }

    public void setIsRecall(Boolean isRecall) {
        this.isRecall = isRecall;
    }

    public Boolean getIsCreateTFSTask() {
        return this.isCreateTFSTask;
    }

    public StepData isCreateTFSTask(Boolean isCreateTFSTask) {
        this.setIsCreateTFSTask(isCreateTFSTask);
        return this;
    }

    public void setIsCreateTFSTask(Boolean isCreateTFSTask) {
        this.isCreateTFSTask = isCreateTFSTask;
    }

    public Boolean getIsDeny() {
        return this.isDeny;
    }

    public StepData isDeny(Boolean isDeny) {
        this.setIsDeny(isDeny);
        return this;
    }

    public void setIsDeny(Boolean isDeny) {
        this.isDeny = isDeny;
    }

    public Long getOptionDeny() {
        return this.optionDeny;
    }

    public StepData optionDeny(Long optionDeny) {
        this.setOptionDeny(optionDeny);
        return this;
    }

    public void setOptionDeny(Long optionDeny) {
        this.optionDeny = optionDeny;
    }

    public Boolean getIsSendMailCustomer() {
        return this.isSendMailCustomer;
    }

    public StepData isSendMailCustomer(Boolean isSendMailCustomer) {
        this.setIsSendMailCustomer(isSendMailCustomer);
        return this;
    }

    public void setIsSendMailCustomer(Boolean isSendMailCustomer) {
        this.isSendMailCustomer = isSendMailCustomer;
    }

    public Boolean getIsSyncSap() {
        return this.isSyncSap;
    }

    public StepData isSyncSap(Boolean isSyncSap) {
        this.setIsSyncSap(isSyncSap);
        return this;
    }

    public void setIsSyncSap(Boolean isSyncSap) {
        this.isSyncSap = isSyncSap;
    }

    public Boolean getIsApprove() {
        return this.isApprove;
    }

    public StepData isApprove(Boolean isApprove) {
        this.setIsApprove(isApprove);
        return this;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public Boolean getIsManageStamp() {
        return this.isManageStamp;
    }

    public StepData isManageStamp(Boolean isManageStamp) {
        this.setIsManageStamp(isManageStamp);
        return this;
    }

    public void setIsManageStamp(Boolean isManageStamp) {
        this.isManageStamp = isManageStamp;
    }

    public Boolean getIsChangeStatus() {
        return this.isChangeStatus;
    }

    public StepData isChangeStatus(Boolean isChangeStatus) {
        this.setIsChangeStatus(isChangeStatus);
        return this;
    }

    public void setIsChangeStatus(Boolean isChangeStatus) {
        this.isChangeStatus = isChangeStatus;
    }

    public String getOrganizationCode() {
        return this.organizationCode;
    }

    public StepData organizationCode(String organizationCode) {
        this.setOrganizationCode(organizationCode);
        return this;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public StepData organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankCode() {
        return this.rankCode;
    }

    public StepData rankCode(String rankCode) {
        this.setRankCode(rankCode);
        return this;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getRankName() {
        return this.rankName;
    }

    public StepData rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getDescription() {
        return this.description;
    }

    public StepData description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public StepData createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public StepData createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public StepData createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public StepData createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public StepData modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public StepData modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public StepData isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public StepData tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public StepData tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public StepData getNextStep() {
        return this.nextStep;
    }

    public void setNextStep(StepData stepData) {
        this.nextStep = stepData;
    }

    public StepData nextStep(StepData stepData) {
        this.setNextStep(stepData);
        return this;
    }

    public ProcessData getProcessData() {
        return this.processData;
    }

    public void setProcessData(ProcessData processData) {
        this.processData = processData;
    }

    public StepData processData(ProcessData processData) {
        this.setProcessData(processData);
        return this;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public StepData requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public StepData tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public StepData created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public StepData modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public StepInProcess getStepInProcess() {
        return this.stepInProcess;
    }

    public void setStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcess = stepInProcess;
    }

    public StepData stepInProcess(StepInProcess stepInProcess) {
        this.setStepInProcess(stepInProcess);
        return this;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public StepData rank(Rank rank) {
        this.setRank(rank);
        return this;
    }

    public MailTemplate getMailTemplate() {
        return this.mailTemplate;
    }

    public void setMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public StepData mailTemplate(MailTemplate mailTemplate) {
        this.setMailTemplate(mailTemplate);
        return this;
    }

    public MailTemplate getMailTemplateCustomer() {
        return this.mailTemplateCustomer;
    }

    public void setMailTemplateCustomer(MailTemplate mailTemplate) {
        this.mailTemplateCustomer = mailTemplate;
    }

    public StepData mailTemplateCustomer(MailTemplate mailTemplate) {
        this.setMailTemplateCustomer(mailTemplate);
        return this;
    }

    public Set<UserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public StepData userInfos(Set<UserInfo> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public StepData addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.getStepData().add(this);
        return this;
    }

    public StepData removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.getStepData().remove(this);
        return this;
    }

    public StepData getPreviousStep() {
        return this.previousStep;
    }

    public void setPreviousStep(StepData stepData) {
        if (this.previousStep != null) {
            this.previousStep.setNextStep(null);
        }
        if (stepData != null) {
            stepData.setNextStep(this);
        }
        this.previousStep = stepData;
    }

    public StepData previousStep(StepData stepData) {
        this.setPreviousStep(stepData);
        return this;
    }

    public Set<ReqdataProcessHis> getReqdataProcessHis() {
        return this.reqdataProcessHis;
    }

    public void setReqdataProcessHis(Set<ReqdataProcessHis> reqdataProcessHis) {
        if (this.reqdataProcessHis != null) {
            this.reqdataProcessHis.forEach(i -> i.setStepData(null));
        }
        if (reqdataProcessHis != null) {
            reqdataProcessHis.forEach(i -> i.setStepData(this));
        }
        this.reqdataProcessHis = reqdataProcessHis;
    }

    public StepData reqdataProcessHis(Set<ReqdataProcessHis> reqdataProcessHis) {
        this.setReqdataProcessHis(reqdataProcessHis);
        return this;
    }

    public StepData addReqdataProcessHis(ReqdataProcessHis reqdataProcessHis) {
        this.reqdataProcessHis.add(reqdataProcessHis);
        reqdataProcessHis.setStepData(this);
        return this;
    }

    public StepData removeReqdataProcessHis(ReqdataProcessHis reqdataProcessHis) {
        this.reqdataProcessHis.remove(reqdataProcessHis);
        reqdataProcessHis.setStepData(null);
        return this;
    }

    public Set<Examine> getExamines() {
        return this.examines;
    }

    public void setExamines(Set<Examine> examines) {
        if (this.examines != null) {
            this.examines.forEach(i -> i.setStepData(null));
        }
        if (examines != null) {
            examines.forEach(i -> i.setStepData(this));
        }
        this.examines = examines;
    }

    public StepData examines(Set<Examine> examines) {
        this.setExamines(examines);
        return this;
    }

    public StepData addExamine(Examine examine) {
        this.examines.add(examine);
        examine.setStepData(this);
        return this;
    }

    public StepData removeExamine(Examine examine) {
        this.examines.remove(examine);
        examine.setStepData(null);
        return this;
    }

    public Set<Consult> getConsults() {
        return this.consults;
    }

    public void setConsults(Set<Consult> consults) {
        if (this.consults != null) {
            this.consults.forEach(i -> i.setStepData(null));
        }
        if (consults != null) {
            consults.forEach(i -> i.setStepData(this));
        }
        this.consults = consults;
    }

    public StepData consults(Set<Consult> consults) {
        this.setConsults(consults);
        return this;
    }

    public StepData addConsult(Consult consult) {
        this.consults.add(consult);
        consult.setStepData(this);
        return this;
    }

    public StepData removeConsult(Consult consult) {
        this.consults.remove(consult);
        consult.setStepData(null);
        return this;
    }

    public Set<AttachmentInStep> getAttachmentInSteps() {
        return this.attachmentInSteps;
    }

    public void setAttachmentInSteps(Set<AttachmentInStep> attachmentInSteps) {
        if (this.attachmentInSteps != null) {
            this.attachmentInSteps.forEach(i -> i.setStepData(null));
        }
        if (attachmentInSteps != null) {
            attachmentInSteps.forEach(i -> i.setStepData(this));
        }
        this.attachmentInSteps = attachmentInSteps;
    }

    public StepData attachmentInSteps(Set<AttachmentInStep> attachmentInSteps) {
        this.setAttachmentInSteps(attachmentInSteps);
        return this;
    }

    public StepData addAttachmentInStep(AttachmentInStep attachmentInStep) {
        this.attachmentInSteps.add(attachmentInStep);
        attachmentInStep.setStepData(this);
        return this;
    }

    public StepData removeAttachmentInStep(AttachmentInStep attachmentInStep) {
        this.attachmentInSteps.remove(attachmentInStep);
        attachmentInStep.setStepData(null);
        return this;
    }

    public Set<RequestRecall> getRequestRecalls() {
        return this.requestRecalls;
    }

    public void setRequestRecalls(Set<RequestRecall> requestRecalls) {
        if (this.requestRecalls != null) {
            this.requestRecalls.forEach(i -> i.setStepData(null));
        }
        if (requestRecalls != null) {
            requestRecalls.forEach(i -> i.setStepData(this));
        }
        this.requestRecalls = requestRecalls;
    }

    public StepData requestRecalls(Set<RequestRecall> requestRecalls) {
        this.setRequestRecalls(requestRecalls);
        return this;
    }

    public StepData addRequestRecall(RequestRecall requestRecall) {
        this.requestRecalls.add(requestRecall);
        requestRecall.setStepData(this);
        return this;
    }

    public StepData removeRequestRecall(RequestRecall requestRecall) {
        this.requestRecalls.remove(requestRecall);
        requestRecall.setStepData(null);
        return this;
    }

    public Set<TransferHandle> getTransferHandles() {
        return this.transferHandles;
    }

    public void setTransferHandles(Set<TransferHandle> transferHandles) {
        if (this.transferHandles != null) {
            this.transferHandles.forEach(i -> i.setStepData(null));
        }
        if (transferHandles != null) {
            transferHandles.forEach(i -> i.setStepData(this));
        }
        this.transferHandles = transferHandles;
    }

    public StepData transferHandles(Set<TransferHandle> transferHandles) {
        this.setTransferHandles(transferHandles);
        return this;
    }

    public StepData addTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.add(transferHandle);
        transferHandle.setStepData(this);
        return this;
    }

    public StepData removeTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.remove(transferHandle);
        transferHandle.setStepData(null);
        return this;
    }

    public Set<ResultOfStep> getResultOfSteps() {
        return this.resultOfSteps;
    }

    public void setResultOfSteps(Set<ResultOfStep> resultOfSteps) {
        if (this.resultOfSteps != null) {
            this.resultOfSteps.forEach(i -> i.setStepData(null));
        }
        if (resultOfSteps != null) {
            resultOfSteps.forEach(i -> i.setStepData(this));
        }
        this.resultOfSteps = resultOfSteps;
    }

    public StepData resultOfSteps(Set<ResultOfStep> resultOfSteps) {
        this.setResultOfSteps(resultOfSteps);
        return this;
    }

    public StepData addResultOfStep(ResultOfStep resultOfStep) {
        this.resultOfSteps.add(resultOfStep);
        resultOfStep.setStepData(this);
        return this;
    }

    public StepData removeResultOfStep(ResultOfStep resultOfStep) {
        this.resultOfSteps.remove(resultOfStep);
        resultOfStep.setStepData(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StepData)) {
            return false;
        }
        return id != null && id.equals(((StepData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StepData{" +
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
            "}";
    }
}
