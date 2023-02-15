package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;

/**
 * A DTO for the {@link com.vsm.business.domain.Role} entity.
 */
public class RoleDTO implements Serializable {

    private Long id;

    private String roleCode;

    private String roleName;

    private String roleType;

    private Boolean isHaveView;

    private Boolean isHaveEdit;

    private Boolean isHaveDelete;

    private Boolean isHaveDownload;

    private Boolean isAutoAdd;

    private String requestList;

    private String organizationList;

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

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private Set<FeatureDTO> features = new HashSet<>();
	
	private List<UserInfoDTO> userInfoDTOList;
    private List<UserGroupDTO> userGroupDTOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Boolean getIsHaveView() {
        return isHaveView;
    }

    public void setIsHaveView(Boolean isHaveView) {
        this.isHaveView = isHaveView;
    }

    public Boolean getIsHaveEdit() {
        return isHaveEdit;
    }

    public void setIsHaveEdit(Boolean isHaveEdit) {
        this.isHaveEdit = isHaveEdit;
    }

    public Boolean getIsHaveDelete() {
        return isHaveDelete;
    }

    public void setIsHaveDelete(Boolean isHaveDelete) {
        this.isHaveDelete = isHaveDelete;
    }

    public Boolean getIsHaveDownload() {
        return isHaveDownload;
    }

    public void setIsHaveDownload(Boolean isHaveDownload) {
        this.isHaveDownload = isHaveDownload;
    }

    public Boolean getIsAutoAdd() {
        return isAutoAdd;
    }

    public void setIsAutoAdd(Boolean isAutoAdd) {
        this.isAutoAdd = isAutoAdd;
    }

    public String getRequestList() {
        return requestList;
    }

    public void setRequestList(String requestList) {
        this.requestList = requestList;
    }

    public String getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(String organizationList) {
        this.organizationList = organizationList;
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

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    public UserInfoDTO getCreated() {
        return created;
    }

    public void setCreated(UserInfoDTO created) {
        this.created = created;
    }

    public UserInfoDTO getModified() {
        return modified;
    }

    public void setModified(UserInfoDTO modified) {
        this.modified = modified;
    }

    public Set<FeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(Set<FeatureDTO> features) {
        this.features = features;
    }

    public List<UserInfoDTO> getUserInfoDTOList() {
        return userInfoDTOList;
    }

    public void setUserInfoDTOList(List<UserInfoDTO> userInfoDTOList) {
        this.userInfoDTOList = userInfoDTOList;
    }

    public List<UserGroupDTO> getUserGroupDTOList() {
        return userGroupDTOList;
    }

    public void setUserGroupDTOList(List<UserGroupDTO> userGroupDTOList) {
        this.userGroupDTOList = userGroupDTOList;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleDTO)) {
            return false;
        }

        RoleDTO roleDTO = (RoleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", roleCode='" + getRoleCode() + "'" +
            ", roleName='" + getRoleName() + "'" +
            ", roleType='" + getRoleType() + "'" +
            ", isHaveView='" + getIsHaveView() + "'" +
            ", isHaveEdit='" + getIsHaveEdit() + "'" +
            ", isHaveDelete='" + getIsHaveDelete() + "'" +
            ", isHaveDownload='" + getIsHaveDownload() + "'" +
            ", isAutoAdd='" + getIsAutoAdd() + "'" +
            ", requestList='" + getRequestList() + "'" +
            ", organizationList='" + getOrganizationList() + "'" +
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
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", features=" + getFeatures() +
            "}";
    }
}
