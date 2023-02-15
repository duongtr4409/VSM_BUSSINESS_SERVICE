package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.GoodService} entity.
 */
public class GoodServiceDTO implements Serializable {

    private Long id;

    private String goodServiceName;

    private String goodServiceCode;

    private String shortName;

    private String fullName;

    private String description;

    private Double goodServicePrice;

    private String currencyUnitCode;

    private String currencyUnitName;

    private Boolean isSyncFromSAP;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private CurrencyUnitDTO currencyUnit;

    private GoodServiceTypeDTO goodServiceType;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodServiceName() {
        return goodServiceName;
    }

    public void setGoodServiceName(String goodServiceName) {
        this.goodServiceName = goodServiceName;
    }

    public String getGoodServiceCode() {
        return goodServiceCode;
    }

    public void setGoodServiceCode(String goodServiceCode) {
        this.goodServiceCode = goodServiceCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGoodServicePrice() {
        return goodServicePrice;
    }

    public void setGoodServicePrice(Double goodServicePrice) {
        this.goodServicePrice = goodServicePrice;
    }

    public String getCurrencyUnitCode() {
        return currencyUnitCode;
    }

    public void setCurrencyUnitCode(String currencyUnitCode) {
        this.currencyUnitCode = currencyUnitCode;
    }

    public String getCurrencyUnitName() {
        return currencyUnitName;
    }

    public void setCurrencyUnitName(String currencyUnitName) {
        this.currencyUnitName = currencyUnitName;
    }

    public Boolean getIsSyncFromSAP() {
        return isSyncFromSAP;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
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

    public CurrencyUnitDTO getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnitDTO currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public GoodServiceTypeDTO getGoodServiceType() {
        return goodServiceType;
    }

    public void setGoodServiceType(GoodServiceTypeDTO goodServiceType) {
        this.goodServiceType = goodServiceType;
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
        if (!(o instanceof GoodServiceDTO)) {
            return false;
        }

        GoodServiceDTO goodServiceDTO = (GoodServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, goodServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodServiceDTO{" +
            "id=" + getId() +
            ", goodServiceName='" + getGoodServiceName() + "'" +
            ", goodServiceCode='" + getGoodServiceCode() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", description='" + getDescription() + "'" +
            ", goodServicePrice=" + getGoodServicePrice() +
            ", currencyUnitCode='" + getCurrencyUnitCode() + "'" +
            ", currencyUnitName='" + getCurrencyUnitName() + "'" +
            ", isSyncFromSAP='" + getIsSyncFromSAP() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", currencyUnit=" + getCurrencyUnit() +
            ", goodServiceType=" + getGoodServiceType() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
