package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A FieldData.
 */
@Entity
@Table(name = "field_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fielddata")
public class FieldData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "field_data_code")
    private String fieldDataCode;

    @Column(name = "field_data_name")
    private String fieldDataName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "object_schema")
    private String objectSchema;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "object_model")
    private String objectModel;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "option")
    private String option;

    @Column(name = "description")
    private String description;

    @Column(name = "name_of_field")
    private String nameOfField;

    @Column(name = "field_pattern")
    private String fieldPattern;

    @Column(name = "required")
    private String required;

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

    @Column(name = "row")
    private Long row;

    @Column(name = "col")
    private Long col;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "fieldData", "fieldInForms", "forms" }, allowSetters = true)
    private Field field;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requestData", "form", "tennant", "created", "modified", "fieldData" }, allowSetters = true)
    private FormData formData;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "field", "form", "fieldData" }, allowSetters = true)
    private FieldInForm fieldInForm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldDataCode() {
        return this.fieldDataCode;
    }

    public FieldData fieldDataCode(String fieldDataCode) {
        this.setFieldDataCode(fieldDataCode);
        return this;
    }

    public void setFieldDataCode(String fieldDataCode) {
        this.fieldDataCode = fieldDataCode;
    }

    public String getFieldDataName() {
        return this.fieldDataName;
    }

    public FieldData fieldDataName(String fieldDataName) {
        this.setFieldDataName(fieldDataName);
        return this;
    }

    public void setFieldDataName(String fieldDataName) {
        this.fieldDataName = fieldDataName;
    }

    public String getObjectSchema() {
        return this.objectSchema;
    }

    public FieldData objectSchema(String objectSchema) {
        this.setObjectSchema(objectSchema);
        return this;
    }

    public void setObjectSchema(String objectSchema) {
        this.objectSchema = objectSchema;
    }

    public String getObjectModel() {
        return this.objectModel;
    }

    public FieldData objectModel(String objectModel) {
        this.setObjectModel(objectModel);
        return this;
    }

    public void setObjectModel(String objectModel) {
        this.objectModel = objectModel;
    }

    public String getOption() {
        return this.option;
    }

    public FieldData option(String option) {
        this.setOption(option);
        return this;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDescription() {
        return this.description;
    }

    public FieldData description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameOfField() {
        return this.nameOfField;
    }

    public FieldData nameOfField(String nameOfField) {
        this.setNameOfField(nameOfField);
        return this;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public String getFieldPattern() {
        return this.fieldPattern;
    }

    public FieldData fieldPattern(String fieldPattern) {
        this.setFieldPattern(fieldPattern);
        return this;
    }

    public void setFieldPattern(String fieldPattern) {
        this.fieldPattern = fieldPattern;
    }

    public String getRequired() {
        return this.required;
    }

    public FieldData required(String required) {
        this.setRequired(required);
        return this;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public Long getOrder() {
        return this.order;
    }

    public FieldData order(Long order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public FieldData createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public FieldData createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public FieldData createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public FieldData createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public FieldData modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public FieldData modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FieldData isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getRow() {
        return this.row;
    }

    public FieldData row(Long row) {
        this.setRow(row);
        return this;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getCol() {
        return this.col;
    }

    public FieldData col(Long col) {
        this.setCol(col);
        return this;
    }

    public void setCol(Long col) {
        this.col = col;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public FieldData tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public FieldData tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public FieldData field(Field field) {
        this.setField(field);
        return this;
    }

    public FormData getFormData() {
        return this.formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    public FieldData formData(FormData formData) {
        this.setFormData(formData);
        return this;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public FieldData requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public FieldData tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public FieldData created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public FieldData modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public FieldInForm getFieldInForm() {
        return this.fieldInForm;
    }

    public void setFieldInForm(FieldInForm fieldInForm) {
        this.fieldInForm = fieldInForm;
    }

    public FieldData fieldInForm(FieldInForm fieldInForm) {
        this.setFieldInForm(fieldInForm);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldData)) {
            return false;
        }
        return id != null && id.equals(((FieldData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldData{" +
            "id=" + getId() +
            ", fieldDataCode='" + getFieldDataCode() + "'" +
            ", fieldDataName='" + getFieldDataName() + "'" +
            ", objectSchema='" + getObjectSchema() + "'" +
            ", objectModel='" + getObjectModel() + "'" +
            ", option='" + getOption() + "'" +
            ", description='" + getDescription() + "'" +
            ", nameOfField='" + getNameOfField() + "'" +
            ", fieldPattern='" + getFieldPattern() + "'" +
            ", required='" + getRequired() + "'" +
            ", order=" + getOrder() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", row=" + getRow() +
            ", col=" + getCol() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
