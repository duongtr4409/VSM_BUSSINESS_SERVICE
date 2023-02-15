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
 * A MailTemplate.
 */
@Entity
@Table(name = "mail_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mailtemplate")
public class MailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mail_template_name")
    private String mailTemplateName;

    @Column(name = "mail_template_code")
    private String mailTemplateCode;

    @Column(name = "receiver_default")
    private String receiverDefault;

    @Column(name = "ccer_default")
    private String ccerDefault;

    @Column(name = "bccer_default")
    private String bccerDefault;

    @Column(name = "subject")
    private String subject;

    @Column(name = "item_id_365")
    private String itemId365;

    @Column(name = "description")
    private String description;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content_file")
    private String contentFile;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "footer")
    private String footer;

    @Column(name = "path_file")
    private String pathFile;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "mapping_info")
    private String mappingInfo;

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

    @ManyToMany
    @JoinTable(
        name = "rel_mail_template__organization",
        joinColumns = @JoinColumn(name = "mail_template_id"),
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

    @ManyToMany
    @JoinTable(
        name = "rel_mail_template__process_info",
        joinColumns = @JoinColumn(name = "mail_template_id"),
        inverseJoinColumns = @JoinColumn(name = "process_info_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates" },
        allowSetters = true
    )
    private Set<ProcessInfo> processInfos = new HashSet<>();

    @OneToMany(mappedBy = "mailTemplate")
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

    @OneToMany(mappedBy = "mailTemplateCustomer")
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
    private Set<StepInProcess> stepInProMailTemplateCustomers = new HashSet<>();

    @OneToMany(mappedBy = "mailTemplate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "rank", "organization", "mailTemplate", "mailTemplateCustomer", "stepInProcesses" },
        allowSetters = true
    )
    private Set<Step> steps = new HashSet<>();

    @OneToMany(mappedBy = "mailTemplateCustomer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "rank", "organization", "mailTemplate", "mailTemplateCustomer", "stepInProcesses" },
        allowSetters = true
    )
    private Set<Step> stepMailTemplateCustomers = new HashSet<>();

    @OneToMany(mappedBy = "mailTemplate")
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

    @OneToMany(mappedBy = "mailTemplateCustomer")
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
    private Set<StepData> stepDataMailTemplateCustomers = new HashSet<>();

    @OneToMany(mappedBy = "mailTemplate")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MailTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailTemplateName() {
        return this.mailTemplateName;
    }

    public MailTemplate mailTemplateName(String mailTemplateName) {
        this.setMailTemplateName(mailTemplateName);
        return this;
    }

    public void setMailTemplateName(String mailTemplateName) {
        this.mailTemplateName = mailTemplateName;
    }

    public String getMailTemplateCode() {
        return this.mailTemplateCode;
    }

    public MailTemplate mailTemplateCode(String mailTemplateCode) {
        this.setMailTemplateCode(mailTemplateCode);
        return this;
    }

    public void setMailTemplateCode(String mailTemplateCode) {
        this.mailTemplateCode = mailTemplateCode;
    }

    public String getReceiverDefault() {
        return this.receiverDefault;
    }

    public MailTemplate receiverDefault(String receiverDefault) {
        this.setReceiverDefault(receiverDefault);
        return this;
    }

    public void setReceiverDefault(String receiverDefault) {
        this.receiverDefault = receiverDefault;
    }

    public String getCcerDefault() {
        return this.ccerDefault;
    }

    public MailTemplate ccerDefault(String ccerDefault) {
        this.setCcerDefault(ccerDefault);
        return this;
    }

    public void setCcerDefault(String ccerDefault) {
        this.ccerDefault = ccerDefault;
    }

    public String getBccerDefault() {
        return this.bccerDefault;
    }

    public MailTemplate bccerDefault(String bccerDefault) {
        this.setBccerDefault(bccerDefault);
        return this;
    }

    public void setBccerDefault(String bccerDefault) {
        this.bccerDefault = bccerDefault;
    }

    public String getSubject() {
        return this.subject;
    }

    public MailTemplate subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getItemId365() {
        return this.itemId365;
    }

    public MailTemplate itemId365(String itemId365) {
        this.setItemId365(itemId365);
        return this;
    }

    public void setItemId365(String itemId365) {
        this.itemId365 = itemId365;
    }

    public String getDescription() {
        return this.description;
    }

    public MailTemplate description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentFile() {
        return this.contentFile;
    }

    public MailTemplate contentFile(String contentFile) {
        this.setContentFile(contentFile);
        return this;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public String getContent() {
        return this.content;
    }

    public MailTemplate content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFooter() {
        return this.footer;
    }

    public MailTemplate footer(String footer) {
        this.setFooter(footer);
        return this;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getPathFile() {
        return this.pathFile;
    }

    public MailTemplate pathFile(String pathFile) {
        this.setPathFile(pathFile);
        return this;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getMappingInfo() {
        return this.mappingInfo;
    }

    public MailTemplate mappingInfo(String mappingInfo) {
        this.setMappingInfo(mappingInfo);
        return this;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public MailTemplate createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public MailTemplate createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public MailTemplate createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public MailTemplate createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public MailTemplate modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public MailTemplate modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public MailTemplate isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public MailTemplate isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public MailTemplate organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public MailTemplate addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.getMailTemplates().add(this);
        return this;
    }

    public MailTemplate removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.getMailTemplates().remove(this);
        return this;
    }

    public Set<ProcessInfo> getProcessInfos() {
        return this.processInfos;
    }

    public void setProcessInfos(Set<ProcessInfo> processInfos) {
        this.processInfos = processInfos;
    }

    public MailTemplate processInfos(Set<ProcessInfo> processInfos) {
        this.setProcessInfos(processInfos);
        return this;
    }

    public MailTemplate addProcessInfo(ProcessInfo processInfo) {
        this.processInfos.add(processInfo);
        processInfo.getMailTemplates().add(this);
        return this;
    }

    public MailTemplate removeProcessInfo(ProcessInfo processInfo) {
        this.processInfos.remove(processInfo);
        processInfo.getMailTemplates().remove(this);
        return this;
    }

    public Set<StepInProcess> getStepInProcesses() {
        return this.stepInProcesses;
    }

    public void setStepInProcesses(Set<StepInProcess> stepInProcesses) {
        if (this.stepInProcesses != null) {
            this.stepInProcesses.forEach(i -> i.setMailTemplate(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setMailTemplate(this));
        }
        this.stepInProcesses = stepInProcesses;
    }

    public MailTemplate stepInProcesses(Set<StepInProcess> stepInProcesses) {
        this.setStepInProcesses(stepInProcesses);
        return this;
    }

    public MailTemplate addStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.add(stepInProcess);
        stepInProcess.setMailTemplate(this);
        return this;
    }

    public MailTemplate removeStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.remove(stepInProcess);
        stepInProcess.setMailTemplate(null);
        return this;
    }

    public Set<StepInProcess> getStepInProMailTemplateCustomers() {
        return this.stepInProMailTemplateCustomers;
    }

    public void setStepInProMailTemplateCustomers(Set<StepInProcess> stepInProcesses) {
        if (this.stepInProMailTemplateCustomers != null) {
            this.stepInProMailTemplateCustomers.forEach(i -> i.setMailTemplateCustomer(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setMailTemplateCustomer(this));
        }
        this.stepInProMailTemplateCustomers = stepInProcesses;
    }

    public MailTemplate stepInProMailTemplateCustomers(Set<StepInProcess> stepInProcesses) {
        this.setStepInProMailTemplateCustomers(stepInProcesses);
        return this;
    }

    public MailTemplate addStepInProMailTemplateCustomer(StepInProcess stepInProcess) {
        this.stepInProMailTemplateCustomers.add(stepInProcess);
        stepInProcess.setMailTemplateCustomer(this);
        return this;
    }

    public MailTemplate removeStepInProMailTemplateCustomer(StepInProcess stepInProcess) {
        this.stepInProMailTemplateCustomers.remove(stepInProcess);
        stepInProcess.setMailTemplateCustomer(null);
        return this;
    }

    public Set<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<Step> steps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setMailTemplate(null));
        }
        if (steps != null) {
            steps.forEach(i -> i.setMailTemplate(this));
        }
        this.steps = steps;
    }

    public MailTemplate steps(Set<Step> steps) {
        this.setSteps(steps);
        return this;
    }

    public MailTemplate addStep(Step step) {
        this.steps.add(step);
        step.setMailTemplate(this);
        return this;
    }

    public MailTemplate removeStep(Step step) {
        this.steps.remove(step);
        step.setMailTemplate(null);
        return this;
    }

    public Set<Step> getStepMailTemplateCustomers() {
        return this.stepMailTemplateCustomers;
    }

    public void setStepMailTemplateCustomers(Set<Step> steps) {
        if (this.stepMailTemplateCustomers != null) {
            this.stepMailTemplateCustomers.forEach(i -> i.setMailTemplateCustomer(null));
        }
        if (steps != null) {
            steps.forEach(i -> i.setMailTemplateCustomer(this));
        }
        this.stepMailTemplateCustomers = steps;
    }

    public MailTemplate stepMailTemplateCustomers(Set<Step> steps) {
        this.setStepMailTemplateCustomers(steps);
        return this;
    }

    public MailTemplate addStepMailTemplateCustomer(Step step) {
        this.stepMailTemplateCustomers.add(step);
        step.setMailTemplateCustomer(this);
        return this;
    }

    public MailTemplate removeStepMailTemplateCustomer(Step step) {
        this.stepMailTemplateCustomers.remove(step);
        step.setMailTemplateCustomer(null);
        return this;
    }

    public Set<StepData> getStepData() {
        return this.stepData;
    }

    public void setStepData(Set<StepData> stepData) {
        if (this.stepData != null) {
            this.stepData.forEach(i -> i.setMailTemplate(null));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.setMailTemplate(this));
        }
        this.stepData = stepData;
    }

    public MailTemplate stepData(Set<StepData> stepData) {
        this.setStepData(stepData);
        return this;
    }

    public MailTemplate addStepData(StepData stepData) {
        this.stepData.add(stepData);
        stepData.setMailTemplate(this);
        return this;
    }

    public MailTemplate removeStepData(StepData stepData) {
        this.stepData.remove(stepData);
        stepData.setMailTemplate(null);
        return this;
    }

    public Set<StepData> getStepDataMailTemplateCustomers() {
        return this.stepDataMailTemplateCustomers;
    }

    public void setStepDataMailTemplateCustomers(Set<StepData> stepData) {
        if (this.stepDataMailTemplateCustomers != null) {
            this.stepDataMailTemplateCustomers.forEach(i -> i.setMailTemplateCustomer(null));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.setMailTemplateCustomer(this));
        }
        this.stepDataMailTemplateCustomers = stepData;
    }

    public MailTemplate stepDataMailTemplateCustomers(Set<StepData> stepData) {
        this.setStepDataMailTemplateCustomers(stepData);
        return this;
    }

    public MailTemplate addStepDataMailTemplateCustomer(StepData stepData) {
        this.stepDataMailTemplateCustomers.add(stepData);
        stepData.setMailTemplateCustomer(this);
        return this;
    }

    public MailTemplate removeStepDataMailTemplateCustomer(StepData stepData) {
        this.stepDataMailTemplateCustomers.remove(stepData);
        stepData.setMailTemplateCustomer(null);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setMailTemplate(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setMailTemplate(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public MailTemplate attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public MailTemplate addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setMailTemplate(this);
        return this;
    }

    public MailTemplate removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setMailTemplate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MailTemplate)) {
            return false;
        }
        return id != null && id.equals(((MailTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MailTemplate{" +
            "id=" + getId() +
            ", mailTemplateName='" + getMailTemplateName() + "'" +
            ", mailTemplateCode='" + getMailTemplateCode() + "'" +
            ", receiverDefault='" + getReceiverDefault() + "'" +
            ", ccerDefault='" + getCcerDefault() + "'" +
            ", bccerDefault='" + getBccerDefault() + "'" +
            ", subject='" + getSubject() + "'" +
            ", itemId365='" + getItemId365() + "'" +
            ", description='" + getDescription() + "'" +
            ", contentFile='" + getContentFile() + "'" +
            ", content='" + getContent() + "'" +
            ", footer='" + getFooter() + "'" +
            ", pathFile='" + getPathFile() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
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
