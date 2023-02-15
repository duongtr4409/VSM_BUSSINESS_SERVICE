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
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "userinfo")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "identification")
    private String identification;

    @Column(name = "issuse_date")
    private Instant issuseDate;

    @Column(name = "issuse_org")
    private String issuseOrg;

    @Column(name = "fax")
    private String fax;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "password_encode")
    private String passwordEncode;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "number_of_login_failed")
    private Long numberOfLoginFailed;

    @Column(name = "description")
    private String description;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "user_type_name")
    private String userTypeName;

    @Column(name = "user_type_code")
    private String userTypeCode;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "sign_type_name")
    private String signTypeName;

    @Column(name = "sign_type_code")
    private String signTypeCode;

    @Column(name = "sign_type")
    private String signType;

    @Column(name = "sign_folder")
    private String signFolder;

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

    @Column(name = "id_in_microsoft")
    private String idInMicrosoft;

    @Column(name = "o_data_context")
    private String oDataContext;

    @Column(name = "business_phones")
    private String businessPhones;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "mail")
    private String mail;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "office_location")
    private String officeLocation;

    @Column(name = "preferred_language")
    private String preferredLanguage;

    @Column(name = "surname")
    private String surname;

    @Column(name = "user_principal_name")
    private String userPrincipalName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "info_in_microsoft")
    private String infoInMicrosoft;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

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
    @OneToOne
    @JoinColumn(unique = true)
    private Organization leader;

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
    @JoinTable(
        name = "rel_user_info__role",
        joinColumns = @JoinColumn(name = "user_info_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "features", "roleRequests", "roleOrganizations", "userInfos", "userGroups" },
        allowSetters = true
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_user_info__rank",
        joinColumns = @JoinColumn(name = "user_info_id"),
        inverseJoinColumns = @JoinColumn(name = "rank_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Set<Rank> ranks = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_user_info__organization",
        joinColumns = @JoinColumn(name = "user_info_id"),
        inverseJoinColumns = @JoinColumn(name = "organization_id")
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
    private Set<Organization> organizations = new HashSet<>();

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "stepInProcess", "tennant", "created", "modified" }, allowSetters = true)
    private Set<UserInStep> userInSteps = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "fieldData", "fieldInForms", "forms" }, allowSetters = true)
    private Set<Field> createdFields = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "fieldData", "fieldInForms", "forms" }, allowSetters = true)
    private Set<Field> modifiedFields = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "fields", "requests", "formData", "fieldInForms" },
        allowSetters = true
    )
    private Set<Form> createdForms = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "fields", "requests", "formData", "fieldInForms" },
        allowSetters = true
    )
    private Set<Form> modifiedForms = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "field", "form", "fieldData" }, allowSetters = true)
    private Set<FieldInForm> createdFieldInForms = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "field", "form", "fieldData" }, allowSetters = true)
    private Set<FieldInForm> modifiedFieldInForms = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Set<Rank> createdRanks = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Set<Rank> modifiedRanks = new HashSet<>();

    @OneToMany(mappedBy = "created")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "features", "roleRequests", "roleOrganizations", "userInfos", "userGroups" },
        allowSetters = true
    )
    private Set<Role> createdRoles = new HashSet<>();

    @OneToMany(mappedBy = "modified")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "features", "roleRequests", "roleOrganizations", "userInfos", "userGroups" },
        allowSetters = true
    )
    private Set<Role> modifiedRoles = new HashSet<>();

    @OneToMany(mappedBy = "created")
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
    private Set<UserInfo> createdUserInfos = new HashSet<>();

    @OneToMany(mappedBy = "modified")
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
    private Set<UserInfo> modifiedUserInfos = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "stepInProcess", "tennant", "created", "modified" }, allowSetters = true)
    private Set<UserInStep> createdUserInSteps = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "stepInProcess", "tennant", "created", "modified" }, allowSetters = true)
    private Set<UserInStep> modifiedUserInSteps = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "step",
            "processInfo",
            "tennant",
            "created",
            "modified",
            "rank",
            "organization",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInSteps",
        },
        allowSetters = true
    )
    private Set<StepInProcess> createdStepInProcesses = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "step",
            "processInfo",
            "tennant",
            "created",
            "modified",
            "rank",
            "organization",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInSteps",
        },
        allowSetters = true
    )
    private Set<StepInProcess> modifiedStepInProcesses = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "rank", "organization", "mailTemplate", "mailTemplateCustomer", "stepInProcesses" },
        allowSetters = true
    )
    private Set<Step> createdSteps = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "rank", "organization", "mailTemplate", "mailTemplateCustomer", "stepInProcesses" },
        allowSetters = true
    )
    private Set<Step> modifiedSteps = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates" },
        allowSetters = true
    )
    private Set<ProcessInfo> createdProcessInfos = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "organizations", "stepInProcesses", "requests", "mailTemplates" },
        allowSetters = true
    )
    private Set<ProcessInfo> modifiedProcessInfos = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "organizations", "attachmentFiles", "requests" }, allowSetters = true)
    private Set<TemplateForm> createdTemplateForms = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "organizations", "attachmentFiles", "requests" }, allowSetters = true)
    private Set<TemplateForm> modifiedTemplateForms = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "requestType",
            "requestGroup",
            "form",
            "tennant",
            "created",
            "modified",
            "templateForms",
            "processInfos",
            "requestData",
            "roleRequests",
        },
        allowSetters = true
    )
    private Set<Request> createdRequests = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "requestType",
            "requestGroup",
            "form",
            "tennant",
            "created",
            "modified",
            "templateForms",
            "processInfos",
            "requestData",
            "roleRequests",
        },
        allowSetters = true
    )
    private Set<Request> modifiedRequests = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestGroup", "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private Set<RequestType> createdRequestTypes = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestGroup", "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private Set<RequestType> modifiedRequestTypes = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private Set<RequestGroup> createdRequestGroups = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "requests", "requestData" }, allowSetters = true)
    private Set<RequestGroup> modifiedRequestGroups = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified", "stepData" }, allowSetters = true)
    private Set<ProcessData> createdProcessDatas = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified", "stepData" }, allowSetters = true)
    private Set<ProcessData> modifiedProcessDatas = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<StepData> createdStepDatas = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<StepData> modifiedStepDatas = new HashSet<>();

    @OneToMany(mappedBy = "created")
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
    private Set<RequestData> createdRequestDatas = new HashSet<>();

    @OneToMany(mappedBy = "modified")
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
    private Set<RequestData> modifiedRequestDatas = new HashSet<>();

    @OneToMany(mappedBy = "approver")
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
    private Set<RequestData> approvedRequestDatas = new HashSet<>();

    @OneToMany(mappedBy = "revoker")
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
    private Set<RequestData> revokedRequestDatas = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "statusRequests", "subStatusRequests" }, allowSetters = true)
    private Set<Status> createdStatuses = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "statusRequests", "subStatusRequests" }, allowSetters = true)
    private Set<Status> modifiedStatuses = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "form", "tennant", "created", "modified", "fieldData" }, allowSetters = true)
    private Set<FormData> createdFormDatas = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "form", "tennant", "created", "modified", "fieldData" }, allowSetters = true)
    private Set<FormData> modifiedFormDatas = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "field", "formData", "requestData", "tennant", "created", "modified", "fieldInForm" },
        allowSetters = true
    )
    private Set<FieldData> createdFieldDatas = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "field", "formData", "requestData", "tennant", "created", "modified", "fieldInForm" },
        allowSetters = true
    )
    private Set<FieldData> modifiedFieldDatas = new HashSet<>();

    @OneToMany(mappedBy = "created")
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
    private Set<AttachmentFile> createdAttachmentFiles = new HashSet<>();

    @OneToMany(mappedBy = "modified")
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
    private Set<AttachmentFile> modifiedAttachmentFiles = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "attachmentFiles" }, allowSetters = true)
    private Set<FileType> createdFileTypes = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "attachmentFiles" }, allowSetters = true)
    private Set<FileType> modifiedFileTypes = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified" }, allowSetters = true)
    private Set<ReqdataChangeHis> createdReqdataChangeHis = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "tennant", "created", "modified" }, allowSetters = true)
    private Set<ReqdataChangeHis> modifiedReqdataChangeHis = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoryGroup", "tennant", "created", "modified" }, allowSetters = true)
    private Set<CategoryData> createdCategoryDatas = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoryGroup", "tennant", "created", "modified" }, allowSetters = true)
    private Set<CategoryData> modifiedCategoryDatas = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "parent", "categoryData", "children" }, allowSetters = true)
    private Set<CategoryGroup> createdCategoryGroups = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "parent", "categoryData", "children" }, allowSetters = true)
    private Set<CategoryGroup> modifiedCategoryGroups = new HashSet<>();

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "signData" }, allowSetters = true)
    private Set<SignatureInfomation> signatureInfomations = new HashSet<>();

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfo", "attachmentFile", "created", "modified" }, allowSetters = true)
    private Set<AttachmentPermisition> attachmentPermisitions = new HashSet<>();

    @OneToMany(mappedBy = "created")
    @JsonIgnoreProperties(value = { "userInfo", "attachmentFile", "created", "modified" }, allowSetters = true)
    private Set<AttachmentPermisition> createdAttachmentPermisions = new HashSet<>();

    @OneToMany(mappedBy = "modified")
    @JsonIgnoreProperties(value = { "userInfo", "attachmentFile", "created", "modified" }, allowSetters = true)
    private Set<AttachmentPermisition> modifiedAttachmentPermisions = new HashSet<>();

    @OneToMany(mappedBy = "userInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestData", "signatureInfomation", "userInfo" }, allowSetters = true)
    private Set<SignData> signData = new HashSet<>();

    @ManyToMany(mappedBy = "userInfos")
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

    @ManyToMany(mappedBy = "userInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<StepData> stepData = new HashSet<>();

    @ManyToMany(mappedBy = "userInfos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userInfos", "roles" }, allowSetters = true)
    private Set<UserGroup> userGroups = new HashSet<>();

    @ManyToMany(mappedBy = "offDispatchUserReads")
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
    private Set<OfficialDispatch> offDispatchUserReads = new HashSet<>();

    @ManyToMany(mappedBy = "receiversHandles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "statusTransferHandle", "organization", "dispatchBook", "stepData", "transfer", "creater", "modifier", "receiversHandles",
        },
        allowSetters = true
    )
    private Set<TransferHandle> receiversHandles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public UserInfo name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public UserInfo fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return this.address;
    }

    public UserInfo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactAddress() {
        return this.contactAddress;
    }

    public UserInfo contactAddress(String contactAddress) {
        this.setContactAddress(contactAddress);
        return this;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getIdentification() {
        return this.identification;
    }

    public UserInfo identification(String identification) {
        this.setIdentification(identification);
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Instant getIssuseDate() {
        return this.issuseDate;
    }

    public UserInfo issuseDate(Instant issuseDate) {
        this.setIssuseDate(issuseDate);
        return this;
    }

    public void setIssuseDate(Instant issuseDate) {
        this.issuseDate = issuseDate;
    }

    public String getIssuseOrg() {
        return this.issuseOrg;
    }

    public UserInfo issuseOrg(String issuseOrg) {
        this.setIssuseOrg(issuseOrg);
        return this;
    }

    public void setIssuseOrg(String issuseOrg) {
        this.issuseOrg = issuseOrg;
    }

    public String getFax() {
        return this.fax;
    }

    public UserInfo fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public UserInfo avatar(String avatar) {
        this.setAvatar(avatar);
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return this.email;
    }

    public UserInfo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return this.userName;
    }

    public UserInfo userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public UserInfo password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordEncode() {
        return this.passwordEncode;
    }

    public UserInfo passwordEncode(String passwordEncode) {
        this.setPasswordEncode(passwordEncode);
        return this;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public UserInfo expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getNumberOfLoginFailed() {
        return this.numberOfLoginFailed;
    }

    public UserInfo numberOfLoginFailed(Long numberOfLoginFailed) {
        this.setNumberOfLoginFailed(numberOfLoginFailed);
        return this;
    }

    public void setNumberOfLoginFailed(Long numberOfLoginFailed) {
        this.numberOfLoginFailed = numberOfLoginFailed;
    }

    public String getDescription() {
        return this.description;
    }

    public UserInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsLocked() {
        return this.isLocked;
    }

    public UserInfo isLocked(Boolean isLocked) {
        this.setIsLocked(isLocked);
        return this;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getUserTypeName() {
        return this.userTypeName;
    }

    public UserInfo userTypeName(String userTypeName) {
        this.setUserTypeName(userTypeName);
        return this;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserTypeCode() {
        return this.userTypeCode;
    }

    public UserInfo userTypeCode(String userTypeCode) {
        this.setUserTypeCode(userTypeCode);
        return this;
    }

    public void setUserTypeCode(String userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public String getUserType() {
        return this.userType;
    }

    public UserInfo userType(String userType) {
        this.setUserType(userType);
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSignTypeName() {
        return this.signTypeName;
    }

    public UserInfo signTypeName(String signTypeName) {
        this.setSignTypeName(signTypeName);
        return this;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTypeCode() {
        return this.signTypeCode;
    }

    public UserInfo signTypeCode(String signTypeCode) {
        this.setSignTypeCode(signTypeCode);
        return this;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public String getSignType() {
        return this.signType;
    }

    public UserInfo signType(String signType) {
        this.setSignType(signType);
        return this;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignFolder() {
        return this.signFolder;
    }

    public UserInfo signFolder(String signFolder) {
        this.setSignFolder(signFolder);
        return this;
    }

    public void setSignFolder(String signFolder) {
        this.signFolder = signFolder;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public UserInfo createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public UserInfo createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public UserInfo createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public UserInfo createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public UserInfo modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public UserInfo modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getIdInMicrosoft() {
        return this.idInMicrosoft;
    }

    public UserInfo idInMicrosoft(String idInMicrosoft) {
        this.setIdInMicrosoft(idInMicrosoft);
        return this;
    }

    public void setIdInMicrosoft(String idInMicrosoft) {
        this.idInMicrosoft = idInMicrosoft;
    }

    public String getoDataContext() {
        return this.oDataContext;
    }

    public UserInfo oDataContext(String oDataContext) {
        this.setoDataContext(oDataContext);
        return this;
    }

    public void setoDataContext(String oDataContext) {
        this.oDataContext = oDataContext;
    }

    public String getBusinessPhones() {
        return this.businessPhones;
    }

    public UserInfo businessPhones(String businessPhones) {
        this.setBusinessPhones(businessPhones);
        return this;
    }

    public void setBusinessPhones(String businessPhones) {
        this.businessPhones = businessPhones;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public UserInfo displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public UserInfo givenName(String givenName) {
        this.setGivenName(givenName);
        return this;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public UserInfo jobTitle(String jobTitle) {
        this.setJobTitle(jobTitle);
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMail() {
        return this.mail;
    }

    public UserInfo mail(String mail) {
        this.setMail(mail);
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public UserInfo mobilePhone(String mobilePhone) {
        this.setMobilePhone(mobilePhone);
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficeLocation() {
        return this.officeLocation;
    }

    public UserInfo officeLocation(String officeLocation) {
        this.setOfficeLocation(officeLocation);
        return this;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getPreferredLanguage() {
        return this.preferredLanguage;
    }

    public UserInfo preferredLanguage(String preferredLanguage) {
        this.setPreferredLanguage(preferredLanguage);
        return this;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getSurname() {
        return this.surname;
    }

    public UserInfo surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserPrincipalName() {
        return this.userPrincipalName;
    }

    public UserInfo userPrincipalName(String userPrincipalName) {
        this.setUserPrincipalName(userPrincipalName);
        return this;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getInfoInMicrosoft() {
        return this.infoInMicrosoft;
    }

    public UserInfo infoInMicrosoft(String infoInMicrosoft) {
        this.setInfoInMicrosoft(infoInMicrosoft);
        return this;
    }

    public void setInfoInMicrosoft(String infoInMicrosoft) {
        this.infoInMicrosoft = infoInMicrosoft;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public UserInfo isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public UserInfo isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public UserInfo tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public UserInfo tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Organization getLeader() {
        return this.leader;
    }

    public void setLeader(Organization organization) {
        this.leader = organization;
    }

    public UserInfo leader(Organization organization) {
        this.setLeader(organization);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public UserInfo tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public UserInfo created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public UserInfo modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserInfo roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public UserInfo addRole(Role role) {
        this.roles.add(role);
        role.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeRole(Role role) {
        this.roles.remove(role);
        role.getUserInfos().remove(this);
        return this;
    }

    public Set<Rank> getRanks() {
        return this.ranks;
    }

    public void setRanks(Set<Rank> ranks) {
        this.ranks = ranks;
    }

    public UserInfo ranks(Set<Rank> ranks) {
        this.setRanks(ranks);
        return this;
    }

    public UserInfo addRank(Rank rank) {
        this.ranks.add(rank);
        rank.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeRank(Rank rank) {
        this.ranks.remove(rank);
        rank.getUserInfos().remove(this);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public UserInfo organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public UserInfo addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.getUserInfos().remove(this);
        return this;
    }

    public Set<UserInStep> getUserInSteps() {
        return this.userInSteps;
    }

    public void setUserInSteps(Set<UserInStep> userInSteps) {
        if (this.userInSteps != null) {
            this.userInSteps.forEach(i -> i.setUserInfo(null));
        }
        if (userInSteps != null) {
            userInSteps.forEach(i -> i.setUserInfo(this));
        }
        this.userInSteps = userInSteps;
    }

    public UserInfo userInSteps(Set<UserInStep> userInSteps) {
        this.setUserInSteps(userInSteps);
        return this;
    }

    public UserInfo addUserInStep(UserInStep userInStep) {
        this.userInSteps.add(userInStep);
        userInStep.setUserInfo(this);
        return this;
    }

    public UserInfo removeUserInStep(UserInStep userInStep) {
        this.userInSteps.remove(userInStep);
        userInStep.setUserInfo(null);
        return this;
    }

    public Set<Field> getCreatedFields() {
        return this.createdFields;
    }

    public void setCreatedFields(Set<Field> fields) {
        if (this.createdFields != null) {
            this.createdFields.forEach(i -> i.setCreated(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setCreated(this));
        }
        this.createdFields = fields;
    }

    public UserInfo createdFields(Set<Field> fields) {
        this.setCreatedFields(fields);
        return this;
    }

    public UserInfo addCreatedFields(Field field) {
        this.createdFields.add(field);
        field.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedFields(Field field) {
        this.createdFields.remove(field);
        field.setCreated(null);
        return this;
    }

    public Set<Field> getModifiedFields() {
        return this.modifiedFields;
    }

    public void setModifiedFields(Set<Field> fields) {
        if (this.modifiedFields != null) {
            this.modifiedFields.forEach(i -> i.setModified(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setModified(this));
        }
        this.modifiedFields = fields;
    }

    public UserInfo modifiedFields(Set<Field> fields) {
        this.setModifiedFields(fields);
        return this;
    }

    public UserInfo addModifiedFields(Field field) {
        this.modifiedFields.add(field);
        field.setModified(this);
        return this;
    }

    public UserInfo removeModifiedFields(Field field) {
        this.modifiedFields.remove(field);
        field.setModified(null);
        return this;
    }

    public Set<Form> getCreatedForms() {
        return this.createdForms;
    }

    public void setCreatedForms(Set<Form> forms) {
        if (this.createdForms != null) {
            this.createdForms.forEach(i -> i.setCreated(null));
        }
        if (forms != null) {
            forms.forEach(i -> i.setCreated(this));
        }
        this.createdForms = forms;
    }

    public UserInfo createdForms(Set<Form> forms) {
        this.setCreatedForms(forms);
        return this;
    }

    public UserInfo addCreatedForms(Form form) {
        this.createdForms.add(form);
        form.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedForms(Form form) {
        this.createdForms.remove(form);
        form.setCreated(null);
        return this;
    }

    public Set<Form> getModifiedForms() {
        return this.modifiedForms;
    }

    public void setModifiedForms(Set<Form> forms) {
        if (this.modifiedForms != null) {
            this.modifiedForms.forEach(i -> i.setModified(null));
        }
        if (forms != null) {
            forms.forEach(i -> i.setModified(this));
        }
        this.modifiedForms = forms;
    }

    public UserInfo modifiedForms(Set<Form> forms) {
        this.setModifiedForms(forms);
        return this;
    }

    public UserInfo addModifiedForms(Form form) {
        this.modifiedForms.add(form);
        form.setModified(this);
        return this;
    }

    public UserInfo removeModifiedForms(Form form) {
        this.modifiedForms.remove(form);
        form.setModified(null);
        return this;
    }

    public Set<FieldInForm> getCreatedFieldInForms() {
        return this.createdFieldInForms;
    }

    public void setCreatedFieldInForms(Set<FieldInForm> fieldInForms) {
        if (this.createdFieldInForms != null) {
            this.createdFieldInForms.forEach(i -> i.setCreated(null));
        }
        if (fieldInForms != null) {
            fieldInForms.forEach(i -> i.setCreated(this));
        }
        this.createdFieldInForms = fieldInForms;
    }

    public UserInfo createdFieldInForms(Set<FieldInForm> fieldInForms) {
        this.setCreatedFieldInForms(fieldInForms);
        return this;
    }

    public UserInfo addCreatedFieldInForms(FieldInForm fieldInForm) {
        this.createdFieldInForms.add(fieldInForm);
        fieldInForm.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedFieldInForms(FieldInForm fieldInForm) {
        this.createdFieldInForms.remove(fieldInForm);
        fieldInForm.setCreated(null);
        return this;
    }

    public Set<FieldInForm> getModifiedFieldInForms() {
        return this.modifiedFieldInForms;
    }

    public void setModifiedFieldInForms(Set<FieldInForm> fieldInForms) {
        if (this.modifiedFieldInForms != null) {
            this.modifiedFieldInForms.forEach(i -> i.setModified(null));
        }
        if (fieldInForms != null) {
            fieldInForms.forEach(i -> i.setModified(this));
        }
        this.modifiedFieldInForms = fieldInForms;
    }

    public UserInfo modifiedFieldInForms(Set<FieldInForm> fieldInForms) {
        this.setModifiedFieldInForms(fieldInForms);
        return this;
    }

    public UserInfo addModifiedFieldInForms(FieldInForm fieldInForm) {
        this.modifiedFieldInForms.add(fieldInForm);
        fieldInForm.setModified(this);
        return this;
    }

    public UserInfo removeModifiedFieldInForms(FieldInForm fieldInForm) {
        this.modifiedFieldInForms.remove(fieldInForm);
        fieldInForm.setModified(null);
        return this;
    }

    public Set<Rank> getCreatedRanks() {
        return this.createdRanks;
    }

    public void setCreatedRanks(Set<Rank> ranks) {
        if (this.createdRanks != null) {
            this.createdRanks.forEach(i -> i.setCreated(null));
        }
        if (ranks != null) {
            ranks.forEach(i -> i.setCreated(this));
        }
        this.createdRanks = ranks;
    }

    public UserInfo createdRanks(Set<Rank> ranks) {
        this.setCreatedRanks(ranks);
        return this;
    }

    public UserInfo addCreatedRanks(Rank rank) {
        this.createdRanks.add(rank);
        rank.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedRanks(Rank rank) {
        this.createdRanks.remove(rank);
        rank.setCreated(null);
        return this;
    }

    public Set<Rank> getModifiedRanks() {
        return this.modifiedRanks;
    }

    public void setModifiedRanks(Set<Rank> ranks) {
        if (this.modifiedRanks != null) {
            this.modifiedRanks.forEach(i -> i.setModified(null));
        }
        if (ranks != null) {
            ranks.forEach(i -> i.setModified(this));
        }
        this.modifiedRanks = ranks;
    }

    public UserInfo modifiedRanks(Set<Rank> ranks) {
        this.setModifiedRanks(ranks);
        return this;
    }

    public UserInfo addModifiedRanks(Rank rank) {
        this.modifiedRanks.add(rank);
        rank.setModified(this);
        return this;
    }

    public UserInfo removeModifiedRanks(Rank rank) {
        this.modifiedRanks.remove(rank);
        rank.setModified(null);
        return this;
    }

    public Set<Role> getCreatedRoles() {
        return this.createdRoles;
    }

    public void setCreatedRoles(Set<Role> roles) {
        if (this.createdRoles != null) {
            this.createdRoles.forEach(i -> i.setCreated(null));
        }
        if (roles != null) {
            roles.forEach(i -> i.setCreated(this));
        }
        this.createdRoles = roles;
    }

    public UserInfo createdRoles(Set<Role> roles) {
        this.setCreatedRoles(roles);
        return this;
    }

    public UserInfo addCreatedRoles(Role role) {
        this.createdRoles.add(role);
        role.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedRoles(Role role) {
        this.createdRoles.remove(role);
        role.setCreated(null);
        return this;
    }

    public Set<Role> getModifiedRoles() {
        return this.modifiedRoles;
    }

    public void setModifiedRoles(Set<Role> roles) {
        if (this.modifiedRoles != null) {
            this.modifiedRoles.forEach(i -> i.setModified(null));
        }
        if (roles != null) {
            roles.forEach(i -> i.setModified(this));
        }
        this.modifiedRoles = roles;
    }

    public UserInfo modifiedRoles(Set<Role> roles) {
        this.setModifiedRoles(roles);
        return this;
    }

    public UserInfo addModifiedRoles(Role role) {
        this.modifiedRoles.add(role);
        role.setModified(this);
        return this;
    }

    public UserInfo removeModifiedRoles(Role role) {
        this.modifiedRoles.remove(role);
        role.setModified(null);
        return this;
    }

    public Set<UserInfo> getCreatedUserInfos() {
        return this.createdUserInfos;
    }

    public void setCreatedUserInfos(Set<UserInfo> userInfos) {
        if (this.createdUserInfos != null) {
            this.createdUserInfos.forEach(i -> i.setCreated(null));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.setCreated(this));
        }
        this.createdUserInfos = userInfos;
    }

    public UserInfo createdUserInfos(Set<UserInfo> userInfos) {
        this.setCreatedUserInfos(userInfos);
        return this;
    }

    public UserInfo addCreatedUserInfos(UserInfo userInfo) {
        this.createdUserInfos.add(userInfo);
        userInfo.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedUserInfos(UserInfo userInfo) {
        this.createdUserInfos.remove(userInfo);
        userInfo.setCreated(null);
        return this;
    }

    public Set<UserInfo> getModifiedUserInfos() {
        return this.modifiedUserInfos;
    }

    public void setModifiedUserInfos(Set<UserInfo> userInfos) {
        if (this.modifiedUserInfos != null) {
            this.modifiedUserInfos.forEach(i -> i.setModified(null));
        }
        if (userInfos != null) {
            userInfos.forEach(i -> i.setModified(this));
        }
        this.modifiedUserInfos = userInfos;
    }

    public UserInfo modifiedUserInfos(Set<UserInfo> userInfos) {
        this.setModifiedUserInfos(userInfos);
        return this;
    }

    public UserInfo addModifiedUserInfos(UserInfo userInfo) {
        this.modifiedUserInfos.add(userInfo);
        userInfo.setModified(this);
        return this;
    }

    public UserInfo removeModifiedUserInfos(UserInfo userInfo) {
        this.modifiedUserInfos.remove(userInfo);
        userInfo.setModified(null);
        return this;
    }

    public Set<UserInStep> getCreatedUserInSteps() {
        return this.createdUserInSteps;
    }

    public void setCreatedUserInSteps(Set<UserInStep> userInSteps) {
        if (this.createdUserInSteps != null) {
            this.createdUserInSteps.forEach(i -> i.setCreated(null));
        }
        if (userInSteps != null) {
            userInSteps.forEach(i -> i.setCreated(this));
        }
        this.createdUserInSteps = userInSteps;
    }

    public UserInfo createdUserInSteps(Set<UserInStep> userInSteps) {
        this.setCreatedUserInSteps(userInSteps);
        return this;
    }

    public UserInfo addCreatedUserInSteps(UserInStep userInStep) {
        this.createdUserInSteps.add(userInStep);
        userInStep.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedUserInSteps(UserInStep userInStep) {
        this.createdUserInSteps.remove(userInStep);
        userInStep.setCreated(null);
        return this;
    }

    public Set<UserInStep> getModifiedUserInSteps() {
        return this.modifiedUserInSteps;
    }

    public void setModifiedUserInSteps(Set<UserInStep> userInSteps) {
        if (this.modifiedUserInSteps != null) {
            this.modifiedUserInSteps.forEach(i -> i.setModified(null));
        }
        if (userInSteps != null) {
            userInSteps.forEach(i -> i.setModified(this));
        }
        this.modifiedUserInSteps = userInSteps;
    }

    public UserInfo modifiedUserInSteps(Set<UserInStep> userInSteps) {
        this.setModifiedUserInSteps(userInSteps);
        return this;
    }

    public UserInfo addModifiedUserInSteps(UserInStep userInStep) {
        this.modifiedUserInSteps.add(userInStep);
        userInStep.setModified(this);
        return this;
    }

    public UserInfo removeModifiedUserInSteps(UserInStep userInStep) {
        this.modifiedUserInSteps.remove(userInStep);
        userInStep.setModified(null);
        return this;
    }

    public Set<StepInProcess> getCreatedStepInProcesses() {
        return this.createdStepInProcesses;
    }

    public void setCreatedStepInProcesses(Set<StepInProcess> stepInProcesses) {
        if (this.createdStepInProcesses != null) {
            this.createdStepInProcesses.forEach(i -> i.setCreated(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setCreated(this));
        }
        this.createdStepInProcesses = stepInProcesses;
    }

    public UserInfo createdStepInProcesses(Set<StepInProcess> stepInProcesses) {
        this.setCreatedStepInProcesses(stepInProcesses);
        return this;
    }

    public UserInfo addCreatedStepInProcesses(StepInProcess stepInProcess) {
        this.createdStepInProcesses.add(stepInProcess);
        stepInProcess.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedStepInProcesses(StepInProcess stepInProcess) {
        this.createdStepInProcesses.remove(stepInProcess);
        stepInProcess.setCreated(null);
        return this;
    }

    public Set<StepInProcess> getModifiedStepInProcesses() {
        return this.modifiedStepInProcesses;
    }

    public void setModifiedStepInProcesses(Set<StepInProcess> stepInProcesses) {
        if (this.modifiedStepInProcesses != null) {
            this.modifiedStepInProcesses.forEach(i -> i.setModified(null));
        }
        if (stepInProcesses != null) {
            stepInProcesses.forEach(i -> i.setModified(this));
        }
        this.modifiedStepInProcesses = stepInProcesses;
    }

    public UserInfo modifiedStepInProcesses(Set<StepInProcess> stepInProcesses) {
        this.setModifiedStepInProcesses(stepInProcesses);
        return this;
    }

    public UserInfo addModifiedStepInProcesses(StepInProcess stepInProcess) {
        this.modifiedStepInProcesses.add(stepInProcess);
        stepInProcess.setModified(this);
        return this;
    }

    public UserInfo removeModifiedStepInProcesses(StepInProcess stepInProcess) {
        this.modifiedStepInProcesses.remove(stepInProcess);
        stepInProcess.setModified(null);
        return this;
    }

    public Set<Step> getCreatedSteps() {
        return this.createdSteps;
    }

    public void setCreatedSteps(Set<Step> steps) {
        if (this.createdSteps != null) {
            this.createdSteps.forEach(i -> i.setCreated(null));
        }
        if (steps != null) {
            steps.forEach(i -> i.setCreated(this));
        }
        this.createdSteps = steps;
    }

    public UserInfo createdSteps(Set<Step> steps) {
        this.setCreatedSteps(steps);
        return this;
    }

    public UserInfo addCreatedSteps(Step step) {
        this.createdSteps.add(step);
        step.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedSteps(Step step) {
        this.createdSteps.remove(step);
        step.setCreated(null);
        return this;
    }

    public Set<Step> getModifiedSteps() {
        return this.modifiedSteps;
    }

    public void setModifiedSteps(Set<Step> steps) {
        if (this.modifiedSteps != null) {
            this.modifiedSteps.forEach(i -> i.setModified(null));
        }
        if (steps != null) {
            steps.forEach(i -> i.setModified(this));
        }
        this.modifiedSteps = steps;
    }

    public UserInfo modifiedSteps(Set<Step> steps) {
        this.setModifiedSteps(steps);
        return this;
    }

    public UserInfo addModifiedSteps(Step step) {
        this.modifiedSteps.add(step);
        step.setModified(this);
        return this;
    }

    public UserInfo removeModifiedSteps(Step step) {
        this.modifiedSteps.remove(step);
        step.setModified(null);
        return this;
    }

    public Set<ProcessInfo> getCreatedProcessInfos() {
        return this.createdProcessInfos;
    }

    public void setCreatedProcessInfos(Set<ProcessInfo> processInfos) {
        if (this.createdProcessInfos != null) {
            this.createdProcessInfos.forEach(i -> i.setCreated(null));
        }
        if (processInfos != null) {
            processInfos.forEach(i -> i.setCreated(this));
        }
        this.createdProcessInfos = processInfos;
    }

    public UserInfo createdProcessInfos(Set<ProcessInfo> processInfos) {
        this.setCreatedProcessInfos(processInfos);
        return this;
    }

    public UserInfo addCreatedProcessInfos(ProcessInfo processInfo) {
        this.createdProcessInfos.add(processInfo);
        processInfo.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedProcessInfos(ProcessInfo processInfo) {
        this.createdProcessInfos.remove(processInfo);
        processInfo.setCreated(null);
        return this;
    }

    public Set<ProcessInfo> getModifiedProcessInfos() {
        return this.modifiedProcessInfos;
    }

    public void setModifiedProcessInfos(Set<ProcessInfo> processInfos) {
        if (this.modifiedProcessInfos != null) {
            this.modifiedProcessInfos.forEach(i -> i.setModified(null));
        }
        if (processInfos != null) {
            processInfos.forEach(i -> i.setModified(this));
        }
        this.modifiedProcessInfos = processInfos;
    }

    public UserInfo modifiedProcessInfos(Set<ProcessInfo> processInfos) {
        this.setModifiedProcessInfos(processInfos);
        return this;
    }

    public UserInfo addModifiedProcessInfos(ProcessInfo processInfo) {
        this.modifiedProcessInfos.add(processInfo);
        processInfo.setModified(this);
        return this;
    }

    public UserInfo removeModifiedProcessInfos(ProcessInfo processInfo) {
        this.modifiedProcessInfos.remove(processInfo);
        processInfo.setModified(null);
        return this;
    }

    public Set<TemplateForm> getCreatedTemplateForms() {
        return this.createdTemplateForms;
    }

    public void setCreatedTemplateForms(Set<TemplateForm> templateForms) {
        if (this.createdTemplateForms != null) {
            this.createdTemplateForms.forEach(i -> i.setCreated(null));
        }
        if (templateForms != null) {
            templateForms.forEach(i -> i.setCreated(this));
        }
        this.createdTemplateForms = templateForms;
    }

    public UserInfo createdTemplateForms(Set<TemplateForm> templateForms) {
        this.setCreatedTemplateForms(templateForms);
        return this;
    }

    public UserInfo addCreatedTemplateForms(TemplateForm templateForm) {
        this.createdTemplateForms.add(templateForm);
        templateForm.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedTemplateForms(TemplateForm templateForm) {
        this.createdTemplateForms.remove(templateForm);
        templateForm.setCreated(null);
        return this;
    }

    public Set<TemplateForm> getModifiedTemplateForms() {
        return this.modifiedTemplateForms;
    }

    public void setModifiedTemplateForms(Set<TemplateForm> templateForms) {
        if (this.modifiedTemplateForms != null) {
            this.modifiedTemplateForms.forEach(i -> i.setModified(null));
        }
        if (templateForms != null) {
            templateForms.forEach(i -> i.setModified(this));
        }
        this.modifiedTemplateForms = templateForms;
    }

    public UserInfo modifiedTemplateForms(Set<TemplateForm> templateForms) {
        this.setModifiedTemplateForms(templateForms);
        return this;
    }

    public UserInfo addModifiedTemplateForms(TemplateForm templateForm) {
        this.modifiedTemplateForms.add(templateForm);
        templateForm.setModified(this);
        return this;
    }

    public UserInfo removeModifiedTemplateForms(TemplateForm templateForm) {
        this.modifiedTemplateForms.remove(templateForm);
        templateForm.setModified(null);
        return this;
    }

    public Set<Request> getCreatedRequests() {
        return this.createdRequests;
    }

    public void setCreatedRequests(Set<Request> requests) {
        if (this.createdRequests != null) {
            this.createdRequests.forEach(i -> i.setCreated(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setCreated(this));
        }
        this.createdRequests = requests;
    }

    public UserInfo createdRequests(Set<Request> requests) {
        this.setCreatedRequests(requests);
        return this;
    }

    public UserInfo addCreatedRequests(Request request) {
        this.createdRequests.add(request);
        request.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedRequests(Request request) {
        this.createdRequests.remove(request);
        request.setCreated(null);
        return this;
    }

    public Set<Request> getModifiedRequests() {
        return this.modifiedRequests;
    }

    public void setModifiedRequests(Set<Request> requests) {
        if (this.modifiedRequests != null) {
            this.modifiedRequests.forEach(i -> i.setModified(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setModified(this));
        }
        this.modifiedRequests = requests;
    }

    public UserInfo modifiedRequests(Set<Request> requests) {
        this.setModifiedRequests(requests);
        return this;
    }

    public UserInfo addModifiedRequests(Request request) {
        this.modifiedRequests.add(request);
        request.setModified(this);
        return this;
    }

    public UserInfo removeModifiedRequests(Request request) {
        this.modifiedRequests.remove(request);
        request.setModified(null);
        return this;
    }

    public Set<RequestType> getCreatedRequestTypes() {
        return this.createdRequestTypes;
    }

    public void setCreatedRequestTypes(Set<RequestType> requestTypes) {
        if (this.createdRequestTypes != null) {
            this.createdRequestTypes.forEach(i -> i.setCreated(null));
        }
        if (requestTypes != null) {
            requestTypes.forEach(i -> i.setCreated(this));
        }
        this.createdRequestTypes = requestTypes;
    }

    public UserInfo createdRequestTypes(Set<RequestType> requestTypes) {
        this.setCreatedRequestTypes(requestTypes);
        return this;
    }

    public UserInfo addCreatedRequestTypes(RequestType requestType) {
        this.createdRequestTypes.add(requestType);
        requestType.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedRequestTypes(RequestType requestType) {
        this.createdRequestTypes.remove(requestType);
        requestType.setCreated(null);
        return this;
    }

    public Set<RequestType> getModifiedRequestTypes() {
        return this.modifiedRequestTypes;
    }

    public void setModifiedRequestTypes(Set<RequestType> requestTypes) {
        if (this.modifiedRequestTypes != null) {
            this.modifiedRequestTypes.forEach(i -> i.setModified(null));
        }
        if (requestTypes != null) {
            requestTypes.forEach(i -> i.setModified(this));
        }
        this.modifiedRequestTypes = requestTypes;
    }

    public UserInfo modifiedRequestTypes(Set<RequestType> requestTypes) {
        this.setModifiedRequestTypes(requestTypes);
        return this;
    }

    public UserInfo addModifiedRequestTypes(RequestType requestType) {
        this.modifiedRequestTypes.add(requestType);
        requestType.setModified(this);
        return this;
    }

    public UserInfo removeModifiedRequestTypes(RequestType requestType) {
        this.modifiedRequestTypes.remove(requestType);
        requestType.setModified(null);
        return this;
    }

    public Set<RequestGroup> getCreatedRequestGroups() {
        return this.createdRequestGroups;
    }

    public void setCreatedRequestGroups(Set<RequestGroup> requestGroups) {
        if (this.createdRequestGroups != null) {
            this.createdRequestGroups.forEach(i -> i.setCreated(null));
        }
        if (requestGroups != null) {
            requestGroups.forEach(i -> i.setCreated(this));
        }
        this.createdRequestGroups = requestGroups;
    }

    public UserInfo createdRequestGroups(Set<RequestGroup> requestGroups) {
        this.setCreatedRequestGroups(requestGroups);
        return this;
    }

    public UserInfo addCreatedRequestGroups(RequestGroup requestGroup) {
        this.createdRequestGroups.add(requestGroup);
        requestGroup.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedRequestGroups(RequestGroup requestGroup) {
        this.createdRequestGroups.remove(requestGroup);
        requestGroup.setCreated(null);
        return this;
    }

    public Set<RequestGroup> getModifiedRequestGroups() {
        return this.modifiedRequestGroups;
    }

    public void setModifiedRequestGroups(Set<RequestGroup> requestGroups) {
        if (this.modifiedRequestGroups != null) {
            this.modifiedRequestGroups.forEach(i -> i.setModified(null));
        }
        if (requestGroups != null) {
            requestGroups.forEach(i -> i.setModified(this));
        }
        this.modifiedRequestGroups = requestGroups;
    }

    public UserInfo modifiedRequestGroups(Set<RequestGroup> requestGroups) {
        this.setModifiedRequestGroups(requestGroups);
        return this;
    }

    public UserInfo addModifiedRequestGroups(RequestGroup requestGroup) {
        this.modifiedRequestGroups.add(requestGroup);
        requestGroup.setModified(this);
        return this;
    }

    public UserInfo removeModifiedRequestGroups(RequestGroup requestGroup) {
        this.modifiedRequestGroups.remove(requestGroup);
        requestGroup.setModified(null);
        return this;
    }

    public Set<ProcessData> getCreatedProcessDatas() {
        return this.createdProcessDatas;
    }

    public void setCreatedProcessDatas(Set<ProcessData> processData) {
        if (this.createdProcessDatas != null) {
            this.createdProcessDatas.forEach(i -> i.setCreated(null));
        }
        if (processData != null) {
            processData.forEach(i -> i.setCreated(this));
        }
        this.createdProcessDatas = processData;
    }

    public UserInfo createdProcessDatas(Set<ProcessData> processData) {
        this.setCreatedProcessDatas(processData);
        return this;
    }

    public UserInfo addCreatedProcessDatas(ProcessData processData) {
        this.createdProcessDatas.add(processData);
        processData.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedProcessDatas(ProcessData processData) {
        this.createdProcessDatas.remove(processData);
        processData.setCreated(null);
        return this;
    }

    public Set<ProcessData> getModifiedProcessDatas() {
        return this.modifiedProcessDatas;
    }

    public void setModifiedProcessDatas(Set<ProcessData> processData) {
        if (this.modifiedProcessDatas != null) {
            this.modifiedProcessDatas.forEach(i -> i.setModified(null));
        }
        if (processData != null) {
            processData.forEach(i -> i.setModified(this));
        }
        this.modifiedProcessDatas = processData;
    }

    public UserInfo modifiedProcessDatas(Set<ProcessData> processData) {
        this.setModifiedProcessDatas(processData);
        return this;
    }

    public UserInfo addModifiedProcessDatas(ProcessData processData) {
        this.modifiedProcessDatas.add(processData);
        processData.setModified(this);
        return this;
    }

    public UserInfo removeModifiedProcessDatas(ProcessData processData) {
        this.modifiedProcessDatas.remove(processData);
        processData.setModified(null);
        return this;
    }

    public Set<StepData> getCreatedStepDatas() {
        return this.createdStepDatas;
    }

    public void setCreatedStepDatas(Set<StepData> stepData) {
        if (this.createdStepDatas != null) {
            this.createdStepDatas.forEach(i -> i.setCreated(null));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.setCreated(this));
        }
        this.createdStepDatas = stepData;
    }

    public UserInfo createdStepDatas(Set<StepData> stepData) {
        this.setCreatedStepDatas(stepData);
        return this;
    }

    public UserInfo addCreatedStepDatas(StepData stepData) {
        this.createdStepDatas.add(stepData);
        stepData.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedStepDatas(StepData stepData) {
        this.createdStepDatas.remove(stepData);
        stepData.setCreated(null);
        return this;
    }

    public Set<StepData> getModifiedStepDatas() {
        return this.modifiedStepDatas;
    }

    public void setModifiedStepDatas(Set<StepData> stepData) {
        if (this.modifiedStepDatas != null) {
            this.modifiedStepDatas.forEach(i -> i.setModified(null));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.setModified(this));
        }
        this.modifiedStepDatas = stepData;
    }

    public UserInfo modifiedStepDatas(Set<StepData> stepData) {
        this.setModifiedStepDatas(stepData);
        return this;
    }

    public UserInfo addModifiedStepDatas(StepData stepData) {
        this.modifiedStepDatas.add(stepData);
        stepData.setModified(this);
        return this;
    }

    public UserInfo removeModifiedStepDatas(StepData stepData) {
        this.modifiedStepDatas.remove(stepData);
        stepData.setModified(null);
        return this;
    }

    public Set<RequestData> getCreatedRequestDatas() {
        return this.createdRequestDatas;
    }

    public void setCreatedRequestDatas(Set<RequestData> requestData) {
        if (this.createdRequestDatas != null) {
            this.createdRequestDatas.forEach(i -> i.setCreated(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setCreated(this));
        }
        this.createdRequestDatas = requestData;
    }

    public UserInfo createdRequestDatas(Set<RequestData> requestData) {
        this.setCreatedRequestDatas(requestData);
        return this;
    }

    public UserInfo addCreatedRequestDatas(RequestData requestData) {
        this.createdRequestDatas.add(requestData);
        requestData.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedRequestDatas(RequestData requestData) {
        this.createdRequestDatas.remove(requestData);
        requestData.setCreated(null);
        return this;
    }

    public Set<RequestData> getModifiedRequestDatas() {
        return this.modifiedRequestDatas;
    }

    public void setModifiedRequestDatas(Set<RequestData> requestData) {
        if (this.modifiedRequestDatas != null) {
            this.modifiedRequestDatas.forEach(i -> i.setModified(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setModified(this));
        }
        this.modifiedRequestDatas = requestData;
    }

    public UserInfo modifiedRequestDatas(Set<RequestData> requestData) {
        this.setModifiedRequestDatas(requestData);
        return this;
    }

    public UserInfo addModifiedRequestDatas(RequestData requestData) {
        this.modifiedRequestDatas.add(requestData);
        requestData.setModified(this);
        return this;
    }

    public UserInfo removeModifiedRequestDatas(RequestData requestData) {
        this.modifiedRequestDatas.remove(requestData);
        requestData.setModified(null);
        return this;
    }

    public Set<RequestData> getApprovedRequestDatas() {
        return this.approvedRequestDatas;
    }

    public void setApprovedRequestDatas(Set<RequestData> requestData) {
        if (this.approvedRequestDatas != null) {
            this.approvedRequestDatas.forEach(i -> i.setApprover(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setApprover(this));
        }
        this.approvedRequestDatas = requestData;
    }

    public UserInfo approvedRequestDatas(Set<RequestData> requestData) {
        this.setApprovedRequestDatas(requestData);
        return this;
    }

    public UserInfo addApprovedRequestDatas(RequestData requestData) {
        this.approvedRequestDatas.add(requestData);
        requestData.setApprover(this);
        return this;
    }

    public UserInfo removeApprovedRequestDatas(RequestData requestData) {
        this.approvedRequestDatas.remove(requestData);
        requestData.setApprover(null);
        return this;
    }

    public Set<RequestData> getRevokedRequestDatas() {
        return this.revokedRequestDatas;
    }

    public void setRevokedRequestDatas(Set<RequestData> requestData) {
        if (this.revokedRequestDatas != null) {
            this.revokedRequestDatas.forEach(i -> i.setRevoker(null));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.setRevoker(this));
        }
        this.revokedRequestDatas = requestData;
    }

    public UserInfo revokedRequestDatas(Set<RequestData> requestData) {
        this.setRevokedRequestDatas(requestData);
        return this;
    }

    public UserInfo addRevokedRequestDatas(RequestData requestData) {
        this.revokedRequestDatas.add(requestData);
        requestData.setRevoker(this);
        return this;
    }

    public UserInfo removeRevokedRequestDatas(RequestData requestData) {
        this.revokedRequestDatas.remove(requestData);
        requestData.setRevoker(null);
        return this;
    }

    public Set<Status> getCreatedStatuses() {
        return this.createdStatuses;
    }

    public void setCreatedStatuses(Set<Status> statuses) {
        if (this.createdStatuses != null) {
            this.createdStatuses.forEach(i -> i.setCreated(null));
        }
        if (statuses != null) {
            statuses.forEach(i -> i.setCreated(this));
        }
        this.createdStatuses = statuses;
    }

    public UserInfo createdStatuses(Set<Status> statuses) {
        this.setCreatedStatuses(statuses);
        return this;
    }

    public UserInfo addCreatedStatus(Status status) {
        this.createdStatuses.add(status);
        status.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedStatus(Status status) {
        this.createdStatuses.remove(status);
        status.setCreated(null);
        return this;
    }

    public Set<Status> getModifiedStatuses() {
        return this.modifiedStatuses;
    }

    public void setModifiedStatuses(Set<Status> statuses) {
        if (this.modifiedStatuses != null) {
            this.modifiedStatuses.forEach(i -> i.setModified(null));
        }
        if (statuses != null) {
            statuses.forEach(i -> i.setModified(this));
        }
        this.modifiedStatuses = statuses;
    }

    public UserInfo modifiedStatuses(Set<Status> statuses) {
        this.setModifiedStatuses(statuses);
        return this;
    }

    public UserInfo addModifiedStatus(Status status) {
        this.modifiedStatuses.add(status);
        status.setModified(this);
        return this;
    }

    public UserInfo removeModifiedStatus(Status status) {
        this.modifiedStatuses.remove(status);
        status.setModified(null);
        return this;
    }

    public Set<FormData> getCreatedFormDatas() {
        return this.createdFormDatas;
    }

    public void setCreatedFormDatas(Set<FormData> formData) {
        if (this.createdFormDatas != null) {
            this.createdFormDatas.forEach(i -> i.setCreated(null));
        }
        if (formData != null) {
            formData.forEach(i -> i.setCreated(this));
        }
        this.createdFormDatas = formData;
    }

    public UserInfo createdFormDatas(Set<FormData> formData) {
        this.setCreatedFormDatas(formData);
        return this;
    }

    public UserInfo addCreatedFormDatas(FormData formData) {
        this.createdFormDatas.add(formData);
        formData.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedFormDatas(FormData formData) {
        this.createdFormDatas.remove(formData);
        formData.setCreated(null);
        return this;
    }

    public Set<FormData> getModifiedFormDatas() {
        return this.modifiedFormDatas;
    }

    public void setModifiedFormDatas(Set<FormData> formData) {
        if (this.modifiedFormDatas != null) {
            this.modifiedFormDatas.forEach(i -> i.setModified(null));
        }
        if (formData != null) {
            formData.forEach(i -> i.setModified(this));
        }
        this.modifiedFormDatas = formData;
    }

    public UserInfo modifiedFormDatas(Set<FormData> formData) {
        this.setModifiedFormDatas(formData);
        return this;
    }

    public UserInfo addModifiedFormDatas(FormData formData) {
        this.modifiedFormDatas.add(formData);
        formData.setModified(this);
        return this;
    }

    public UserInfo removeModifiedFormDatas(FormData formData) {
        this.modifiedFormDatas.remove(formData);
        formData.setModified(null);
        return this;
    }

    public Set<FieldData> getCreatedFieldDatas() {
        return this.createdFieldDatas;
    }

    public void setCreatedFieldDatas(Set<FieldData> fieldData) {
        if (this.createdFieldDatas != null) {
            this.createdFieldDatas.forEach(i -> i.setCreated(null));
        }
        if (fieldData != null) {
            fieldData.forEach(i -> i.setCreated(this));
        }
        this.createdFieldDatas = fieldData;
    }

    public UserInfo createdFieldDatas(Set<FieldData> fieldData) {
        this.setCreatedFieldDatas(fieldData);
        return this;
    }

    public UserInfo addCreatedFieldDatas(FieldData fieldData) {
        this.createdFieldDatas.add(fieldData);
        fieldData.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedFieldDatas(FieldData fieldData) {
        this.createdFieldDatas.remove(fieldData);
        fieldData.setCreated(null);
        return this;
    }

    public Set<FieldData> getModifiedFieldDatas() {
        return this.modifiedFieldDatas;
    }

    public void setModifiedFieldDatas(Set<FieldData> fieldData) {
        if (this.modifiedFieldDatas != null) {
            this.modifiedFieldDatas.forEach(i -> i.setModified(null));
        }
        if (fieldData != null) {
            fieldData.forEach(i -> i.setModified(this));
        }
        this.modifiedFieldDatas = fieldData;
    }

    public UserInfo modifiedFieldDatas(Set<FieldData> fieldData) {
        this.setModifiedFieldDatas(fieldData);
        return this;
    }

    public UserInfo addModifiedFieldDatas(FieldData fieldData) {
        this.modifiedFieldDatas.add(fieldData);
        fieldData.setModified(this);
        return this;
    }

    public UserInfo removeModifiedFieldDatas(FieldData fieldData) {
        this.modifiedFieldDatas.remove(fieldData);
        fieldData.setModified(null);
        return this;
    }

    public Set<AttachmentFile> getCreatedAttachmentFiles() {
        return this.createdAttachmentFiles;
    }

    public void setCreatedAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.createdAttachmentFiles != null) {
            this.createdAttachmentFiles.forEach(i -> i.setCreated(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setCreated(this));
        }
        this.createdAttachmentFiles = attachmentFiles;
    }

    public UserInfo createdAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setCreatedAttachmentFiles(attachmentFiles);
        return this;
    }

    public UserInfo addCreatedAttachmentFiles(AttachmentFile attachmentFile) {
        this.createdAttachmentFiles.add(attachmentFile);
        attachmentFile.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedAttachmentFiles(AttachmentFile attachmentFile) {
        this.createdAttachmentFiles.remove(attachmentFile);
        attachmentFile.setCreated(null);
        return this;
    }

    public Set<AttachmentFile> getModifiedAttachmentFiles() {
        return this.modifiedAttachmentFiles;
    }

    public void setModifiedAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.modifiedAttachmentFiles != null) {
            this.modifiedAttachmentFiles.forEach(i -> i.setModified(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setModified(this));
        }
        this.modifiedAttachmentFiles = attachmentFiles;
    }

    public UserInfo modifiedAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setModifiedAttachmentFiles(attachmentFiles);
        return this;
    }

    public UserInfo addModifiedAttachmentFiles(AttachmentFile attachmentFile) {
        this.modifiedAttachmentFiles.add(attachmentFile);
        attachmentFile.setModified(this);
        return this;
    }

    public UserInfo removeModifiedAttachmentFiles(AttachmentFile attachmentFile) {
        this.modifiedAttachmentFiles.remove(attachmentFile);
        attachmentFile.setModified(null);
        return this;
    }

    public Set<FileType> getCreatedFileTypes() {
        return this.createdFileTypes;
    }

    public void setCreatedFileTypes(Set<FileType> fileTypes) {
        if (this.createdFileTypes != null) {
            this.createdFileTypes.forEach(i -> i.setCreated(null));
        }
        if (fileTypes != null) {
            fileTypes.forEach(i -> i.setCreated(this));
        }
        this.createdFileTypes = fileTypes;
    }

    public UserInfo createdFileTypes(Set<FileType> fileTypes) {
        this.setCreatedFileTypes(fileTypes);
        return this;
    }

    public UserInfo addCreatedFileTypes(FileType fileType) {
        this.createdFileTypes.add(fileType);
        fileType.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedFileTypes(FileType fileType) {
        this.createdFileTypes.remove(fileType);
        fileType.setCreated(null);
        return this;
    }

    public Set<FileType> getModifiedFileTypes() {
        return this.modifiedFileTypes;
    }

    public void setModifiedFileTypes(Set<FileType> fileTypes) {
        if (this.modifiedFileTypes != null) {
            this.modifiedFileTypes.forEach(i -> i.setModified(null));
        }
        if (fileTypes != null) {
            fileTypes.forEach(i -> i.setModified(this));
        }
        this.modifiedFileTypes = fileTypes;
    }

    public UserInfo modifiedFileTypes(Set<FileType> fileTypes) {
        this.setModifiedFileTypes(fileTypes);
        return this;
    }

    public UserInfo addModifiedFileTypes(FileType fileType) {
        this.modifiedFileTypes.add(fileType);
        fileType.setModified(this);
        return this;
    }

    public UserInfo removeModifiedFileTypes(FileType fileType) {
        this.modifiedFileTypes.remove(fileType);
        fileType.setModified(null);
        return this;
    }

    public Set<ReqdataChangeHis> getCreatedReqdataChangeHis() {
        return this.createdReqdataChangeHis;
    }

    public void setCreatedReqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        if (this.createdReqdataChangeHis != null) {
            this.createdReqdataChangeHis.forEach(i -> i.setCreated(null));
        }
        if (reqdataChangeHis != null) {
            reqdataChangeHis.forEach(i -> i.setCreated(this));
        }
        this.createdReqdataChangeHis = reqdataChangeHis;
    }

    public UserInfo createdReqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        this.setCreatedReqdataChangeHis(reqdataChangeHis);
        return this;
    }

    public UserInfo addCreatedReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.createdReqdataChangeHis.add(reqdataChangeHis);
        reqdataChangeHis.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.createdReqdataChangeHis.remove(reqdataChangeHis);
        reqdataChangeHis.setCreated(null);
        return this;
    }

    public Set<ReqdataChangeHis> getModifiedReqdataChangeHis() {
        return this.modifiedReqdataChangeHis;
    }

    public void setModifiedReqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        if (this.modifiedReqdataChangeHis != null) {
            this.modifiedReqdataChangeHis.forEach(i -> i.setModified(null));
        }
        if (reqdataChangeHis != null) {
            reqdataChangeHis.forEach(i -> i.setModified(this));
        }
        this.modifiedReqdataChangeHis = reqdataChangeHis;
    }

    public UserInfo modifiedReqdataChangeHis(Set<ReqdataChangeHis> reqdataChangeHis) {
        this.setModifiedReqdataChangeHis(reqdataChangeHis);
        return this;
    }

    public UserInfo addModifiedReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.modifiedReqdataChangeHis.add(reqdataChangeHis);
        reqdataChangeHis.setModified(this);
        return this;
    }

    public UserInfo removeModifiedReqdataChangeHis(ReqdataChangeHis reqdataChangeHis) {
        this.modifiedReqdataChangeHis.remove(reqdataChangeHis);
        reqdataChangeHis.setModified(null);
        return this;
    }

    public Set<CategoryData> getCreatedCategoryDatas() {
        return this.createdCategoryDatas;
    }

    public void setCreatedCategoryDatas(Set<CategoryData> categoryData) {
        if (this.createdCategoryDatas != null) {
            this.createdCategoryDatas.forEach(i -> i.setCreated(null));
        }
        if (categoryData != null) {
            categoryData.forEach(i -> i.setCreated(this));
        }
        this.createdCategoryDatas = categoryData;
    }

    public UserInfo createdCategoryDatas(Set<CategoryData> categoryData) {
        this.setCreatedCategoryDatas(categoryData);
        return this;
    }

    public UserInfo addCreatedCategoryDatas(CategoryData categoryData) {
        this.createdCategoryDatas.add(categoryData);
        categoryData.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedCategoryDatas(CategoryData categoryData) {
        this.createdCategoryDatas.remove(categoryData);
        categoryData.setCreated(null);
        return this;
    }

    public Set<CategoryData> getModifiedCategoryDatas() {
        return this.modifiedCategoryDatas;
    }

    public void setModifiedCategoryDatas(Set<CategoryData> categoryData) {
        if (this.modifiedCategoryDatas != null) {
            this.modifiedCategoryDatas.forEach(i -> i.setModified(null));
        }
        if (categoryData != null) {
            categoryData.forEach(i -> i.setModified(this));
        }
        this.modifiedCategoryDatas = categoryData;
    }

    public UserInfo modifiedCategoryDatas(Set<CategoryData> categoryData) {
        this.setModifiedCategoryDatas(categoryData);
        return this;
    }

    public UserInfo addModifiedCategoryDatas(CategoryData categoryData) {
        this.modifiedCategoryDatas.add(categoryData);
        categoryData.setModified(this);
        return this;
    }

    public UserInfo removeModifiedCategoryDatas(CategoryData categoryData) {
        this.modifiedCategoryDatas.remove(categoryData);
        categoryData.setModified(null);
        return this;
    }

    public Set<CategoryGroup> getCreatedCategoryGroups() {
        return this.createdCategoryGroups;
    }

    public void setCreatedCategoryGroups(Set<CategoryGroup> categoryGroups) {
        if (this.createdCategoryGroups != null) {
            this.createdCategoryGroups.forEach(i -> i.setCreated(null));
        }
        if (categoryGroups != null) {
            categoryGroups.forEach(i -> i.setCreated(this));
        }
        this.createdCategoryGroups = categoryGroups;
    }

    public UserInfo createdCategoryGroups(Set<CategoryGroup> categoryGroups) {
        this.setCreatedCategoryGroups(categoryGroups);
        return this;
    }

    public UserInfo addCreatedCategoryGroups(CategoryGroup categoryGroup) {
        this.createdCategoryGroups.add(categoryGroup);
        categoryGroup.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedCategoryGroups(CategoryGroup categoryGroup) {
        this.createdCategoryGroups.remove(categoryGroup);
        categoryGroup.setCreated(null);
        return this;
    }

    public Set<CategoryGroup> getModifiedCategoryGroups() {
        return this.modifiedCategoryGroups;
    }

    public void setModifiedCategoryGroups(Set<CategoryGroup> categoryGroups) {
        if (this.modifiedCategoryGroups != null) {
            this.modifiedCategoryGroups.forEach(i -> i.setModified(null));
        }
        if (categoryGroups != null) {
            categoryGroups.forEach(i -> i.setModified(this));
        }
        this.modifiedCategoryGroups = categoryGroups;
    }

    public UserInfo modifiedCategoryGroups(Set<CategoryGroup> categoryGroups) {
        this.setModifiedCategoryGroups(categoryGroups);
        return this;
    }

    public UserInfo addModifiedCategoryGroups(CategoryGroup categoryGroup) {
        this.modifiedCategoryGroups.add(categoryGroup);
        categoryGroup.setModified(this);
        return this;
    }

    public UserInfo removeModifiedCategoryGroups(CategoryGroup categoryGroup) {
        this.modifiedCategoryGroups.remove(categoryGroup);
        categoryGroup.setModified(null);
        return this;
    }

    public Set<SignatureInfomation> getSignatureInfomations() {
        return this.signatureInfomations;
    }

    public void setSignatureInfomations(Set<SignatureInfomation> signatureInfomations) {
        if (this.signatureInfomations != null) {
            this.signatureInfomations.forEach(i -> i.setUserInfo(null));
        }
        if (signatureInfomations != null) {
            signatureInfomations.forEach(i -> i.setUserInfo(this));
        }
        this.signatureInfomations = signatureInfomations;
    }

    public UserInfo signatureInfomations(Set<SignatureInfomation> signatureInfomations) {
        this.setSignatureInfomations(signatureInfomations);
        return this;
    }

    public UserInfo addSignatureInfomation(SignatureInfomation signatureInfomation) {
        this.signatureInfomations.add(signatureInfomation);
        signatureInfomation.setUserInfo(this);
        return this;
    }

    public UserInfo removeSignatureInfomation(SignatureInfomation signatureInfomation) {
        this.signatureInfomations.remove(signatureInfomation);
        signatureInfomation.setUserInfo(null);
        return this;
    }

    public Set<AttachmentPermisition> getAttachmentPermisitions() {
        return this.attachmentPermisitions;
    }

    public void setAttachmentPermisitions(Set<AttachmentPermisition> attachmentPermisitions) {
        if (this.attachmentPermisitions != null) {
            this.attachmentPermisitions.forEach(i -> i.setUserInfo(null));
        }
        if (attachmentPermisitions != null) {
            attachmentPermisitions.forEach(i -> i.setUserInfo(this));
        }
        this.attachmentPermisitions = attachmentPermisitions;
    }

    public UserInfo attachmentPermisitions(Set<AttachmentPermisition> attachmentPermisitions) {
        this.setAttachmentPermisitions(attachmentPermisitions);
        return this;
    }

    public UserInfo addAttachmentPermisition(AttachmentPermisition attachmentPermisition) {
        this.attachmentPermisitions.add(attachmentPermisition);
        attachmentPermisition.setUserInfo(this);
        return this;
    }

    public UserInfo removeAttachmentPermisition(AttachmentPermisition attachmentPermisition) {
        this.attachmentPermisitions.remove(attachmentPermisition);
        attachmentPermisition.setUserInfo(null);
        return this;
    }

    public Set<AttachmentPermisition> getCreatedAttachmentPermisions() {
        return this.createdAttachmentPermisions;
    }

    public void setCreatedAttachmentPermisions(Set<AttachmentPermisition> attachmentPermisitions) {
        if (this.createdAttachmentPermisions != null) {
            this.createdAttachmentPermisions.forEach(i -> i.setCreated(null));
        }
        if (attachmentPermisitions != null) {
            attachmentPermisitions.forEach(i -> i.setCreated(this));
        }
        this.createdAttachmentPermisions = attachmentPermisitions;
    }

    public UserInfo createdAttachmentPermisions(Set<AttachmentPermisition> attachmentPermisitions) {
        this.setCreatedAttachmentPermisions(attachmentPermisitions);
        return this;
    }

    public UserInfo addCreatedAttachmentPermision(AttachmentPermisition attachmentPermisition) {
        this.createdAttachmentPermisions.add(attachmentPermisition);
        attachmentPermisition.setCreated(this);
        return this;
    }

    public UserInfo removeCreatedAttachmentPermision(AttachmentPermisition attachmentPermisition) {
        this.createdAttachmentPermisions.remove(attachmentPermisition);
        attachmentPermisition.setCreated(null);
        return this;
    }

    public Set<AttachmentPermisition> getModifiedAttachmentPermisions() {
        return this.modifiedAttachmentPermisions;
    }

    public void setModifiedAttachmentPermisions(Set<AttachmentPermisition> attachmentPermisitions) {
        if (this.modifiedAttachmentPermisions != null) {
            this.modifiedAttachmentPermisions.forEach(i -> i.setModified(null));
        }
        if (attachmentPermisitions != null) {
            attachmentPermisitions.forEach(i -> i.setModified(this));
        }
        this.modifiedAttachmentPermisions = attachmentPermisitions;
    }

    public UserInfo modifiedAttachmentPermisions(Set<AttachmentPermisition> attachmentPermisitions) {
        this.setModifiedAttachmentPermisions(attachmentPermisitions);
        return this;
    }

    public UserInfo addModifiedAttachmentPermision(AttachmentPermisition attachmentPermisition) {
        this.modifiedAttachmentPermisions.add(attachmentPermisition);
        attachmentPermisition.setModified(this);
        return this;
    }

    public UserInfo removeModifiedAttachmentPermision(AttachmentPermisition attachmentPermisition) {
        this.modifiedAttachmentPermisions.remove(attachmentPermisition);
        attachmentPermisition.setModified(null);
        return this;
    }

    public Set<SignData> getSignData() {
        return this.signData;
    }

    public void setSignData(Set<SignData> signData) {
        if (this.signData != null) {
            this.signData.forEach(i -> i.setUserInfo(null));
        }
        if (signData != null) {
            signData.forEach(i -> i.setUserInfo(this));
        }
        this.signData = signData;
    }

    public UserInfo signData(Set<SignData> signData) {
        this.setSignData(signData);
        return this;
    }

    public UserInfo addSignData(SignData signData) {
        this.signData.add(signData);
        signData.setUserInfo(this);
        return this;
    }

    public UserInfo removeSignData(SignData signData) {
        this.signData.remove(signData);
        signData.setUserInfo(null);
        return this;
    }

    public Set<RequestData> getRequestData() {
        return this.requestData;
    }

    public void setRequestData(Set<RequestData> requestData) {
        if (this.requestData != null) {
            this.requestData.forEach(i -> i.removeUserInfo(this));
        }
        if (requestData != null) {
            requestData.forEach(i -> i.addUserInfo(this));
        }
        this.requestData = requestData;
    }

    public UserInfo requestData(Set<RequestData> requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public UserInfo addRequestData(RequestData requestData) {
        this.requestData.add(requestData);
        requestData.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeRequestData(RequestData requestData) {
        this.requestData.remove(requestData);
        requestData.getUserInfos().remove(this);
        return this;
    }

    public Set<StepData> getStepData() {
        return this.stepData;
    }

    public void setStepData(Set<StepData> stepData) {
        if (this.stepData != null) {
            this.stepData.forEach(i -> i.removeUserInfo(this));
        }
        if (stepData != null) {
            stepData.forEach(i -> i.addUserInfo(this));
        }
        this.stepData = stepData;
    }

    public UserInfo stepData(Set<StepData> stepData) {
        this.setStepData(stepData);
        return this;
    }

    public UserInfo addStepData(StepData stepData) {
        this.stepData.add(stepData);
        stepData.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeStepData(StepData stepData) {
        this.stepData.remove(stepData);
        stepData.getUserInfos().remove(this);
        return this;
    }

    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        if (this.userGroups != null) {
            this.userGroups.forEach(i -> i.removeUserInfo(this));
        }
        if (userGroups != null) {
            userGroups.forEach(i -> i.addUserInfo(this));
        }
        this.userGroups = userGroups;
    }

    public UserInfo userGroups(Set<UserGroup> userGroups) {
        this.setUserGroups(userGroups);
        return this;
    }

    public UserInfo addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
        userGroup.getUserInfos().add(this);
        return this;
    }

    public UserInfo removeUserGroup(UserGroup userGroup) {
        this.userGroups.remove(userGroup);
        userGroup.getUserInfos().remove(this);
        return this;
    }

    public Set<OfficialDispatch> getOffDispatchUserReads() {
        return this.offDispatchUserReads;
    }

    public void setOffDispatchUserReads(Set<OfficialDispatch> officialDispatches) {
        if (this.offDispatchUserReads != null) {
            this.offDispatchUserReads.forEach(i -> i.removeOffDispatchUserRead(this));
        }
        if (officialDispatches != null) {
            officialDispatches.forEach(i -> i.addOffDispatchUserRead(this));
        }
        this.offDispatchUserReads = officialDispatches;
    }

    public UserInfo offDispatchUserReads(Set<OfficialDispatch> officialDispatches) {
        this.setOffDispatchUserReads(officialDispatches);
        return this;
    }

    public UserInfo addOffDispatchUserRead(OfficialDispatch officialDispatch) {
        this.offDispatchUserReads.add(officialDispatch);
        officialDispatch.getOffDispatchUserReads().add(this);
        return this;
    }

    public UserInfo removeOffDispatchUserRead(OfficialDispatch officialDispatch) {
        this.offDispatchUserReads.remove(officialDispatch);
        officialDispatch.getOffDispatchUserReads().remove(this);
        return this;
    }

    public Set<TransferHandle> getReceiversHandles() {
        return this.receiversHandles;
    }

    public void setReceiversHandles(Set<TransferHandle> transferHandles) {
        if (this.receiversHandles != null) {
            this.receiversHandles.forEach(i -> i.removeReceiversHandle(this));
        }
        if (transferHandles != null) {
            transferHandles.forEach(i -> i.addReceiversHandle(this));
        }
        this.receiversHandles = transferHandles;
    }

    public UserInfo receiversHandles(Set<TransferHandle> transferHandles) {
        this.setReceiversHandles(transferHandles);
        return this;
    }

    public UserInfo addReceiversHandle(TransferHandle transferHandle) {
        this.receiversHandles.add(transferHandle);
        transferHandle.getReceiversHandles().add(this);
        return this;
    }

    public UserInfo removeReceiversHandle(TransferHandle transferHandle) {
        this.receiversHandles.remove(transferHandle);
        transferHandle.getReceiversHandles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactAddress='" + getContactAddress() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", issuseDate='" + getIssuseDate() + "'" +
            ", issuseOrg='" + getIssuseOrg() + "'" +
            ", fax='" + getFax() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", email='" + getEmail() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", passwordEncode='" + getPasswordEncode() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", numberOfLoginFailed=" + getNumberOfLoginFailed() +
            ", description='" + getDescription() + "'" +
            ", isLocked='" + getIsLocked() + "'" +
            ", userTypeName='" + getUserTypeName() + "'" +
            ", userTypeCode='" + getUserTypeCode() + "'" +
            ", userType='" + getUserType() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", signType='" + getSignType() + "'" +
            ", signFolder='" + getSignFolder() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", idInMicrosoft='" + getIdInMicrosoft() + "'" +
            ", oDataContext='" + getoDataContext() + "'" +
            ", businessPhones='" + getBusinessPhones() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", givenName='" + getGivenName() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", mail='" + getMail() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", officeLocation='" + getOfficeLocation() + "'" +
            ", preferredLanguage='" + getPreferredLanguage() + "'" +
            ", surname='" + getSurname() + "'" +
            ", userPrincipalName='" + getUserPrincipalName() + "'" +
            ", infoInMicrosoft='" + getInfoInMicrosoft() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
