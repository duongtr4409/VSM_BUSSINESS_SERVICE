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
 * A DispatchBook.
 */
@Entity
@Table(name = "dispatch_book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dispatchbook")
public class DispatchBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dispatch_book_name")
    private String dispatchBookName;

    @Column(name = "dispatch_book_code")
    private String dispatchBookCode;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "dispatch_book_type_name")
    private String dispatchBookTypeName;

    @Column(name = "dispatch_book_type_code")
    private String dispatchBookTypeCode;

    @Column(name = "total_doc")
    private Long totalDoc;

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

    @Column(name = "modified_d_date")
    private Instant modifiedDDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

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
        name = "rel_dispatch_book__dispatch_book_org",
        joinColumns = @JoinColumn(name = "dispatch_book_id"),
        inverseJoinColumns = @JoinColumn(name = "dispatch_book_org_id")
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
    private Set<Organization> dispatchBookOrgs = new HashSet<>();

    @OneToMany(mappedBy = "dispatchBook")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "dispatchBook",
            "releaseOrg",
            "composeOrg",
            "ownerOrg",
            "signer",
            "officialDispatchType",
            "documentType",
            "priorityLevel",
            "securityLevel",
            "officialDispatchStatus",
            "creater",
            "modifier",
            "outOrganization",
            "offDispatchUserReads",
            "attachmentFiles",
            "officialDispatchHis",
            "stepProcessDocs",
        },
        allowSetters = true
    )
    private Set<OfficialDispatch> officialDispatches = new HashSet<>();

    @OneToMany(mappedBy = "dispatchBook")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "statusTransferHandle", "organization", "dispatchBook", "stepData", "transfer", "creater", "modifier", "receiversHandles",
        },
        allowSetters = true
    )
    private Set<TransferHandle> transferHandles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DispatchBook id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDispatchBookName() {
        return this.dispatchBookName;
    }

    public DispatchBook dispatchBookName(String dispatchBookName) {
        this.setDispatchBookName(dispatchBookName);
        return this;
    }

    public void setDispatchBookName(String dispatchBookName) {
        this.dispatchBookName = dispatchBookName;
    }

    public String getDispatchBookCode() {
        return this.dispatchBookCode;
    }

    public DispatchBook dispatchBookCode(String dispatchBookCode) {
        this.setDispatchBookCode(dispatchBookCode);
        return this;
    }

    public void setDispatchBookCode(String dispatchBookCode) {
        this.dispatchBookCode = dispatchBookCode;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public DispatchBook organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return this.organizationCode;
    }

    public DispatchBook organizationCode(String organizationCode) {
        this.setOrganizationCode(organizationCode);
        return this;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getDispatchBookTypeName() {
        return this.dispatchBookTypeName;
    }

    public DispatchBook dispatchBookTypeName(String dispatchBookTypeName) {
        this.setDispatchBookTypeName(dispatchBookTypeName);
        return this;
    }

    public void setDispatchBookTypeName(String dispatchBookTypeName) {
        this.dispatchBookTypeName = dispatchBookTypeName;
    }

    public String getDispatchBookTypeCode() {
        return this.dispatchBookTypeCode;
    }

    public DispatchBook dispatchBookTypeCode(String dispatchBookTypeCode) {
        this.setDispatchBookTypeCode(dispatchBookTypeCode);
        return this;
    }

    public void setDispatchBookTypeCode(String dispatchBookTypeCode) {
        this.dispatchBookTypeCode = dispatchBookTypeCode;
    }

    public Long getTotalDoc() {
        return this.totalDoc;
    }

    public DispatchBook totalDoc(Long totalDoc) {
        this.setTotalDoc(totalDoc);
        return this;
    }

    public void setTotalDoc(Long totalDoc) {
        this.totalDoc = totalDoc;
    }

    public String getDescription() {
        return this.description;
    }

    public DispatchBook description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public DispatchBook createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public DispatchBook createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public DispatchBook createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public DispatchBook createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public DispatchBook modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDDate() {
        return this.modifiedDDate;
    }

    public DispatchBook modifiedDDate(Instant modifiedDDate) {
        this.setModifiedDDate(modifiedDDate);
        return this;
    }

    public void setModifiedDDate(Instant modifiedDDate) {
        this.modifiedDDate = modifiedDDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DispatchBook isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public DispatchBook isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public DispatchBook organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public DispatchBook creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public DispatchBook modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    public Set<Organization> getDispatchBookOrgs() {
        return this.dispatchBookOrgs;
    }

    public void setDispatchBookOrgs(Set<Organization> organizations) {
        this.dispatchBookOrgs = organizations;
    }

    public DispatchBook dispatchBookOrgs(Set<Organization> organizations) {
        this.setDispatchBookOrgs(organizations);
        return this;
    }

    public DispatchBook addDispatchBookOrg(Organization organization) {
        this.dispatchBookOrgs.add(organization);
        organization.getDispatchBookOrgs().add(this);
        return this;
    }

    public DispatchBook removeDispatchBookOrg(Organization organization) {
        this.dispatchBookOrgs.remove(organization);
        organization.getDispatchBookOrgs().remove(this);
        return this;
    }

    public Set<OfficialDispatch> getOfficialDispatches() {
        return this.officialDispatches;
    }

    public void setOfficialDispatches(Set<OfficialDispatch> officialDispatches) {
        if (this.officialDispatches != null) {
            this.officialDispatches.forEach(i -> i.setDispatchBook(null));
        }
        if (officialDispatches != null) {
            officialDispatches.forEach(i -> i.setDispatchBook(this));
        }
        this.officialDispatches = officialDispatches;
    }

    public DispatchBook officialDispatches(Set<OfficialDispatch> officialDispatches) {
        this.setOfficialDispatches(officialDispatches);
        return this;
    }

    public DispatchBook addOfficialDispatch(OfficialDispatch officialDispatch) {
        this.officialDispatches.add(officialDispatch);
        officialDispatch.setDispatchBook(this);
        return this;
    }

    public DispatchBook removeOfficialDispatch(OfficialDispatch officialDispatch) {
        this.officialDispatches.remove(officialDispatch);
        officialDispatch.setDispatchBook(null);
        return this;
    }

    public Set<TransferHandle> getTransferHandles() {
        return this.transferHandles;
    }

    public void setTransferHandles(Set<TransferHandle> transferHandles) {
        if (this.transferHandles != null) {
            this.transferHandles.forEach(i -> i.setDispatchBook(null));
        }
        if (transferHandles != null) {
            transferHandles.forEach(i -> i.setDispatchBook(this));
        }
        this.transferHandles = transferHandles;
    }

    public DispatchBook transferHandles(Set<TransferHandle> transferHandles) {
        this.setTransferHandles(transferHandles);
        return this;
    }

    public DispatchBook addTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.add(transferHandle);
        transferHandle.setDispatchBook(this);
        return this;
    }

    public DispatchBook removeTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.remove(transferHandle);
        transferHandle.setDispatchBook(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DispatchBook)) {
            return false;
        }
        return id != null && id.equals(((DispatchBook) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DispatchBook{" +
            "id=" + getId() +
            ", dispatchBookName='" + getDispatchBookName() + "'" +
            ", dispatchBookCode='" + getDispatchBookCode() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", dispatchBookTypeName='" + getDispatchBookTypeName() + "'" +
            ", dispatchBookTypeCode='" + getDispatchBookTypeCode() + "'" +
            ", totalDoc=" + getTotalDoc() +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDDate='" + getModifiedDDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
