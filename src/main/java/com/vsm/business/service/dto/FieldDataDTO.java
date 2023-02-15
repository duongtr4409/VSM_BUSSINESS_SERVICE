package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.FieldData} entity.
 */
public class FieldDataDTO implements Serializable {

    private Long id;

    private String fieldDataCode;

    private String fieldDataName;

    @Lob
    private String objectSchema;

    @Lob
    private String objectModel;

    @Lob
    private String option;

    private String description;

    private String nameOfField;

    private String fieldPattern;

    private String required;

    private Long order;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Long row;

    private Long col;

    private String tennantCode;

    private String tennantName;

    private FieldDTO field;

    private FormDataDTO formData;

    private RequestDataDTO requestData;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private FieldInFormDTO fieldInForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldDataCode() {
        return fieldDataCode;
    }

    public void setFieldDataCode(String fieldDataCode) {
        this.fieldDataCode = fieldDataCode;
    }

    public String getFieldDataName() {
        return fieldDataName;
    }

    public void setFieldDataName(String fieldDataName) {
        this.fieldDataName = fieldDataName;
    }

    public String getObjectSchema() {
        return objectSchema;
    }

    public void setObjectSchema(String objectSchema) {
        this.objectSchema = objectSchema;
    }

    public String getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(String objectModel) {
        this.objectModel = objectModel;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameOfField() {
        return nameOfField;
    }

    public void setNameOfField(String nameOfField) {
        this.nameOfField = nameOfField;
    }

    public String getFieldPattern() {
        return fieldPattern;
    }

    public void setFieldPattern(String fieldPattern) {
        this.fieldPattern = fieldPattern;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return createdOrgName;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return createdRankName;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(Long row) {
        this.row = row;
    }

    public Long getCol() {
        return col;
    }

    public void setCol(Long col) {
        this.col = col;
    }

    public String getTennantCode() {
        return tennantCode;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return tennantName;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public FieldDTO getField() {
        return field;
    }

    public void setField(FieldDTO field) {
        this.field = field;
    }

    public FormDataDTO getFormData() {
        return formData;
    }

    public void setFormData(FormDataDTO formData) {
        this.formData = formData;
    }

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    public UserInfoDTO getCreated() {
        return created;
    }

    public void setCreated(UserInfoDTO created) {
        this.created = created;
    }

    public UserInfoDTO getModified() {
        return modified;
    }

    public void setModified(UserInfoDTO modified) {
        this.modified = modified;
    }

    public FieldInFormDTO getFieldInForm() {
        return fieldInForm;
    }

    public void setFieldInForm(FieldInFormDTO fieldInForm) {
        this.fieldInForm = fieldInForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldDataDTO)) {
            return false;
        }

        FieldDataDTO fieldDataDTO = (FieldDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldDataDTO{" +
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
            ", field=" + getField() +
            ", formData=" + getFormData() +
            ", requestData=" + getRequestData() +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", fieldInForm=" + getFieldInForm() +
            "}";
    }
}
