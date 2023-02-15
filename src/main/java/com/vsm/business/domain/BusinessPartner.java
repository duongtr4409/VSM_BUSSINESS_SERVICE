package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessPartner.
 */
@Entity
@Table(name = "business_partner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "businesspartner")
public class BusinessPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ma_chuoi")
    private String maChuoi;

    @Column(name = "ten_chuoi")
    private String tenChuoi;

    @Column(name = "address_number")
    private String addressNumber;

    @Column(name = "customer")
    private String customer;

    @Column(name = "name")
    private String name;

    @Column(name = "street")
    private String street;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "vat_tegistration_no")
    private String vatTegistrationNo;

    @Column(name = "e_mail_address_1")
    private String eMailAddress1;

    @Column(name = "e_mail_address_2")
    private String eMailAddress2;

    @Column(name = "is_sync_from_sap")
    private Boolean isSyncFromSAP;

    @Column(name = "time_sync")
    private Instant timeSync;

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
    @JsonIgnoreProperties(value = { "creater", "modifier", "businessPartners" }, allowSetters = true)
    private BusinessPartnerType businessPartnerType;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessPartner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaChuoi() {
        return this.maChuoi;
    }

    public BusinessPartner maChuoi(String maChuoi) {
        this.setMaChuoi(maChuoi);
        return this;
    }

    public void setMaChuoi(String maChuoi) {
        this.maChuoi = maChuoi;
    }

    public String getTenChuoi() {
        return this.tenChuoi;
    }

    public BusinessPartner tenChuoi(String tenChuoi) {
        this.setTenChuoi(tenChuoi);
        return this;
    }

    public void setTenChuoi(String tenChuoi) {
        this.tenChuoi = tenChuoi;
    }

    public String getAddressNumber() {
        return this.addressNumber;
    }

    public BusinessPartner addressNumber(String addressNumber) {
        this.setAddressNumber(addressNumber);
        return this;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getCustomer() {
        return this.customer;
    }

    public BusinessPartner customer(String customer) {
        this.setCustomer(customer);
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getName() {
        return this.name;
    }

    public BusinessPartner name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return this.street;
    }

    public BusinessPartner street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public BusinessPartner telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVatTegistrationNo() {
        return this.vatTegistrationNo;
    }

    public BusinessPartner vatTegistrationNo(String vatTegistrationNo) {
        this.setVatTegistrationNo(vatTegistrationNo);
        return this;
    }

    public void setVatTegistrationNo(String vatTegistrationNo) {
        this.vatTegistrationNo = vatTegistrationNo;
    }

    public String geteMailAddress1() {
        return this.eMailAddress1;
    }

    public BusinessPartner eMailAddress1(String eMailAddress1) {
        this.seteMailAddress1(eMailAddress1);
        return this;
    }

    public void seteMailAddress1(String eMailAddress1) {
        this.eMailAddress1 = eMailAddress1;
    }

    public String geteMailAddress2() {
        return this.eMailAddress2;
    }

    public BusinessPartner eMailAddress2(String eMailAddress2) {
        this.seteMailAddress2(eMailAddress2);
        return this;
    }

    public void seteMailAddress2(String eMailAddress2) {
        this.eMailAddress2 = eMailAddress2;
    }

    public Boolean getIsSyncFromSAP() {
        return this.isSyncFromSAP;
    }

    public BusinessPartner isSyncFromSAP(Boolean isSyncFromSAP) {
        this.setIsSyncFromSAP(isSyncFromSAP);
        return this;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public Instant getTimeSync() {
        return this.timeSync;
    }

    public BusinessPartner timeSync(Instant timeSync) {
        this.setTimeSync(timeSync);
        return this;
    }

    public void setTimeSync(Instant timeSync) {
        this.timeSync = timeSync;
    }

    public String getDescription() {
        return this.description;
    }

    public BusinessPartner description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public BusinessPartner createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public BusinessPartner createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public BusinessPartner createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public BusinessPartner createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public BusinessPartner modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public BusinessPartner modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public BusinessPartner isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public BusinessPartner isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public BusinessPartnerType getBusinessPartnerType() {
        return this.businessPartnerType;
    }

    public void setBusinessPartnerType(BusinessPartnerType businessPartnerType) {
        this.businessPartnerType = businessPartnerType;
    }

    public BusinessPartner businessPartnerType(BusinessPartnerType businessPartnerType) {
        this.setBusinessPartnerType(businessPartnerType);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public BusinessPartner creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public BusinessPartner modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessPartner)) {
            return false;
        }
        return id != null && id.equals(((BusinessPartner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessPartner{" +
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
            "}";
    }
}
