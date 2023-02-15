package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.AttachmentInStep} entity.
 */
public class AttachmentInStepDTO implements Serializable {

    private Long id;

    private String typeName;

    private Long referenceId;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedate;

    private Boolean isActive;

    private Boolean isDelete;

    private AttachmentInStepTypeDTO attachmentInStepType;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private StepDataDTO stepData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
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

    public Instant getModifiedate() {
        return modifiedate;
    }

    public void setModifiedate(Instant modifiedate) {
        this.modifiedate = modifiedate;
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

    public AttachmentInStepTypeDTO getAttachmentInStepType() {
        return attachmentInStepType;
    }

    public void setAttachmentInStepType(AttachmentInStepTypeDTO attachmentInStepType) {
        this.attachmentInStepType = attachmentInStepType;
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

    public StepDataDTO getStepData() {
        return stepData;
    }

    public void setStepData(StepDataDTO stepData) {
        this.stepData = stepData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentInStepDTO)) {
            return false;
        }

        AttachmentInStepDTO attachmentInStepDTO = (AttachmentInStepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachmentInStepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentInStepDTO{" +
            "id=" + getId() +
            ", typeName='" + getTypeName() + "'" +
            ", referenceId=" + getReferenceId() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedate='" + getModifiedate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", attachmentInStepType=" + getAttachmentInStepType() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", stepData=" + getStepData() +
            "}";
    }
}
