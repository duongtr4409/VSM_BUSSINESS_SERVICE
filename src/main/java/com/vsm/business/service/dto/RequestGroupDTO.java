package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.RequestGroup} entity.
 */
public class RequestGroupDTO implements Serializable {

    private Long id;

    private String requestGroupCode;

    private String requestGroupName;

    //private String avatarPath = "";

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private Long numberRequestType;

    private Long numberRequest;

    private Long numberRequestData;

    private Long version;

    private String tennantCode;

    private String tennantName;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestGroupCode() {
        return requestGroupCode;
    }

    public void setRequestGroupCode(String requestGroupCode) {
        this.requestGroupCode = requestGroupCode;
    }

    public String getRequestGroupName() {
        return requestGroupName;
    }

    public void setRequestGroupName(String requestGroupName) {
        this.requestGroupName = requestGroupName;
    }

//    public String getAvatarPath() {
//        return avatarPath;
//    }
//
//    public void setAvatarPath(String avatarPath) {
//        this.avatarPath = avatarPath;
//    }

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

    public Long getNumberRequestType() {
        return numberRequestType;
    }

    public void setNumberRequestType(Long numberRequestType) {
        this.numberRequestType = numberRequestType;
    }

    public Long getNumberRequest() {
        return numberRequest;
    }

    public void setNumberRequest(Long numberRequest) {
        this.numberRequest = numberRequest;
    }

    public Long getNumberRequestData() {
        return numberRequestData;
    }

    public void setNumberRequestData(Long numberRequestData) {
        this.numberRequestData = numberRequestData;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestGroupDTO)) {
            return false;
        }

        RequestGroupDTO requestGroupDTO = (RequestGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestGroupDTO{" +
            "id=" + getId() +
            ", requestGroupCode='" + getRequestGroupCode() + "'" +
            ", requestGroupName='" + getRequestGroupName() + "'" +
//            ", avatarPath='" + getAvatarPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", numberRequestType=" + getNumberRequestType() +
            ", numberRequest=" + getNumberRequest() +
            ", numberRequestData=" + getNumberRequestData() +
            ", version=" + getVersion() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            "}";
    }
}
