package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.SignData} entity.
 */
public class SignDataDTO implements Serializable {

    private Long id;

    private String signName;

    private String rankName;

    private String orgName;

    private String email;

    private String phoneNumber;

    private String status;

    private String address;

    @Lob
    private String signature;

    private Instant signDate;

    private String signTypeName;

    private String signTypeCode;

    private Long numberView;

    private Long numberPrint;

    private Long numberSign;

    private Long numberDownload;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedate;

    private Boolean isActive;

    private Boolean isDelete;

    private RequestDataDTO requestData;

    private SignatureInfomationDTO signatureInfomation;

    private UserInfoDTO userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Instant getSignDate() {
        return signDate;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public String getSignTypeName() {
        return signTypeName;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTypeCode() {
        return signTypeCode;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
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

    public Long getNumberSign() {
        return numberSign;
    }

    public void setNumberSign(Long numberSign) {
        this.numberSign = numberSign;
    }

    public Long getNumberDownload() {
        return numberDownload;
    }

    public void setNumberDownload(Long numberDownload) {
        this.numberDownload = numberDownload;
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

    public SignatureInfomationDTO getSignatureInfomation() {
        return signatureInfomation;
    }

    public void setSignatureInfomation(SignatureInfomationDTO signatureInfomation) {
        this.signatureInfomation = signatureInfomation;
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
        if (!(o instanceof SignDataDTO)) {
            return false;
        }

        SignDataDTO signDataDTO = (SignDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, signDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignDataDTO{" +
            "id=" + getId() +
            ", signName='" + getSignName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", address='" + getAddress() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", numberView=" + getNumberView() +
            ", numberPrint=" + getNumberPrint() +
            ", numberSign=" + getNumberSign() +
            ", numberDownload=" + getNumberDownload() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedate='" + getModifiedate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", requestData=" + getRequestData() +
            ", signatureInfomation=" + getSignatureInfomation() +
            ", userInfo=" + getUserInfo() +
            "}";
    }
}
