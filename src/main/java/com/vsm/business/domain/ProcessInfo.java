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
 * A ProcessInfo.
 */
@Entity
@Table(name = "process_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "processinfo")
public class ProcessInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "process_code")
    private String processCode;

    @Column(name = "process_name")
    private String processName;

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

    @Column(name = "number_request")
    private Long numberRequest;

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
        name = "rel_process_info__organization",
        joinColumns = @JoinColumn(name = "process_info_id"),
        inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @OneToMany(mappedBy = "processInfo")
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

    @ManyToMany(mappedBy = "processInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @ManyToMany(mappedBy = "processInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<MailTemplate> mailTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProcessInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessCode() {
        return this.processCode;
    }

    public ProcessInfo processCode(String processCode) {
        this.setProcessCode(processCode);
        return this;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessName() {
        return this.processName;
    }

    public ProcessInfo processName(String processName) {
        this.setProcessName(processName);
        return this;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getDescription() {
        return this.description;
    }

    public ProcessInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public ProcessInfo createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public ProcessInfo createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public ProcessInfo createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ProcessInfo createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public ProcessInfo modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public ProcessInfo modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ProcessInfo isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public ProcessInfo isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getNumberRequest() {
        return this.numberRequest;
    }

    public ProcessInfo numberRequest(Long numberRequest) {
        this.setNumberRequest(numberRequest);
        return this;
    }

    public void setNumberRequest(Long numberRequest) {
        this.numberRequest = numberRequest;
    }

    public Long getVersion() {
        return this.version;
    }

    public ProcessInfo version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public ProcessInfo tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public ProcessInfo tennantName(String tennantName) {
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

    public ProcessInfo tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public ProcessInfo created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public ProcessInfo modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public ProcessInfo organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public ProcessInfo addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.getProcessInfos().add(this);
        return this;
    }

    public ProcessInfo removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.getProcessInfos().remove(this);
        return this;
    }

    public Set<StepInProcess> getStepInProcesses() {
        return this.stepInProcesses;
    }

    public void setStepInProcesses(Set<StepInProcess> stepInProcesses) {
        if (this.stepInProcesses != null) {
            this.stepInProcesses.forEach(i -> i.setProcessInfo(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setProcessInfo(this));
        }
        this.stepInProcesses = stepInProcesses;
    }

    public ProcessInfo stepInProcesses(Set<StepInProcess> stepInProcesses) {
        this.setStepInProcesses(stepInProcesses);
        return this;
    }

    public ProcessInfo addStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.add(stepInProcess);
        stepInProcess.setProcessInfo(this);
        return this;
    }

    public ProcessInfo removeStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.remove(stepInProcess);
        stepInProcess.setProcessInfo(null);
        return this;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.removeProcessInfo(this));
        }
        if (requests != null) {
            requests.forEach(i -> i.addProcessInfo(this));
        }
        this.requests = requests;
    }

    public ProcessInfo requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public ProcessInfo addRequest(Request request) {
        this.requests.add(request);
        request.getProcessInfos().add(this);
        return this;
    }

    public ProcessInfo removeRequest(Request request) {
        this.requests.remove(request);
        request.getProcessInfos().remove(this);
        return this;
    }

    public Set<MailTemplate> getMailTemplates() {
        return this.mailTemplates;
    }

    public void setMailTemplates(Set<MailTemplate> mailTemplates) {
        if (this.mailTemplates != null) {
            this.mailTemplates.forEach(i -> i.removeProcessInfo(this));
        }
        if (mailTemplates != null) {
            mailTemplates.forEach(i -> i.addProcessInfo(this));
        }
        this.mailTemplates = mailTemplates;
    }

    public ProcessInfo mailTemplates(Set<MailTemplate> mailTemplates) {
        this.setMailTemplates(mailTemplates);
        return this;
    }

    public ProcessInfo addMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplates.add(mailTemplate);
        mailTemplate.getProcessInfos().add(this);
        return this;
    }

    public ProcessInfo removeMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplates.remove(mailTemplate);
        mailTemplate.getProcessInfos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInfo)) {
            return false;
        }
        return id != null && id.equals(((ProcessInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInfo{" +
            "id=" + getId() +
            ", processCode='" + getProcessCode() + "'" +
            ", processName='" + getProcessName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", numberRequest=" + getNumberRequest() +
            ", version=" + getVersion() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
