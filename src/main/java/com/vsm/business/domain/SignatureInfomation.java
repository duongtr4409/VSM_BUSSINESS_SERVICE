package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A SignatureInfomation.
 */
@Entity
@Table(name = "signature_infomation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "signatureinfomation")
public class SignatureInfomation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sign_type")
    private String signType;

    @Column(name = "sign_type_code")
    private String signTypeCode;

    @Column(name = "sign_type_name")
    private String signTypeName;

    @Column(name = "seri_or_code")
    private String seriOrCode;

    @Column(name = "owner_name")
    private String ownerName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "sign_data_text")
    private String signDataText;

    @Column(name = "infomation")
    private String infomation;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "mst_or_sdt")
    private String mstOrSdt;

    @Column(name = "is_default")
    private Boolean isDefault;

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
    private UserInfo userInfo;

    @OneToMany(mappedBy = "signatureInfomation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "signatureInfomation", "userInfo" }, allowSetters = true)
    private Set<SignData> signData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SignatureInfomation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignType() {
        return this.signType;
    }

    public SignatureInfomation signType(String signType) {
        this.setSignType(signType);
        return this;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignTypeCode() {
        return this.signTypeCode;
    }

    public SignatureInfomation signTypeCode(String signTypeCode) {
        this.setSignTypeCode(signTypeCode);
        return this;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public String getSignTypeName() {
        return this.signTypeName;
    }

    public SignatureInfomation signTypeName(String signTypeName) {
        this.setSignTypeName(signTypeName);
        return this;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSeriOrCode() {
        return this.seriOrCode;
    }

    public SignatureInfomation seriOrCode(String seriOrCode) {
        this.setSeriOrCode(seriOrCode);
        return this;
    }

    public void setSeriOrCode(String seriOrCode) {
        this.seriOrCode = seriOrCode;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public SignatureInfomation ownerName(String ownerName) {
        this.setOwnerName(ownerName);
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getSignDataText() {
        return this.signDataText;
    }

    public SignatureInfomation signDataText(String signDataText) {
        this.setSignDataText(signDataText);
        return this;
    }

    public void setSignDataText(String signDataText) {
        this.signDataText = signDataText;
    }

    public String getInfomation() {
        return this.infomation;
    }

    public SignatureInfomation infomation(String infomation) {
        this.setInfomation(infomation);
        return this;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public SignatureInfomation expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMstOrSdt() {
        return this.mstOrSdt;
    }

    public SignatureInfomation mstOrSdt(String mstOrSdt) {
        this.setMstOrSdt(mstOrSdt);
        return this;
    }

    public void setMstOrSdt(String mstOrSdt) {
        this.mstOrSdt = mstOrSdt;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public SignatureInfomation isDefault(Boolean isDefault) {
        this.setIsDefault(isDefault);
        return this;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getDescription() {
        return this.description;
    }

    public SignatureInfomation description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public SignatureInfomation createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public SignatureInfomation createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public SignatureInfomation createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public SignatureInfomation createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public SignatureInfomation modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public SignatureInfomation modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SignatureInfomation isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public SignatureInfomation isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SignatureInfomation userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    public Set<SignData> getSignData() {
        return this.signData;
    }

    public void setSignData(Set<SignData> signData) {
        if (this.signData != null) {
            this.signData.forEach(i -> i.setSignatureInfomation(null));
        }
        if (signData != null) {
            signData.forEach(i -> i.setSignatureInfomation(this));
        }
        this.signData = signData;
    }

    public SignatureInfomation signData(Set<SignData> signData) {
        this.setSignData(signData);
        return this;
    }

    public SignatureInfomation addSignData(SignData signData) {
        this.signData.add(signData);
        signData.setSignatureInfomation(this);
        return this;
    }

    public SignatureInfomation removeSignData(SignData signData) {
        this.signData.remove(signData);
        signData.setSignatureInfomation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignatureInfomation)) {
            return false;
        }
        return id != null && id.equals(((SignatureInfomation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignatureInfomation{" +
            "id=" + getId() +
            ", signType='" + getSignType() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", seriOrCode='" + getSeriOrCode() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", signDataText='" + getSignDataText() + "'" +
            ", infomation='" + getInfomation() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", mstOrSdt='" + getMstOrSdt() + "'" +
            ", isDefault='" + getIsDefault() + "'" +
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
