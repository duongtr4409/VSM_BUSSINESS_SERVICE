package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.Vendor} entity.
 */
public class VendorDTO implements Serializable {

    private Long id;

    private String vendorName;

    private String vendorCode;

    private String vendorType;

    private String shortName;

    private String fullName;

    private String mst;

    private String email;

    private String fax;

    private String sdt;

    private String identification;

    private Instant issuseDate;

    private String issuseOrg;

    private String mobilePhone;

    private String businessPhones;

    private String address;

    private Boolean isSyncFromSAP;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Instant getIssuseDate() {
        return issuseDate;
    }

    public void setIssuseDate(Instant issuseDate) {
        this.issuseDate = issuseDate;
    }

    public String getIssuseOrg() {
        return issuseOrg;
    }

    public void setIssuseOrg(String issuseOrg) {
        this.issuseOrg = issuseOrg;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBusinessPhones() {
        return businessPhones;
    }

    public void setBusinessPhones(String businessPhones) {
        this.businessPhones = businessPhones;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsSyncFromSAP() {
        return isSyncFromSAP;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
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
        if (!(o instanceof VendorDTO)) {
            return false;
        }

        VendorDTO vendorDTO = (VendorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vendorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorDTO{" +
            "id=" + getId() +
            ", vendorName='" + getVendorName() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorType='" + getVendorType() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", mst='" + getMst() + "'" +
            ", email='" + getEmail() + "'" +
            ", fax='" + getFax() + "'" +
            ", sdt='" + getSdt() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", issuseDate='" + getIssuseDate() + "'" +
            ", issuseOrg='" + getIssuseOrg() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", businessPhones='" + getBusinessPhones() + "'" +
            ", address='" + getAddress() + "'" +
            ", isSyncFromSAP='" + getIsSyncFromSAP() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
