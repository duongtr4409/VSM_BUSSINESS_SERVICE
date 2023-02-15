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
 * A OfficialDispatch.
 */
@Entity
@Table(name = "official_dispatch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "officialdispatch")
public class OfficialDispatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "official_dispatch_name")
    private String officialDispatchName;

    @Column(name = "official_dispatch_code")
    private String officialDispatchCode;

    @Column(name = "official_dispatch_number")
    private String officialDispatchNumber;

    @Column(name = "official_dispatch_type_name")
    private String officialDispatchTypeName;

    @Column(name = "official_dispatch_type_code")
    private String officialDispatchTypeCode;

    @Column(name = "compendium")
    private String compendium;

    @Column(name = "priority_name")
    private String priorityName;

    @Column(name = "priority_code")
    private String priorityCode;

    @Column(name = "security_name")
    private String securityName;

    @Column(name = "security_code")
    private String securityCode;

    @Column(name = "document_type_name")
    private String documentTypeName;

    @Column(name = "document_type_code")
    private String documentTypeCode;

    @Column(name = "release_org_name")
    private String releaseOrgName;

    @Column(name = "release_org_avatar")
    private String releaseOrgAvatar;

    @Column(name = "release_date")
    private Instant releaseDate;

    @Column(name = "compose_org_name")
    private String composeOrgName;

    @Column(name = "compose_org_avatar")
    private String composeOrgAvatar;

    @Column(name = "sign_name")
    private String signName;

    @Column(name = "sign_avatar")
    private String signAvatar;

    @Column(name = "sign_date")
    private Instant signDate;

    @Column(name = "outgoing_date")
    private Instant outgoingDate;

    @Column(name = "receive_org_text")
    private String receiveOrgText;

    @Column(name = "place_send_name")
    private String placeSendName;

    @Column(name = "place_send_code")
    private String placeSendCode;

    @Column(name = "dispatch_official_date")
    private Instant dispatchOfficialDate;

    @Column(name = "comming_date")
    private Instant commingDate;

    @Column(name = "final_scan_doc_name")
    private String finalScanDocName;

    @Column(name = "into_outging_book_date")
    private Instant intoOutgingBookDate;

    @Column(name = "number_of_book")
    private String numberOfBook;

    @Column(name = "dispatch_book_name")
    private String dispatchBookName;

    @Column(name = "dispatch_book_code")
    private String dispatchBookCode;

    @Column(name = "dispatch_book_type")
    private String dispatchBookType;

    @Column(name = "outgoing_status_name")
    private String outgoingStatusName;

    @Column(name = "old_status")
    private Long oldStatus;

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

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "organization", "creater", "modifier", "dispatchBookOrgs", "officialDispatches", "transferHandles" },
        allowSetters = true
    )
    private DispatchBook dispatchBook;

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
    private Organization releaseOrg;

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
    private Organization composeOrg;

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
    private Organization ownerOrg;

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
    private UserInfo signer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creater", "modifier", "officialDispatches" }, allowSetters = true)
    private OfficialDispatchType officialDispatchType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creater", "modifier", "officialDispatches" }, allowSetters = true)
    private DocumentType documentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creater", "modifier", "officialDispatches" }, allowSetters = true)
    private PriorityLevel priorityLevel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creater", "modifier", "officialDispatches" }, allowSetters = true)
    private SecurityLevel securityLevel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creater", "modifier", "officialDispatches" }, allowSetters = true)
    private OfficialDispatchStatus officialDispatchStatus;

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
    private UserInfo creater;

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
    private UserInfo modifier;

    @ManyToOne
    @JsonIgnoreProperties(value = { "officialDispatches" }, allowSetters = true)
    private OutOrganization outOrganization;

    @ManyToMany
    @JoinTable(
        name = "rel_official_dispatch__off_dispatch_user_read",
        joinColumns = @JoinColumn(name = "official_dispatch_id"),
        inverseJoinColumns = @JoinColumn(name = "off_dispatch_user_read_id")
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
    private Set<UserInfo> offDispatchUserReads = new HashSet<>();

    @OneToMany(mappedBy = "officialDispatch")
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

    @OneToMany(mappedBy = "officialDispatch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "officialDispatch", "creater", "modifier" }, allowSetters = true)
    private Set<OfficialDispatchHis> officialDispatchHis = new HashSet<>();

    @OneToMany(mappedBy = "officialDispatch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "officialDispatch", "attachmentFiles" }, allowSetters = true)
    private Set<StepProcessDoc> stepProcessDocs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OfficialDispatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficialDispatchName() {
        return this.officialDispatchName;
    }

    public OfficialDispatch officialDispatchName(String officialDispatchName) {
        this.setOfficialDispatchName(officialDispatchName);
        return this;
    }

    public void setOfficialDispatchName(String officialDispatchName) {
        this.officialDispatchName = officialDispatchName;
    }

    public String getOfficialDispatchCode() {
        return this.officialDispatchCode;
    }

    public OfficialDispatch officialDispatchCode(String officialDispatchCode) {
        this.setOfficialDispatchCode(officialDispatchCode);
        return this;
    }

    public void setOfficialDispatchCode(String officialDispatchCode) {
        this.officialDispatchCode = officialDispatchCode;
    }

    public String getOfficialDispatchNumber() {
        return this.officialDispatchNumber;
    }

    public OfficialDispatch officialDispatchNumber(String officialDispatchNumber) {
        this.setOfficialDispatchNumber(officialDispatchNumber);
        return this;
    }

    public void setOfficialDispatchNumber(String officialDispatchNumber) {
        this.officialDispatchNumber = officialDispatchNumber;
    }

    public String getOfficialDispatchTypeName() {
        return this.officialDispatchTypeName;
    }

    public OfficialDispatch officialDispatchTypeName(String officialDispatchTypeName) {
        this.setOfficialDispatchTypeName(officialDispatchTypeName);
        return this;
    }

    public void setOfficialDispatchTypeName(String officialDispatchTypeName) {
        this.officialDispatchTypeName = officialDispatchTypeName;
    }

    public String getOfficialDispatchTypeCode() {
        return this.officialDispatchTypeCode;
    }

    public OfficialDispatch officialDispatchTypeCode(String officialDispatchTypeCode) {
        this.setOfficialDispatchTypeCode(officialDispatchTypeCode);
        return this;
    }

    public void setOfficialDispatchTypeCode(String officialDispatchTypeCode) {
        this.officialDispatchTypeCode = officialDispatchTypeCode;
    }

    public String getCompendium() {
        return this.compendium;
    }

    public OfficialDispatch compendium(String compendium) {
        this.setCompendium(compendium);
        return this;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
    }

    public String getPriorityName() {
        return this.priorityName;
    }

    public OfficialDispatch priorityName(String priorityName) {
        this.setPriorityName(priorityName);
        return this;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityCode() {
        return this.priorityCode;
    }

    public OfficialDispatch priorityCode(String priorityCode) {
        this.setPriorityCode(priorityCode);
        return this;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getSecurityName() {
        return this.securityName;
    }

    public OfficialDispatch securityName(String securityName) {
        this.setSecurityName(securityName);
        return this;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public OfficialDispatch securityCode(String securityCode) {
        this.setSecurityCode(securityCode);
        return this;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getDocumentTypeName() {
        return this.documentTypeName;
    }

    public OfficialDispatch documentTypeName(String documentTypeName) {
        this.setDocumentTypeName(documentTypeName);
        return this;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeCode() {
        return this.documentTypeCode;
    }

    public OfficialDispatch documentTypeCode(String documentTypeCode) {
        this.setDocumentTypeCode(documentTypeCode);
        return this;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getReleaseOrgName() {
        return this.releaseOrgName;
    }

    public OfficialDispatch releaseOrgName(String releaseOrgName) {
        this.setReleaseOrgName(releaseOrgName);
        return this;
    }

    public void setReleaseOrgName(String releaseOrgName) {
        this.releaseOrgName = releaseOrgName;
    }

    public String getReleaseOrgAvatar() {
        return this.releaseOrgAvatar;
    }

    public OfficialDispatch releaseOrgAvatar(String releaseOrgAvatar) {
        this.setReleaseOrgAvatar(releaseOrgAvatar);
        return this;
    }

    public void setReleaseOrgAvatar(String releaseOrgAvatar) {
        this.releaseOrgAvatar = releaseOrgAvatar;
    }

    public Instant getReleaseDate() {
        return this.releaseDate;
    }

    public OfficialDispatch releaseDate(Instant releaseDate) {
        this.setReleaseDate(releaseDate);
        return this;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getComposeOrgName() {
        return this.composeOrgName;
    }

    public OfficialDispatch composeOrgName(String composeOrgName) {
        this.setComposeOrgName(composeOrgName);
        return this;
    }

    public void setComposeOrgName(String composeOrgName) {
        this.composeOrgName = composeOrgName;
    }

    public String getComposeOrgAvatar() {
        return this.composeOrgAvatar;
    }

    public OfficialDispatch composeOrgAvatar(String composeOrgAvatar) {
        this.setComposeOrgAvatar(composeOrgAvatar);
        return this;
    }

    public void setComposeOrgAvatar(String composeOrgAvatar) {
        this.composeOrgAvatar = composeOrgAvatar;
    }

    public String getSignName() {
        return this.signName;
    }

    public OfficialDispatch signName(String signName) {
        this.setSignName(signName);
        return this;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignAvatar() {
        return this.signAvatar;
    }

    public OfficialDispatch signAvatar(String signAvatar) {
        this.setSignAvatar(signAvatar);
        return this;
    }

    public void setSignAvatar(String signAvatar) {
        this.signAvatar = signAvatar;
    }

    public Instant getSignDate() {
        return this.signDate;
    }

    public OfficialDispatch signDate(Instant signDate) {
        this.setSignDate(signDate);
        return this;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public Instant getOutgoingDate() {
        return this.outgoingDate;
    }

    public OfficialDispatch outgoingDate(Instant outgoingDate) {
        this.setOutgoingDate(outgoingDate);
        return this;
    }

    public void setOutgoingDate(Instant outgoingDate) {
        this.outgoingDate = outgoingDate;
    }

    public String getReceiveOrgText() {
        return this.receiveOrgText;
    }

    public OfficialDispatch receiveOrgText(String receiveOrgText) {
        this.setReceiveOrgText(receiveOrgText);
        return this;
    }

    public void setReceiveOrgText(String receiveOrgText) {
        this.receiveOrgText = receiveOrgText;
    }

    public String getPlaceSendName() {
        return this.placeSendName;
    }

    public OfficialDispatch placeSendName(String placeSendName) {
        this.setPlaceSendName(placeSendName);
        return this;
    }

    public void setPlaceSendName(String placeSendName) {
        this.placeSendName = placeSendName;
    }

    public String getPlaceSendCode() {
        return this.placeSendCode;
    }

    public OfficialDispatch placeSendCode(String placeSendCode) {
        this.setPlaceSendCode(placeSendCode);
        return this;
    }

    public void setPlaceSendCode(String placeSendCode) {
        this.placeSendCode = placeSendCode;
    }

    public Instant getDispatchOfficialDate() {
        return this.dispatchOfficialDate;
    }

    public OfficialDispatch dispatchOfficialDate(Instant dispatchOfficialDate) {
        this.setDispatchOfficialDate(dispatchOfficialDate);
        return this;
    }

    public void setDispatchOfficialDate(Instant dispatchOfficialDate) {
        this.dispatchOfficialDate = dispatchOfficialDate;
    }

    public Instant getCommingDate() {
        return this.commingDate;
    }

    public OfficialDispatch commingDate(Instant commingDate) {
        this.setCommingDate(commingDate);
        return this;
    }

    public void setCommingDate(Instant commingDate) {
        this.commingDate = commingDate;
    }

    public String getFinalScanDocName() {
        return this.finalScanDocName;
    }

    public OfficialDispatch finalScanDocName(String finalScanDocName) {
        this.setFinalScanDocName(finalScanDocName);
        return this;
    }

    public void setFinalScanDocName(String finalScanDocName) {
        this.finalScanDocName = finalScanDocName;
    }

    public Instant getIntoOutgingBookDate() {
        return this.intoOutgingBookDate;
    }

    public OfficialDispatch intoOutgingBookDate(Instant intoOutgingBookDate) {
        this.setIntoOutgingBookDate(intoOutgingBookDate);
        return this;
    }

    public void setIntoOutgingBookDate(Instant intoOutgingBookDate) {
        this.intoOutgingBookDate = intoOutgingBookDate;
    }

    public String getNumberOfBook() {
        return this.numberOfBook;
    }

    public OfficialDispatch numberOfBook(String numberOfBook) {
        this.setNumberOfBook(numberOfBook);
        return this;
    }

    public void setNumberOfBook(String numberOfBook) {
        this.numberOfBook = numberOfBook;
    }

    public String getDispatchBookName() {
        return this.dispatchBookName;
    }

    public OfficialDispatch dispatchBookName(String dispatchBookName) {
        this.setDispatchBookName(dispatchBookName);
        return this;
    }

    public void setDispatchBookName(String dispatchBookName) {
        this.dispatchBookName = dispatchBookName;
    }

    public String getDispatchBookCode() {
        return this.dispatchBookCode;
    }

    public OfficialDispatch dispatchBookCode(String dispatchBookCode) {
        this.setDispatchBookCode(dispatchBookCode);
        return this;
    }

    public void setDispatchBookCode(String dispatchBookCode) {
        this.dispatchBookCode = dispatchBookCode;
    }

    public String getDispatchBookType() {
        return this.dispatchBookType;
    }

    public OfficialDispatch dispatchBookType(String dispatchBookType) {
        this.setDispatchBookType(dispatchBookType);
        return this;
    }

    public void setDispatchBookType(String dispatchBookType) {
        this.dispatchBookType = dispatchBookType;
    }

    public String getOutgoingStatusName() {
        return this.outgoingStatusName;
    }

    public OfficialDispatch outgoingStatusName(String outgoingStatusName) {
        this.setOutgoingStatusName(outgoingStatusName);
        return this;
    }

    public void setOutgoingStatusName(String outgoingStatusName) {
        this.outgoingStatusName = outgoingStatusName;
    }

    public Long getOldStatus() {
        return this.oldStatus;
    }

    public OfficialDispatch oldStatus(Long oldStatus) {
        this.setOldStatus(oldStatus);
        return this;
    }

    public void setOldStatus(Long oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public OfficialDispatch description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public OfficialDispatch createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public OfficialDispatch createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public OfficialDispatch createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OfficialDispatch createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public OfficialDispatch modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public OfficialDispatch modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public OfficialDispatch isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public OfficialDispatch isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public DispatchBook getDispatchBook() {
        return this.dispatchBook;
    }

    public void setDispatchBook(DispatchBook dispatchBook) {
        this.dispatchBook = dispatchBook;
    }

    public OfficialDispatch dispatchBook(DispatchBook dispatchBook) {
        this.setDispatchBook(dispatchBook);
        return this;
    }

    public Organization getReleaseOrg() {
        return this.releaseOrg;
    }

    public void setReleaseOrg(Organization organization) {
        this.releaseOrg = organization;
    }

    public OfficialDispatch releaseOrg(Organization organization) {
        this.setReleaseOrg(organization);
        return this;
    }

    public Organization getComposeOrg() {
        return this.composeOrg;
    }

    public void setComposeOrg(Organization organization) {
        this.composeOrg = organization;
    }

    public OfficialDispatch composeOrg(Organization organization) {
        this.setComposeOrg(organization);
        return this;
    }

    public Organization getOwnerOrg() {
        return this.ownerOrg;
    }

    public void setOwnerOrg(Organization organization) {
        this.ownerOrg = organization;
    }

    public OfficialDispatch ownerOrg(Organization organization) {
        this.setOwnerOrg(organization);
        return this;
    }

    public UserInfo getSigner() {
        return this.signer;
    }

    public void setSigner(UserInfo userInfo) {
        this.signer = userInfo;
    }

    public OfficialDispatch signer(UserInfo userInfo) {
        this.setSigner(userInfo);
        return this;
    }

    public OfficialDispatchType getOfficialDispatchType() {
        return this.officialDispatchType;
    }

    public void setOfficialDispatchType(OfficialDispatchType officialDispatchType) {
        this.officialDispatchType = officialDispatchType;
    }

    public OfficialDispatch officialDispatchType(OfficialDispatchType officialDispatchType) {
        this.setOfficialDispatchType(officialDispatchType);
        return this;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public OfficialDispatch documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    public PriorityLevel getPriorityLevel() {
        return this.priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public OfficialDispatch priorityLevel(PriorityLevel priorityLevel) {
        this.setPriorityLevel(priorityLevel);
        return this;
    }

    public SecurityLevel getSecurityLevel() {
        return this.securityLevel;
    }

    public void setSecurityLevel(SecurityLevel securityLevel) {
        this.securityLevel = securityLevel;
    }

    public OfficialDispatch securityLevel(SecurityLevel securityLevel) {
        this.setSecurityLevel(securityLevel);
        return this;
    }

    public OfficialDispatchStatus getOfficialDispatchStatus() {
        return this.officialDispatchStatus;
    }

    public void setOfficialDispatchStatus(OfficialDispatchStatus officialDispatchStatus) {
        this.officialDispatchStatus = officialDispatchStatus;
    }

    public OfficialDispatch officialDispatchStatus(OfficialDispatchStatus officialDispatchStatus) {
        this.setOfficialDispatchStatus(officialDispatchStatus);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public OfficialDispatch creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public OfficialDispatch modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public OutOrganization getOutOrganization() {
        return this.outOrganization;
    }

    public void setOutOrganization(OutOrganization outOrganization) {
        this.outOrganization = outOrganization;
    }

    public OfficialDispatch outOrganization(OutOrganization outOrganization) {
        this.setOutOrganization(outOrganization);
        return this;
    }

    public Set<UserInfo> getOffDispatchUserReads() {
        return this.offDispatchUserReads;
    }

    public void setOffDispatchUserReads(Set<UserInfo> userInfos) {
        this.offDispatchUserReads = userInfos;
    }

    public OfficialDispatch offDispatchUserReads(Set<UserInfo> userInfos) {
        this.setOffDispatchUserReads(userInfos);
        return this;
    }

    public OfficialDispatch addOffDispatchUserRead(UserInfo userInfo) {
        this.offDispatchUserReads.add(userInfo);
        userInfo.getOffDispatchUserReads().add(this);
        return this;
    }

    public OfficialDispatch removeOffDispatchUserRead(UserInfo userInfo) {
        this.offDispatchUserReads.remove(userInfo);
        userInfo.getOffDispatchUserReads().remove(this);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setOfficialDispatch(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setOfficialDispatch(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public OfficialDispatch attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public OfficialDispatch addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setOfficialDispatch(this);
        return this;
    }

    public OfficialDispatch removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setOfficialDispatch(null);
        return this;
    }

    public Set<OfficialDispatchHis> getOfficialDispatchHis() {
        return this.officialDispatchHis;
    }

    public void setOfficialDispatchHis(Set<OfficialDispatchHis> officialDispatchHis) {
        if (this.officialDispatchHis != null) {
            this.officialDispatchHis.forEach(i -> i.setOfficialDispatch(null));
        }
        if (officialDispatchHis != null) {
            officialDispatchHis.forEach(i -> i.setOfficialDispatch(this));
        }
        this.officialDispatchHis = officialDispatchHis;
    }

    public OfficialDispatch officialDispatchHis(Set<OfficialDispatchHis> officialDispatchHis) {
        this.setOfficialDispatchHis(officialDispatchHis);
        return this;
    }

    public OfficialDispatch addOfficialDispatchHis(OfficialDispatchHis officialDispatchHis) {
        this.officialDispatchHis.add(officialDispatchHis);
        officialDispatchHis.setOfficialDispatch(this);
        return this;
    }

    public OfficialDispatch removeOfficialDispatchHis(OfficialDispatchHis officialDispatchHis) {
        this.officialDispatchHis.remove(officialDispatchHis);
        officialDispatchHis.setOfficialDispatch(null);
        return this;
    }

    public Set<StepProcessDoc> getStepProcessDocs() {
        return this.stepProcessDocs;
    }

    public void setStepProcessDocs(Set<StepProcessDoc> stepProcessDocs) {
        if (this.stepProcessDocs != null) {
            this.stepProcessDocs.forEach(i -> i.setOfficialDispatch(null));
        }
        if (stepProcessDocs != null) {
            stepProcessDocs.forEach(i -> i.setOfficialDispatch(this));
        }
        this.stepProcessDocs = stepProcessDocs;
    }

    public OfficialDispatch stepProcessDocs(Set<StepProcessDoc> stepProcessDocs) {
        this.setStepProcessDocs(stepProcessDocs);
        return this;
    }

    public OfficialDispatch addStepProcessDoc(StepProcessDoc stepProcessDoc) {
        this.stepProcessDocs.add(stepProcessDoc);
        stepProcessDoc.setOfficialDispatch(this);
        return this;
    }

    public OfficialDispatch removeStepProcessDoc(StepProcessDoc stepProcessDoc) {
        this.stepProcessDocs.remove(stepProcessDoc);
        stepProcessDoc.setOfficialDispatch(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficialDispatch)) {
            return false;
        }
        return id != null && id.equals(((OfficialDispatch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficialDispatch{" +
            "id=" + getId() +
            ", officialDispatchName='" + getOfficialDispatchName() + "'" +
            ", officialDispatchCode='" + getOfficialDispatchCode() + "'" +
            ", officialDispatchNumber='" + getOfficialDispatchNumber() + "'" +
            ", officialDispatchTypeName='" + getOfficialDispatchTypeName() + "'" +
            ", officialDispatchTypeCode='" + getOfficialDispatchTypeCode() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", priorityName='" + getPriorityName() + "'" +
            ", priorityCode='" + getPriorityCode() + "'" +
            ", securityName='" + getSecurityName() + "'" +
            ", securityCode='" + getSecurityCode() + "'" +
            ", documentTypeName='" + getDocumentTypeName() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", releaseOrgName='" + getReleaseOrgName() + "'" +
            ", releaseOrgAvatar='" + getReleaseOrgAvatar() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", composeOrgName='" + getComposeOrgName() + "'" +
            ", composeOrgAvatar='" + getComposeOrgAvatar() + "'" +
            ", signName='" + getSignName() + "'" +
            ", signAvatar='" + getSignAvatar() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", outgoingDate='" + getOutgoingDate() + "'" +
            ", receiveOrgText='" + getReceiveOrgText() + "'" +
            ", placeSendName='" + getPlaceSendName() + "'" +
            ", placeSendCode='" + getPlaceSendCode() + "'" +
            ", dispatchOfficialDate='" + getDispatchOfficialDate() + "'" +
            ", commingDate='" + getCommingDate() + "'" +
            ", finalScanDocName='" + getFinalScanDocName() + "'" +
            ", intoOutgingBookDate='" + getIntoOutgingBookDate() + "'" +
            ", numberOfBook='" + getNumberOfBook() + "'" +
            ", dispatchBookName='" + getDispatchBookName() + "'" +
            ", dispatchBookCode='" + getDispatchBookCode() + "'" +
            ", dispatchBookType='" + getDispatchBookType() + "'" +
            ", outgoingStatusName='" + getOutgoingStatusName() + "'" +
            ", oldStatus=" + getOldStatus() +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
