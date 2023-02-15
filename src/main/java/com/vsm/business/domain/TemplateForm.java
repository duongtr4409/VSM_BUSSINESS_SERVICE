package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Type;

/**
 * A TemplateForm.
 */
@Entity
@Table(name = "template_form")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "templateform")
public class TemplateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "template_form_code")
    private String templateFormCode;

    @Column(name = "template_form_name")
    private String templateFormName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "path")
    private String path;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "ofice_365_path")
    private String ofice365Path;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "mapping_info")
    private String mappingInfo;

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

    @ManyToMany
    @JoinTable(
        name = "rel_template_form__organization",
        joinColumns = @JoinColumn(name = "template_form_id"),
        inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
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
    private Set<Organization> organizations = new HashSet<>();

    @OneToMany(mappedBy = "templateForm")
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

    @ManyToMany(mappedBy = "templateForms")
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
    private Set<Request> requests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TemplateForm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateFormCode() {
        return this.templateFormCode;
    }

    public TemplateForm templateFormCode(String templateFormCode) {
        this.setTemplateFormCode(templateFormCode);
        return this;
    }

    public void setTemplateFormCode(String templateFormCode) {
        this.templateFormCode = templateFormCode;
    }

    public String getTemplateFormName() {
        return this.templateFormName;
    }

    public TemplateForm templateFormName(String templateFormName) {
        this.setTemplateFormName(templateFormName);
        return this;
    }

    public void setTemplateFormName(String templateFormName) {
        this.templateFormName = templateFormName;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public TemplateForm fileExtension(String fileExtension) {
        this.setFileExtension(fileExtension);
        return this;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getPath() {
        return this.path;
    }

    public TemplateForm path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return this.fullPath;
    }

    public TemplateForm fullPath(String fullPath) {
        this.setFullPath(fullPath);
        return this;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getOfice365Path() {
        return this.ofice365Path;
    }

    public TemplateForm ofice365Path(String ofice365Path) {
        this.setOfice365Path(ofice365Path);
        return this;
    }

    public void setOfice365Path(String ofice365Path) {
        this.ofice365Path = ofice365Path;
    }

    public String getMappingInfo() {
        return this.mappingInfo;
    }

    public TemplateForm mappingInfo(String mappingInfo) {
        this.setMappingInfo(mappingInfo);
        return this;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
    }

    public String getDescription() {
        return this.description;
    }

    public TemplateForm description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public TemplateForm createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public TemplateForm createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public TemplateForm createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TemplateForm createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public TemplateForm modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public TemplateForm modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TemplateForm isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public TemplateForm isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getVersion() {
        return this.version;
    }

    public TemplateForm version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public TemplateForm tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public TemplateForm tennantName(String tennantName) {
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

    public TemplateForm tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public TemplateForm created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public TemplateForm modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public TemplateForm organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public TemplateForm addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.getTemplateForms().add(this);
        return this;
    }

    public TemplateForm removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.getTemplateForms().remove(this);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setTemplateForm(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setTemplateForm(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public TemplateForm attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public TemplateForm addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setTemplateForm(this);
        return this;
    }

    public TemplateForm removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setTemplateForm(null);
        return this;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.removeTemplateForm(this));
        }
        if (requests != null) {
            requests.forEach(i -> i.addTemplateForm(this));
        }
        this.requests = requests;
    }

    public TemplateForm requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public TemplateForm addRequest(Request request) {
        this.requests.add(request);
        request.getTemplateForms().add(this);
        return this;
    }

    public TemplateForm removeRequest(Request request) {
        this.requests.remove(request);
        request.getTemplateForms().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateForm)) {
            return false;
        }
        return id != null && id.equals(((TemplateForm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateForm{" +
            "id=" + getId() +
            ", templateFormCode='" + getTemplateFormCode() + "'" +
            ", templateFormName='" + getTemplateFormName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", path='" + getPath() + "'" +
            ", fullPath='" + getFullPath() + "'" +
            ", ofice365Path='" + getOfice365Path() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
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
