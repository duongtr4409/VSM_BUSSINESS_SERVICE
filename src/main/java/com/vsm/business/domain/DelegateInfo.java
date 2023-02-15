package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DelegateInfo.
 */
@Entity
@Table(name = "delegate_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "delegateinfo")
public class DelegateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "delegate_name")
    private String delegateName;

    @Column(name = "delegate_code")
    private String delegateCode;

    @Column(name = "delegate_id")
    private Long delegateId;

    @Column(name = "delegate_to_name")
    private String delegateToName;

    @Column(name = "delegate_to_code")
    private String delegateToCode;

    @Column(name = "delegate_to_id")
    private Long delegateToId;

    @Column(name = "delegate_doc_name")
    private String delegateDocName;

    @Column(name = "delegate_doc_code")
    private String delegateDocCode;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "note")
    private String note;

    @Column(name = "delegate_type_name")
    private String delegateTypeName;

    @Column(name = "delegate_type_code")
    private String delegateTypeCode;

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
    private UserInfo delegater;

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
    private UserInfo delegated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "delegateInfos" }, allowSetters = true)
    private DelegateType delegateType;

    @ManyToOne
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
    private Request docDelegate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "delegateInfos" }, allowSetters = true)
    private DelegateDocument delegateDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DelegateInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegateName() {
        return this.delegateName;
    }

    public DelegateInfo delegateName(String delegateName) {
        this.setDelegateName(delegateName);
        return this;
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName;
    }

    public String getDelegateCode() {
        return this.delegateCode;
    }

    public DelegateInfo delegateCode(String delegateCode) {
        this.setDelegateCode(delegateCode);
        return this;
    }

    public void setDelegateCode(String delegateCode) {
        this.delegateCode = delegateCode;
    }

    public Long getDelegateId() {
        return this.delegateId;
    }

    public DelegateInfo delegateId(Long delegateId) {
        this.setDelegateId(delegateId);
        return this;
    }

    public void setDelegateId(Long delegateId) {
        this.delegateId = delegateId;
    }

    public String getDelegateToName() {
        return this.delegateToName;
    }

    public DelegateInfo delegateToName(String delegateToName) {
        this.setDelegateToName(delegateToName);
        return this;
    }

    public void setDelegateToName(String delegateToName) {
        this.delegateToName = delegateToName;
    }

    public String getDelegateToCode() {
        return this.delegateToCode;
    }

    public DelegateInfo delegateToCode(String delegateToCode) {
        this.setDelegateToCode(delegateToCode);
        return this;
    }

    public void setDelegateToCode(String delegateToCode) {
        this.delegateToCode = delegateToCode;
    }

    public Long getDelegateToId() {
        return this.delegateToId;
    }

    public DelegateInfo delegateToId(Long delegateToId) {
        this.setDelegateToId(delegateToId);
        return this;
    }

    public void setDelegateToId(Long delegateToId) {
        this.delegateToId = delegateToId;
    }

    public String getDelegateDocName() {
        return this.delegateDocName;
    }

    public DelegateInfo delegateDocName(String delegateDocName) {
        this.setDelegateDocName(delegateDocName);
        return this;
    }

    public void setDelegateDocName(String delegateDocName) {
        this.delegateDocName = delegateDocName;
    }

    public String getDelegateDocCode() {
        return this.delegateDocCode;
    }

    public DelegateInfo delegateDocCode(String delegateDocCode) {
        this.setDelegateDocCode(delegateDocCode);
        return this;
    }

    public void setDelegateDocCode(String delegateDocCode) {
        this.delegateDocCode = delegateDocCode;
    }

    public Long getDocumentId() {
        return this.documentId;
    }

    public DelegateInfo documentId(Long documentId) {
        this.setDocumentId(documentId);
        return this;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getNote() {
        return this.note;
    }

    public DelegateInfo note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDelegateTypeName() {
        return this.delegateTypeName;
    }

    public DelegateInfo delegateTypeName(String delegateTypeName) {
        this.setDelegateTypeName(delegateTypeName);
        return this;
    }

    public void setDelegateTypeName(String delegateTypeName) {
        this.delegateTypeName = delegateTypeName;
    }

    public String getDelegateTypeCode() {
        return this.delegateTypeCode;
    }

    public DelegateInfo delegateTypeCode(String delegateTypeCode) {
        this.setDelegateTypeCode(delegateTypeCode);
        return this;
    }

    public void setDelegateTypeCode(String delegateTypeCode) {
        this.delegateTypeCode = delegateTypeCode;
    }

    public String getDescription() {
        return this.description;
    }

    public DelegateInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public DelegateInfo createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public DelegateInfo createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public DelegateInfo createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public DelegateInfo createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public DelegateInfo modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public DelegateInfo modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DelegateInfo isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public DelegateInfo isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public DelegateInfo creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public DelegateInfo modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public UserInfo getDelegater() {
        return this.delegater;
    }

    public void setDelegater(UserInfo userInfo) {
        this.delegater = userInfo;
    }

    public DelegateInfo delegater(UserInfo userInfo) {
        this.setDelegater(userInfo);
        return this;
    }

    public UserInfo getDelegated() {
        return this.delegated;
    }

    public void setDelegated(UserInfo userInfo) {
        this.delegated = userInfo;
    }

    public DelegateInfo delegated(UserInfo userInfo) {
        this.setDelegated(userInfo);
        return this;
    }

    public DelegateType getDelegateType() {
        return this.delegateType;
    }

    public void setDelegateType(DelegateType delegateType) {
        this.delegateType = delegateType;
    }

    public DelegateInfo delegateType(DelegateType delegateType) {
        this.setDelegateType(delegateType);
        return this;
    }

    public Request getDocDelegate() {
        return this.docDelegate;
    }

    public void setDocDelegate(Request request) {
        this.docDelegate = request;
    }

    public DelegateInfo docDelegate(Request request) {
        this.setDocDelegate(request);
        return this;
    }

    public DelegateDocument getDelegateDocument() {
        return this.delegateDocument;
    }

    public void setDelegateDocument(DelegateDocument delegateDocument) {
        this.delegateDocument = delegateDocument;
    }

    public DelegateInfo delegateDocument(DelegateDocument delegateDocument) {
        this.setDelegateDocument(delegateDocument);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelegateInfo)) {
            return false;
        }
        return id != null && id.equals(((DelegateInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DelegateInfo{" +
            "id=" + getId() +
            ", delegateName='" + getDelegateName() + "'" +
            ", delegateCode='" + getDelegateCode() + "'" +
            ", delegateId=" + getDelegateId() +
            ", delegateToName='" + getDelegateToName() + "'" +
            ", delegateToCode='" + getDelegateToCode() + "'" +
            ", delegateToId=" + getDelegateToId() +
            ", delegateDocName='" + getDelegateDocName() + "'" +
            ", delegateDocCode='" + getDelegateDocCode() + "'" +
            ", documentId=" + getDocumentId() +
            ", note='" + getNote() + "'" +
            ", delegateTypeName='" + getDelegateTypeName() + "'" +
            ", delegateTypeCode='" + getDelegateTypeCode() + "'" +
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
