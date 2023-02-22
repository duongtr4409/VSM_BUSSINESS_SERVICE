package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A RequestData.
 */
@Entity
@Table(name = "request_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "requestdata")
public class RequestData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_data_code")
    private String requestDataCode;

    @Column(name = "request_data_name")
    private String requestDataName;

    @Column(name = "directory_path")
    private String directoryPath;

    @Column(name = "id_directory_path")
    private String idDirectoryPath;

    @Column(name = "rule_generate_attach_name")
    private String ruleGenerateAttachName;

    @Column(name = "number_attach")
    private Long numberAttach;

    @Column(name = "current_round")
    private Long currentRound;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "sign_type")
    private String signType;

    @Column(name = "sign_type_name")
    private String signTypeName;

    @Column(name = "sign_type_code")
    private String signTypeCode;

    @Column(name = "request_type_name")
    private String requestTypeName;

    @Column(name = "request_type_code")
    private String requestTypeCode;

    @Column(name = "request_group_name")
    private String requestGroupName;

    @Column(name = "request_group_code")
    private String requestGroupCode;

    @Column(name = "is_create_outgoing_doc")
    private Boolean isCreateOutgoingDoc;

    @Column(name = "is_approve")
    private Boolean isApprove;

    @Column(name = "approver_name")
    private String approverName;

    @Column(name = "approver_org_name")
    private String approverOrgName;

    @Column(name = "approver_rank_name")
    private String approverRankName;

    @Column(name = "is_revoked")
    private Boolean isRevoked;

    @Column(name = "revoker_name")
    private String revokerName;

    @Column(name = "revoker_org_name")
    private String revokerOrgName;

    @Column(name = "revoker_rank_name")
    private String revokerRankName;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "old_status")
    private Long oldStatus;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "object_schema")
    private String objectSchema;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "object_model")
    private String objectModel;

    @Column(name = "expired_time")
    private Instant expiredTime;

    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "time_done")
    private Instant timeDone;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "mapping_info")
    private String mappingInfo;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "sap_mapping")
    private String sapMapping;

    @Column(name = "data_room_path")
    private String dataRoomPath;

    @Column(name = "data_room_id")
    private String dataRoomId;

    @Column(name = "data_room_drive_id")
    private String dataRoomDriveId;

    @Column(name = "contract_number")
    private String contractNumber;

    @Column(name = "result_sync_contract")
    private Boolean resultSyncContract;

    @Column(name = "contract_expire_time")
    private Instant contractExpireTime;

    @Column(name = "plot_of_land_number")
    private String plotOfLandNumber;

    @Column(name = "is_sync_sap")
    private Boolean isSyncSap;

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

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "requestType",
            "requestGroup",
            "form",
            "tennant",
            "created",
            "modified",
            "templateForms",
            "processInfos",
            "requestData",
            "roleRequests",
        },
        allowSetters = true
    )
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "statusRequests", "subStatusRequests" }, allowSetters = true)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestGroup", "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private RequestType requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private RequestGroup requestGroup;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "statusRequests", "subStatusRequests" }, allowSetters = true)
    private Status subStatus;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private RequestData reqDataConcerned;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private UserInfo approver;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private UserInfo revoker;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_request_data__user_info",
        joinColumns = @JoinColumn(name = "request_data_id"),
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

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "form", "tennant", "created", "modified", "fieldData" }, allowSetters = true)
    private Set<FormData> formData = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "fileType",
            "requestData",
            "tennant",
            "created",
            "modified",
            "templateForm",
            "reqdataProcessHis",
            "officialDispatch",
            "stepProcessDoc",
            "mailTemplate",
            "manageStampInfo",
            "attachmentPermisitions",
            "changeFileHistories",
        },
        allowSetters = true
    )
    private Set<AttachmentFile> attachmentFiles = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "stepData", "processer", "attachmentFiles" }, allowSetters = true)
    private Set<ReqdataProcessHis> reqdataProcessHis = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified" }, allowSetters = true)
    private Set<ReqdataChangeHis> reqdataChangeHis = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified", "stepData" }, allowSetters = true)
    private Set<ProcessData> processData = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<StepData> stepData = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "field", "formData", "requestData", "tennant", "created", "modified", "fieldInForm" },
        allowSetters = true
    )
    private Set<FieldData> fieldData = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sender", "requestData", "tagInExchanges" }, allowSetters = true)
    private Set<InformationInExchange> informationInExchanges = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tager", "tagged", "requestData", "informationInExchange" }, allowSetters = true)
    private Set<TagInExchange> tagInExchanges = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recaller", "processer", "requestData", "stepData" }, allowSetters = true)
    private Set<RequestRecall> requestRecalls = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData" }, allowSetters = true)
    private Set<OTP> oTPS = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "signatureInfomation", "userInfo" }, allowSetters = true)
    private Set<SignData> signData = new HashSet<>();

    @OneToMany(mappedBy = "requestData", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "requestData", "stamp", "orgReturn", "creater", "modifier", "orgStorages", "attachmentFiles" },
        allowSetters = true
    )
    private Set<ManageStampInfo> manageStampInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestDataCode() {
        return this.requestDataCode;
    }

    public RequestData requestDataCode(String requestDataCode) {
        this.setRequestDataCode(requestDataCode);
        return this;
    }

    public void setRequestDataCode(String requestDataCode) {
        this.requestDataCode = requestDataCode;
    }

    public String getRequestDataName() {
        return this.requestDataName;
    }

    public RequestData requestDataName(String requestDataName) {
        this.setRequestDataName(requestDataName);
        return this;
    }

    public void setRequestDataName(String requestDataName) {
        this.requestDataName = requestDataName;
    }

    public String getDirectoryPath() {
        return this.directoryPath;
    }

    public RequestData directoryPath(String directoryPath) {
        this.setDirectoryPath(directoryPath);
        return this;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getIdDirectoryPath() {
        return this.idDirectoryPath;
    }

    public RequestData idDirectoryPath(String idDirectoryPath) {
        this.setIdDirectoryPath(idDirectoryPath);
        return this;
    }

    public void setIdDirectoryPath(String idDirectoryPath) {
        this.idDirectoryPath = idDirectoryPath;
    }

    public String getRuleGenerateAttachName() {
        return this.ruleGenerateAttachName;
    }

    public RequestData ruleGenerateAttachName(String ruleGenerateAttachName) {
        this.setRuleGenerateAttachName(ruleGenerateAttachName);
        return this;
    }

    public void setRuleGenerateAttachName(String ruleGenerateAttachName) {
        this.ruleGenerateAttachName = ruleGenerateAttachName;
    }

    public Long getNumberAttach() {
        return this.numberAttach;
    }

    public RequestData numberAttach(Long numberAttach) {
        this.setNumberAttach(numberAttach);
        return this;
    }

    public void setNumberAttach(Long numberAttach) {
        this.numberAttach = numberAttach;
    }

    public Long getCurrentRound() {
        return this.currentRound;
    }

    public RequestData currentRound(Long currentRound) {
        this.setCurrentRound(currentRound);
        return this;
    }

    public void setCurrentRound(Long currentRound) {
        this.currentRound = currentRound;
    }

    public String getTitle() {
        return this.title;
    }

    public RequestData title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public RequestData description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSignType() {
        return this.signType;
    }

    public RequestData signType(String signType) {
        this.setSignType(signType);
        return this;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignTypeName() {
        return this.signTypeName;
    }

    public RequestData signTypeName(String signTypeName) {
        this.setSignTypeName(signTypeName);
        return this;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTypeCode() {
        return this.signTypeCode;
    }

    public RequestData signTypeCode(String signTypeCode) {
        this.setSignTypeCode(signTypeCode);
        return this;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public String getRequestTypeName() {
        return this.requestTypeName;
    }

    public RequestData requestTypeName(String requestTypeName) {
        this.setRequestTypeName(requestTypeName);
        return this;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    public String getRequestTypeCode() {
        return this.requestTypeCode;
    }

    public RequestData requestTypeCode(String requestTypeCode) {
        this.setRequestTypeCode(requestTypeCode);
        return this;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestGroupName() {
        return this.requestGroupName;
    }

    public RequestData requestGroupName(String requestGroupName) {
        this.setRequestGroupName(requestGroupName);
        return this;
    }

    public void setRequestGroupName(String requestGroupName) {
        this.requestGroupName = requestGroupName;
    }

    public String getRequestGroupCode() {
        return this.requestGroupCode;
    }

    public RequestData requestGroupCode(String requestGroupCode) {
        this.setRequestGroupCode(requestGroupCode);
        return this;
    }

    public void setRequestGroupCode(String requestGroupCode) {
        this.requestGroupCode = requestGroupCode;
    }

    public Boolean getIsCreateOutgoingDoc() {
        return this.isCreateOutgoingDoc;
    }

    public RequestData isCreateOutgoingDoc(Boolean isCreateOutgoingDoc) {
        this.setIsCreateOutgoingDoc(isCreateOutgoingDoc);
        return this;
    }

    public void setIsCreateOutgoingDoc(Boolean isCreateOutgoingDoc) {
        this.isCreateOutgoingDoc = isCreateOutgoingDoc;
    }

    public Boolean getIsApprove() {
        return this.isApprove;
    }

    public RequestData isApprove(Boolean isApprove) {
        this.setIsApprove(isApprove);
        return this;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public String getApproverName() {
        return this.approverName;
    }

    public RequestData approverName(String approverName) {
        this.setApproverName(approverName);
        return this;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverOrgName() {
        return this.approverOrgName;
    }

    public RequestData approverOrgName(String approverOrgName) {
        this.setApproverOrgName(approverOrgName);
        return this;
    }

    public void setApproverOrgName(String approverOrgName) {
        this.approverOrgName = approverOrgName;
    }

    public String getApproverRankName() {
        return this.approverRankName;
    }

    public RequestData approverRankName(String approverRankName) {
        this.setApproverRankName(approverRankName);
        return this;
    }

    public void setApproverRankName(String approverRankName) {
        this.approverRankName = approverRankName;
    }

    public Boolean getIsRevoked() {
        return this.isRevoked;
    }

    public RequestData isRevoked(Boolean isRevoked) {
        this.setIsRevoked(isRevoked);
        return this;
    }

    public void setIsRevoked(Boolean isRevoked) {
        this.isRevoked = isRevoked;
    }

    public String getRevokerName() {
        return this.revokerName;
    }

    public RequestData revokerName(String revokerName) {
        this.setRevokerName(revokerName);
        return this;
    }

    public void setRevokerName(String revokerName) {
        this.revokerName = revokerName;
    }

    public String getRevokerOrgName() {
        return this.revokerOrgName;
    }

    public RequestData revokerOrgName(String revokerOrgName) {
        this.setRevokerOrgName(revokerOrgName);
        return this;
    }

    public void setRevokerOrgName(String revokerOrgName) {
        this.revokerOrgName = revokerOrgName;
    }

    public String getRevokerRankName() {
        return this.revokerRankName;
    }

    public RequestData revokerRankName(String revokerRankName) {
        this.setRevokerRankName(revokerRankName);
        return this;
    }

    public void setRevokerRankName(String revokerRankName) {
        this.revokerRankName = revokerRankName;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public RequestData statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getOldStatus() {
        return this.oldStatus;
    }

    public RequestData oldStatus(Long oldStatus) {
        this.setOldStatus(oldStatus);
        return this;
    }

    public void setOldStatus(Long oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getObjectSchema() {
        return this.objectSchema;
    }

    public RequestData objectSchema(String objectSchema) {
        this.setObjectSchema(objectSchema);
        return this;
    }

    public void setObjectSchema(String objectSchema) {
        this.objectSchema = objectSchema;
    }

    public String getObjectModel() {
        return this.objectModel;
    }

    public RequestData objectModel(String objectModel) {
        this.setObjectModel(objectModel);
        return this;
    }

    public void setObjectModel(String objectModel) {
        this.objectModel = objectModel;
    }

    public Instant getExpiredTime() {
        return this.expiredTime;
    }

    public RequestData expiredTime(Instant expiredTime) {
        this.setExpiredTime(expiredTime);
        return this;
    }

    public void setExpiredTime(Instant expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Boolean getIsDone() {
        return this.isDone;
    }

    public RequestData isDone(Boolean isDone) {
        this.setIsDone(isDone);
        return this;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Instant getTimeDone() {
        return this.timeDone;
    }

    public RequestData timeDone(Instant timeDone) {
        this.setTimeDone(timeDone);
        return this;
    }

    public void setTimeDone(Instant timeDone) {
        this.timeDone = timeDone;
    }

    public String getMappingInfo() {
        return this.mappingInfo;
    }

    public RequestData mappingInfo(String mappingInfo) {
        this.setMappingInfo(mappingInfo);
        return this;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
    }

    public String getSapMapping() {
        return this.sapMapping;
    }

    public RequestData sapMapping(String sapMapping) {
        this.setSapMapping(sapMapping);
        return this;
    }

    public void setSapMapping(String sapMapping) {
        this.sapMapping = sapMapping;
    }

    public String getDataRoomPath() {
        return this.dataRoomPath;
    }

    public RequestData dataRoomPath(String dataRoomPath) {
        this.setDataRoomPath(dataRoomPath);
        return this;
    }

    public void setDataRoomPath(String dataRoomPath) {
        this.dataRoomPath = dataRoomPath;
    }

    public String getDataRoomId() {
        return this.dataRoomId;
    }

    public RequestData dataRoomId(String dataRoomId) {
        this.setDataRoomId(dataRoomId);
        return this;
    }

    public void setDataRoomId(String dataRoomId) {
        this.dataRoomId = dataRoomId;
    }

    public String getDataRoomDriveId() {
        return this.dataRoomDriveId;
    }

    public RequestData dataRoomDriveId(String dataRoomDriveId) {
        this.setDataRoomDriveId(dataRoomDriveId);
        return this;
    }

    public void setDataRoomDriveId(String dataRoomDriveId) {
        this.dataRoomDriveId = dataRoomDriveId;
    }

    public String getContractNumber() {
        return this.contractNumber;
    }

    public RequestData contractNumber(String contractNumber) {
        this.setContractNumber(contractNumber);
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Boolean getResultSyncContract(){
        return this.resultSyncContract;
    }

    public RequestData resultSyncContract(Boolean resultSyncContract){
        this.setResultSyncContract(resultSyncContract);
        return this;
    }

    public void setResultSyncContract(Boolean resultSyncContract){
        this.resultSyncContract = resultSyncContract;
    }

    public Instant getContractExpireTime() {
        return this.contractExpireTime;
    }

    public RequestData contractExpireTime(Instant contractExpireTime) {
        this.setContractExpireTime(contractExpireTime);
        return this;
    }

    public void setContractExpireTime(Instant contractExpireTime) {
        this.contractExpireTime = contractExpireTime;
    }

    public String getPlotOfLandNumber() {
        return this.plotOfLandNumber;
    }

    public RequestData plotOfLandNumber(String plotOfLandNumber) {
        this.setPlotOfLandNumber(plotOfLandNumber);
        return this;
    }

    public void setPlotOfLandNumber(String plotOfLandNumber) {
        this.plotOfLandNumber = plotOfLandNumber;
    }

    public Boolean getIsSyncSap() {
        return this.isSyncSap;
    }

    public RequestData isSyncSap(Boolean isSyncSap) {
        this.setIsSyncSap(isSyncSap);
        return this;
    }

    public void setIsSyncSap(Boolean isSyncSap) {
        this.isSyncSap = isSyncSap;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public RequestData createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public RequestData createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public RequestData createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public RequestData createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public RequestData modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public RequestData modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public RequestData isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public RequestData isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public RequestData tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public RequestData tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Request getRequest() {
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestData request(Request request) {
        this.setRequest(request);
        return this;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RequestData status(Status status) {
        this.setStatus(status);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public RequestData tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestData requestType(RequestType requestType) {
        this.setRequestType(requestType);
        return this;
    }

    public RequestGroup getRequestGroup() {
        return this.requestGroup;
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }

    public RequestData requestGroup(RequestGroup requestGroup) {
        this.setRequestGroup(requestGroup);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public RequestData organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public RequestData created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public RequestData modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Status getSubStatus() {
        return this.subStatus;
    }

    public void setSubStatus(Status status) {
        this.subStatus = status;
    }

    public RequestData subStatus(Status status) {
        this.setSubStatus(status);
        return this;
    }

    public RequestData getReqDataConcerned() {
        return this.reqDataConcerned;
    }

    public void setReqDataConcerned(RequestData requestData) {
        this.reqDataConcerned = requestData;
    }

    public RequestData reqDataConcerned(RequestData requestData) {
        this.setReqDataConcerned(requestData);
        return this;
    }

    public UserInfo getApprover() {
        return this.approver;
    }

    public void setApprover(UserInfo userInfo) {
        this.approver = userInfo;
    }

    public RequestData approver(UserInfo userInfo) {
        this.setApprover(userInfo);
        return this;
    }

    public UserInfo getRevoker() {
        return this.revoker;
    }

    public void setRevoker(UserInfo userInfo) {
        this.revoker = userInfo;
    }

    public RequestData revoker(UserInfo userInfo) {
        this.setRevoker(userInfo);
        return this;
    }

    public Set<UserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public RequestData userInfos(Set<UserInfo> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public RequestData addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.getRequestData().add(this);
        return this;
    }

    public RequestData removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.getRequestData().remove(this);
        return this;
    }

    public Set<FormData> getFormData() {
        return this.formData;
    }

    public void setFormData(Set<FormData> formData) {
        if (this.formData != null) {
            this.formData.forEach(i -> i.setRequestData(null));
        }
        if (formData != null) {
            formData.forEach(i -> i.setRequestData(this));
        }
        this.formData = formData;
    }

    public RequestData formData(Set<FormData> formData) {
        this.setFormData(formData);
        return this;
    }

    public RequestData addFormData(FormData formData) {
        this.formData.add(formData);
        formData.setRequestData(this);
        return this;
    }

    public RequestData removeFormData(FormData formData) {
        this.formData.remove(formData);
        formData.setRequestData(null);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setRequestData(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setRequestData(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public RequestData attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public RequestData addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setRequestData(this);
        return this;
    }

    public RequestData removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setRequestData(null);
        return this;
    }

    public Set<ReqdataProcessHis> getReqdataProcessHis() {
        return this.reqdataProcessHis;
    }

    public void setReqdataProcessHis(Set<ReqdataProcessHis> reqdataProcessHis) {
        if (this.reqdataProcessHis != null) {
            this.reqdataProcessHis.forEach(i -> i.setRequestData(null));
        }
        if (reqdataProcessHis != null) {
            reqdataProcessHis.forEach(i -> i.setRequestData(this));
        }
        this.reqdataProcessHis = reqdataProcessHis;
    }

    public RequestData reqdataProcessHis(Set<ReqdataProcessHis> reqdataProcessHis) {
        this.setReqdataProcessHis(reqdataProcessHis);
        return this;
    }

    public RequestData addReqdataProcessHis(ReqdataProcessHis reqdataProcessHis) {
        this.reqdataProcessHis.add(reqdataProcessHis);
        reqdataProcessHis.setRequestData(this);
        return this;
    }

    public RequestData removeReqdataProcessHis(ReqdataProcessHis reqdataProcessHis) {
        this.reqdataProcessHis.remove(reqdataProcessHis);
        reqdataProcessHis.setRequestData(null);
        return this;
    }

    public Set<ReqdataChangeHis> getReqdataChangeHis() {
        return this.reqdataChangeHis;
    }

    public void setReqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        if (this.reqdataChangeHis != null) {
            this.reqdataChangeHis.forEach(i -> i.setRequestData(null));
        }
        if (reqdataChangeHis != null) {
            reqdataChangeHis.forEach(i -> i.setRequestData(this));
        }
        this.reqdataChangeHis = reqdataChangeHis;
    }

    public RequestData reqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        this.setReqdataChangeHis(reqdataChangeHis);
        return this;
    }

    public RequestData addReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.reqdataChangeHis.add(reqdataChangeHis);
        reqdataChangeHis.setRequestData(this);
        return this;
    }

    public RequestData removeReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.reqdataChangeHis.remove(reqdataChangeHis);
        reqdataChangeHis.setRequestData(null);
        return this;
    }

    public Set<ProcessData> getProcessData() {
        return this.processData;
    }

    public void setProcessData(Set<ProcessData> processData) {
        if (this.processData != null) {
            this.processData.forEach(i -> i.setRequestData(null));
        }
        if (processData != null) {
            processData.forEach(i -> i.setRequestData(this));
        }
        this.processData = processData;
    }

    public RequestData processData(Set<ProcessData> processData) {
        this.setProcessData(processData);
        return this;
    }

    public RequestData addProcessData(ProcessData processData) {
        this.processData.add(processData);
        processData.setRequestData(this);
        return this;
    }

    public RequestData removeProcessData(ProcessData processData) {
        this.processData.remove(processData);
        processData.setRequestData(null);
        return this;
    }

    public Set<StepData> getStepData() {
        return this.stepData;
    }

    public void setStepData(Set<StepData> stepData) {
        if (this.stepData != null) {
            this.stepData.forEach(i -> i.setRequestData(null));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.setRequestData(this));
        }
        this.stepData = stepData;
    }

    public RequestData stepData(Set<StepData> stepData) {
        this.setStepData(stepData);
        return this;
    }

    public RequestData addStepData(StepData stepData) {
        this.stepData.add(stepData);
        stepData.setRequestData(this);
        return this;
    }

    public RequestData removeStepData(StepData stepData) {
        this.stepData.remove(stepData);
        stepData.setRequestData(null);
        return this;
    }

    public Set<FieldData> getFieldData() {
        return this.fieldData;
    }

    public void setFieldData(Set<FieldData> fieldData) {
        if (this.fieldData != null) {
            this.fieldData.forEach(i -> i.setRequestData(null));
        }
        if (fieldData != null) {
            fieldData.forEach(i -> i.setRequestData(this));
        }
        this.fieldData = fieldData;
    }

    public RequestData fieldData(Set<FieldData> fieldData) {
        this.setFieldData(fieldData);
        return this;
    }

    public RequestData addFieldData(FieldData fieldData) {
        this.fieldData.add(fieldData);
        fieldData.setRequestData(this);
        return this;
    }

    public RequestData removeFieldData(FieldData fieldData) {
        this.fieldData.remove(fieldData);
        fieldData.setRequestData(null);
        return this;
    }

    public Set<InformationInExchange> getInformationInExchanges() {
        return this.informationInExchanges;
    }

    public void setInformationInExchanges(Set<InformationInExchange> informationInExchanges) {
        if (this.informationInExchanges != null) {
            this.informationInExchanges.forEach(i -> i.setRequestData(null));
        }
        if (informationInExchanges != null) {
            informationInExchanges.forEach(i -> i.setRequestData(this));
        }
        this.informationInExchanges = informationInExchanges;
    }

    public RequestData informationInExchanges(Set<InformationInExchange> informationInExchanges) {
        this.setInformationInExchanges(informationInExchanges);
        return this;
    }

    public RequestData addInformationInExchange(InformationInExchange informationInExchange) {
        this.informationInExchanges.add(informationInExchange);
        informationInExchange.setRequestData(this);
        return this;
    }

    public RequestData removeInformationInExchange(InformationInExchange informationInExchange) {
        this.informationInExchanges.remove(informationInExchange);
        informationInExchange.setRequestData(null);
        return this;
    }

    public Set<TagInExchange> getTagInExchanges() {
        return this.tagInExchanges;
    }

    public void setTagInExchanges(Set<TagInExchange> tagInExchanges) {
        if (this.tagInExchanges != null) {
            this.tagInExchanges.forEach(i -> i.setRequestData(null));
        }
        if (tagInExchanges != null) {
            tagInExchanges.forEach(i -> i.setRequestData(this));
        }
        this.tagInExchanges = tagInExchanges;
    }

    public RequestData tagInExchanges(Set<TagInExchange> tagInExchanges) {
        this.setTagInExchanges(tagInExchanges);
        return this;
    }

    public RequestData addTagInExchange(TagInExchange tagInExchange) {
        this.tagInExchanges.add(tagInExchange);
        tagInExchange.setRequestData(this);
        return this;
    }

    public RequestData removeTagInExchange(TagInExchange tagInExchange) {
        this.tagInExchanges.remove(tagInExchange);
        tagInExchange.setRequestData(null);
        return this;
    }

    public Set<RequestRecall> getRequestRecalls() {
        return this.requestRecalls;
    }

    public void setRequestRecalls(Set<RequestRecall> requestRecalls) {
        if (this.requestRecalls != null) {
            this.requestRecalls.forEach(i -> i.setRequestData(null));
        }
        if (requestRecalls != null) {
            requestRecalls.forEach(i -> i.setRequestData(this));
        }
        this.requestRecalls = requestRecalls;
    }

    public RequestData requestRecalls(Set<RequestRecall> requestRecalls) {
        this.setRequestRecalls(requestRecalls);
        return this;
    }

    public RequestData addRequestRecall(RequestRecall requestRecall) {
        this.requestRecalls.add(requestRecall);
        requestRecall.setRequestData(this);
        return this;
    }

    public RequestData removeRequestRecall(RequestRecall requestRecall) {
        this.requestRecalls.remove(requestRecall);
        requestRecall.setRequestData(null);
        return this;
    }

    public Set<OTP> getOTPS() {
        return this.oTPS;
    }

    public void setOTPS(Set<OTP> oTPS) {
        if (this.oTPS != null) {
            this.oTPS.forEach(i -> i.setRequestData(null));
        }
        if (oTPS != null) {
            oTPS.forEach(i -> i.setRequestData(this));
        }
        this.oTPS = oTPS;
    }

    public RequestData oTPS(Set<OTP> oTPS) {
        this.setOTPS(oTPS);
        return this;
    }

    public RequestData addOTP(OTP oTP) {
        this.oTPS.add(oTP);
        oTP.setRequestData(this);
        return this;
    }

    public RequestData removeOTP(OTP oTP) {
        this.oTPS.remove(oTP);
        oTP.setRequestData(null);
        return this;
    }

    public Set<SignData> getSignData() {
        return this.signData;
    }

    public void setSignData(Set<SignData> signData) {
        if (this.signData != null) {
            this.signData.forEach(i -> i.setRequestData(null));
        }
        if (signData != null) {
            signData.forEach(i -> i.setRequestData(this));
        }
        this.signData = signData;
    }

    public RequestData signData(Set<SignData> signData) {
        this.setSignData(signData);
        return this;
    }

    public RequestData addSignData(SignData signData) {
        this.signData.add(signData);
        signData.setRequestData(this);
        return this;
    }

    public RequestData removeSignData(SignData signData) {
        this.signData.remove(signData);
        signData.setRequestData(null);
        return this;
    }

    public Set<ManageStampInfo> getManageStampInfos() {
        return this.manageStampInfos;
    }

    public void setManageStampInfos(Set<ManageStampInfo> manageStampInfos) {
        if (this.manageStampInfos != null) {
            this.manageStampInfos.forEach(i -> i.setRequestData(null));
        }
        if (manageStampInfos != null) {
            manageStampInfos.forEach(i -> i.setRequestData(this));
        }
        this.manageStampInfos = manageStampInfos;
    }

    public RequestData manageStampInfos(Set<ManageStampInfo> manageStampInfos) {
        this.setManageStampInfos(manageStampInfos);
        return this;
    }

    public RequestData addManageStampInfo(ManageStampInfo manageStampInfo) {
        this.manageStampInfos.add(manageStampInfo);
        manageStampInfo.setRequestData(this);
        return this;
    }

    public RequestData removeManageStampInfo(ManageStampInfo manageStampInfo) {
        this.manageStampInfos.remove(manageStampInfo);
        manageStampInfo.setRequestData(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestData)) {
            return false;
        }
        return id != null && id.equals(((RequestData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestData{" +
            "id=" + getId() +
            ", requestDataCode='" + getRequestDataCode() + "'" +
            ", requestDataName='" + getRequestDataName() + "'" +
            ", directoryPath='" + getDirectoryPath() + "'" +
            ", idDirectoryPath='" + getIdDirectoryPath() + "'" +
            ", ruleGenerateAttachName='" + getRuleGenerateAttachName() + "'" +
            ", numberAttach=" + getNumberAttach() +
            ", currentRound=" + getCurrentRound() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", signType='" + getSignType() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", requestTypeName='" + getRequestTypeName() + "'" +
            ", requestTypeCode='" + getRequestTypeCode() + "'" +
            ", requestGroupName='" + getRequestGroupName() + "'" +
            ", requestGroupCode='" + getRequestGroupCode() + "'" +
            ", isCreateOutgoingDoc='" + getIsCreateOutgoingDoc() + "'" +
            ", isApprove='" + getIsApprove() + "'" +
            ", approverName='" + getApproverName() + "'" +
            ", approverOrgName='" + getApproverOrgName() + "'" +
            ", approverRankName='" + getApproverRankName() + "'" +
            ", isRevoked='" + getIsRevoked() + "'" +
            ", revokerName='" + getRevokerName() + "'" +
            ", revokerOrgName='" + getRevokerOrgName() + "'" +
            ", revokerRankName='" + getRevokerRankName() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", oldStatus=" + getOldStatus() +
            ", objectSchema='" + getObjectSchema() + "'" +
            ", objectModel='" + getObjectModel() + "'" +
            ", expiredTime='" + getExpiredTime() + "'" +
            ", isDone='" + getIsDone() + "'" +
            ", timeDone='" + getTimeDone() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
            ", sapMapping='" + getSapMapping() + "'" +
            ", dataRoomPath='" + getDataRoomPath() + "'" +
            ", dataRoomId='" + getDataRoomId() + "'" +
            ", dataRoomDriveId='" + getDataRoomDriveId() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", resultSyncContract='" + getResultSyncContract() + "'" +
            ", contractExpireTime='" + getContractExpireTime() + "'" +
            ", plotOfLandNumber='" + getPlotOfLandNumber() + "'" +
            ", isSyncSap='" + getIsSyncSap() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
