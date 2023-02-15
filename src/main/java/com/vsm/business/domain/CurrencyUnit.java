package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CurrencyUnit.
 */
@Entity
@Table(name = "currency_unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "currencyunit")
public class CurrencyUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_unit_name")
    private String currencyUnitName;

    @Column(name = "currency_unit_code")
    private String currencyUnitCode;

    @Column(name = "exchange_value")
    private Double exchangeValue;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "created_org_name")
    private String createdOrgName;

    @Column(name = "created_rank_name")
    private String createdRankName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leader",
            "tennant",
            "created",
            "modified",
            "roles",
            "ranks",
            "organizations",
            "userInSteps",
            "createdFields",
            "modifiedFields",
            "createdForms",
            "modifiedForms",
            "createdFieldInForms",
            "modifiedFieldInForms",
            "createdRanks",
            "modifiedRanks",
            "createdRoles",
            "modifiedRoles",
            "createdUserInfos",
            "modifiedUserInfos",
            "createdUserInSteps",
            "modifiedUserInSteps",
            "createdStepInProcesses",
            "modifiedStepInProcesses",
            "createdSteps",
            "modifiedSteps",
            "createdProcessInfos",
            "modifiedProcessInfos",
            "createdTemplateForms",
            "modifiedTemplateForms",
            "createdRequests",
            "modifiedRequests",
            "createdRequestTypes",
            "modifiedRequestTypes",
            "createdRequestGroups",
            "modifiedRequestGroups",
            "createdProcessDatas",
            "modifiedProcessDatas",
            "createdStepDatas",
            "modifiedStepDatas",
            "createdRequestDatas",
            "modifiedRequestDatas",
            "approvedRequestDatas",
            "revokedRequestDatas",
            "createdStatuses",
            "modifiedStatuses",
            "createdFormDatas",
            "modifiedFormDatas",
            "createdFieldDatas",
            "modifiedFieldDatas",
            "createdAttachmentFiles",
            "modifiedAttachmentFiles",
            "createdFileTypes",
            "modifiedFileTypes",
            "createdReqdataChangeHis",
            "modifiedReqdataChangeHis",
            "createdCategoryDatas",
            "modifiedCategoryDatas",
            "createdCategoryGroups",
            "modifiedCategoryGroups",
            "signatureInfomations",
            "attachmentPermisitions",
            "createdAttachmentPermisions",
            "modifiedAttachmentPermisions",
            "signData",
            "requestData",
            "stepData",
            "userGroups",
            "offDispatchUserReads",
            "receiversHandles",
        },
        allowSetters = true
    )
    private UserInfo creater;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leader",
            "tennant",
            "created",
            "modified",
            "roles",
            "ranks",
            "organizations",
            "userInSteps",
            "createdFields",
            "modifiedFields",
            "createdForms",
            "modifiedForms",
            "createdFieldInForms",
            "modifiedFieldInForms",
            "createdRanks",
            "modifiedRanks",
            "createdRoles",
            "modifiedRoles",
            "createdUserInfos",
            "modifiedUserInfos",
            "createdUserInSteps",
            "modifiedUserInSteps",
            "createdStepInProcesses",
            "modifiedStepInProcesses",
            "createdSteps",
            "modifiedSteps",
            "createdProcessInfos",
            "modifiedProcessInfos",
            "createdTemplateForms",
            "modifiedTemplateForms",
            "createdRequests",
            "modifiedRequests",
            "createdRequestTypes",
            "modifiedRequestTypes",
            "createdRequestGroups",
            "modifiedRequestGroups",
            "createdProcessDatas",
            "modifiedProcessDatas",
            "createdStepDatas",
            "modifiedStepDatas",
            "createdRequestDatas",
            "modifiedRequestDatas",
            "approvedRequestDatas",
            "revokedRequestDatas",
            "createdStatuses",
            "modifiedStatuses",
            "createdFormDatas",
            "modifiedFormDatas",
            "createdFieldDatas",
            "modifiedFieldDatas",
            "createdAttachmentFiles",
            "modifiedAttachmentFiles",
            "createdFileTypes",
            "modifiedFileTypes",
            "createdReqdataChangeHis",
            "modifiedReqdataChangeHis",
            "createdCategoryDatas",
            "modifiedCategoryDatas",
            "createdCategoryGroups",
            "modifiedCategoryGroups",
            "signatureInfomations",
            "attachmentPermisitions",
            "createdAttachmentPermisions",
            "modifiedAttachmentPermisions",
            "signData",
            "requestData",
            "stepData",
            "userGroups",
            "offDispatchUserReads",
            "receiversHandles",
        },
        allowSetters = true
    )
    private UserInfo modifier;

    @OneToMany(mappedBy = "currencyUnit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "currencyUnit", "goodServiceType", "creater", "modifier", "priceInfos" }, allowSetters = true)
    private Set<GoodService> goodServices = new HashSet<>();

    @OneToMany(mappedBy = "currencyUnit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vendor", "goodService", "currencyUnit", "creater", "modifier" }, allowSetters = true)
    private Set<PriceInfo> priceInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CurrencyUnit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyUnitName() {
        return this.currencyUnitName;
    }

    public CurrencyUnit currencyUnitName(String currencyUnitName) {
        this.setCurrencyUnitName(currencyUnitName);
        return this;
    }

    public void setCurrencyUnitName(String currencyUnitName) {
        this.currencyUnitName = currencyUnitName;
    }

    public String getCurrencyUnitCode() {
        return this.currencyUnitCode;
    }

    public CurrencyUnit currencyUnitCode(String currencyUnitCode) {
        this.setCurrencyUnitCode(currencyUnitCode);
        return this;
    }

    public void setCurrencyUnitCode(String currencyUnitCode) {
        this.currencyUnitCode = currencyUnitCode;
    }

    public Double getExchangeValue() {
        return this.exchangeValue;
    }

    public CurrencyUnit exchangeValue(Double exchangeValue) {
        this.setExchangeValue(exchangeValue);
        return this;
    }

    public void setExchangeValue(Double exchangeValue) {
        this.exchangeValue = exchangeValue;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public CurrencyUnit createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public CurrencyUnit createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public CurrencyUnit createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CurrencyUnit createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public CurrencyUnit modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public CurrencyUnit modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public CurrencyUnit isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public CurrencyUnit isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public CurrencyUnit creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public CurrencyUnit modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public Set<GoodService> getGoodServices() {
        return this.goodServices;
    }

    public void setGoodServices(Set<GoodService> goodServices) {
        if (this.goodServices != null) {
            this.goodServices.forEach(i -> i.setCurrencyUnit(null));
        }
        if (goodServices != null) {
            goodServices.forEach(i -> i.setCurrencyUnit(this));
        }
        this.goodServices = goodServices;
    }

    public CurrencyUnit goodServices(Set<GoodService> goodServices) {
        this.setGoodServices(goodServices);
        return this;
    }

    public CurrencyUnit addGoodService(GoodService goodService) {
        this.goodServices.add(goodService);
        goodService.setCurrencyUnit(this);
        return this;
    }

    public CurrencyUnit removeGoodService(GoodService goodService) {
        this.goodServices.remove(goodService);
        goodService.setCurrencyUnit(null);
        return this;
    }

    public Set<PriceInfo> getPriceInfos() {
        return this.priceInfos;
    }

    public void setPriceInfos(Set<PriceInfo> priceInfos) {
        if (this.priceInfos != null) {
            this.priceInfos.forEach(i -> i.setCurrencyUnit(null));
        }
        if (priceInfos != null) {
            priceInfos.forEach(i -> i.setCurrencyUnit(this));
        }
        this.priceInfos = priceInfos;
    }

    public CurrencyUnit priceInfos(Set<PriceInfo> priceInfos) {
        this.setPriceInfos(priceInfos);
        return this;
    }

    public CurrencyUnit addPriceInfo(PriceInfo priceInfo) {
        this.priceInfos.add(priceInfo);
        priceInfo.setCurrencyUnit(this);
        return this;
    }

    public CurrencyUnit removePriceInfo(PriceInfo priceInfo) {
        this.priceInfos.remove(priceInfo);
        priceInfo.setCurrencyUnit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrencyUnit)) {
            return false;
        }
        return id != null && id.equals(((CurrencyUnit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CurrencyUnit{" +
            "id=" + getId() +
            ", currencyUnitName='" + getCurrencyUnitName() + "'" +
            ", currencyUnitCode='" + getCurrencyUnitCode() + "'" +
            ", exchangeValue=" + getExchangeValue() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
