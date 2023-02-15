package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.IncomingDocHis} entity.
 */
public class IncomingDocHisDTO implements Serializable {

    private Long id;

    private String incomingDocName;

    private String incomingDocCode;

    private String incomingDocNumber;

    private String placeSendName;

    private String placeSendCode;

    private Instant incommingDocDate;

    private String incomingDocTypeName;

    private String incomingDocTypeCode;

    private String compendium;

    private String incomingStatusName;

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

    private IncomingDocDTO incomingDoc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingDocName() {
        return incomingDocName;
    }

    public void setIncomingDocName(String incomingDocName) {
        this.incomingDocName = incomingDocName;
    }

    public String getIncomingDocCode() {
        return incomingDocCode;
    }

    public void setIncomingDocCode(String incomingDocCode) {
        this.incomingDocCode = incomingDocCode;
    }

    public String getIncomingDocNumber() {
        return incomingDocNumber;
    }

    public void setIncomingDocNumber(String incomingDocNumber) {
        this.incomingDocNumber = incomingDocNumber;
    }

    public String getPlaceSendName() {
        return placeSendName;
    }

    public void setPlaceSendName(String placeSendName) {
        this.placeSendName = placeSendName;
    }

    public String getPlaceSendCode() {
        return placeSendCode;
    }

    public void setPlaceSendCode(String placeSendCode) {
        this.placeSendCode = placeSendCode;
    }

    public Instant getIncommingDocDate() {
        return incommingDocDate;
    }

    public void setIncommingDocDate(Instant incommingDocDate) {
        this.incommingDocDate = incommingDocDate;
    }

    public String getIncomingDocTypeName() {
        return incomingDocTypeName;
    }

    public void setIncomingDocTypeName(String incomingDocTypeName) {
        this.incomingDocTypeName = incomingDocTypeName;
    }

    public String getIncomingDocTypeCode() {
        return incomingDocTypeCode;
    }

    public void setIncomingDocTypeCode(String incomingDocTypeCode) {
        this.incomingDocTypeCode = incomingDocTypeCode;
    }

    public String getCompendium() {
        return compendium;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
    }

    public String getIncomingStatusName() {
        return incomingStatusName;
    }

    public void setIncomingStatusName(String incomingStatusName) {
        this.incomingStatusName = incomingStatusName;
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

    public IncomingDocDTO getIncomingDoc() {
        return incomingDoc;
    }

    public void setIncomingDoc(IncomingDocDTO incomingDoc) {
        this.incomingDoc = incomingDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingDocHisDTO)) {
            return false;
        }

        IncomingDocHisDTO incomingDocHisDTO = (IncomingDocHisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, incomingDocHisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingDocHisDTO{" +
            "id=" + getId() +
            ", incomingDocName='" + getIncomingDocName() + "'" +
            ", incomingDocCode='" + getIncomingDocCode() + "'" +
            ", incomingDocNumber='" + getIncomingDocNumber() + "'" +
            ", placeSendName='" + getPlaceSendName() + "'" +
            ", placeSendCode='" + getPlaceSendCode() + "'" +
            ", incommingDocDate='" + getIncommingDocDate() + "'" +
            ", incomingDocTypeName='" + getIncomingDocTypeName() + "'" +
            ", incomingDocTypeCode='" + getIncomingDocTypeCode() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", incomingStatusName='" + getIncomingStatusName() + "'" +
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
            ", incomingDoc=" + getIncomingDoc() +
            "}";
    }
}
