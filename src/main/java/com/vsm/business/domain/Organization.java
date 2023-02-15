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
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "org_parent_code")
    private String orgParentCode;

    @Column(name = "org_parent_name")
    private String orgParentName;

    @Column(name = "is_sync_ad")
    private Boolean isSyncAD;

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

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

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
    private Organization orgParent;

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
    @OneToOne(mappedBy = "leader")
    private UserInfo leader;

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<RequestData> requestData = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "organization", "creater", "modifier", "dispatchBookOrgs", "officialDispatches", "transferHandles" },
        allowSetters = true
    )
    private Set<DispatchBook> dispatchBooks = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "statusTransferHandle", "organization", "dispatchBook", "stepData", "transfer", "creater", "modifier", "receiversHandles",
        },
        allowSetters = true
    )
    private Set<TransferHandle> transferHandles = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rank", "organization" }, allowSetters = true)
    private Set<RankInOrg> rankInOrgs = new HashSet<>();

    @OneToMany(mappedBy = "organization")
    @JsonIgnoreProperties(value = { "created", "modified", "role", "organization" }, allowSetters = true)
    private Set<RoleOrganization> roleOrganizations = new HashSet<>();

    @ManyToMany(mappedBy = "organizations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "organizations", "attachmentFiles", "requests" }, allowSetters = true)
    private Set<TemplateForm> templateForms = new HashSet<>();

    @ManyToMany(mappedBy = "organizations")
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
    private Set<UserInfo> userInfos = new HashSet<>();

    @ManyToMany(mappedBy = "organizations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates" },
        allowSetters = true
    )
    private Set<ProcessInfo> processInfos = new HashSet<>();

    @ManyToMany(mappedBy = "dispatchBookOrgs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "organization", "creater", "modifier", "dispatchBookOrgs", "officialDispatches", "transferHandles" },
        allowSetters = true
    )
    private Set<DispatchBook> dispatchBookOrgs = new HashSet<>();

    @ManyToMany(mappedBy = "organizations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "organizations",
            "processInfos",
            "stepInProcesses",
            "stepInProMailTemplateCustomers",
            "steps",
            "stepMailTemplateCustomers",
            "stepData",
            "stepDataMailTemplateCustomers",
            "attachmentFiles",
        },
        allowSetters = true
    )
    private Set<MailTemplate> mailTemplates = new HashSet<>();

    @ManyToMany(mappedBy = "orgStorages")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "requestData", "stamp", "orgReturn", "creater", "modifier", "orgStorages", "attachmentFiles" },
        allowSetters = true
    )
    private Set<ManageStampInfo> orgStorages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationCode() {
        return this.organizationCode;
    }

    public Organization organizationCode(String organizationCode) {
        this.setOrganizationCode(organizationCode);
        return this;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Organization organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrgParentCode() {
        return this.orgParentCode;
    }

    public Organization orgParentCode(String orgParentCode) {
        this.setOrgParentCode(orgParentCode);
        return this;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public String getOrgParentName() {
        return this.orgParentName;
    }

    public Organization orgParentName(String orgParentName) {
        this.setOrgParentName(orgParentName);
        return this;
    }

    public void setOrgParentName(String orgParentName) {
        this.orgParentName = orgParentName;
    }

    public Boolean getIsSyncAD() {
        return this.isSyncAD;
    }

    public Organization isSyncAD(Boolean isSyncAD) {
        this.setIsSyncAD(isSyncAD);
        return this;
    }

    public void setIsSyncAD(Boolean isSyncAD) {
        this.isSyncAD = isSyncAD;
    }

    public String getDescription() {
        return this.description;
    }

    public Organization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Organization createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Organization createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Organization createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Organization createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Organization modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Organization modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Organization isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Organization isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public Organization tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public Organization tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Organization getOrgParent() {
        return this.orgParent;
    }

    public void setOrgParent(Organization organization) {
        this.orgParent = organization;
    }

    public Organization orgParent(Organization organization) {
        this.setOrgParent(organization);
        return this;
    }

    public UserInfo getLeader() {
        return this.leader;
    }

    public void setLeader(UserInfo userInfo) {
        if (this.leader != null) {
            this.leader.setLeader(null);
        }
        if (userInfo != null) {
            userInfo.setLeader(this);
        }
        this.leader = userInfo;
    }

    public Organization leader(UserInfo userInfo) {
        this.setLeader(userInfo);
        return this;
    }

    public Set<RequestData> getRequestData() {
        return this.requestData;
    }

    public void setRequestData(Set<RequestData> requestData) {
        if (this.requestData != null) {
            this.requestData.forEach(i -> i.setOrganization(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setOrganization(this));
        }
        this.requestData = requestData;
    }

    public Organization requestData(Set<RequestData> requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Organization addRequestData(RequestData requestData) {
        this.requestData.add(requestData);
        requestData.setOrganization(this);
        return this;
    }

    public Organization removeRequestData(RequestData requestData) {
        this.requestData.remove(requestData);
        requestData.setOrganization(null);
        return this;
    }

    public Set<DispatchBook> getDispatchBooks() {
        return this.dispatchBooks;
    }

    public void setDispatchBooks(Set<DispatchBook> dispatchBooks) {
        if (this.dispatchBooks != null) {
            this.dispatchBooks.forEach(i -> i.setOrganization(null));
        }
        if (dispatchBooks != null) {
            dispatchBooks.forEach(i -> i.setOrganization(this));
        }
        this.dispatchBooks = dispatchBooks;
    }

    public Organization dispatchBooks(Set<DispatchBook> dispatchBooks) {
        this.setDispatchBooks(dispatchBooks);
        return this;
    }

    public Organization addDispatchBook(DispatchBook dispatchBook) {
        this.dispatchBooks.add(dispatchBook);
        dispatchBook.setOrganization(this);
        return this;
    }

    public Organization removeDispatchBook(DispatchBook dispatchBook) {
        this.dispatchBooks.remove(dispatchBook);
        dispatchBook.setOrganization(null);
        return this;
    }

    public Set<TransferHandle> getTransferHandles() {
        return this.transferHandles;
    }

    public void setTransferHandles(Set<TransferHandle> transferHandles) {
        if (this.transferHandles != null) {
            this.transferHandles.forEach(i -> i.setOrganization(null));
        }
        if (transferHandles != null) {
            transferHandles.forEach(i -> i.setOrganization(this));
        }
        this.transferHandles = transferHandles;
    }

    public Organization transferHandles(Set<TransferHandle> transferHandles) {
        this.setTransferHandles(transferHandles);
        return this;
    }

    public Organization addTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.add(transferHandle);
        transferHandle.setOrganization(this);
        return this;
    }

    public Organization removeTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.remove(transferHandle);
        transferHandle.setOrganization(null);
        return this;
    }

    public Set<RankInOrg> getRankInOrgs() {
        return this.rankInOrgs;
    }

    public void setRankInOrgs(Set<RankInOrg> rankInOrgs) {
        if (this.rankInOrgs != null) {
            this.rankInOrgs.forEach(i -> i.setOrganization(null));
        }
        if (rankInOrgs != null) {
            rankInOrgs.forEach(i -> i.setOrganization(this));
        }
        this.rankInOrgs = rankInOrgs;
    }

    public Organization rankInOrgs(Set<RankInOrg> rankInOrgs) {
        this.setRankInOrgs(rankInOrgs);
        return this;
    }

    public Organization addRankInOrg(RankInOrg rankInOrg) {
        this.rankInOrgs.add(rankInOrg);
        rankInOrg.setOrganization(this);
        return this;
    }

    public Organization removeRankInOrg(RankInOrg rankInOrg) {
        this.rankInOrgs.remove(rankInOrg);
        rankInOrg.setOrganization(null);
        return this;
    }

    public Set<RoleOrganization> getRoleOrganizations() {
        return this.roleOrganizations;
    }

    public void setRoleOrganizations(Set<RoleOrganization> roleOrganizations) {
        if (this.roleOrganizations != null) {
            this.roleOrganizations.forEach(i -> i.setOrganization(null));
        }
        if (roleOrganizations != null) {
            roleOrganizations.forEach(i -> i.setOrganization(this));
        }
        this.roleOrganizations = roleOrganizations;
    }

    public Organization roleOrganizations(Set<RoleOrganization> roleOrganizations) {
        this.setRoleOrganizations(roleOrganizations);
        return this;
    }

    public Organization addRoleOrganization(RoleOrganization roleOrganization) {
        this.roleOrganizations.add(roleOrganization);
        roleOrganization.setOrganization(this);
        return this;
    }

    public Organization removeRoleOrganization(RoleOrganization roleOrganization) {
        this.roleOrganizations.remove(roleOrganization);
        roleOrganization.setOrganization(null);
        return this;
    }

    public Set<TemplateForm> getTemplateForms() {
        return this.templateForms;
    }

    public void setTemplateForms(Set<TemplateForm> templateForms) {
        if (this.templateForms != null) {
            this.templateForms.forEach(i -> i.removeOrganization(this));
        }
        if (templateForms != null) {
            templateForms.forEach(i -> i.addOrganization(this));
        }
        this.templateForms = templateForms;
    }

    public Organization templateForms(Set<TemplateForm> templateForms) {
        this.setTemplateForms(templateForms);
        return this;
    }

    public Organization addTemplateForm(TemplateForm templateForm) {
        this.templateForms.add(templateForm);
        templateForm.getOrganizations().add(this);
        return this;
    }

    public Organization removeTemplateForm(TemplateForm templateForm) {
        this.templateForms.remove(templateForm);
        templateForm.getOrganizations().remove(this);
        return this;
    }

    public Set<UserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        if (this.userInfos != null) {
            this.userInfos.forEach(i -> i.removeOrganization(this));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.addOrganization(this));
        }
        this.userInfos = userInfos;
    }

    public Organization userInfos(Set<UserInfo> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public Organization addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.getOrganizations().add(this);
        return this;
    }

    public Organization removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.getOrganizations().remove(this);
        return this;
    }

    public Set<ProcessInfo> getProcessInfos() {
        return this.processInfos;
    }

    public void setProcessInfos(Set<ProcessInfo> processInfos) {
        if (this.processInfos != null) {
            this.processInfos.forEach(i -> i.removeOrganization(this));
        }
        if (processInfos != null) {
            processInfos.forEach(i -> i.addOrganization(this));
        }
        this.processInfos = processInfos;
    }

    public Organization processInfos(Set<ProcessInfo> processInfos) {
        this.setProcessInfos(processInfos);
        return this;
    }

    public Organization addProcessInfo(ProcessInfo processInfo) {
        this.processInfos.add(processInfo);
        processInfo.getOrganizations().add(this);
        return this;
    }

    public Organization removeProcessInfo(ProcessInfo processInfo) {
        this.processInfos.remove(processInfo);
        processInfo.getOrganizations().remove(this);
        return this;
    }

    public Set<DispatchBook> getDispatchBookOrgs() {
        return this.dispatchBookOrgs;
    }

    public void setDispatchBookOrgs(Set<DispatchBook> dispatchBooks) {
        if (this.dispatchBookOrgs != null) {
            this.dispatchBookOrgs.forEach(i -> i.removeDispatchBookOrg(this));
        }
        if (dispatchBooks != null) {
            dispatchBooks.forEach(i -> i.addDispatchBookOrg(this));
        }
        this.dispatchBookOrgs = dispatchBooks;
    }

    public Organization dispatchBookOrgs(Set<DispatchBook> dispatchBooks) {
        this.setDispatchBookOrgs(dispatchBooks);
        return this;
    }

    public Organization addDispatchBookOrg(DispatchBook dispatchBook) {
        this.dispatchBookOrgs.add(dispatchBook);
        dispatchBook.getDispatchBookOrgs().add(this);
        return this;
    }

    public Organization removeDispatchBookOrg(DispatchBook dispatchBook) {
        this.dispatchBookOrgs.remove(dispatchBook);
        dispatchBook.getDispatchBookOrgs().remove(this);
        return this;
    }

    public Set<MailTemplate> getMailTemplates() {
        return this.mailTemplates;
    }

    public void setMailTemplates(Set<MailTemplate> mailTemplates) {
        if (this.mailTemplates != null) {
            this.mailTemplates.forEach(i -> i.removeOrganization(this));
        }
        if (mailTemplates != null) {
            mailTemplates.forEach(i -> i.addOrganization(this));
        }
        this.mailTemplates = mailTemplates;
    }

    public Organization mailTemplates(Set<MailTemplate> mailTemplates) {
        this.setMailTemplates(mailTemplates);
        return this;
    }

    public Organization addMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplates.add(mailTemplate);
        mailTemplate.getOrganizations().add(this);
        return this;
    }

    public Organization removeMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplates.remove(mailTemplate);
        mailTemplate.getOrganizations().remove(this);
        return this;
    }

    public Set<ManageStampInfo> getOrgStorages() {
        return this.orgStorages;
    }

    public void setOrgStorages(Set<ManageStampInfo> manageStampInfos) {
        if (this.orgStorages != null) {
            this.orgStorages.forEach(i -> i.removeOrgStorage(this));
        }
        if (manageStampInfos != null) {
            manageStampInfos.forEach(i -> i.addOrgStorage(this));
        }
        this.orgStorages = manageStampInfos;
    }

    public Organization orgStorages(Set<ManageStampInfo> manageStampInfos) {
        this.setOrgStorages(manageStampInfos);
        return this;
    }

    public Organization addOrgStorage(ManageStampInfo manageStampInfo) {
        this.orgStorages.add(manageStampInfo);
        manageStampInfo.getOrgStorages().add(this);
        return this;
    }

    public Organization removeOrgStorage(ManageStampInfo manageStampInfo) {
        this.orgStorages.remove(manageStampInfo);
        manageStampInfo.getOrgStorages().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", orgParentCode='" + getOrgParentCode() + "'" +
            ", orgParentName='" + getOrgParentName() + "'" +
            ", isSyncAD='" + getIsSyncAD() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
