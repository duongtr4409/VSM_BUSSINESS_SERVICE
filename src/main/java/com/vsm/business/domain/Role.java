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
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_type")
    private String roleType;

    @Column(name = "is_have_view")
    private Boolean isHaveView;

    @Column(name = "is_have_edit")
    private Boolean isHaveEdit;

    @Column(name = "is_have_delete")
    private Boolean isHaveDelete;

    @Column(name = "is_have_download")
    private Boolean isHaveDownload;

    @Column(name = "is_auto_add")
    private Boolean isAutoAdd;

    @Column(name = "request_list")
    private String requestList;

    @Column(name = "organization_list")
    private String organizationList;

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
            "fields",
            "forms",
            "fieldInForms",
            "ranks",
            "roles",
            "userInfos",
            "userInSteps",
            "stepInProcesses",
            "steps",
            "processInfos",
            "templateForms",
            "requests",
            "requestTypes",
            "requestGroups",
            "processData",
            "stepData",
            "requestData",
            "statuses",
            "formData",
            "fieldData",
            "attachmentFiles",
            "fileTypes",
            "reqdataChangeHis",
            "categoryData",
            "categoryGroups",
            "resultOfSteps",
        },
        allowSetters = true
    )
    private Tennant tennant;

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
    private UserInfo created;

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
    private UserInfo modified;

    @ManyToMany
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_role__feature",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    @JsonIgnoreProperties(value = { "roles" }, allowSetters = true)
    private Set<Feature> features = new HashSet<>();

    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties(value = { "created", "modified", "role", "request" }, allowSetters = true)
    private Set<RoleRequest> roleRequests = new HashSet<>();

    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties(value = { "created", "modified", "role", "organization" }, allowSetters = true)
    private Set<RoleOrganization> roleOrganizations = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
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

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfos", "roles" }, allowSetters = true)
    private Set<UserGroup> userGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Role id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public Role roleCode(String roleCode) {
        this.setRoleCode(roleCode);
        return this;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public Role roleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public Role roleType(String roleType) {
        this.setRoleType(roleType);
        return this;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Boolean getIsHaveView() {
        return this.isHaveView;
    }

    public Role isHaveView(Boolean isHaveView) {
        this.setIsHaveView(isHaveView);
        return this;
    }

    public void setIsHaveView(Boolean isHaveView) {
        this.isHaveView = isHaveView;
    }

    public Boolean getIsHaveEdit() {
        return this.isHaveEdit;
    }

    public Role isHaveEdit(Boolean isHaveEdit) {
        this.setIsHaveEdit(isHaveEdit);
        return this;
    }

    public void setIsHaveEdit(Boolean isHaveEdit) {
        this.isHaveEdit = isHaveEdit;
    }

    public Boolean getIsHaveDelete() {
        return this.isHaveDelete;
    }

    public Role isHaveDelete(Boolean isHaveDelete) {
        this.setIsHaveDelete(isHaveDelete);
        return this;
    }

    public void setIsHaveDelete(Boolean isHaveDelete) {
        this.isHaveDelete = isHaveDelete;
    }

    public Boolean getIsHaveDownload() {
        return this.isHaveDownload;
    }

    public Role isHaveDownload(Boolean isHaveDownload) {
        this.setIsHaveDownload(isHaveDownload);
        return this;
    }

    public void setIsHaveDownload(Boolean isHaveDownload) {
        this.isHaveDownload = isHaveDownload;
    }

    public Boolean getIsAutoAdd() {
        return this.isAutoAdd;
    }

    public Role isAutoAdd(Boolean isAutoAdd) {
        this.setIsAutoAdd(isAutoAdd);
        return this;
    }

    public void setIsAutoAdd(Boolean isAutoAdd) {
        this.isAutoAdd = isAutoAdd;
    }

    public String getRequestList() {
        return this.requestList;
    }

    public Role requestList(String requestList) {
        this.setRequestList(requestList);
        return this;
    }

    public void setRequestList(String requestList) {
        this.requestList = requestList;
    }

    public String getOrganizationList() {
        return this.organizationList;
    }

    public Role organizationList(String organizationList) {
        this.setOrganizationList(organizationList);
        return this;
    }

    public void setOrganizationList(String organizationList) {
        this.organizationList = organizationList;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Role createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Role createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Role createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Role createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Role modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Role modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Role isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Role isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public Role tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public Role tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public Role tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public Role created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public Role modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Set<Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Role features(Set<Feature> features) {
        this.setFeatures(features);
        return this;
    }

    public Role addFeature(Feature feature) {
        this.features.add(feature);
        feature.getRoles().add(this);
        return this;
    }

    public Role removeFeature(Feature feature) {
        this.features.remove(feature);
        feature.getRoles().remove(this);
        return this;
    }

    public Set<RoleRequest> getRoleRequests() {
        return this.roleRequests;
    }

    public void setRoleRequests(Set<RoleRequest> roleRequests) {
        if (this.roleRequests != null) {
            this.roleRequests.forEach(i -> i.setRole(null));
        }
        if (roleRequests != null) {
            roleRequests.forEach(i -> i.setRole(this));
        }
        this.roleRequests = roleRequests;
    }

    public Role roleRequests(Set<RoleRequest> roleRequests) {
        this.setRoleRequests(roleRequests);
        return this;
    }

    public Role addRoleRequest(RoleRequest roleRequest) {
        this.roleRequests.add(roleRequest);
        roleRequest.setRole(this);
        return this;
    }

    public Role removeRoleRequest(RoleRequest roleRequest) {
        this.roleRequests.remove(roleRequest);
        roleRequest.setRole(null);
        return this;
    }

    public Set<RoleOrganization> getRoleOrganizations() {
        return this.roleOrganizations;
    }

    public void setRoleOrganizations(Set<RoleOrganization> roleOrganizations) {
        if (this.roleOrganizations != null) {
            this.roleOrganizations.forEach(i -> i.setRole(null));
        }
        if (roleOrganizations != null) {
            roleOrganizations.forEach(i -> i.setRole(this));
        }
        this.roleOrganizations = roleOrganizations;
    }

    public Role roleOrganizations(Set<RoleOrganization> roleOrganizations) {
        this.setRoleOrganizations(roleOrganizations);
        return this;
    }

    public Role addRoleOrganization(RoleOrganization roleOrganization) {
        this.roleOrganizations.add(roleOrganization);
        roleOrganization.setRole(this);
        return this;
    }

    public Role removeRoleOrganization(RoleOrganization roleOrganization) {
        this.roleOrganizations.remove(roleOrganization);
        roleOrganization.setRole(null);
        return this;
    }

    public Set<UserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        if (this.userInfos != null) {
            this.userInfos.forEach(i -> i.removeRole(this));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.addRole(this));
        }
        this.userInfos = userInfos;
    }

    public Role userInfos(Set<UserInfo> userInfos) {
        this.setUserInfos(userInfos);
        return this;
    }

    public Role addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.getRoles().add(this);
        return this;
    }

    public Role removeUserInfo(UserInfo userInfo) {
        this.userInfos.remove(userInfo);
        userInfo.getRoles().remove(this);
        return this;
    }

    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        if (this.userGroups != null) {
            this.userGroups.forEach(i -> i.removeRole(this));
        }
        if (userGroups != null) {
            userGroups.forEach(i -> i.addRole(this));
        }
        this.userGroups = userGroups;
    }

    public Role userGroups(Set<UserGroup> userGroups) {
        this.setUserGroups(userGroups);
        return this;
    }

    public Role addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
        userGroup.getRoles().add(this);
        return this;
    }

    public Role removeUserGroup(UserGroup userGroup) {
        this.userGroups.remove(userGroup);
        userGroup.getRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", roleCode='" + getRoleCode() + "'" +
            ", roleName='" + getRoleName() + "'" +
            ", roleType='" + getRoleType() + "'" +
            ", isHaveView='" + getIsHaveView() + "'" +
            ", isHaveEdit='" + getIsHaveEdit() + "'" +
            ", isHaveDelete='" + getIsHaveDelete() + "'" +
            ", isHaveDownload='" + getIsHaveDownload() + "'" +
            ", isAutoAdd='" + getIsAutoAdd() + "'" +
            ", requestList='" + getRequestList() + "'" +
            ", organizationList='" + getOrganizationList() + "'" +
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
