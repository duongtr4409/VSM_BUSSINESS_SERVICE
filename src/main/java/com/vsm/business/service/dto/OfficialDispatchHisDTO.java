package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.OfficialDispatchHis} entity.
 */
public class OfficialDispatchHisDTO implements Serializable {

    private Long id;

    private String officialDispatchName;

    private String officialDispatchCode;

    private String officialDispatchNumber;

    private String compendium;

    private String officialDispatchTypeName;

    private String officialDispatchTypeCode;

    private String officialDispatchStatusName;

    private String officialDispatchStatusCode;

    private String releaseOrgName;

    private String releaseOrgAvatar;

    private Instant releaseDate;

    private String composeOrgName;

    private String composeOrgAvatar;

    private String placeSendName;

    private String placeSendCode;

    private String placeReceiveName;

    private String placeReceiveCode;

    private String executorName;

    private String executorOrgName;

    private String executorRankName;

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

    private OfficialDispatchDTO officialDispatch;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficialDispatchName() {
        return officialDispatchName;
    }

    public void setOfficialDispatchName(String officialDispatchName) {
        this.officialDispatchName = officialDispatchName;
    }

    public String getOfficialDispatchCode() {
        return officialDispatchCode;
    }

    public void setOfficialDispatchCode(String officialDispatchCode) {
        this.officialDispatchCode = officialDispatchCode;
    }

    public String getOfficialDispatchNumber() {
        return officialDispatchNumber;
    }

    public void setOfficialDispatchNumber(String officialDispatchNumber) {
        this.officialDispatchNumber = officialDispatchNumber;
    }

    public String getCompendium() {
        return compendium;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
    }

    public String getOfficialDispatchTypeName() {
        return officialDispatchTypeName;
    }

    public void setOfficialDispatchTypeName(String officialDispatchTypeName) {
        this.officialDispatchTypeName = officialDispatchTypeName;
    }

    public String getOfficialDispatchTypeCode() {
        return officialDispatchTypeCode;
    }

    public void setOfficialDispatchTypeCode(String officialDispatchTypeCode) {
        this.officialDispatchTypeCode = officialDispatchTypeCode;
    }

    public String getOfficialDispatchStatusName() {
        return officialDispatchStatusName;
    }

    public void setOfficialDispatchStatusName(String officialDispatchStatusName) {
        this.officialDispatchStatusName = officialDispatchStatusName;
    }

    public String getOfficialDispatchStatusCode() {
        return officialDispatchStatusCode;
    }

    public void setOfficialDispatchStatusCode(String officialDispatchStatusCode) {
        this.officialDispatchStatusCode = officialDispatchStatusCode;
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

    public String getPlaceReceiveName() {
        return placeReceiveName;
    }

    public void setPlaceReceiveName(String placeReceiveName) {
        this.placeReceiveName = placeReceiveName;
    }

    public String getPlaceReceiveCode() {
        return placeReceiveCode;
    }

    public void setPlaceReceiveCode(String placeReceiveCode) {
        this.placeReceiveCode = placeReceiveCode;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getExecutorOrgName() {
        return executorOrgName;
    }

    public void setExecutorOrgName(String executorOrgName) {
        this.executorOrgName = executorOrgName;
    }

    public String getExecutorRankName() {
        return executorRankName;
    }

    public void setExecutorRankName(String executorRankName) {
        this.executorRankName = executorRankName;
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

    public OfficialDispatchDTO getOfficialDispatch() {
        return officialDispatch;
    }

    public void setOfficialDispatch(OfficialDispatchDTO officialDispatch) {
        this.officialDispatch = officialDispatch;
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
        if (!(o instanceof OfficialDispatchHisDTO)) {
            return false;
        }

        OfficialDispatchHisDTO officialDispatchHisDTO = (OfficialDispatchHisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, officialDispatchHisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficialDispatchHisDTO{" +
            "id=" + getId() +
            ", officialDispatchName='" + getOfficialDispatchName() + "'" +
            ", officialDispatchCode='" + getOfficialDispatchCode() + "'" +
            ", officialDispatchNumber='" + getOfficialDispatchNumber() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", officialDispatchTypeName='" + getOfficialDispatchTypeName() + "'" +
            ", officialDispatchTypeCode='" + getOfficialDispatchTypeCode() + "'" +
            ", officialDispatchStatusName='" + getOfficialDispatchStatusName() + "'" +
            ", officialDispatchStatusCode='" + getOfficialDispatchStatusCode() + "'" +
            ", releaseOrgName='" + getReleaseOrgName() + "'" +
            ", releaseOrgAvatar='" + getReleaseOrgAvatar() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", composeOrgName='" + getComposeOrgName() + "'" +
            ", composeOrgAvatar='" + getComposeOrgAvatar() + "'" +
            ", placeSendName='" + getPlaceSendName() + "'" +
            ", placeSendCode='" + getPlaceSendCode() + "'" +
            ", placeReceiveName='" + getPlaceReceiveName() + "'" +
            ", placeReceiveCode='" + getPlaceReceiveCode() + "'" +
            ", executorName='" + getExecutorName() + "'" +
            ", executorOrgName='" + getExecutorOrgName() + "'" +
            ", executorRankName='" + getExecutorRankName() + "'" +
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
            ", officialDispatch=" + getOfficialDispatch() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
