package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.PriceInfo} entity.
 */
public class PriceInfoDTO implements Serializable {

    private Long id;

    private Double price;

    private Double retailPrice;

    private String currencyUnitName;

    private String currencyUnitCode;

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

    private VendorDTO vendor;

    private GoodServiceDTO goodService;

    private CurrencyUnitDTO currencyUnit;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCurrencyUnitName() {
        return currencyUnitName;
    }

    public void setCurrencyUnitName(String currencyUnitName) {
        this.currencyUnitName = currencyUnitName;
    }

    public String getCurrencyUnitCode() {
        return currencyUnitCode;
    }

    public void setCurrencyUnitCode(String currencyUnitCode) {
        this.currencyUnitCode = currencyUnitCode;
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

    public VendorDTO getVendor() {
        return vendor;
    }

    public void setVendor(VendorDTO vendor) {
        this.vendor = vendor;
    }

    public GoodServiceDTO getGoodService() {
        return goodService;
    }

    public void setGoodService(GoodServiceDTO goodService) {
        this.goodService = goodService;
    }

    public CurrencyUnitDTO getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnitDTO currencyUnit) {
        this.currencyUnit = currencyUnit;
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
        if (!(o instanceof PriceInfoDTO)) {
            return false;
        }

        PriceInfoDTO priceInfoDTO = (PriceInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, priceInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PriceInfoDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", retailPrice=" + getRetailPrice() +
            ", currencyUnitName='" + getCurrencyUnitName() + "'" +
            ", currencyUnitCode='" + getCurrencyUnitCode() + "'" +
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
            ", vendor=" + getVendor() +
            ", goodService=" + getGoodService() +
            ", currencyUnit=" + getCurrencyUnit() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
