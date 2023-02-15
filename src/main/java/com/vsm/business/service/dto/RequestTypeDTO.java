package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.RequestType} entity.
 */
public class RequestTypeDTO implements Serializable {

    private Long id;

    private String requestTypeCode;

    private String requestTypeName;

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

    private Long numberRequest;

    private Long numberRequestData;

    private Long version;

    private String tennantCode;

    private String tennantName;

    private RequestGroupDTO requestGroup;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
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

    public RequestGroupDTO getRequestGroup() {
        return requestGroup;
    }

    public void setRequestGroup(RequestGroupDTO requestGroup) {
        this.requestGroup = requestGroup;
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
        if (!(o instanceof RequestTypeDTO)) {
            return false;
        }

        RequestTypeDTO requestTypeDTO = (RequestTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestTypeDTO{" +
            "id=" + getId() +
            ", requestTypeCode='" + getRequestTypeCode() + "'" +
            ", requestTypeName='" + getRequestTypeName() + "'" +
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
            ", numberRequest=" + getNumberRequest() +
            ", numberRequestData=" + getNumberRequestData() +
            ", version=" + getVersion() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", requestGroup=" + getRequestGroup() +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            "}";
    }
}
