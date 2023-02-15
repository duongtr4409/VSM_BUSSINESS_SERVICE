package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.OTP} entity.
 */
public class OTPDTO implements Serializable {

    private Long id;

    private String oTPCode;

    private String link;

    private String status;

    private Long numberView;

    private Long numberPrint;

    private Long numberDownload;

    private Instant expiryDate;

    private Long numberFail;

    private Long numberGenerate;

    private Boolean isCustomerSign;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedate;

    private Boolean isActive;

    private Boolean isDelete;

    private RequestDataDTO requestData;

    private SignDataDTO signData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getoTPCode() {
        return oTPCode;
    }

    public void setoTPCode(String oTPCode) {
        this.oTPCode = oTPCode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNumberView() {
        return numberView;
    }

    public void setNumberView(Long numberView) {
        this.numberView = numberView;
    }

    public Long getNumberPrint() {
        return numberPrint;
    }

    public void setNumberPrint(Long numberPrint) {
        this.numberPrint = numberPrint;
    }

    public Long getNumberDownload() {
        return numberDownload;
    }

    public void setNumberDownload(Long numberDownload) {
        this.numberDownload = numberDownload;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getNumberFail() {
        return numberFail;
    }

    public void setNumberFail(Long numberFail) {
        this.numberFail = numberFail;
    }

    public Long getNumberGenerate() {
        return numberGenerate;
    }

    public void setNumberGenerate(Long numberGenerate) {
        this.numberGenerate = numberGenerate;
    }

    public Boolean getIsCustomerSign() {
        return isCustomerSign;
    }

    public void setIsCustomerSign(Boolean isCustomerSign) {
        this.isCustomerSign = isCustomerSign;
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

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public SignDataDTO getSignData() {
        return signData;
    }

    public void setSignData(SignDataDTO signData) {
        this.signData = signData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTPDTO)) {
            return false;
        }

        OTPDTO oTPDTO = (OTPDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oTPDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTPDTO{" +
            "id=" + getId() +
            ", oTPCode='" + getoTPCode() + "'" +
            ", link='" + getLink() + "'" +
            ", status='" + getStatus() + "'" +
            ", numberView=" + getNumberView() +
            ", numberPrint=" + getNumberPrint() +
            ", numberDownload=" + getNumberDownload() +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", numberFail=" + getNumberFail() +
            ", numberGenerate=" + getNumberGenerate() +
            ", isCustomerSign='" + getIsCustomerSign() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedate='" + getModifiedate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", requestData=" + getRequestData() +
            ", signData=" + getSignData() +
            "}";
    }
}
