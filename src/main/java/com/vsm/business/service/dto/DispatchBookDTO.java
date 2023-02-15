package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.vsm.business.domain.DispatchBook} entity.
 */
public class DispatchBookDTO implements Serializable {

    private Long id;

    private String dispatchBookName;

    private String dispatchBookCode;

    private String organizationName;

    private String organizationCode;

    private String dispatchBookTypeName;

    private String dispatchBookTypeCode;

    private Long totalDoc;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDDate;

    private Boolean isActive;

    private Boolean isDelete;

    private OrganizationDTO organization;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private Set<OrganizationDTO> dispatchBookOrgs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDispatchBookName() {
        return dispatchBookName;
    }

    public void setDispatchBookName(String dispatchBookName) {
        this.dispatchBookName = dispatchBookName;
    }

    public String getDispatchBookCode() {
        return dispatchBookCode;
    }

    public void setDispatchBookCode(String dispatchBookCode) {
        this.dispatchBookCode = dispatchBookCode;
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

    public String getDispatchBookTypeName() {
        return dispatchBookTypeName;
    }

    public void setDispatchBookTypeName(String dispatchBookTypeName) {
        this.dispatchBookTypeName = dispatchBookTypeName;
    }

    public String getDispatchBookTypeCode() {
        return dispatchBookTypeCode;
    }

    public void setDispatchBookTypeCode(String dispatchBookTypeCode) {
        this.dispatchBookTypeCode = dispatchBookTypeCode;
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

    public Instant getModifiedDDate() {
        return modifiedDDate;
    }

    public void setModifiedDDate(Instant modifiedDDate) {
        this.modifiedDDate = modifiedDDate;
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

    public Set<OrganizationDTO> getDispatchBookOrgs() {
        return dispatchBookOrgs;
    }

    public void setDispatchBookOrgs(Set<OrganizationDTO> dispatchBookOrgs) {
        this.dispatchBookOrgs = dispatchBookOrgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispatchBookDTO)) {
            return false;
        }

        DispatchBookDTO dispatchBookDTO = (DispatchBookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dispatchBookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispatchBookDTO{" +
            "id=" + getId() +
            ", dispatchBookName='" + getDispatchBookName() + "'" +
            ", dispatchBookCode='" + getDispatchBookCode() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", dispatchBookTypeName='" + getDispatchBookTypeName() + "'" +
            ", dispatchBookTypeCode='" + getDispatchBookTypeCode() + "'" +
            ", totalDoc=" + getTotalDoc() +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDDate='" + getModifiedDDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", organization=" + getOrganization() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", dispatchBookOrgs=" + getDispatchBookOrgs() +
            "}";
    }
}
