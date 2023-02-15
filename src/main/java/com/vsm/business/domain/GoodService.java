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
 * A GoodService.
 */
@Entity
@Table(name = "good_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "goodservice")
public class GoodService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "good_service_name")
    private String goodServiceName;

    @Column(name = "good_service_code")
    private String goodServiceCode;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;

    @Column(name = "good_service_price")
    private Double goodServicePrice;

    @Column(name = "currency_unit_code")
    private String currencyUnitCode;

    @Column(name = "currency_unit_name")
    private String currencyUnitName;

    @Column(name = "is_sync_from_sap")
    private Boolean isSyncFromSAP;

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
    @JsonIgnoreProperties(value = { "creater", "modifier", "goodServices", "priceInfos" }, allowSetters = true)
    private CurrencyUnit currencyUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creater", "modifier", "goodServices" }, allowSetters = true)
    private GoodServiceType goodServiceType;

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

    @OneToMany(mappedBy = "goodService")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vendor", "goodService", "currencyUnit", "creater", "modifier" }, allowSetters = true)
    private Set<PriceInfo> priceInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GoodService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodServiceName() {
        return this.goodServiceName;
    }

    public GoodService goodServiceName(String goodServiceName) {
        this.setGoodServiceName(goodServiceName);
        return this;
    }

    public void setGoodServiceName(String goodServiceName) {
        this.goodServiceName = goodServiceName;
    }

    public String getGoodServiceCode() {
        return this.goodServiceCode;
    }

    public GoodService goodServiceCode(String goodServiceCode) {
        this.setGoodServiceCode(goodServiceCode);
        return this;
    }

    public void setGoodServiceCode(String goodServiceCode) {
        this.goodServiceCode = goodServiceCode;
    }

    public String getShortName() {
        return this.shortName;
    }

    public GoodService shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public GoodService fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return this.description;
    }

    public GoodService description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getGoodServicePrice() {
        return this.goodServicePrice;
    }

    public GoodService goodServicePrice(Double goodServicePrice) {
        this.setGoodServicePrice(goodServicePrice);
        return this;
    }

    public void setGoodServicePrice(Double goodServicePrice) {
        this.goodServicePrice = goodServicePrice;
    }

    public String getCurrencyUnitCode() {
        return this.currencyUnitCode;
    }

    public GoodService currencyUnitCode(String currencyUnitCode) {
        this.setCurrencyUnitCode(currencyUnitCode);
        return this;
    }

    public void setCurrencyUnitCode(String currencyUnitCode) {
        this.currencyUnitCode = currencyUnitCode;
    }

    public String getCurrencyUnitName() {
        return this.currencyUnitName;
    }

    public GoodService currencyUnitName(String currencyUnitName) {
        this.setCurrencyUnitName(currencyUnitName);
        return this;
    }

    public void setCurrencyUnitName(String currencyUnitName) {
        this.currencyUnitName = currencyUnitName;
    }

    public Boolean getIsSyncFromSAP() {
        return this.isSyncFromSAP;
    }

    public GoodService isSyncFromSAP(Boolean isSyncFromSAP) {
        this.setIsSyncFromSAP(isSyncFromSAP);
        return this;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public GoodService createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public GoodService createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public GoodService createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public GoodService createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public GoodService modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public GoodService modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public GoodService isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public GoodService isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public CurrencyUnit getCurrencyUnit() {
        return this.currencyUnit;
    }

    public void setCurrencyUnit(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public GoodService currencyUnit(CurrencyUnit currencyUnit) {
        this.setCurrencyUnit(currencyUnit);
        return this;
    }

    public GoodServiceType getGoodServiceType() {
        return this.goodServiceType;
    }

    public void setGoodServiceType(GoodServiceType goodServiceType) {
        this.goodServiceType = goodServiceType;
    }

    public GoodService goodServiceType(GoodServiceType goodServiceType) {
        this.setGoodServiceType(goodServiceType);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public GoodService creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public GoodService modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public Set<PriceInfo> getPriceInfos() {
        return this.priceInfos;
    }

    public void setPriceInfos(Set<PriceInfo> priceInfos) {
        if (this.priceInfos != null) {
            this.priceInfos.forEach(i -> i.setGoodService(null));
        }
        if (priceInfos != null) {
            priceInfos.forEach(i -> i.setGoodService(this));
        }
        this.priceInfos = priceInfos;
    }

    public GoodService priceInfos(Set<PriceInfo> priceInfos) {
        this.setPriceInfos(priceInfos);
        return this;
    }

    public GoodService addPriceInfo(PriceInfo priceInfo) {
        this.priceInfos.add(priceInfo);
        priceInfo.setGoodService(this);
        return this;
    }

    public GoodService removePriceInfo(PriceInfo priceInfo) {
        this.priceInfos.remove(priceInfo);
        priceInfo.setGoodService(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodService)) {
            return false;
        }
        return id != null && id.equals(((GoodService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodService{" +
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
            "}";
    }
}
