package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.vsm.business.domain.ManageStampInfo} entity.
 */
public class ManageStampInfoDTO implements Serializable {

    private Long id;

    private String content;

    private Long copiesNumber;

    private String stampName;

    private String stampCode;

    private String stamperName;

    private String storageCode;

    private String storageLocation;

    private String storagePosition;

    private Instant expiredDateStorage;

    private Instant stampDate;

    private Instant expiredDateReturn;

    private Instant returnDate;

    private String orgReturnName;

    private Instant receiveDate;

    private Instant submitSignDate;

    private String status;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private RequestDataDTO requestData;

    private StampDTO stamp;

    private OrganizationDTO orgReturn;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private Set<OrganizationDTO> orgStorages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCopiesNumber() {
        return copiesNumber;
    }

    public void setCopiesNumber(Long copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public String getStampName() {
        return stampName;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getStampCode() {
        return stampCode;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public String getStamperName() {
        return stamperName;
    }

    public void setStamperName(String stamperName) {
        this.stamperName = stamperName;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getStoragePosition() {
        return storagePosition;
    }

    public void setStoragePosition(String storagePosition) {
        this.storagePosition = storagePosition;
    }

    public Instant getExpiredDateStorage() {
        return expiredDateStorage;
    }

    public void setExpiredDateStorage(Instant expiredDateStorage) {
        this.expiredDateStorage = expiredDateStorage;
    }

    public Instant getStampDate() {
        return stampDate;
    }

    public void setStampDate(Instant stampDate) {
        this.stampDate = stampDate;
    }

    public Instant getExpiredDateReturn() {
        return expiredDateReturn;
    }

    public void setExpiredDateReturn(Instant expiredDateReturn) {
        this.expiredDateReturn = expiredDateReturn;
    }

    public Instant getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Instant returnDate) {
        this.returnDate = returnDate;
    }

    public String getOrgReturnName() {
        return orgReturnName;
    }

    public void setOrgReturnName(String orgReturnName) {
        this.orgReturnName = orgReturnName;
    }

    public Instant getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Instant getSubmitSignDate() {
        return submitSignDate;
    }

    public void setSubmitSignDate(Instant submitSignDate) {
        this.submitSignDate = submitSignDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public StampDTO getStamp() {
        return stamp;
    }

    public void setStamp(StampDTO stamp) {
        this.stamp = stamp;
    }

    public OrganizationDTO getOrgReturn() {
        return orgReturn;
    }

    public void setOrgReturn(OrganizationDTO orgReturn) {
        this.orgReturn = orgReturn;
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

    public Set<OrganizationDTO> getOrgStorages() {
        return orgStorages;
    }

    public void setOrgStorages(Set<OrganizationDTO> orgStorages) {
        this.orgStorages = orgStorages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManageStampInfoDTO)) {
            return false;
        }

        ManageStampInfoDTO manageStampInfoDTO = (ManageStampInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, manageStampInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManageStampInfoDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", copiesNumber=" + getCopiesNumber() +
            ", stampName='" + getStampName() + "'" +
            ", stampCode='" + getStampCode() + "'" +
            ", stamperName='" + getStamperName() + "'" +
            ", storageCode='" + getStorageCode() + "'" +
            ", storageLocation='" + getStorageLocation() + "'" +
            ", storagePosition='" + getStoragePosition() + "'" +
            ", expiredDateStorage='" + getExpiredDateStorage() + "'" +
            ", stampDate='" + getStampDate() + "'" +
            ", expiredDateReturn='" + getExpiredDateReturn() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", orgReturnName='" + getOrgReturnName() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", submitSignDate='" + getSubmitSignDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", requestData=" + getRequestData() +
            ", stamp=" + getStamp() +
            ", orgReturn=" + getOrgReturn() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", orgStorages=" + getOrgStorages() +
            "}";
    }
}
