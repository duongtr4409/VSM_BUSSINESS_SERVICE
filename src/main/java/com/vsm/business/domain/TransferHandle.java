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
 * A TransferHandle.
 */
@Entity
@Table(name = "transfer_handle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transferhandle")
public class TransferHandle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "transfer_name")
    private String transferName;

    @Column(name = "transfer_avatar")
    private String transferAvatar;

    @Column(name = "transfer_email")
    private String transferEmail;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "dispatch_book_name")
    private String dispatchBookName;

    @Column(name = "dispatch_book_code")
    private String dispatchBookCode;

    @Column(name = "expired_time")
    private Instant expiredTime;

    @Column(name = "process_date")
    private Instant processDate;

    @Column(name = "content")
    private String content;

    @Column(name = "attach_file_list")
    private String attachFileList;

    @Column(name = "vsm_order")
    private Long order;

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
    @JsonIgnoreProperties(value = { "transferHandles" }, allowSetters = true)
    private StatusTransferHandle statusTransferHandle;

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
    private Organization organization;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "organization", "creater", "modifier", "dispatchBookOrgs", "officialDispatches", "transferHandles" },
        allowSetters = true
    )
    private DispatchBook dispatchBook;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "nextStep",
            "processData",
            "requestData",
            "tennant",
            "created",
            "modified",
            "stepInProcess",
            "rank",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInfos",
            "previousStep",
            "reqdataProcessHis",
            "examines",
            "consults",
            "attachmentInSteps",
            "requestRecalls",
            "transferHandles",
            "resultOfSteps",
        },
        allowSetters = true
    )
    private StepData stepData;

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
    private UserInfo transfer;

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
        name = "rel_transfer_handle__receivers_handle",
        joinColumns = @JoinColumn(name = "transfer_handle_id"),
        inverseJoinColumns = @JoinColumn(name = "receivers_handle_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<UserInfo> receiversHandles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransferHandle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferName() {
        return this.transferName;
    }

    public TransferHandle transferName(String transferName) {
        this.setTransferName(transferName);
        return this;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getTransferAvatar() {
        return this.transferAvatar;
    }

    public TransferHandle transferAvatar(String transferAvatar) {
        this.setTransferAvatar(transferAvatar);
        return this;
    }

    public void setTransferAvatar(String transferAvatar) {
        this.transferAvatar = transferAvatar;
    }

    public String getTransferEmail() {
        return this.transferEmail;
    }

    public TransferHandle transferEmail(String transferEmail) {
        this.setTransferEmail(transferEmail);
        return this;
    }

    public void setTransferEmail(String transferEmail) {
        this.transferEmail = transferEmail;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public TransferHandle statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public TransferHandle orgName(String orgName) {
        this.setOrgName(orgName);
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public TransferHandle orgCode(String orgCode) {
        this.setOrgCode(orgCode);
        return this;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDispatchBookName() {
        return this.dispatchBookName;
    }

    public TransferHandle dispatchBookName(String dispatchBookName) {
        this.setDispatchBookName(dispatchBookName);
        return this;
    }

    public void setDispatchBookName(String dispatchBookName) {
        this.dispatchBookName = dispatchBookName;
    }

    public String getDispatchBookCode() {
        return this.dispatchBookCode;
    }

    public TransferHandle dispatchBookCode(String dispatchBookCode) {
        this.setDispatchBookCode(dispatchBookCode);
        return this;
    }

    public void setDispatchBookCode(String dispatchBookCode) {
        this.dispatchBookCode = dispatchBookCode;
    }

    public Instant getExpiredTime() {
        return this.expiredTime;
    }

    public TransferHandle expiredTime(Instant expiredTime) {
        this.setExpiredTime(expiredTime);
        return this;
    }

    public void setExpiredTime(Instant expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Instant getProcessDate() {
        return this.processDate;
    }

    public TransferHandle processDate(Instant processDate) {
        this.setProcessDate(processDate);
        return this;
    }

    public void setProcessDate(Instant processDate) {
        this.processDate = processDate;
    }

    public String getContent() {
        return this.content;
    }

    public TransferHandle content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachFileList() {
        return this.attachFileList;
    }

    public TransferHandle attachFileList(String attachFileList) {
        this.setAttachFileList(attachFileList);
        return this;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public Long getOrder() {
        return this.order;
    }

    public TransferHandle order(Long order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public TransferHandle createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public TransferHandle createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public TransferHandle createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TransferHandle createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public TransferHandle modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public TransferHandle modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TransferHandle isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public TransferHandle isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public StatusTransferHandle getStatusTransferHandle() {
        return this.statusTransferHandle;
    }

    public void setStatusTransferHandle(StatusTransferHandle statusTransferHandle) {
        this.statusTransferHandle = statusTransferHandle;
    }

    public TransferHandle statusTransferHandle(StatusTransferHandle statusTransferHandle) {
        this.setStatusTransferHandle(statusTransferHandle);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public TransferHandle organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public DispatchBook getDispatchBook() {
        return this.dispatchBook;
    }

    public void setDispatchBook(DispatchBook dispatchBook) {
        this.dispatchBook = dispatchBook;
    }

    public TransferHandle dispatchBook(DispatchBook dispatchBook) {
        this.setDispatchBook(dispatchBook);
        return this;
    }

    public StepData getStepData() {
        return this.stepData;
    }

    public void setStepData(StepData stepData) {
        this.stepData = stepData;
    }

    public TransferHandle stepData(StepData stepData) {
        this.setStepData(stepData);
        return this;
    }

    public UserInfo getTransfer() {
        return this.transfer;
    }

    public void setTransfer(UserInfo userInfo) {
        this.transfer = userInfo;
    }

    public TransferHandle transfer(UserInfo userInfo) {
        this.setTransfer(userInfo);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public TransferHandle creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public TransferHandle modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public Set<UserInfo> getReceiversHandles() {
        return this.receiversHandles;
    }

    public void setReceiversHandles(Set<UserInfo> userInfos) {
        this.receiversHandles = userInfos;
    }

    public TransferHandle receiversHandles(Set<UserInfo> userInfos) {
        this.setReceiversHandles(userInfos);
        return this;
    }

    public TransferHandle addReceiversHandle(UserInfo userInfo) {
        this.receiversHandles.add(userInfo);
        userInfo.getReceiversHandles().add(this);
        return this;
    }

    public TransferHandle removeReceiversHandle(UserInfo userInfo) {
        this.receiversHandles.remove(userInfo);
        userInfo.getReceiversHandles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferHandle)) {
            return false;
        }
        return id != null && id.equals(((TransferHandle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferHandle{" +
            "id=" + getId() +
            ", transferName='" + getTransferName() + "'" +
            ", transferAvatar='" + getTransferAvatar() + "'" +
            ", transferEmail='" + getTransferEmail() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", orgCode='" + getOrgCode() + "'" +
            ", dispatchBookName='" + getDispatchBookName() + "'" +
            ", dispatchBookCode='" + getDispatchBookCode() + "'" +
            ", expiredTime='" + getExpiredTime() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            ", content='" + getContent() + "'" +
            ", attachFileList='" + getAttachFileList() + "'" +
            ", order=" + getOrder() +
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
