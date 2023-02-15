package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.TemplateForm} entity.
 */
public class TemplateFormDTO implements Serializable {

    private Long id;

    private String templateFormCode;

    private String templateFormName;

    private String fileExtension;

    private String path;

    private String fullPath;

    private String ofice365Path;

    @Lob
    private String mappingInfo;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private Long version;

    private String tennantCode;

    private String tennantName;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private Set<OrganizationDTO> organizations = new HashSet<>();
    private List<AttachmentFileDTO> attachmentFileDTOS = new ArrayList<>();

    private List<RequestDTO> requestDTOS = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateFormCode() {
        return templateFormCode;
    }

    public void setTemplateFormCode(String templateFormCode) {
        this.templateFormCode = templateFormCode;
    }

    public String getTemplateFormName() {
        return templateFormName;
    }

    public void setTemplateFormName(String templateFormName) {
        this.templateFormName = templateFormName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getOfice365Path() {
        return ofice365Path;
    }

    public void setOfice365Path(String ofice365Path) {
        this.ofice365Path = ofice365Path;
    }

    public String getMappingInfo() {
        return mappingInfo;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Set<OrganizationDTO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationDTO> organizations) {
        this.organizations = organizations;
    }

    public List<AttachmentFileDTO> getAttachmentFileDTOS() {
        return attachmentFileDTOS;
    }

    public void setAttachmentFileDTOS(List<AttachmentFileDTO> attachmentFileDTOS) {
        this.attachmentFileDTOS = attachmentFileDTOS;
    }

    public List<RequestDTO> getRequestDTOS() {
        return requestDTOS;
    }

    public void setRequestDTOS(List<RequestDTO> requestDTOS) {
        this.requestDTOS = requestDTOS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateFormDTO)) {
            return false;
        }

        TemplateFormDTO templateFormDTO = (TemplateFormDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateFormDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateFormDTO{" +
            "id=" + getId() +
            ", templateFormCode='" + getTemplateFormCode() + "'" +
            ", templateFormName='" + getTemplateFormName() + "'" +
            ", fileExtension='" + getFileExtension() + "'" +
            ", path='" + getPath() + "'" +
            ", fullPath='" + getFullPath() + "'" +
            ", ofice365Path='" + getOfice365Path() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", version=" + getVersion() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", organizations=" + getOrganizations() +
            "}";
    }
}
