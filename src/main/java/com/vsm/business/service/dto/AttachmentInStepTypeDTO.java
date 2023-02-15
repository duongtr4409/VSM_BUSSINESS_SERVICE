package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.AttachmentInStepType} entity.
 */
public class AttachmentInStepTypeDTO implements Serializable {

    private Long id;

    private String typeCode;

    private String typeName;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedate;

    private Boolean isActive;

    private Boolean isDelete;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentInStepTypeDTO)) {
            return false;
        }

        AttachmentInStepTypeDTO attachmentInStepTypeDTO = (AttachmentInStepTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachmentInStepTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentInStepTypeDTO{" +
            "id=" + getId() +
            ", typeCode='" + getTypeCode() + "'" +
            ", typeName='" + getTypeName() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedate='" + getModifiedate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
