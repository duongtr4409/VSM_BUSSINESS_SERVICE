package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.StepProcessDoc} entity.
 */
public class StepProcessDocDTO implements Serializable {

    private Long id;

    private String processerName;

    private String processerAvatar;

    private String processerEmail;

    private String involveUserText;

    private String content;

    private Instant expireTime;

    private Instant transferTime;

    private String transferName;

    private String transferAvatar;

    private String transferEmail;

    private Long order;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private OfficialDispatchDTO officialDispatch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcesserName() {
        return processerName;
    }

    public void setProcesserName(String processerName) {
        this.processerName = processerName;
    }

    public String getProcesserAvatar() {
        return processerAvatar;
    }

    public void setProcesserAvatar(String processerAvatar) {
        this.processerAvatar = processerAvatar;
    }

    public String getProcesserEmail() {
        return processerEmail;
    }

    public void setProcesserEmail(String processerEmail) {
        this.processerEmail = processerEmail;
    }

    public String getInvolveUserText() {
        return involveUserText;
    }

    public void setInvolveUserText(String involveUserText) {
        this.involveUserText = involveUserText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Instant expireTime) {
        this.expireTime = expireTime;
    }

    public Instant getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Instant transferTime) {
        this.transferTime = transferTime;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getTransferAvatar() {
        return transferAvatar;
    }

    public void setTransferAvatar(String transferAvatar) {
        this.transferAvatar = transferAvatar;
    }

    public String getTransferEmail() {
        return transferEmail;
    }

    public void setTransferEmail(String transferEmail) {
        this.transferEmail = transferEmail;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StepProcessDocDTO)) {
            return false;
        }

        StepProcessDocDTO stepProcessDocDTO = (StepProcessDocDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stepProcessDocDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StepProcessDocDTO{" +
            "id=" + getId() +
            ", processerName='" + getProcesserName() + "'" +
            ", processerAvatar='" + getProcesserAvatar() + "'" +
            ", processerEmail='" + getProcesserEmail() + "'" +
            ", involveUserText='" + getInvolveUserText() + "'" +
            ", content='" + getContent() + "'" +
            ", expireTime='" + getExpireTime() + "'" +
            ", transferTime='" + getTransferTime() + "'" +
            ", transferName='" + getTransferName() + "'" +
            ", transferAvatar='" + getTransferAvatar() + "'" +
            ", transferEmail='" + getTransferEmail() + "'" +
            ", order=" + getOrder() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", officialDispatch=" + getOfficialDispatch() +
            "}";
    }
}
