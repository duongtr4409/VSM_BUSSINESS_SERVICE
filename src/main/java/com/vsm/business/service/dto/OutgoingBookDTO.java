package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.OutgoingBook} entity.
 */
public class OutgoingBookDTO implements Serializable {

    private Long id;

    private String outgoingBookName;

    private String outgoingBookCode;

    private String organizationName;

    private String organizationCode;

    private Long totalDoc;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private OrganizationDTO organization;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutgoingBookName() {
        return outgoingBookName;
    }

    public void setOutgoingBookName(String outgoingBookName) {
        this.outgoingBookName = outgoingBookName;
    }

    public String getOutgoingBookCode() {
        return outgoingBookCode;
    }

    public void setOutgoingBookCode(String outgoingBookCode) {
        this.outgoingBookCode = outgoingBookCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Long getTotalDoc() {
        return totalDoc;
    }

    public void setTotalDoc(Long totalDoc) {
        this.totalDoc = totalDoc;
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

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
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
        if (!(o instanceof OutgoingBookDTO)) {
            return false;
        }

        OutgoingBookDTO outgoingBookDTO = (OutgoingBookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, outgoingBookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutgoingBookDTO{" +
            "id=" + getId() +
            ", outgoingBookName='" + getOutgoingBookName() + "'" +
            ", outgoingBookCode='" + getOutgoingBookCode() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", totalDoc=" + getTotalDoc() +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", organization=" + getOrganization() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
