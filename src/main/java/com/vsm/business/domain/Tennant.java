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
 * A Tennant.
 */
@Entity
@Table(name = "tennant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tennant")
public class Tennant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @Column(name = "description")
    private String description;

    @Column(name = "database_url")
    private String databaseUrl;

    @Column(name = "database_schema_name")
    private String databaseSchemaName;

    @Column(name = "database_usermame")
    private String databaseUsermame;

    @Column(name = "database_password")
    private String databasePassword;

    @Column(name = "database_driver_class")
    private String databaseDriverClass;

    @Column(name = "file_path_folder")
    private String filePathFolder;

    @Column(name = "created_id")
    private Long createdId;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "created_org_name")
    private String createdOrgName;

    @Column(name = "created_rank_name")
    private String createdRankName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_id")
    private Long modifiedId;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "fieldData", "fieldInForms", "forms" }, allowSetters = true)
    private Set<Field> fields = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "fields", "requests", "formData", "fieldInForms" },
        allowSetters = true
    )
    private Set<Form> forms = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "field", "form", "fieldData" }, allowSetters = true)
    private Set<FieldInForm> fieldInForms = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Set<Rank> ranks = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "features", "roleRequests", "roleOrganizations", "userInfos", "userGroups" },
        allowSetters = true
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
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

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "stepInProcess", "tennant", "created", "modified" }, allowSetters = true)
    private Set<UserInStep> userInSteps = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
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

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "rank", "organization", "mailTemplate", "mailTemplateCustomer", "stepInProcesses" },
        allowSetters = true
    )
    private Set<Step> steps = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates" },
        allowSetters = true
    )
    private Set<ProcessInfo> processInfos = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "organizations", "attachmentFiles", "requests" }, allowSetters = true)
    private Set<TemplateForm> templateForms = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
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

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestGroup", "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private Set<RequestType> requestTypes = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private Set<RequestGroup> requestGroups = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified", "stepData" }, allowSetters = true)
    private Set<ProcessData> processData = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
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

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<RequestData> requestData = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "statusRequests", "subStatusRequests" }, allowSetters = true)
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "form", "tennant", "created", "modified", "fieldData" }, allowSetters = true)
    private Set<FormData> formData = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "field", "formData", "requestData", "tennant", "created", "modified", "fieldInForm" },
        allowSetters = true
    )
    private Set<FieldData> fieldData = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
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

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "attachmentFiles" }, allowSetters = true)
    private Set<FileType> fileTypes = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified" }, allowSetters = true)
    private Set<ReqdataChangeHis> reqdataChangeHis = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoryGroup", "tennant", "created", "modified" }, allowSetters = true)
    private Set<CategoryData> categoryData = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "parent", "categoryData", "children" }, allowSetters = true)
    private Set<CategoryGroup> categoryGroups = new HashSet<>();

    @OneToMany(mappedBy = "tennant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "excutor", "stepData", "tennant" }, allowSetters = true)
    private Set<ResultOfStep> resultOfSteps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tennant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public Tennant tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public Tennant tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public String getDescription() {
        return this.description;
    }

    public Tennant description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

    public Tennant databaseUrl(String databaseUrl) {
        this.setDatabaseUrl(databaseUrl);
        return this;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseSchemaName() {
        return this.databaseSchemaName;
    }

    public Tennant databaseSchemaName(String databaseSchemaName) {
        this.setDatabaseSchemaName(databaseSchemaName);
        return this;
    }

    public void setDatabaseSchemaName(String databaseSchemaName) {
        this.databaseSchemaName = databaseSchemaName;
    }

    public String getDatabaseUsermame() {
        return this.databaseUsermame;
    }

    public Tennant databaseUsermame(String databaseUsermame) {
        this.setDatabaseUsermame(databaseUsermame);
        return this;
    }

    public void setDatabaseUsermame(String databaseUsermame) {
        this.databaseUsermame = databaseUsermame;
    }

    public String getDatabasePassword() {
        return this.databasePassword;
    }

    public Tennant databasePassword(String databasePassword) {
        this.setDatabasePassword(databasePassword);
        return this;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseDriverClass() {
        return this.databaseDriverClass;
    }

    public Tennant databaseDriverClass(String databaseDriverClass) {
        this.setDatabaseDriverClass(databaseDriverClass);
        return this;
    }

    public void setDatabaseDriverClass(String databaseDriverClass) {
        this.databaseDriverClass = databaseDriverClass;
    }

    public String getFilePathFolder() {
        return this.filePathFolder;
    }

    public Tennant filePathFolder(String filePathFolder) {
        this.setFilePathFolder(filePathFolder);
        return this;
    }

    public void setFilePathFolder(String filePathFolder) {
        this.filePathFolder = filePathFolder;
    }

    public Long getCreatedId() {
        return this.createdId;
    }

    public Tennant createdId(Long createdId) {
        this.setCreatedId(createdId);
        return this;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Tennant createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Tennant createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Tennant createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Tennant createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedId() {
        return this.modifiedId;
    }

    public Tennant modifiedId(Long modifiedId) {
        this.setModifiedId(modifiedId);
        return this;
    }

    public void setModifiedId(Long modifiedId) {
        this.modifiedId = modifiedId;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Tennant modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Tennant modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Tennant isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Tennant isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Set<Field> getFields() {
        return this.fields;
    }

    public void setFields(Set<Field> fields) {
        if (this.fields != null) {
            this.fields.forEach(i -> i.setTennant(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setTennant(this));
        }
        this.fields = fields;
    }

    public Tennant fields(Set<Field> fields) {
        this.setFields(fields);
        return this;
    }

    public Tennant addField(Field field) {
        this.fields.add(field);
        field.setTennant(this);
        return this;
    }

    public Tennant removeField(Field field) {
        this.fields.remove(field);
        field.setTennant(null);
        return this;
    }

    public Set<Form> getForms() {
        return this.forms;
    }

    public void setForms(Set<Form> forms) {
        if (this.forms != null) {
            this.forms.forEach(i -> i.setTennant(null));
        }
        if (forms != null) {
            forms.forEach(i -> i.setTennant(this));
        }
        this.forms = forms;
    }

    public Tennant forms(Set<Form> forms) {
        this.setForms(forms);
        return this;
    }

    public Tennant addForm(Form form) {
        this.forms.add(form);
        form.setTennant(this);
        return this;
    }

    public Tennant removeForm(Form form) {
        this.forms.remove(form);
        form.setTennant(null);
        return this;
    }

    public Set<FieldInForm> getFieldInForms() {
        return this.fieldInForms;
    }

    public void setFieldInForms(Set<FieldInForm> fieldInForms) {
        if (this.fieldInForms != null) {
            this.fieldInForms.forEach(i -> i.setTennant(null));
        }
        if (fieldInForms != null) {
            fieldInForms.forEach(i -> i.setTennant(this));
        }
        this.fieldInForms = fieldInForms;
    }

    public Tennant fieldInForms(Set<FieldInForm> fieldInForms) {
        this.setFieldInForms(fieldInForms);
        return this;
    }

    public Tennant addFieldInForm(FieldInForm fieldInForm) {
        this.fieldInForms.add(fieldInForm);
        fieldInForm.setTennant(this);
        return this;
    }

    public Tennant removeFieldInForm(FieldInForm fieldInForm) {
        this.fieldInForms.remove(fieldInForm);
        fieldInForm.setTennant(null);
        return this;
    }

    public Set<Rank> getRanks() {
        return this.ranks;
    }

    public void setRanks(Set<Rank> ranks) {
        if (this.ranks != null) {
            this.ranks.forEach(i -> i.setTennant(null));
        }
        if (ranks != null) {
            ranks.forEach(i -> i.setTennant(this));
        }
        this.ranks = ranks;
    }

    public Tennant ranks(Set<Rank> ranks) {
        this.setRanks(ranks);
        return this;
    }

    public Tennant addRank(Rank rank) {
        this.ranks.add(rank);
        rank.setTennant(this);
        return this;
    }

    public Tennant removeRank(Rank rank) {
        this.ranks.remove(rank);
        rank.setTennant(null);
        return this;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.setTennant(null));
        }
        if (roles != null) {
            roles.forEach(i -> i.setTennant(this));
        }
        this.roles = roles;
    }

    public Tennant roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Tennant addRole(Role role) {
        this.roles.add(role);
        role.setTennant(this);
        return this;
    }

    public Tennant removeRole(Role role) {
        this.roles.remove(role);
        role.setTennant(null);
        return this;
    }

    public Set<UserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        if (this.userInfos != null) {
            this.userInfos.forEach(i -> i.setTennant(null));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.setTennant(this));
        }
        this.userInfos = userInfos;
    }

    public Tennant userInfos(Set<UserInfo> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public Tennant addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.setTennant(this);
        return this;
    }

    public Tennant removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.setTennant(null);
        return this;
    }

    public Set<UserInStep> getUserInSteps() {
        return this.userInSteps;
    }

    public void setUserInSteps(Set<UserInStep> userInSteps) {
        if (this.userInSteps != null) {
            this.userInSteps.forEach(i -> i.setTennant(null));
        }
        if (userInSteps != null) {
            userInSteps.forEach(i -> i.setTennant(this));
        }
        this.userInSteps = userInSteps;
    }

    public Tennant userInSteps(Set<UserInStep> userInSteps) {
        this.setUserInSteps(userInSteps);
        return this;
    }

    public Tennant addUserInStep(UserInStep userInStep) {
        this.userInSteps.add(userInStep);
        userInStep.setTennant(this);
        return this;
    }

    public Tennant removeUserInStep(UserInStep userInStep) {
        this.userInSteps.remove(userInStep);
        userInStep.setTennant(null);
        return this;
    }

    public Set<StepInProcess> getStepInProcesses() {
        return this.stepInProcesses;
    }

    public void setStepInProcesses(Set<StepInProcess> stepInProcesses) {
        if (this.stepInProcesses != null) {
            this.stepInProcesses.forEach(i -> i.setTennant(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setTennant(this));
        }
        this.stepInProcesses = stepInProcesses;
    }

    public Tennant stepInProcesses(Set<StepInProcess> stepInProcesses) {
        this.setStepInProcesses(stepInProcesses);
        return this;
    }

    public Tennant addStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.add(stepInProcess);
        stepInProcess.setTennant(this);
        return this;
    }

    public Tennant removeStepInProcess(StepInProcess stepInProcess) {
        this.stepInProcesses.remove(stepInProcess);
        stepInProcess.setTennant(null);
        return this;
    }

    public Set<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(Set<Step> steps) {
        if (this.steps != null) {
            this.steps.forEach(i -> i.setTennant(null));
        }
        if (steps != null) {
            steps.forEach(i -> i.setTennant(this));
        }
        this.steps = steps;
    }

    public Tennant steps(Set<Step> steps) {
        this.setSteps(steps);
        return this;
    }

    public Tennant addStep(Step step) {
        this.steps.add(step);
        step.setTennant(this);
        return this;
    }

    public Tennant removeStep(Step step) {
        this.steps.remove(step);
        step.setTennant(null);
        return this;
    }

    public Set<ProcessInfo> getProcessInfos() {
        return this.processInfos;
    }

    public void setProcessInfos(Set<ProcessInfo> processInfos) {
        if (this.processInfos != null) {
            this.processInfos.forEach(i -> i.setTennant(null));
        }
        if (processInfos != null) {
            processInfos.forEach(i -> i.setTennant(this));
        }
        this.processInfos = processInfos;
    }

    public Tennant processInfos(Set<ProcessInfo> processInfos) {
        this.setProcessInfos(processInfos);
        return this;
    }

    public Tennant addProcessInfo(ProcessInfo processInfo) {
        this.processInfos.add(processInfo);
        processInfo.setTennant(this);
        return this;
    }

    public Tennant removeProcessInfo(ProcessInfo processInfo) {
        this.processInfos.remove(processInfo);
        processInfo.setTennant(null);
        return this;
    }

    public Set<TemplateForm> getTemplateForms() {
        return this.templateForms;
    }

    public void setTemplateForms(Set<TemplateForm> templateForms) {
        if (this.templateForms != null) {
            this.templateForms.forEach(i -> i.setTennant(null));
        }
        if (templateForms != null) {
            templateForms.forEach(i -> i.setTennant(this));
        }
        this.templateForms = templateForms;
    }

    public Tennant templateForms(Set<TemplateForm> templateForms) {
        this.setTemplateForms(templateForms);
        return this;
    }

    public Tennant addTemplateForm(TemplateForm templateForm) {
        this.templateForms.add(templateForm);
        templateForm.setTennant(this);
        return this;
    }

    public Tennant removeTemplateForm(TemplateForm templateForm) {
        this.templateForms.remove(templateForm);
        templateForm.setTennant(null);
        return this;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setTennant(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setTennant(this));
        }
        this.requests = requests;
    }

    public Tennant requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public Tennant addRequest(Request request) {
        this.requests.add(request);
        request.setTennant(this);
        return this;
    }

    public Tennant removeRequest(Request request) {
        this.requests.remove(request);
        request.setTennant(null);
        return this;
    }

    public Set<RequestType> getRequestTypes() {
        return this.requestTypes;
    }

    public void setRequestTypes(Set<RequestType> requestTypes) {
        if (this.requestTypes != null) {
            this.requestTypes.forEach(i -> i.setTennant(null));
        }
        if (requestTypes != null) {
            requestTypes.forEach(i -> i.setTennant(this));
        }
        this.requestTypes = requestTypes;
    }

    public Tennant requestTypes(Set<RequestType> requestTypes) {
        this.setRequestTypes(requestTypes);
        return this;
    }

    public Tennant addRequestType(RequestType requestType) {
        this.requestTypes.add(requestType);
        requestType.setTennant(this);
        return this;
    }

    public Tennant removeRequestType(RequestType requestType) {
        this.requestTypes.remove(requestType);
        requestType.setTennant(null);
        return this;
    }

    public Set<RequestGroup> getRequestGroups() {
        return this.requestGroups;
    }

    public void setRequestGroups(Set<RequestGroup> requestGroups) {
        if (this.requestGroups != null) {
            this.requestGroups.forEach(i -> i.setTennant(null));
        }
        if (requestGroups != null) {
            requestGroups.forEach(i -> i.setTennant(this));
        }
        this.requestGroups = requestGroups;
    }

    public Tennant requestGroups(Set<RequestGroup> requestGroups) {
        this.setRequestGroups(requestGroups);
        return this;
    }

    public Tennant addRequestGroup(RequestGroup requestGroup) {
        this.requestGroups.add(requestGroup);
        requestGroup.setTennant(this);
        return this;
    }

    public Tennant removeRequestGroup(RequestGroup requestGroup) {
        this.requestGroups.remove(requestGroup);
        requestGroup.setTennant(null);
        return this;
    }

    public Set<ProcessData> getProcessData() {
        return this.processData;
    }

    public void setProcessData(Set<ProcessData> processData) {
        if (this.processData != null) {
            this.processData.forEach(i -> i.setTennant(null));
        }
        if (processData != null) {
            processData.forEach(i -> i.setTennant(this));
        }
        this.processData = processData;
    }

    public Tennant processData(Set<ProcessData> processData) {
        this.setProcessData(processData);
        return this;
    }

    public Tennant addProcessData(ProcessData processData) {
        this.processData.add(processData);
        processData.setTennant(this);
        return this;
    }

    public Tennant removeProcessData(ProcessData processData) {
        this.processData.remove(processData);
        processData.setTennant(null);
        return this;
    }

    public Set<StepData> getStepData() {
        return this.stepData;
    }

    public void setStepData(Set<StepData> stepData) {
        if (this.stepData != null) {
            this.stepData.forEach(i -> i.setTennant(null));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.setTennant(this));
        }
        this.stepData = stepData;
    }

    public Tennant stepData(Set<StepData> stepData) {
        this.setStepData(stepData);
        return this;
    }

    public Tennant addStepData(StepData stepData) {
        this.stepData.add(stepData);
        stepData.setTennant(this);
        return this;
    }

    public Tennant removeStepData(StepData stepData) {
        this.stepData.remove(stepData);
        stepData.setTennant(null);
        return this;
    }

    public Set<RequestData> getRequestData() {
        return this.requestData;
    }

    public void setRequestData(Set<RequestData> requestData) {
        if (this.requestData != null) {
            this.requestData.forEach(i -> i.setTennant(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setTennant(this));
        }
        this.requestData = requestData;
    }

    public Tennant requestData(Set<RequestData> requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Tennant addRequestData(RequestData requestData) {
        this.requestData.add(requestData);
        requestData.setTennant(this);
        return this;
    }

    public Tennant removeRequestData(RequestData requestData) {
        this.requestData.remove(requestData);
        requestData.setTennant(null);
        return this;
    }

    public Set<Status> getStatuses() {
        return this.statuses;
    }

    public void setStatuses(Set<Status> statuses) {
        if (this.statuses != null) {
            this.statuses.forEach(i -> i.setTennant(null));
        }
        if (statuses != null) {
            statuses.forEach(i -> i.setTennant(this));
        }
        this.statuses = statuses;
    }

    public Tennant statuses(Set<Status> statuses) {
        this.setStatuses(statuses);
        return this;
    }

    public Tennant addStatus(Status status) {
        this.statuses.add(status);
        status.setTennant(this);
        return this;
    }

    public Tennant removeStatus(Status status) {
        this.statuses.remove(status);
        status.setTennant(null);
        return this;
    }

    public Set<FormData> getFormData() {
        return this.formData;
    }

    public void setFormData(Set<FormData> formData) {
        if (this.formData != null) {
            this.formData.forEach(i -> i.setTennant(null));
        }
        if (formData != null) {
            formData.forEach(i -> i.setTennant(this));
        }
        this.formData = formData;
    }

    public Tennant formData(Set<FormData> formData) {
        this.setFormData(formData);
        return this;
    }

    public Tennant addFormData(FormData formData) {
        this.formData.add(formData);
        formData.setTennant(this);
        return this;
    }

    public Tennant removeFormData(FormData formData) {
        this.formData.remove(formData);
        formData.setTennant(null);
        return this;
    }

    public Set<FieldData> getFieldData() {
        return this.fieldData;
    }

    public void setFieldData(Set<FieldData> fieldData) {
        if (this.fieldData != null) {
            this.fieldData.forEach(i -> i.setTennant(null));
        }
        if (fieldData != null) {
            fieldData.forEach(i -> i.setTennant(this));
        }
        this.fieldData = fieldData;
    }

    public Tennant fieldData(Set<FieldData> fieldData) {
        this.setFieldData(fieldData);
        return this;
    }

    public Tennant addFieldData(FieldData fieldData) {
        this.fieldData.add(fieldData);
        fieldData.setTennant(this);
        return this;
    }

    public Tennant removeFieldData(FieldData fieldData) {
        this.fieldData.remove(fieldData);
        fieldData.setTennant(null);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setTennant(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setTennant(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public Tennant attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public Tennant addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setTennant(this);
        return this;
    }

    public Tennant removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setTennant(null);
        return this;
    }

    public Set<FileType> getFileTypes() {
        return this.fileTypes;
    }

    public void setFileTypes(Set<FileType> fileTypes) {
        if (this.fileTypes != null) {
            this.fileTypes.forEach(i -> i.setTennant(null));
        }
        if (fileTypes != null) {
            fileTypes.forEach(i -> i.setTennant(this));
        }
        this.fileTypes = fileTypes;
    }

    public Tennant fileTypes(Set<FileType> fileTypes) {
        this.setFileTypes(fileTypes);
        return this;
    }

    public Tennant addFileType(FileType fileType) {
        this.fileTypes.add(fileType);
        fileType.setTennant(this);
        return this;
    }

    public Tennant removeFileType(FileType fileType) {
        this.fileTypes.remove(fileType);
        fileType.setTennant(null);
        return this;
    }

    public Set<ReqdataChangeHis> getReqdataChangeHis() {
        return this.reqdataChangeHis;
    }

    public void setReqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        if (this.reqdataChangeHis != null) {
            this.reqdataChangeHis.forEach(i -> i.setTennant(null));
        }
        if (reqdataChangeHis != null) {
            reqdataChangeHis.forEach(i -> i.setTennant(this));
        }
        this.reqdataChangeHis = reqdataChangeHis;
    }

    public Tennant reqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        this.setReqdataChangeHis(reqdataChangeHis);
        return this;
    }

    public Tennant addReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.reqdataChangeHis.add(reqdataChangeHis);
        reqdataChangeHis.setTennant(this);
        return this;
    }

    public Tennant removeReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.reqdataChangeHis.remove(reqdataChangeHis);
        reqdataChangeHis.setTennant(null);
        return this;
    }

    public Set<CategoryData> getCategoryData() {
        return this.categoryData;
    }

    public void setCategoryData(Set<CategoryData> categoryData) {
        if (this.categoryData != null) {
            this.categoryData.forEach(i -> i.setTennant(null));
        }
        if (categoryData != null) {
            categoryData.forEach(i -> i.setTennant(this));
        }
        this.categoryData = categoryData;
    }

    public Tennant categoryData(Set<CategoryData> categoryData) {
        this.setCategoryData(categoryData);
        return this;
    }

    public Tennant addCategoryData(CategoryData categoryData) {
        this.categoryData.add(categoryData);
        categoryData.setTennant(this);
        return this;
    }

    public Tennant removeCategoryData(CategoryData categoryData) {
        this.categoryData.remove(categoryData);
        categoryData.setTennant(null);
        return this;
    }

    public Set<CategoryGroup> getCategoryGroups() {
        return this.categoryGroups;
    }

    public void setCategoryGroups(Set<CategoryGroup> categoryGroups) {
        if (this.categoryGroups != null) {
            this.categoryGroups.forEach(i -> i.setTennant(null));
        }
        if (categoryGroups != null) {
            categoryGroups.forEach(i -> i.setTennant(this));
        }
        this.categoryGroups = categoryGroups;
    }

    public Tennant categoryGroups(Set<CategoryGroup> categoryGroups) {
        this.setCategoryGroups(categoryGroups);
        return this;
    }

    public Tennant addCategoryGroup(CategoryGroup categoryGroup) {
        this.categoryGroups.add(categoryGroup);
        categoryGroup.setTennant(this);
        return this;
    }

    public Tennant removeCategoryGroup(CategoryGroup categoryGroup) {
        this.categoryGroups.remove(categoryGroup);
        categoryGroup.setTennant(null);
        return this;
    }

    public Set<ResultOfStep> getResultOfSteps() {
        return this.resultOfSteps;
    }

    public void setResultOfSteps(Set<ResultOfStep> resultOfSteps) {
        if (this.resultOfSteps != null) {
            this.resultOfSteps.forEach(i -> i.setTennant(null));
        }
        if (resultOfSteps != null) {
            resultOfSteps.forEach(i -> i.setTennant(this));
        }
        this.resultOfSteps = resultOfSteps;
    }

    public Tennant resultOfSteps(Set<ResultOfStep> resultOfSteps) {
        this.setResultOfSteps(resultOfSteps);
        return this;
    }

    public Tennant addResultOfStep(ResultOfStep resultOfStep) {
        this.resultOfSteps.add(resultOfStep);
        resultOfStep.setTennant(this);
        return this;
    }

    public Tennant removeResultOfStep(ResultOfStep resultOfStep) {
        this.resultOfSteps.remove(resultOfStep);
        resultOfStep.setTennant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tennant)) {
            return false;
        }
        return id != null && id.equals(((Tennant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tennant{" +
            "id=" + getId() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", description='" + getDescription() + "'" +
            ", databaseUrl='" + getDatabaseUrl() + "'" +
            ", databaseSchemaName='" + getDatabaseSchemaName() + "'" +
            ", databaseUsermame='" + getDatabaseUsermame() + "'" +
            ", databasePassword='" + getDatabasePassword() + "'" +
            ", databaseDriverClass='" + getDatabaseDriverClass() + "'" +
            ", filePathFolder='" + getFilePathFolder() + "'" +
            ", createdId=" + getCreatedId() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedId=" + getModifiedId() +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
