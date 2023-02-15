package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.OutgoingDocHis} entity.
 */
public class OutgoingDocHisDTO implements Serializable {

    private Long id;

    private String outgoingDocName;

    private String outgoingDocCode;

    private String outgoingDocNumber;

    private String releaseOrgName;

    private String releaseOrgAvatar;

    private Instant releaseDate;

    private String composeOrgName;

    private String composeOrgAvatar;

    private String documentTypeName;

    private String documentTypeCode;

    private String compendium;

    private String outgoingStatusName;

    private String executorName;

    private String action;

    private String content;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private OutgoingDocDTO outgoingDoc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutgoingDocName() {
        return outgoingDocName;
    }

    public void setOutgoingDocName(String outgoingDocName) {
        this.outgoingDocName = outgoingDocName;
    }

    public String getOutgoingDocCode() {
        return outgoingDocCode;
    }

    public void setOutgoingDocCode(String outgoingDocCode) {
        this.outgoingDocCode = outgoingDocCode;
    }

    public String getOutgoingDocNumber() {
        return outgoingDocNumber;
    }

    public void setOutgoingDocNumber(String outgoingDocNumber) {
        this.outgoingDocNumber = outgoingDocNumber;
    }

    public String getReleaseOrgName() {
        return releaseOrgName;
    }

    public void setReleaseOrgName(String releaseOrgName) {
        this.releaseOrgName = releaseOrgName;
    }

    public String getReleaseOrgAvatar() {
        return releaseOrgAvatar;
    }

    public void setReleaseOrgAvatar(String releaseOrgAvatar) {
        this.releaseOrgAvatar = releaseOrgAvatar;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getComposeOrgName() {
        return composeOrgName;
    }

    public void setComposeOrgName(String composeOrgName) {
        this.composeOrgName = composeOrgName;
    }

    public String getComposeOrgAvatar() {
        return composeOrgAvatar;
    }

    public void setComposeOrgAvatar(String composeOrgAvatar) {
        this.composeOrgAvatar = composeOrgAvatar;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getCompendium() {
        return compendium;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
    }

    public String getOutgoingStatusName() {
        return outgoingStatusName;
    }

    public void setOutgoingStatusName(String outgoingStatusName) {
        this.outgoingStatusName = outgoingStatusName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public OutgoingDocDTO getOutgoingDoc() {
        return outgoingDoc;
    }

    public void setOutgoingDoc(OutgoingDocDTO outgoingDoc) {
        this.outgoingDoc = outgoingDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutgoingDocHisDTO)) {
            return false;
        }

        OutgoingDocHisDTO outgoingDocHisDTO = (OutgoingDocHisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, outgoingDocHisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutgoingDocHisDTO{" +
            "id=" + getId() +
            ", outgoingDocName='" + getOutgoingDocName() + "'" +
            ", outgoingDocCode='" + getOutgoingDocCode() + "'" +
            ", outgoingDocNumber='" + getOutgoingDocNumber() + "'" +
            ", releaseOrgName='" + getReleaseOrgName() + "'" +
            ", releaseOrgAvatar='" + getReleaseOrgAvatar() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", composeOrgName='" + getComposeOrgName() + "'" +
            ", composeOrgAvatar='" + getComposeOrgAvatar() + "'" +
            ", documentTypeName='" + getDocumentTypeName() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", outgoingStatusName='" + getOutgoingStatusName() + "'" +
            ", executorName='" + getExecutorName() + "'" +
            ", action='" + getAction() + "'" +
            ", content='" + getContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", outgoingDoc=" + getOutgoingDoc() +
            "}";
    }
}
