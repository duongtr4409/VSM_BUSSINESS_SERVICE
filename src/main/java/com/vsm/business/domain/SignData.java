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
 * A SignData.
 */
@Entity
@Table(name = "sign_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "signdata")
public class SignData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sign_name")
    private String signName;

    @Column(name = "rank_name")
    private String rankName;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "address")
    private String address;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "signature")
    private String signature;

    @Column(name = "sign_date")
    private Instant signDate;

    @Column(name = "sign_type_name")
    private String signTypeName;

    @Column(name = "sign_type_code")
    private String signTypeCode;

    @Column(name = "number_view")
    private Long numberView;

    @Column(name = "number_print")
    private Long numberPrint;

    @Column(name = "number_sign")
    private Long numberSign;

    @Column(name = "number_download")
    private Long numberDownload;

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
    @JsonIgnoreProperties(value = { "userInfo", "signData" }, allowSetters = true)
    private SignatureInfomation signatureInfomation;

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

    @OneToMany(mappedBy = "signData")
    @JsonIgnoreProperties(value = { "requestData", "signData" }, allowSetters = true)
    private Set<OTP> oTPS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SignData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignName() {
        return this.signName;
    }

    public SignData signName(String signName) {
        this.setSignName(signName);
        return this;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRankName() {
        return this.rankName;
    }

    public SignData rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public SignData orgName(String orgName) {
        this.setOrgName(orgName);
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEmail() {
        return this.email;
    }

    public SignData email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public SignData phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public SignData status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return this.address;
    }

    public SignData address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return this.signature;
    }

    public SignData signature(String signature) {
        this.setSignature(signature);
        return this;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Instant getSignDate() {
        return this.signDate;
    }

    public SignData signDate(Instant signDate) {
        this.setSignDate(signDate);
        return this;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public String getSignTypeName() {
        return this.signTypeName;
    }

    public SignData signTypeName(String signTypeName) {
        this.setSignTypeName(signTypeName);
        return this;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTypeCode() {
        return this.signTypeCode;
    }

    public SignData signTypeCode(String signTypeCode) {
        this.setSignTypeCode(signTypeCode);
        return this;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public Long getNumberView() {
        return this.numberView;
    }

    public SignData numberView(Long numberView) {
        this.setNumberView(numberView);
        return this;
    }

    public void setNumberView(Long numberView) {
        this.numberView = numberView;
    }

    public Long getNumberPrint() {
        return this.numberPrint;
    }

    public SignData numberPrint(Long numberPrint) {
        this.setNumberPrint(numberPrint);
        return this;
    }

    public void setNumberPrint(Long numberPrint) {
        this.numberPrint = numberPrint;
    }

    public Long getNumberSign() {
        return this.numberSign;
    }

    public SignData numberSign(Long numberSign) {
        this.setNumberSign(numberSign);
        return this;
    }

    public void setNumberSign(Long numberSign) {
        this.numberSign = numberSign;
    }

    public Long getNumberDownload() {
        return this.numberDownload;
    }

    public SignData numberDownload(Long numberDownload) {
        this.setNumberDownload(numberDownload);
        return this;
    }

    public void setNumberDownload(Long numberDownload) {
        this.numberDownload = numberDownload;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public SignData createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public SignData createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public SignData createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public SignData createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public SignData modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedate() {
        return this.modifiedate;
    }

    public SignData modifiedate(Instant modifiedate) {
        this.setModifiedate(modifiedate);
        return this;
    }

    public void setModifiedate(Instant modifiedate) {
        this.modifiedate = modifiedate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SignData isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public SignData isDelete(Boolean isDelete) {
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

    public SignData requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public SignatureInfomation getSignatureInfomation() {
        return this.signatureInfomation;
    }

    public void setSignatureInfomation(SignatureInfomation signatureInfomation) {
        this.signatureInfomation = signatureInfomation;
    }

    public SignData signatureInfomation(SignatureInfomation signatureInfomation) {
        this.setSignatureInfomation(signatureInfomation);
        return this;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SignData userInfo(UserInfo userInfo) {
        this.setUserInfo(userInfo);
        return this;
    }

    public Set<OTP> getOTPS() {
        return this.oTPS;
    }

    public void setOTPS(Set<OTP> oTPS) {
        if (this.oTPS != null) {
            this.oTPS.forEach(i -> i.setSignData(null));
        }
        if (oTPS != null) {
            oTPS.forEach(i -> i.setSignData(this));
        }
        this.oTPS = oTPS;
    }

    public SignData oTPS(Set<OTP> oTPS) {
        this.setOTPS(oTPS);
        return this;
    }

    public SignData addOTP(OTP oTP) {
        this.oTPS.add(oTP);
        oTP.setSignData(this);
        return this;
    }

    public SignData removeOTP(OTP oTP) {
        this.oTPS.remove(oTP);
        oTP.setSignData(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignData)) {
            return false;
        }
        return id != null && id.equals(((SignData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SignData{" +
            "id=" + getId() +
            ", signName='" + getSignName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", address='" + getAddress() + "'" +
            ", signature='" + getSignature() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", numberView=" + getNumberView() +
            ", numberPrint=" + getNumberPrint() +
            ", numberSign=" + getNumberSign() +
            ", numberDownload=" + getNumberDownload() +
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
