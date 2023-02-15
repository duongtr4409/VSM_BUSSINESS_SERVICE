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
 * A Request.
 */
@Entity
@Table(name = "request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "request_code")
    private String requestCode;

    @Column(name = "request_name")
    private String requestName;

//    @Column(name = "avatar_path")
//    private String avatarPath;

    @Column(name = "directory_path")
    private String directoryPath;

    @Column(name = "id_directory_path")
    private String idDirectoryPath;

    @Column(name = "description")
    private String description;

    @Column(name = "is_create_outgoing_doc")
    private Boolean isCreateOutgoingDoc;

    @Column(name = "rule_generate_code")
    private String ruleGenerateCode;

    @Column(name = "rule_generate_attach_name")
    private String ruleGenerateAttachName;

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

    @Column(name = "number_request_data")
    private Long numberRequestData;

    @Column(name = "version")
    private Long version;

    @Column(name = "contract_expire_field_name")
    private String contractExpireFieldName;

    @Column(name = "plot_of_land_number")
    private String plotOfLandNumber;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requestGroup", "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private RequestType requestType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private RequestGroup requestGroup;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "fields", "requests", "formData", "fieldInForms" },
        allowSetters = true
    )
    private Form form;

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
        name = "rel_request__template_form",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "template_form_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "organizations", "attachmentFiles", "requests" }, allowSetters = true)
    private Set<TemplateForm> templateForms = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_request__process_info",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "process_info_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates" },
        allowSetters = true
    )
    private Set<ProcessInfo> processInfos = new HashSet<>();

    @OneToMany(mappedBy = "request")
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

    @OneToMany(mappedBy = "request")
    @JsonIgnoreProperties(value = { "created", "modified", "role", "request" }, allowSetters = true)
    private Set<RoleRequest> roleRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Request id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public Request requestCode(String requestCode) {
        this.setRequestCode(requestCode);
        return this;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestName() {
        return this.requestName;
    }

    public Request requestName(String requestName) {
        this.setRequestName(requestName);
        return this;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

//    public String getAvatarPath() {
//        return this.avatarPath;
//    }
//
//    public Request avatarPath(String avatarPath) {
//        this.setAvatarPath(avatarPath);
//        return this;
//    }
//
//    public void setAvatarPath(String avatarPath) {
//        this.avatarPath = avatarPath;
//    }

    public String getDirectoryPath() {
        return this.directoryPath;
    }

    public Request directoryPath(String directoryPath) {
        this.setDirectoryPath(directoryPath);
        return this;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getIdDirectoryPath() {
        return this.idDirectoryPath;
    }

    public Request idDirectoryPath(String idDirectoryPath) {
        this.setIdDirectoryPath(idDirectoryPath);
        return this;
    }

    public void setIdDirectoryPath(String idDirectoryPath) {
        this.idDirectoryPath = idDirectoryPath;
    }

    public String getDescription() {
        return this.description;
    }

    public Request description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCreateOutgoingDoc() {
        return this.isCreateOutgoingDoc;
    }

    public Request isCreateOutgoingDoc(Boolean isCreateOutgoingDoc) {
        this.setIsCreateOutgoingDoc(isCreateOutgoingDoc);
        return this;
    }

    public void setIsCreateOutgoingDoc(Boolean isCreateOutgoingDoc) {
        this.isCreateOutgoingDoc = isCreateOutgoingDoc;
    }

    public String getRuleGenerateCode() {
        return this.ruleGenerateCode;
    }

    public Request ruleGenerateCode(String ruleGenerateCode) {
        this.setRuleGenerateCode(ruleGenerateCode);
        return this;
    }

    public void setRuleGenerateCode(String ruleGenerateCode) {
        this.ruleGenerateCode = ruleGenerateCode;
    }

    public String getRuleGenerateAttachName() {
        return this.ruleGenerateAttachName;
    }

    public Request ruleGenerateAttachName(String ruleGenerateAttachName) {
        this.setRuleGenerateAttachName(ruleGenerateAttachName);
        return this;
    }

    public void setRuleGenerateAttachName(String ruleGenerateAttachName) {
        this.ruleGenerateAttachName = ruleGenerateAttachName;
    }

    public String getMappingInfo() {
        return this.mappingInfo;
    }

    public Request mappingInfo(String mappingInfo) {
        this.setMappingInfo(mappingInfo);
        return this;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
    }

    public String getSapMapping() {
        return this.sapMapping;
    }

    public Request sapMapping(String sapMapping) {
        this.setSapMapping(sapMapping);
        return this;
    }

    public void setSapMapping(String sapMapping) {
        this.sapMapping = sapMapping;
    }

    public String getDataRoomPath() {
        return this.dataRoomPath;
    }

    public Request dataRoomPath(String dataRoomPath) {
        this.setDataRoomPath(dataRoomPath);
        return this;
    }

    public void setDataRoomPath(String dataRoomPath) {
        this.dataRoomPath = dataRoomPath;
    }

    public String getDataRoomId() {
        return this.dataRoomId;
    }

    public Request dataRoomId(String dataRoomId) {
        this.setDataRoomId(dataRoomId);
        return this;
    }

    public void setDataRoomId(String dataRoomId) {
        this.dataRoomId = dataRoomId;
    }

    public String getDataRoomDriveId() {
        return this.dataRoomDriveId;
    }

    public Request dataRoomDriveId(String dataRoomDriveId) {
        this.setDataRoomDriveId(dataRoomDriveId);
        return this;
    }

    public void setDataRoomDriveId(String dataRoomDriveId) {
        this.dataRoomDriveId = dataRoomDriveId;
    }

    public Boolean getIsSyncSap() {
        return this.isSyncSap;
    }

    public Request isSyncSap(Boolean isSyncSap) {
        this.setIsSyncSap(isSyncSap);
        return this;
    }

    public void setIsSyncSap(Boolean isSyncSap) {
        this.isSyncSap = isSyncSap;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Request createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Request createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Request createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Request createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Request modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Request modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Request isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Request isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getNumberRequestData() {
        return this.numberRequestData;
    }

    public Request numberRequestData(Long numberRequestData) {
        this.setNumberRequestData(numberRequestData);
        return this;
    }

    public void setNumberRequestData(Long numberRequestData) {
        this.numberRequestData = numberRequestData;
    }

    public Long getVersion() {
        return this.version;
    }

    public Request version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getContractExpireFieldName() {
        return this.contractExpireFieldName;
    }

    public Request contractExpireFieldName(String contractExpireFieldName) {
        this.setContractExpireFieldName(contractExpireFieldName);
        return this;
    }

    public void setContractExpireFieldName(String contractExpireFieldName) {
        this.contractExpireFieldName = contractExpireFieldName;
    }

    public String getPlotOfLandNumber() {
        return this.plotOfLandNumber;
    }

    public Request plotOfLandNumber(String plotOfLandNumber) {
        this.setPlotOfLandNumber(plotOfLandNumber);
        return this;
    }

    public void setPlotOfLandNumber(String plotOfLandNumber) {
        this.plotOfLandNumber = plotOfLandNumber;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public Request tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public Request tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Request requestType(RequestType requestType) {
        this.setRequestType(requestType);
        return this;
    }

    public RequestGroup getRequestGroup() {
        return this.requestGroup;
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }

    public Request requestGroup(RequestGroup requestGroup) {
        this.setRequestGroup(requestGroup);
        return this;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Request form(Form form) {
        this.setForm(form);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public Request tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public Request created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public Request modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Set<TemplateForm> getTemplateForms() {
        return this.templateForms;
    }

    public void setTemplateForms(Set<TemplateForm> templateForms) {
        this.templateForms = templateForms;
    }

    public Request templateForms(Set<TemplateForm> templateForms) {
        this.setTemplateForms(templateForms);
        return this;
    }

    public Request addTemplateForm(TemplateForm templateForm) {
        this.templateForms.add(templateForm);
        templateForm.getRequests().add(this);
        return this;
    }

    public Request removeTemplateForm(TemplateForm templateForm) {
        this.templateForms.remove(templateForm);
        templateForm.getRequests().remove(this);
        return this;
    }

    public Set<ProcessInfo> getProcessInfos() {
        return this.processInfos;
    }

    public void setProcessInfos(Set<ProcessInfo> processInfos) {
        this.processInfos = processInfos;
    }

    public Request processInfos(Set<ProcessInfo> processInfos) {
        this.setProcessInfos(processInfos);
        return this;
    }

    public Request addProcessInfo(ProcessInfo processInfo) {
        this.processInfos.add(processInfo);
        processInfo.getRequests().add(this);
        return this;
    }

    public Request removeProcessInfo(ProcessInfo processInfo) {
        this.processInfos.remove(processInfo);
        processInfo.getRequests().remove(this);
        return this;
    }

    public Set<RequestData> getRequestData() {
        return this.requestData;
    }

    public void setRequestData(Set<RequestData> requestData) {
        if (this.requestData != null) {
            this.requestData.forEach(i -> i.setRequest(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setRequest(this));
        }
        this.requestData = requestData;
    }

    public Request requestData(Set<RequestData> requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Request addRequestData(RequestData requestData) {
        this.requestData.add(requestData);
        requestData.setRequest(this);
        return this;
    }

    public Request removeRequestData(RequestData requestData) {
        this.requestData.remove(requestData);
        requestData.setRequest(null);
        return this;
    }

    public Set<RoleRequest> getRoleRequests() {
        return this.roleRequests;
    }

    public void setRoleRequests(Set<RoleRequest> roleRequests) {
        if (this.roleRequests != null) {
            this.roleRequests.forEach(i -> i.setRequest(null));
        }
        if (roleRequests != null) {
            roleRequests.forEach(i -> i.setRequest(this));
        }
        this.roleRequests = roleRequests;
    }

    public Request roleRequests(Set<RoleRequest> roleRequests) {
        this.setRoleRequests(roleRequests);
        return this;
    }

    public Request addRoleRequest(RoleRequest roleRequest) {
        this.roleRequests.add(roleRequest);
        roleRequest.setRequest(this);
        return this;
    }

    public Request removeRoleRequest(RoleRequest roleRequest) {
        this.roleRequests.remove(roleRequest);
        roleRequest.setRequest(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", requestCode='" + getRequestCode() + "'" +
            ", requestName='" + getRequestName() + "'" +
//            ", avatarPath='" + getAvatarPath() + "'" +
            ", directoryPath='" + getDirectoryPath() + "'" +
            ", idDirectoryPath='" + getIdDirectoryPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", isCreateOutgoingDoc='" + getIsCreateOutgoingDoc() + "'" +
            ", ruleGenerateCode='" + getRuleGenerateCode() + "'" +
            ", ruleGenerateAttachName='" + getRuleGenerateAttachName() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
            ", sapMapping='" + getSapMapping() + "'" +
            ", dataRoomPath='" + getDataRoomPath() + "'" +
            ", dataRoomId='" + getDataRoomId() + "'" +
            ", dataRoomDriveId='" + getDataRoomDriveId() + "'" +
            ", isSyncSap='" + getIsSyncSap() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", numberRequestData=" + getNumberRequestData() +
            ", version=" + getVersion() +
            ", contractExpireFieldName='" + getContractExpireFieldName() + "'" +
            ", plotOfLandNumber='" + getPlotOfLandNumber() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
