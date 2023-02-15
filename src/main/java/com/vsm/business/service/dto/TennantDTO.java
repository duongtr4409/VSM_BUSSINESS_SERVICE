package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.Tennant} entity.
 */
public class TennantDTO implements Serializable {

    private Long id;

    private String tennantCode;

    private String tennantName;

    private String description;

    private String databaseUrl;

    private String databaseSchemaName;

    private String databaseUsermame;

    private String databasePassword;

    private String databaseDriverClass;

    private String filePathFolder;

    private Long createdId;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private Long modifiedId;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseSchemaName() {
        return databaseSchemaName;
    }

    public void setDatabaseSchemaName(String databaseSchemaName) {
        this.databaseSchemaName = databaseSchemaName;
    }

    public String getDatabaseUsermame() {
        return databaseUsermame;
    }

    public void setDatabaseUsermame(String databaseUsermame) {
        this.databaseUsermame = databaseUsermame;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseDriverClass() {
        return databaseDriverClass;
    }

    public void setDatabaseDriverClass(String databaseDriverClass) {
        this.databaseDriverClass = databaseDriverClass;
    }

    public String getFilePathFolder() {
        return filePathFolder;
    }

    public void setFilePathFolder(String filePathFolder) {
        this.filePathFolder = filePathFolder;
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
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

    public Long getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(Long modifiedId) {
        this.modifiedId = modifiedId;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TennantDTO)) {
            return false;
        }

        TennantDTO tennantDTO = (TennantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tennantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TennantDTO{" +
            "id=" + getId() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", description='" + getDescription() + "'" +
            ", databaseUrl='" + getDatabaseUrl() + "'" +
            ", databaseSchemaName='" + getDatabaseSchemaName() + "'" +
            ", databaseUsermame='" + getDatabaseUsermame() + "'" +
            ", databasePassword='" + getDatabasePassword() + "'" +
            ", databaseDriverClass='" + getDatabaseDriverClass() + "'" +
            ", filePathFolder='" + getFilePathFolder() + "'" +
            ", createdId=" + getCreatedId() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedId=" + getModifiedId() +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
