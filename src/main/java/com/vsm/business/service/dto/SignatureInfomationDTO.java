package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.SignatureInfomation} entity.
 */
public class SignatureInfomationDTO implements Serializable {

    private Long id;

    private String signType;

    private String signTypeCode;

    private String signTypeName;

    private String seriOrCode;

    private String ownerName;

    @Lob
    private String signDataText;

    private String infomation;

    private Instant expiryDate;

    private String mstOrSdt;

    private Boolean isDefault;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private UserInfoDTO userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignTypeCode() {
        return signTypeCode;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public String getSignTypeName() {
        return signTypeName;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSeriOrCode() {
        return seriOrCode;
    }

    public void setSeriOrCode(String seriOrCode) {
        this.seriOrCode = seriOrCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getSignDataText() {
        return signDataText;
    }

    public void setSignDataText(String signDataText) {
        this.signDataText = signDataText;
    }

    public String getInfomation() {
        return infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMstOrSdt() {
        return mstOrSdt;
    }

    public void setMstOrSdt(String mstOrSdt) {
        this.mstOrSdt = mstOrSdt;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignatureInfomationDTO)) {
            return false;
        }

        SignatureInfomationDTO signatureInfomationDTO = (SignatureInfomationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, signatureInfomationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignatureInfomationDTO{" +
            "id=" + getId() +
            ", signType='" + getSignType() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", seriOrCode='" + getSeriOrCode() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", signDataText='" + getSignDataText() + "'" +
            ", infomation='" + getInfomation() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", mstOrSdt='" + getMstOrSdt() + "'" +
            ", isDefault='" + getIsDefault() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", userInfo=" + getUserInfo() +
            "}";
    }
}
