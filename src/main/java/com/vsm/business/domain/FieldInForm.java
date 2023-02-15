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
 * A FieldInForm.
 */
@Entity
@Table(name = "field_in_form")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fieldinform")
public class FieldInForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "field_in_form_code")
    private String fieldInFormCode;

    @Column(name = "field_in_form_name")
    private String fieldInFormName;

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

    @Column(name = "row")
    private Long row;

    @Column(name = "col")
    private Long col;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "fieldData", "fieldInForms", "forms" }, allowSetters = true)
    private Field field;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "fields", "requests", "formData", "fieldInForms" },
        allowSetters = true
    )
    private Form form;

    @OneToMany(mappedBy = "fieldInForm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "field", "formData", "requestData", "tennant", "created", "modified", "fieldInForm" },
        allowSetters = true
    )
    private Set<FieldData> fieldData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldInForm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldInFormCode() {
        return this.fieldInFormCode;
    }

    public FieldInForm fieldInFormCode(String fieldInFormCode) {
        this.setFieldInFormCode(fieldInFormCode);
        return this;
    }

    public void setFieldInFormCode(String fieldInFormCode) {
        this.fieldInFormCode = fieldInFormCode;
    }

    public String getFieldInFormName() {
        return this.fieldInFormName;
    }

    public FieldInForm fieldInFormName(String fieldInFormName) {
        this.setFieldInFormName(fieldInFormName);
        return this;
    }

    public void setFieldInFormName(String fieldInFormName) {
        this.fieldInFormName = fieldInFormName;
    }

    public String getObjectSchema() {
        return this.objectSchema;
    }

    public FieldInForm objectSchema(String objectSchema) {
        this.setObjectSchema(objectSchema);
        return this;
    }

    public void setObjectSchema(String objectSchema) {
        this.objectSchema = objectSchema;
    }

    public String getObjectModel() {
        return this.objectModel;
    }

    public FieldInForm objectModel(String objectModel) {
        this.setObjectModel(objectModel);
        return this;
    }

    public void setObjectModel(String objectModel) {
        this.objectModel = objectModel;
    }

    public String getOption() {
        return this.option;
    }

    public FieldInForm option(String option) {
        this.setOption(option);
        return this;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDescription() {
        return this.description;
    }

    public FieldInForm description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameOfField() {
        return this.nameOfField;
    }

    public FieldInForm nameOfField(String nameOfField) {
        this.setNameOfField(nameOfField);
        return this;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public String getFieldPattern() {
        return this.fieldPattern;
    }

    public FieldInForm fieldPattern(String fieldPattern) {
        this.setFieldPattern(fieldPattern);
        return this;
    }

    public void setFieldPattern(String fieldPattern) {
        this.fieldPattern = fieldPattern;
    }

    public String getRequired() {
        return this.required;
    }

    public FieldInForm required(String required) {
        this.setRequired(required);
        return this;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public Long getOrder() {
        return this.order;
    }

    public FieldInForm order(Long order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public FieldInForm createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public FieldInForm createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public FieldInForm createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public FieldInForm createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public FieldInForm modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public FieldInForm modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getRow() {
        return this.row;
    }

    public FieldInForm row(Long row) {
        this.setRow(row);
        return this;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getCol() {
        return this.col;
    }

    public FieldInForm col(Long col) {
        this.setCol(col);
        return this;
    }

    public void setCol(Long col) {
        this.col = col;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public FieldInForm tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public FieldInForm tennantName(String tennantName) {
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

    public FieldInForm tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    public UserInfo getCreated() {
        return this.created;
    }

    public void setCreated(UserInfo userInfo) {
        this.created = userInfo;
    }

    public FieldInForm created(UserInfo userInfo) {
        this.setCreated(userInfo);
        return this;
    }

    public UserInfo getModified() {
        return this.modified;
    }

    public void setModified(UserInfo userInfo) {
        this.modified = userInfo;
    }

    public FieldInForm modified(UserInfo userInfo) {
        this.setModified(userInfo);
        return this;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public FieldInForm field(Field field) {
        this.setField(field);
        return this;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public FieldInForm form(Form form) {
        this.setForm(form);
        return this;
    }

    public Set<FieldData> getFieldData() {
        return this.fieldData;
    }

    public void setFieldData(Set<FieldData> fieldData) {
        if (this.fieldData != null) {
            this.fieldData.forEach(i -> i.setFieldInForm(null));
        }
        if (fieldData != null) {
            fieldData.forEach(i -> i.setFieldInForm(this));
        }
        this.fieldData = fieldData;
    }

    public FieldInForm fieldData(Set<FieldData> fieldData) {
        this.setFieldData(fieldData);
        return this;
    }

    public FieldInForm addFieldData(FieldData fieldData) {
        this.fieldData.add(fieldData);
        fieldData.setFieldInForm(this);
        return this;
    }

    public FieldInForm removeFieldData(FieldData fieldData) {
        this.fieldData.remove(fieldData);
        fieldData.setFieldInForm(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldInForm)) {
            return false;
        }
        return id != null && id.equals(((FieldInForm) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldInForm{" +
            "id=" + getId() +
            ", fieldInFormCode='" + getFieldInFormCode() + "'" +
            ", fieldInFormName='" + getFieldInFormName() + "'" +
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
            ", row=" + getRow() +
            ", col=" + getCol() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
