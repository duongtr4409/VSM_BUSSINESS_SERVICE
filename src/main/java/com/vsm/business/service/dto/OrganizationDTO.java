package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.Organization} entity.
 */
public class OrganizationDTO implements Serializable {

    private Long id;

    private String organizationCode;

    private String organizationName;

    private String orgParentCode;

    private String orgParentName;

    private Boolean isSyncAD;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private String tennantCode;

    private String tennantName;

    private OrganizationDTO orgParent;

    private List<OrganizationDTO> childOrganization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrgParentCode() {
        return orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public String getOrgParentName() {
        return orgParentName;
    }

    public void setOrgParentName(String orgParentName) {
        this.orgParentName = orgParentName;
    }

    public Boolean getIsSyncAD() {
        return isSyncAD;
    }

    public void setIsSyncAD(Boolean isSyncAD) {
        this.isSyncAD = isSyncAD;
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

    public String getTennantCode() {
        return tennantCode;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return tennantName;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public OrganizationDTO getOrgParent() {
        return orgParent;
    }

    public void setOrgParent(OrganizationDTO orgParent) {
        this.orgParent = orgParent;
    }

    public List<OrganizationDTO> getChildOrganization() {
        return childOrganization;
    }

    public void setChildOrganization(List<OrganizationDTO> childOrganization) {
        this.childOrganization = childOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationDTO)) {
            return false;
        }

        OrganizationDTO organizationDTO = (OrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationDTO{" +
            "id=" + getId() +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", orgParentCode='" + getOrgParentCode() + "'" +
            ", orgParentName='" + getOrgParentName() + "'" +
            ", isSyncAD='" + getIsSyncAD() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", orgParent=" + getOrgParent() +
            "}";
    }
}
