package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.MailTemplate} entity.
 */
public class MailTemplateDTO implements Serializable {

    private Long id;

    private String mailTemplateName;

    private String mailTemplateCode;

    private String receiverDefault;

    private String ccerDefault;

    private String bccerDefault;

    private String subject;

    private String itemId365;

    private String description;

    @Lob
    private String contentFile;

    @Lob
    private String content;

    @Lob
    private String footer;

    private String pathFile;

    @Lob
    private String mappingInfo;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private Set<OrganizationDTO> organizations = new HashSet<>();

    private Set<ProcessInfoDTO> processInfos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailTemplateName() {
        return mailTemplateName;
    }

    public void setMailTemplateName(String mailTemplateName) {
        this.mailTemplateName = mailTemplateName;
    }

    public String getMailTemplateCode() {
        return mailTemplateCode;
    }

    public void setMailTemplateCode(String mailTemplateCode) {
        this.mailTemplateCode = mailTemplateCode;
    }

    public String getReceiverDefault() {
        return receiverDefault;
    }

    public void setReceiverDefault(String receiverDefault) {
        this.receiverDefault = receiverDefault;
    }

    public String getCcerDefault() {
        return ccerDefault;
    }

    public void setCcerDefault(String ccerDefault) {
        this.ccerDefault = ccerDefault;
    }

    public String getBccerDefault() {
        return bccerDefault;
    }

    public void setBccerDefault(String bccerDefault) {
        this.bccerDefault = bccerDefault;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getItemId365() {
        return itemId365;
    }

    public void setItemId365(String itemId365) {
        this.itemId365 = itemId365;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContentFile() {
        return contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getMappingInfo() {
        return mappingInfo;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
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

    public Set<OrganizationDTO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationDTO> organizations) {
        this.organizations = organizations;
    }

    public Set<ProcessInfoDTO> getProcessInfos() {
        return processInfos;
    }

    public void setProcessInfos(Set<ProcessInfoDTO> processInfos) {
        this.processInfos = processInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MailTemplateDTO)) {
            return false;
        }

        MailTemplateDTO mailTemplateDTO = (MailTemplateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mailTemplateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MailTemplateDTO{" +
            "id=" + getId() +
            ", mailTemplateName='" + getMailTemplateName() + "'" +
            ", mailTemplateCode='" + getMailTemplateCode() + "'" +
            ", receiverDefault='" + getReceiverDefault() + "'" +
            ", ccerDefault='" + getCcerDefault() + "'" +
            ", bccerDefault='" + getBccerDefault() + "'" +
            ", subject='" + getSubject() + "'" +
            ", itemId365='" + getItemId365() + "'" +
            ", description='" + getDescription() + "'" +
            ", contentFile='" + getContentFile() + "'" +
            ", content='" + getContent() + "'" +
            ", footer='" + getFooter() + "'" +
            ", pathFile='" + getPathFile() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", organizations=" + getOrganizations() +
            ", processInfos=" + getProcessInfos() +
            "}";
    }
}
