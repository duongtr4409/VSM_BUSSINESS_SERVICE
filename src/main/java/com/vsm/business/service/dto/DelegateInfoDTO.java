package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.DelegateInfo} entity.
 */
public class DelegateInfoDTO implements Serializable {

    private Long id;

    private String delegateName;

    private String delegateCode;

    private Long delegateId;

    private String delegateToName;

    private String delegateToCode;

    private Long delegateToId;

    private String delegateDocName;

    private String delegateDocCode;

    private Long documentId;

    private String note;

    private String delegateTypeName;

    private String delegateTypeCode;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private UserInfoDTO delegater;

    private UserInfoDTO delegated;

    private DelegateTypeDTO delegateType;

    private RequestDTO docDelegate;

    private DelegateDocumentDTO delegateDocument;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegateName() {
        return delegateName;
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName;
    }

    public String getDelegateCode() {
        return delegateCode;
    }

    public void setDelegateCode(String delegateCode) {
        this.delegateCode = delegateCode;
    }

    public Long getDelegateId() {
        return delegateId;
    }

    public void setDelegateId(Long delegateId) {
        this.delegateId = delegateId;
    }

    public String getDelegateToName() {
        return delegateToName;
    }

    public void setDelegateToName(String delegateToName) {
        this.delegateToName = delegateToName;
    }

    public String getDelegateToCode() {
        return delegateToCode;
    }

    public void setDelegateToCode(String delegateToCode) {
        this.delegateToCode = delegateToCode;
    }

    public Long getDelegateToId() {
        return delegateToId;
    }

    public void setDelegateToId(Long delegateToId) {
        this.delegateToId = delegateToId;
    }

    public String getDelegateDocName() {
        return delegateDocName;
    }

    public void setDelegateDocName(String delegateDocName) {
        this.delegateDocName = delegateDocName;
    }

    public String getDelegateDocCode() {
        return delegateDocCode;
    }

    public void setDelegateDocCode(String delegateDocCode) {
        this.delegateDocCode = delegateDocCode;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDelegateTypeName() {
        return delegateTypeName;
    }

    public void setDelegateTypeName(String delegateTypeName) {
        this.delegateTypeName = delegateTypeName;
    }

    public String getDelegateTypeCode() {
        return delegateTypeCode;
    }

    public void setDelegateTypeCode(String delegateTypeCode) {
        this.delegateTypeCode = delegateTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return createdOrgName;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return createdRankName;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfoDTO getCreater() {
        return creater;
    }

    public void setCreater(UserInfoDTO creater) {
        this.creater = creater;
    }

    public UserInfoDTO getModifier() {
        return modifier;
    }

    public void setModifier(UserInfoDTO modifier) {
        this.modifier = modifier;
    }

    public UserInfoDTO getDelegater() {
        return delegater;
    }

    public void setDelegater(UserInfoDTO delegater) {
        this.delegater = delegater;
    }

    public UserInfoDTO getDelegated() {
        return delegated;
    }

    public void setDelegated(UserInfoDTO delegated) {
        this.delegated = delegated;
    }

    public DelegateTypeDTO getDelegateType() {
        return delegateType;
    }

    public void setDelegateType(DelegateTypeDTO delegateType) {
        this.delegateType = delegateType;
    }

    public RequestDTO getDocDelegate() {
        return docDelegate;
    }

    public void setDocDelegate(RequestDTO docDelegate) {
        this.docDelegate = docDelegate;
    }

    public DelegateDocumentDTO getDelegateDocument() {
        return delegateDocument;
    }

    public void setDelegateDocument(DelegateDocumentDTO delegateDocument) {
        this.delegateDocument = delegateDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelegateInfoDTO)) {
            return false;
        }

        DelegateInfoDTO delegateInfoDTO = (DelegateInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, delegateInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DelegateInfoDTO{" +
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
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", delegater=" + getDelegater() +
            ", delegated=" + getDelegated() +
            ", delegateType=" + getDelegateType() +
            ", docDelegate=" + getDocDelegate() +
            ", delegateDocument=" + getDelegateDocument() +
            "}";
    }
}
