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
 * A ManageStampInfo.
 */
@Entity
@Table(name = "manage_stamp_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "managestampinfo")
public class ManageStampInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "copies_number")
    private Long copiesNumber;

    @Column(name = "stamp_name")
    private String stampName;

    @Column(name = "stamp_code")
    private String stampCode;

    @Column(name = "stamper_name")
    private String stamperName;

    @Column(name = "storage_code")
    private String storageCode;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "storage_position")
    private String storagePosition;

    @Column(name = "expired_date_storage")
    private Instant expiredDateStorage;

    @Column(name = "stamp_date")
    private Instant stampDate;

    @Column(name = "expired_date_return")
    private Instant expiredDateReturn;

    @Column(name = "return_date")
    private Instant returnDate;

    @Column(name = "org_return_name")
    private String orgReturnName;

    @Column(name = "receive_date")
    private Instant receiveDate;

    @Column(name = "submit_sign_date")
    private Instant submitSignDate;

    @Column(name = "status")
    private String status;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

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
    @JsonIgnoreProperties(value = { "manageStampInfos" }, allowSetters = true)
    private Stamp stamp;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "orgParent",
            "leader",
            "requestData",
            "dispatchBooks",
            "transferHandles",
            "rankInOrgs",
            "roleOrganizations",
            "templateForms",
            "userInfos",
            "processInfos",
            "dispatchBookOrgs",
            "mailTemplates",
            "orgStorages",
        },
        allowSetters = true
    )
    private Organization orgReturn;

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

    @ManyToMany
    @JoinTable(
        name = "rel_manage_stamp_info__org_storage",
        joinColumns = @JoinColumn(name = "manage_stamp_info_id"),
        inverseJoinColumns = @JoinColumn(name = "org_storage_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "orgParent",
            "leader",
            "requestData",
            "dispatchBooks",
            "transferHandles",
            "rankInOrgs",
            "roleOrganizations",
            "templateForms",
            "userInfos",
            "processInfos",
            "dispatchBookOrgs",
            "mailTemplates",
            "orgStorages",
        },
        allowSetters = true
    )
    private Set<Organization> orgStorages = new HashSet<>();

    @OneToMany(mappedBy = "manageStampInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "fileType",
            "requestData",
            "tennant",
            "created",
            "modified",
            "templateForm",
            "reqdataProcessHis",
            "officialDispatch",
            "stepProcessDoc",
            "mailTemplate",
            "manageStampInfo",
            "attachmentPermisitions",
            "changeFileHistories",
        },
        allowSetters = true
    )
    private Set<AttachmentFile> attachmentFiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ManageStampInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public ManageStampInfo content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCopiesNumber() {
        return this.copiesNumber;
    }

    public ManageStampInfo copiesNumber(Long copiesNumber) {
        this.setCopiesNumber(copiesNumber);
        return this;
    }

    public void setCopiesNumber(Long copiesNumber) {
        this.copiesNumber = copiesNumber;
    }

    public String getStampName() {
        return this.stampName;
    }

    public ManageStampInfo stampName(String stampName) {
        this.setStampName(stampName);
        return this;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getStampCode() {
        return this.stampCode;
    }

    public ManageStampInfo stampCode(String stampCode) {
        this.setStampCode(stampCode);
        return this;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public String getStamperName() {
        return this.stamperName;
    }

    public ManageStampInfo stamperName(String stamperName) {
        this.setStamperName(stamperName);
        return this;
    }

    public void setStamperName(String stamperName) {
        this.stamperName = stamperName;
    }

    public String getStorageCode() {
        return this.storageCode;
    }

    public ManageStampInfo storageCode(String storageCode) {
        this.setStorageCode(storageCode);
        return this;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getStorageLocation() {
        return this.storageLocation;
    }

    public ManageStampInfo storageLocation(String storageLocation) {
        this.setStorageLocation(storageLocation);
        return this;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getStoragePosition() {
        return this.storagePosition;
    }

    public ManageStampInfo storagePosition(String storagePosition) {
        this.setStoragePosition(storagePosition);
        return this;
    }

    public void setStoragePosition(String storagePosition) {
        this.storagePosition = storagePosition;
    }

    public Instant getExpiredDateStorage() {
        return this.expiredDateStorage;
    }

    public ManageStampInfo expiredDateStorage(Instant expiredDateStorage) {
        this.setExpiredDateStorage(expiredDateStorage);
        return this;
    }

    public void setExpiredDateStorage(Instant expiredDateStorage) {
        this.expiredDateStorage = expiredDateStorage;
    }

    public Instant getStampDate() {
        return this.stampDate;
    }

    public ManageStampInfo stampDate(Instant stampDate) {
        this.setStampDate(stampDate);
        return this;
    }

    public void setStampDate(Instant stampDate) {
        this.stampDate = stampDate;
    }

    public Instant getExpiredDateReturn() {
        return this.expiredDateReturn;
    }

    public ManageStampInfo expiredDateReturn(Instant expiredDateReturn) {
        this.setExpiredDateReturn(expiredDateReturn);
        return this;
    }

    public void setExpiredDateReturn(Instant expiredDateReturn) {
        this.expiredDateReturn = expiredDateReturn;
    }

    public Instant getReturnDate() {
        return this.returnDate;
    }

    public ManageStampInfo returnDate(Instant returnDate) {
        this.setReturnDate(returnDate);
        return this;
    }

    public void setReturnDate(Instant returnDate) {
        this.returnDate = returnDate;
    }

    public String getOrgReturnName() {
        return this.orgReturnName;
    }

    public ManageStampInfo orgReturnName(String orgReturnName) {
        this.setOrgReturnName(orgReturnName);
        return this;
    }

    public void setOrgReturnName(String orgReturnName) {
        this.orgReturnName = orgReturnName;
    }

    public Instant getReceiveDate() {
        return this.receiveDate;
    }

    public ManageStampInfo receiveDate(Instant receiveDate) {
        this.setReceiveDate(receiveDate);
        return this;
    }

    public void setReceiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Instant getSubmitSignDate() {
        return this.submitSignDate;
    }

    public ManageStampInfo submitSignDate(Instant submitSignDate) {
        this.setSubmitSignDate(submitSignDate);
        return this;
    }

    public void setSubmitSignDate(Instant submitSignDate) {
        this.submitSignDate = submitSignDate;
    }

    public String getStatus() {
        return this.status;
    }

    public ManageStampInfo status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public ManageStampInfo name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public ManageStampInfo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public ManageStampInfo phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public ManageStampInfo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public ManageStampInfo createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public ManageStampInfo createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public ManageStampInfo createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ManageStampInfo createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public ManageStampInfo modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public ManageStampInfo modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ManageStampInfo isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public ManageStampInfo isDelete(Boolean isDelete) {
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

    public ManageStampInfo requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Stamp getStamp() {
        return this.stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public ManageStampInfo stamp(Stamp stamp) {
        this.setStamp(stamp);
        return this;
    }

    public Organization getOrgReturn() {
        return this.orgReturn;
    }

    public void setOrgReturn(Organization organization) {
        this.orgReturn = organization;
    }

    public ManageStampInfo orgReturn(Organization organization) {
        this.setOrgReturn(organization);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public ManageStampInfo creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public ManageStampInfo modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public Set<Organization> getOrgStorages() {
        return this.orgStorages;
    }

    public void setOrgStorages(Set<Organization> organizations) {
        this.orgStorages = organizations;
    }

    public ManageStampInfo orgStorages(Set<Organization> organizations) {
        this.setOrgStorages(organizations);
        return this;
    }

    public ManageStampInfo addOrgStorage(Organization organization) {
        this.orgStorages.add(organization);
        organization.getOrgStorages().add(this);
        return this;
    }

    public ManageStampInfo removeOrgStorage(Organization organization) {
        this.orgStorages.remove(organization);
        organization.getOrgStorages().remove(this);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setManageStampInfo(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setManageStampInfo(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public ManageStampInfo attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public ManageStampInfo addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setManageStampInfo(this);
        return this;
    }

    public ManageStampInfo removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setManageStampInfo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManageStampInfo)) {
            return false;
        }
        return id != null && id.equals(((ManageStampInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManageStampInfo{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", copiesNumber=" + getCopiesNumber() +
            ", stampName='" + getStampName() + "'" +
            ", stampCode='" + getStampCode() + "'" +
            ", stamperName='" + getStamperName() + "'" +
            ", storageCode='" + getStorageCode() + "'" +
            ", storageLocation='" + getStorageLocation() + "'" +
            ", storagePosition='" + getStoragePosition() + "'" +
            ", expiredDateStorage='" + getExpiredDateStorage() + "'" +
            ", stampDate='" + getStampDate() + "'" +
            ", expiredDateReturn='" + getExpiredDateReturn() + "'" +
            ", returnDate='" + getReturnDate() + "'" +
            ", orgReturnName='" + getOrgReturnName() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", submitSignDate='" + getSubmitSignDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
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
