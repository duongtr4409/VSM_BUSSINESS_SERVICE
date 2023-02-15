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
 * A AttachmentFile.
 */
@Entity
@Table(name = "attachment_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "attachmentfile")
public class AttachmentFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "path_in_hand_book")
    private String pathInHandBook;

    @Column(name = "id_in_file_service")
    private String idInFileService;

    @Column(name = "path")
    private String path;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "ofice_365_path")
    private String ofice365Path;

    @Column(name = "item_id_365")
    private String itemId365;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "url_view")
    private String urlView;

    @Column(name = "description")
    private String description;

    @Column(name = "is_folder")
    private Boolean isFolder;

    @Column(name = "is_main_doc")
    private Boolean isMainDoc;

    @Column(name = "file_type_name")
    private String fileTypeName;

    @Column(name = "file_type_code")
    private String fileTypeCode;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "url_upload")
    private String urlUpload;

    @Column(name = "next_expected_ranges")
    private String nextExpectedRanges;

    @Column(name = "sign_of_file")
    private String signOfFile;

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
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "attachmentFiles" }, allowSetters = true)
    private FileType fileType;

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
    private RequestData requestData;

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
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "organizations", "attachmentFiles", "requests" }, allowSetters = true)
    private TemplateForm templateForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "requestData", "stepData", "processer", "attachmentFiles" }, allowSetters = true)
    private ReqdataProcessHis reqdataProcessHis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "dispatchBook",
            "releaseOrg",
            "composeOrg",
            "ownerOrg",
            "signer",
            "officialDispatchType",
            "documentType",
            "priorityLevel",
            "securityLevel",
            "officialDispatchStatus",
            "creater",
            "modifier",
            "outOrganization",
            "offDispatchUserReads",
            "attachmentFiles",
            "officialDispatchHis",
            "stepProcessDocs",
        },
        allowSetters = true
    )
    private OfficialDispatch officialDispatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "officialDispatch", "attachmentFiles" }, allowSetters = true)
    private StepProcessDoc stepProcessDoc;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "requestData", "stamp", "orgReturn", "creater", "modifier", "orgStorages", "attachmentFiles" },
        allowSetters = true
    )
    private ManageStampInfo manageStampInfo;

    @OneToMany(mappedBy = "attachmentFile", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "attachmentFile", "created", "modified" }, allowSetters = true)
    private Set<AttachmentPermisition> attachmentPermisitions = new HashSet<>();

    @OneToMany(mappedBy = "attachmentFile", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "attachmentFile" }, allowSetters = true)
    private Set<ChangeFileHistory> changeFileHistories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AttachmentFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public AttachmentFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public AttachmentFile fileExtension(String fileExtension) {
        this.setFileExtension(fileExtension);
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getContentType() {
        return this.contentType;
    }

    public AttachmentFile contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPathInHandBook() {
        return this.pathInHandBook;
    }

    public AttachmentFile pathInHandBook(String pathInHandBook) {
        this.setPathInHandBook(pathInHandBook);
        return this;
    }

    public void setPathInHandBook(String pathInHandBook) {
        this.pathInHandBook = pathInHandBook;
    }

    public String getIdInFileService() {
        return this.idInFileService;
    }

    public AttachmentFile idInFileService(String idInFileService) {
        this.setIdInFileService(idInFileService);
        return this;
    }

    public void setIdInFileService(String idInFileService) {
        this.idInFileService = idInFileService;
    }

    public String getPath() {
        return this.path;
    }

    public AttachmentFile path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return this.fullPath;
    }

    public AttachmentFile fullPath(String fullPath) {
        this.setFullPath(fullPath);
        return this;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getOfice365Path() {
        return this.ofice365Path;
    }

    public AttachmentFile ofice365Path(String ofice365Path) {
        this.setOfice365Path(ofice365Path);
        return this;
    }

    public void setOfice365Path(String ofice365Path) {
        this.ofice365Path = ofice365Path;
    }

    public String getItemId365() {
        return this.itemId365;
    }

    public AttachmentFile itemId365(String itemId365) {
        this.setItemId365(itemId365);
        return this;
    }

    public void setItemId365(String itemId365) {
        this.itemId365 = itemId365;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public AttachmentFile fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrlView() {
        return this.urlView;
    }

    public AttachmentFile urlView(String urlView) {
        this.setUrlView(urlView);
        return this;
    }

    public void setUrlView(String urlView) {
        this.urlView = urlView;
    }

    public String getDescription() {
        return this.description;
    }

    public AttachmentFile description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFolder() {
        return this.isFolder;
    }

    public AttachmentFile isFolder(Boolean isFolder) {
        this.setIsFolder(isFolder);
        return this;
    }

    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public Boolean getIsMainDoc() {
        return this.isMainDoc;
    }

    public AttachmentFile isMainDoc(Boolean isMainDoc) {
        this.setIsMainDoc(isMainDoc);
        return this;
    }

    public void setIsMainDoc(Boolean isMainDoc) {
        this.isMainDoc = isMainDoc;
    }

    public String getFileTypeName() {
        return this.fileTypeName;
    }

    public AttachmentFile fileTypeName(String fileTypeName) {
        this.setFileTypeName(fileTypeName);
        return this;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getFileTypeCode() {
        return this.fileTypeCode;
    }

    public AttachmentFile fileTypeCode(String fileTypeCode) {
        this.setFileTypeCode(fileTypeCode);
        return this;
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public AttachmentFile parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrlUpload() {
        return this.urlUpload;
    }

    public AttachmentFile urlUpload(String urlUpload) {
        this.setUrlUpload(urlUpload);
        return this;
    }

    public void setUrlUpload(String urlUpload) {
        this.urlUpload = urlUpload;
    }

    public String getNextExpectedRanges() {
        return this.nextExpectedRanges;
    }

    public AttachmentFile nextExpectedRanges(String nextExpectedRanges) {
        this.setNextExpectedRanges(nextExpectedRanges);
        return this;
    }

    public void setNextExpectedRanges(String nextExpectedRanges) {
        this.nextExpectedRanges = nextExpectedRanges;
    }

    public String getSignOfFile() {
        return this.signOfFile;
    }

    public AttachmentFile signOfFile(String signOfFile) {
        this.setSignOfFile(signOfFile);
        return this;
    }

    public void setSignOfFile(String signOfFile) {
        this.signOfFile = signOfFile;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public AttachmentFile createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public AttachmentFile createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public AttachmentFile createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public AttachmentFile createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public AttachmentFile modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public AttachmentFile modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public AttachmentFile isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public AttachmentFile isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public AttachmentFile tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public AttachmentFile tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public AttachmentFile fileType(FileType fileType) {
        this.setFileType(fileType);
        return this;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public AttachmentFile requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public AttachmentFile tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public AttachmentFile created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public AttachmentFile modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public TemplateForm getTemplateForm() {
        return this.templateForm;
    }

    public void setTemplateForm(TemplateForm templateForm) {
        this.templateForm = templateForm;
    }

    public AttachmentFile templateForm(TemplateForm templateForm) {
        this.setTemplateForm(templateForm);
        return this;
    }

    public ReqdataProcessHis getReqdataProcessHis() {
        return this.reqdataProcessHis;
    }

    public void setReqdataProcessHis(ReqdataProcessHis reqdataProcessHis) {
        this.reqdataProcessHis = reqdataProcessHis;
    }

    public AttachmentFile reqdataProcessHis(ReqdataProcessHis reqdataProcessHis) {
        this.setReqdataProcessHis(reqdataProcessHis);
        return this;
    }

    public OfficialDispatch getOfficialDispatch() {
        return this.officialDispatch;
    }

    public void setOfficialDispatch(OfficialDispatch officialDispatch) {
        this.officialDispatch = officialDispatch;
    }

    public AttachmentFile officialDispatch(OfficialDispatch officialDispatch) {
        this.setOfficialDispatch(officialDispatch);
        return this;
    }

    public StepProcessDoc getStepProcessDoc() {
        return this.stepProcessDoc;
    }

    public void setStepProcessDoc(StepProcessDoc stepProcessDoc) {
        this.stepProcessDoc = stepProcessDoc;
    }

    public AttachmentFile stepProcessDoc(StepProcessDoc stepProcessDoc) {
        this.setStepProcessDoc(stepProcessDoc);
        return this;
    }

    public MailTemplate getMailTemplate() {
        return this.mailTemplate;
    }

    public void setMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public AttachmentFile mailTemplate(MailTemplate mailTemplate) {
        this.setMailTemplate(mailTemplate);
        return this;
    }

    public ManageStampInfo getManageStampInfo() {
        return this.manageStampInfo;
    }

    public void setManageStampInfo(ManageStampInfo manageStampInfo) {
        this.manageStampInfo = manageStampInfo;
    }

    public AttachmentFile manageStampInfo(ManageStampInfo manageStampInfo) {
        this.setManageStampInfo(manageStampInfo);
        return this;
    }

    public Set<AttachmentPermisition> getAttachmentPermisitions() {
        return this.attachmentPermisitions;
    }

    public void setAttachmentPermisitions(Set<AttachmentPermisition> attachmentPermisitions) {
        if (this.attachmentPermisitions != null) {
            this.attachmentPermisitions.forEach(i -> i.setAttachmentFile(null));
        }
        if (attachmentPermisitions != null) {
            attachmentPermisitions.forEach(i -> i.setAttachmentFile(this));
        }
        this.attachmentPermisitions = attachmentPermisitions;
    }

    public AttachmentFile attachmentPermisitions(Set<AttachmentPermisition> attachmentPermisitions) {
        this.setAttachmentPermisitions(attachmentPermisitions);
        return this;
    }

    public AttachmentFile addAttachmentPermisition(AttachmentPermisition attachmentPermisition) {
        this.attachmentPermisitions.add(attachmentPermisition);
        attachmentPermisition.setAttachmentFile(this);
        return this;
    }

    public AttachmentFile removeAttachmentPermisition(AttachmentPermisition attachmentPermisition) {
        this.attachmentPermisitions.remove(attachmentPermisition);
        attachmentPermisition.setAttachmentFile(null);
        return this;
    }

    public Set<ChangeFileHistory> getChangeFileHistories() {
        return this.changeFileHistories;
    }

    public void setChangeFileHistories(Set<ChangeFileHistory> changeFileHistories) {
        if (this.changeFileHistories != null) {
            this.changeFileHistories.forEach(i -> i.setAttachmentFile(null));
        }
        if (changeFileHistories != null) {
            changeFileHistories.forEach(i -> i.setAttachmentFile(this));
        }
        this.changeFileHistories = changeFileHistories;
    }

    public AttachmentFile changeFileHistories(Set<ChangeFileHistory> changeFileHistories) {
        this.setChangeFileHistories(changeFileHistories);
        return this;
    }

    public AttachmentFile addChangeFileHistory(ChangeFileHistory changeFileHistory) {
        this.changeFileHistories.add(changeFileHistory);
        changeFileHistory.setAttachmentFile(this);
        return this;
    }

    public AttachmentFile removeChangeFileHistory(ChangeFileHistory changeFileHistory) {
        this.changeFileHistories.remove(changeFileHistory);
        changeFileHistory.setAttachmentFile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentFile)) {
            return false;
        }
        return id != null && id.equals(((AttachmentFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentFile{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", pathInHandBook='" + getPathInHandBook() + "'" +
            ", idInFileService='" + getIdInFileService() + "'" +
            ", path='" + getPath() + "'" +
            ", fullPath='" + getFullPath() + "'" +
            ", ofice365Path='" + getOfice365Path() + "'" +
            ", itemId365='" + getItemId365() + "'" +
            ", fileSize=" + getFileSize() +
            ", urlView='" + getUrlView() + "'" +
            ", description='" + getDescription() + "'" +
            ", isFolder='" + getIsFolder() + "'" +
            ", isMainDoc='" + getIsMainDoc() + "'" +
            ", fileTypeName='" + getFileTypeName() + "'" +
            ", fileTypeCode='" + getFileTypeCode() + "'" +
            ", parentId=" + getParentId() +
            ", urlUpload='" + getUrlUpload() + "'" +
            ", nextExpectedRanges='" + getNextExpectedRanges() + "'" +
            ", signOfFile='" + getSignOfFile() + "'" +
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
