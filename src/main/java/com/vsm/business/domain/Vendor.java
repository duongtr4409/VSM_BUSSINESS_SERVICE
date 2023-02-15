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
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "vendor")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "vendor_type")
    private String vendorType;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mst")
    private String mst;

    @Column(name = "email")
    private String email;

    @Column(name = "fax")
    private String fax;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "identification")
    private String identification;

    @Column(name = "issuse_date")
    private Instant issuseDate;

    @Column(name = "issuse_org")
    private String issuseOrg;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "business_phones")
    private String businessPhones;

    @Column(name = "address")
    private String address;

    @Column(name = "is_sync_from_sap")
    private Boolean isSyncFromSAP;

    @Column(name = "description")
    private String description;

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

    @OneToMany(mappedBy = "vendor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vendor", "goodService", "currencyUnit", "creater", "modifier" }, allowSetters = true)
    private Set<PriceInfo> priceInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vendor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public Vendor vendorName(String vendorName) {
        this.setVendorName(vendorName);
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public Vendor vendorCode(String vendorCode) {
        this.setVendorCode(vendorCode);
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorType() {
        return this.vendorType;
    }

    public Vendor vendorType(String vendorType) {
        this.setVendorType(vendorType);
        return this;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Vendor shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Vendor fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMst() {
        return this.mst;
    }

    public Vendor mst(String mst) {
        this.setMst(mst);
        return this;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public String getEmail() {
        return this.email;
    }

    public Vendor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return this.fax;
    }

    public Vendor fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSdt() {
        return this.sdt;
    }

    public Vendor sdt(String sdt) {
        this.setSdt(sdt);
        return this;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getIdentification() {
        return this.identification;
    }

    public Vendor identification(String identification) {
        this.setIdentification(identification);
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Instant getIssuseDate() {
        return this.issuseDate;
    }

    public Vendor issuseDate(Instant issuseDate) {
        this.setIssuseDate(issuseDate);
        return this;
    }

    public void setIssuseDate(Instant issuseDate) {
        this.issuseDate = issuseDate;
    }

    public String getIssuseOrg() {
        return this.issuseOrg;
    }

    public Vendor issuseOrg(String issuseOrg) {
        this.setIssuseOrg(issuseOrg);
        return this;
    }

    public void setIssuseOrg(String issuseOrg) {
        this.issuseOrg = issuseOrg;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public Vendor mobilePhone(String mobilePhone) {
        this.setMobilePhone(mobilePhone);
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBusinessPhones() {
        return this.businessPhones;
    }

    public Vendor businessPhones(String businessPhones) {
        this.setBusinessPhones(businessPhones);
        return this;
    }

    public void setBusinessPhones(String businessPhones) {
        this.businessPhones = businessPhones;
    }

    public String getAddress() {
        return this.address;
    }

    public Vendor address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsSyncFromSAP() {
        return this.isSyncFromSAP;
    }

    public Vendor isSyncFromSAP(Boolean isSyncFromSAP) {
        this.setIsSyncFromSAP(isSyncFromSAP);
        return this;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public String getDescription() {
        return this.description;
    }

    public Vendor description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Vendor createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Vendor createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Vendor createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Vendor createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Vendor modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Vendor modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Vendor isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Vendor isDelete(Boolean isDelete) {
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

    public Vendor creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public Vendor modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public Set<PriceInfo> getPriceInfos() {
        return this.priceInfos;
    }

    public void setPriceInfos(Set<PriceInfo> priceInfos) {
        if (this.priceInfos != null) {
            this.priceInfos.forEach(i -> i.setVendor(null));
        }
        if (priceInfos != null) {
            priceInfos.forEach(i -> i.setVendor(this));
        }
        this.priceInfos = priceInfos;
    }

    public Vendor priceInfos(Set<PriceInfo> priceInfos) {
        this.setPriceInfos(priceInfos);
        return this;
    }

    public Vendor addPriceInfo(PriceInfo priceInfo) {
        this.priceInfos.add(priceInfo);
        priceInfo.setVendor(this);
        return this;
    }

    public Vendor removePriceInfo(PriceInfo priceInfo) {
        this.priceInfos.remove(priceInfo);
        priceInfo.setVendor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendor)) {
            return false;
        }
        return id != null && id.equals(((Vendor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vendor{" +
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
            "}";
    }
}
