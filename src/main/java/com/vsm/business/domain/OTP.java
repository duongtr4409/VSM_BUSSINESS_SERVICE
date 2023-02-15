package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OTP.
 */
@Entity
@Table(name = "otp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "otp")
public class OTP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "o_tp_code")
    private String oTPCode;

    @Column(name = "link")
    private String link;

    @Column(name = "status")
    private String status;

    @Column(name = "number_view")
    private Long numberView;

    @Column(name = "number_print")
    private Long numberPrint;

    @Column(name = "number_download")
    private Long numberDownload;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "number_fail")
    private Long numberFail;

    @Column(name = "number_generate")
    private Long numberGenerate;

    @Column(name = "is_customer_sign")
    private Boolean isCustomerSign;

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

    @Column(name = "modifiedate")
    private Instant modifiedate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "request",
            "status",
            "tennant",
            "requestType",
            "requestGroup",
            "organization",
            "created",
            "modified",
            "subStatus",
            "reqDataConcerned",
            "approver",
            "revoker",
            "userInfos",
            "formData",
            "attachmentFiles",
            "reqdataProcessHis",
            "reqdataChangeHis",
            "processData",
            "stepData",
            "fieldData",
            "informationInExchanges",
            "tagInExchanges",
            "requestRecalls",
            "oTPS",
            "signData",
            "manageStampInfos",
        },
        allowSetters = true
    )
    private RequestData requestData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requestData", "signatureInfomation", "userInfo", "oTPS" }, allowSetters = true)
    private SignData signData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OTP id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getoTPCode() {
        return this.oTPCode;
    }

    public OTP oTPCode(String oTPCode) {
        this.setoTPCode(oTPCode);
        return this;
    }

    public void setoTPCode(String oTPCode) {
        this.oTPCode = oTPCode;
    }

    public String getLink() {
        return this.link;
    }

    public OTP link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return this.status;
    }

    public OTP status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getNumberView() {
        return this.numberView;
    }

    public OTP numberView(Long numberView) {
        this.setNumberView(numberView);
        return this;
    }

    public void setNumberView(Long numberView) {
        this.numberView = numberView;
    }

    public Long getNumberPrint() {
        return this.numberPrint;
    }

    public OTP numberPrint(Long numberPrint) {
        this.setNumberPrint(numberPrint);
        return this;
    }

    public void setNumberPrint(Long numberPrint) {
        this.numberPrint = numberPrint;
    }

    public Long getNumberDownload() {
        return this.numberDownload;
    }

    public OTP numberDownload(Long numberDownload) {
        this.setNumberDownload(numberDownload);
        return this;
    }

    public void setNumberDownload(Long numberDownload) {
        this.numberDownload = numberDownload;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public OTP expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getNumberFail() {
        return this.numberFail;
    }

    public OTP numberFail(Long numberFail) {
        this.setNumberFail(numberFail);
        return this;
    }

    public void setNumberFail(Long numberFail) {
        this.numberFail = numberFail;
    }

    public Long getNumberGenerate() {
        return this.numberGenerate;
    }

    public OTP numberGenerate(Long numberGenerate) {
        this.setNumberGenerate(numberGenerate);
        return this;
    }

    public void setNumberGenerate(Long numberGenerate) {
        this.numberGenerate = numberGenerate;
    }

    public Boolean getIsCustomerSign() {
        return this.isCustomerSign;
    }

    public OTP isCustomerSign(Boolean isCustomerSign) {
        this.setIsCustomerSign(isCustomerSign);
        return this;
    }

    public void setIsCustomerSign(Boolean isCustomerSign) {
        this.isCustomerSign = isCustomerSign;
    }

    public String getDescription() {
        return this.description;
    }

    public OTP description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public OTP createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public OTP createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public OTP createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OTP createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public OTP modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedate() {
        return this.modifiedate;
    }

    public OTP modifiedate(Instant modifiedate) {
        this.setModifiedate(modifiedate);
        return this;
    }

    public void setModifiedate(Instant modifiedate) {
        this.modifiedate = modifiedate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public OTP isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public OTP isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public OTP requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public SignData getSignData() {
        return this.signData;
    }

    public void setSignData(SignData signData) {
        this.signData = signData;
    }

    public OTP signData(SignData signData) {
        this.setSignData(signData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTP)) {
            return false;
        }
        return id != null && id.equals(((OTP) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTP{" +
            "id=" + getId() +
            ", oTPCode='" + getoTPCode() + "'" +
            ", link='" + getLink() + "'" +
            ", status='" + getStatus() + "'" +
            ", numberView=" + getNumberView() +
            ", numberPrint=" + getNumberPrint() +
            ", numberDownload=" + getNumberDownload() +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", numberFail=" + getNumberFail() +
            ", numberGenerate=" + getNumberGenerate() +
            ", isCustomerSign='" + getIsCustomerSign() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedate='" + getModifiedate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
