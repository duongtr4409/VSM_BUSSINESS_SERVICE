package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.BusinessPartner} entity.
 */
public class BusinessPartnerDTO implements Serializable {

    private Long id;

    private String maChuoi;

    private String tenChuoi;

    private String addressNumber;

    private String customer;

    private String name;

    private String street;

    private String telephone;

    private String vatTegistrationNo;

    private String eMailAddress1;

    private String eMailAddress2;

    private Boolean isSyncFromSAP;

    private Instant timeSync;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private BusinessPartnerTypeDTO businessPartnerType;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaChuoi() {
        return maChuoi;
    }

    public void setMaChuoi(String maChuoi) {
        this.maChuoi = maChuoi;
    }

    public String getTenChuoi() {
        return tenChuoi;
    }

    public void setTenChuoi(String tenChuoi) {
        this.tenChuoi = tenChuoi;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVatTegistrationNo() {
        return vatTegistrationNo;
    }

    public void setVatTegistrationNo(String vatTegistrationNo) {
        this.vatTegistrationNo = vatTegistrationNo;
    }

    public String geteMailAddress1() {
        return eMailAddress1;
    }

    public void seteMailAddress1(String eMailAddress1) {
        this.eMailAddress1 = eMailAddress1;
    }

    public String geteMailAddress2() {
        return eMailAddress2;
    }

    public void seteMailAddress2(String eMailAddress2) {
        this.eMailAddress2 = eMailAddress2;
    }

    public Boolean getIsSyncFromSAP() {
        return isSyncFromSAP;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public Instant getTimeSync() {
        return timeSync;
    }

    public void setTimeSync(Instant timeSync) {
        this.timeSync = timeSync;
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

    public BusinessPartnerTypeDTO getBusinessPartnerType() {
        return businessPartnerType;
    }

    public void setBusinessPartnerType(BusinessPartnerTypeDTO businessPartnerType) {
        this.businessPartnerType = businessPartnerType;
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
        if (!(o instanceof BusinessPartnerDTO)) {
            return false;
        }

        BusinessPartnerDTO businessPartnerDTO = (BusinessPartnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, businessPartnerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessPartnerDTO{" +
            "id=" + getId() +
            ", maChuoi='" + getMaChuoi() + "'" +
            ", tenChuoi='" + getTenChuoi() + "'" +
            ", addressNumber='" + getAddressNumber() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", name='" + getName() + "'" +
            ", street='" + getStreet() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", vatTegistrationNo='" + getVatTegistrationNo() + "'" +
            ", eMailAddress1='" + geteMailAddress1() + "'" +
            ", eMailAddress2='" + geteMailAddress2() + "'" +
            ", isSyncFromSAP='" + getIsSyncFromSAP() + "'" +
            ", timeSync='" + getTimeSync() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", businessPartnerType=" + getBusinessPartnerType() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
