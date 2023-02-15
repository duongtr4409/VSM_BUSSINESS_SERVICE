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
 * A RequestType.
 */
@Entity
@Table(name = "request_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "requesttype")
public class RequestType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "request_type_code")
    private String requestTypeCode;

    @Column(name = "request_type_name")
    private String requestTypeName;

//    @Column(name = "avatar_path")
//    private String avatarPath = "";

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

    @Column(name = "number_request_data")
    private Long numberRequestData;

    @Column(name = "version")
    private Long version;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private RequestGroup requestGroup;

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

    @OneToMany(mappedBy = "requestType")
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

    @OneToMany(mappedBy = "requestType")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RequestType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestTypeCode() {
        return this.requestTypeCode;
    }

    public RequestType requestTypeCode(String requestTypeCode) {
        this.setRequestTypeCode(requestTypeCode);
        return this;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestTypeName() {
        return this.requestTypeName;
    }

    public RequestType requestTypeName(String requestTypeName) {
        this.setRequestTypeName(requestTypeName);
        return this;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

//    public String getAvatarPath() {
//        return this.avatarPath;
//    }
//
//    public RequestType avatarPath(String avatarPath) {
//        this.setAvatarPath(avatarPath);
//        return this;
//    }
//
//    public void setAvatarPath(String avatarPath) {
//        this.avatarPath = avatarPath;
//    }

    public String getDescription() {
        return this.description;
    }

    public RequestType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public RequestType createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public RequestType createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public RequestType createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public RequestType createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public RequestType modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public RequestType modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public RequestType isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public RequestType isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getNumberRequest() {
        return this.numberRequest;
    }

    public RequestType numberRequest(Long numberRequest) {
        this.setNumberRequest(numberRequest);
        return this;
    }

    public void setNumberRequest(Long numberRequest) {
        this.numberRequest = numberRequest;
    }

    public Long getNumberRequestData() {
        return this.numberRequestData;
    }

    public RequestType numberRequestData(Long numberRequestData) {
        this.setNumberRequestData(numberRequestData);
        return this;
    }

    public void setNumberRequestData(Long numberRequestData) {
        this.numberRequestData = numberRequestData;
    }

    public Long getVersion() {
        return this.version;
    }

    public RequestType version(Long version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public RequestType tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public RequestType tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public RequestGroup getRequestGroup() {
        return this.requestGroup;
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }

    public RequestType requestGroup(RequestGroup requestGroup) {
        this.setRequestGroup(requestGroup);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public RequestType tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public RequestType created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public RequestType modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setRequestType(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setRequestType(this));
        }
        this.requests = requests;
    }

    public RequestType requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public RequestType addRequest(Request request) {
        this.requests.add(request);
        request.setRequestType(this);
        return this;
    }

    public RequestType removeRequest(Request request) {
        this.requests.remove(request);
        request.setRequestType(null);
        return this;
    }

    public Set<RequestData> getRequestData() {
        return this.requestData;
    }

    public void setRequestData(Set<RequestData> requestData) {
        if (this.requestData != null) {
            this.requestData.forEach(i -> i.setRequestType(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setRequestType(this));
        }
        this.requestData = requestData;
    }

    public RequestType requestData(Set<RequestData> requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public RequestType addRequestData(RequestData requestData) {
        this.requestData.add(requestData);
        requestData.setRequestType(this);
        return this;
    }

    public RequestType removeRequestData(RequestData requestData) {
        this.requestData.remove(requestData);
        requestData.setRequestType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestType)) {
            return false;
        }
        return id != null && id.equals(((RequestType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestType{" +
            "id=" + getId() +
            ", requestTypeCode='" + getRequestTypeCode() + "'" +
            ", requestTypeName='" + getRequestTypeName() + "'" +
//            ", avatarPath='" + getAvatarPath() + "'" +
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
            ", numberRequestData=" + getNumberRequestData() +
            ", version=" + getVersion() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
