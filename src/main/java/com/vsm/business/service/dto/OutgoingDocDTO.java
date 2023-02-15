package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.vsm.business.domain.OutgoingDoc} entity.
 */
public class OutgoingDocDTO implements Serializable {

    private Long id;

    private String outgoingDocName;

    private String outgoingDocCode;

    private String outgoingDocNumber;

    private String releaseOrgName;

    private String releaseOrgAvatar;

    private Instant releaseDate;

    private String composeOrgName;

    private String composeOrgAvatar;

    private String documentTypeName;

    private String documentTypeCode;

    private String priorityName;

    private String priorityCode;

    private String securityName;

    private String securityCode;

    private String compendium;

    private String signName;

    private String signAvatar;

    private Instant signDate;

    private String receiveOrgText;

    private String finalScanDocName;

    private Instant intoOutgingBookDate;

    private String numberOfBook;

    private String outgoingBookName;

    private String outgoingBookCode;

    private String outgoingStatusName;

    private Long oldStatus;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private OutgoingBookDTO outgoingBook;

    private OrganizationDTO releaseOrg;

    private OrganizationDTO composeOrg;

    private OrganizationDTO orgOwned;

    private UserInfoDTO signer;

    private DocumentTypeDTO documentType;

    private PriorityLevelDTO priorityLevel;

    private SecurityLevelDTO securityLevel;

    private OutgoingDocStatusDTO outgoingDocStatus;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private Set<OrganizationDTO> organizations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutgoingDocName() {
        return outgoingDocName;
    }

    public void setOutgoingDocName(String outgoingDocName) {
        this.outgoingDocName = outgoingDocName;
    }

    public String getOutgoingDocCode() {
        return outgoingDocCode;
    }

    public void setOutgoingDocCode(String outgoingDocCode) {
        this.outgoingDocCode = outgoingDocCode;
    }

    public String getOutgoingDocNumber() {
        return outgoingDocNumber;
    }

    public void setOutgoingDocNumber(String outgoingDocNumber) {
        this.outgoingDocNumber = outgoingDocNumber;
    }

    public String getReleaseOrgName() {
        return releaseOrgName;
    }

    public void setReleaseOrgName(String releaseOrgName) {
        this.releaseOrgName = releaseOrgName;
    }

    public String getReleaseOrgAvatar() {
        return releaseOrgAvatar;
    }

    public void setReleaseOrgAvatar(String releaseOrgAvatar) {
        this.releaseOrgAvatar = releaseOrgAvatar;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getComposeOrgName() {
        return composeOrgName;
    }

    public void setComposeOrgName(String composeOrgName) {
        this.composeOrgName = composeOrgName;
    }

    public String getComposeOrgAvatar() {
        return composeOrgAvatar;
    }

    public void setComposeOrgAvatar(String composeOrgAvatar) {
        this.composeOrgAvatar = composeOrgAvatar;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getCompendium() {
        return compendium;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignAvatar() {
        return signAvatar;
    }

    public void setSignAvatar(String signAvatar) {
        this.signAvatar = signAvatar;
    }

    public Instant getSignDate() {
        return signDate;
    }

    public void setSignDate(Instant signDate) {
        this.signDate = signDate;
    }

    public String getReceiveOrgText() {
        return receiveOrgText;
    }

    public void setReceiveOrgText(String receiveOrgText) {
        this.receiveOrgText = receiveOrgText;
    }

    public String getFinalScanDocName() {
        return finalScanDocName;
    }

    public void setFinalScanDocName(String finalScanDocName) {
        this.finalScanDocName = finalScanDocName;
    }

    public Instant getIntoOutgingBookDate() {
        return intoOutgingBookDate;
    }

    public void setIntoOutgingBookDate(Instant intoOutgingBookDate) {
        this.intoOutgingBookDate = intoOutgingBookDate;
    }

    public String getNumberOfBook() {
        return numberOfBook;
    }

    public void setNumberOfBook(String numberOfBook) {
        this.numberOfBook = numberOfBook;
    }

    public String getOutgoingBookName() {
        return outgoingBookName;
    }

    public void setOutgoingBookName(String outgoingBookName) {
        this.outgoingBookName = outgoingBookName;
    }

    public String getOutgoingBookCode() {
        return outgoingBookCode;
    }

    public void setOutgoingBookCode(String outgoingBookCode) {
        this.outgoingBookCode = outgoingBookCode;
    }

    public String getOutgoingStatusName() {
        return outgoingStatusName;
    }

    public void setOutgoingStatusName(String outgoingStatusName) {
        this.outgoingStatusName = outgoingStatusName;
    }

    public Long getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Long oldStatus) {
        this.oldStatus = oldStatus;
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

    public OutgoingBookDTO getOutgoingBook() {
        return outgoingBook;
    }

    public void setOutgoingBook(OutgoingBookDTO outgoingBook) {
        this.outgoingBook = outgoingBook;
    }

    public OrganizationDTO getReleaseOrg() {
        return releaseOrg;
    }

    public void setReleaseOrg(OrganizationDTO releaseOrg) {
        this.releaseOrg = releaseOrg;
    }

    public OrganizationDTO getComposeOrg() {
        return composeOrg;
    }

    public void setComposeOrg(OrganizationDTO composeOrg) {
        this.composeOrg = composeOrg;
    }

    public OrganizationDTO getOrgOwned() {
        return orgOwned;
    }

    public void setOrgOwned(OrganizationDTO orgOwned) {
        this.orgOwned = orgOwned;
    }

    public UserInfoDTO getSigner() {
        return signer;
    }

    public void setSigner(UserInfoDTO signer) {
        this.signer = signer;
    }

    public DocumentTypeDTO getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeDTO documentType) {
        this.documentType = documentType;
    }

    public PriorityLevelDTO getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevelDTO priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public SecurityLevelDTO getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(SecurityLevelDTO securityLevel) {
        this.securityLevel = securityLevel;
    }

    public OutgoingDocStatusDTO getOutgoingDocStatus() {
        return outgoingDocStatus;
    }

    public void setOutgoingDocStatus(OutgoingDocStatusDTO outgoingDocStatus) {
        this.outgoingDocStatus = outgoingDocStatus;
    }

    public UserInfoDTO getCreater() {
        return creater;
    }

    public void setCreater(UserInfoDTO creater) {
        this.creater = creater;
    }

    public UserInfoDTO getModifier() {
        return modifier;
    }

    public void setModifier(UserInfoDTO modifier) {
        this.modifier = modifier;
    }

    public Set<OrganizationDTO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationDTO> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutgoingDocDTO)) {
            return false;
        }

        OutgoingDocDTO outgoingDocDTO = (OutgoingDocDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, outgoingDocDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutgoingDocDTO{" +
            "id=" + getId() +
            ", outgoingDocName='" + getOutgoingDocName() + "'" +
            ", outgoingDocCode='" + getOutgoingDocCode() + "'" +
            ", outgoingDocNumber='" + getOutgoingDocNumber() + "'" +
            ", releaseOrgName='" + getReleaseOrgName() + "'" +
            ", releaseOrgAvatar='" + getReleaseOrgAvatar() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", composeOrgName='" + getComposeOrgName() + "'" +
            ", composeOrgAvatar='" + getComposeOrgAvatar() + "'" +
            ", documentTypeName='" + getDocumentTypeName() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", priorityName='" + getPriorityName() + "'" +
            ", priorityCode='" + getPriorityCode() + "'" +
            ", securityName='" + getSecurityName() + "'" +
            ", securityCode='" + getSecurityCode() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", signName='" + getSignName() + "'" +
            ", signAvatar='" + getSignAvatar() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", receiveOrgText='" + getReceiveOrgText() + "'" +
            ", finalScanDocName='" + getFinalScanDocName() + "'" +
            ", intoOutgingBookDate='" + getIntoOutgingBookDate() + "'" +
            ", numberOfBook='" + getNumberOfBook() + "'" +
            ", outgoingBookName='" + getOutgoingBookName() + "'" +
            ", outgoingBookCode='" + getOutgoingBookCode() + "'" +
            ", outgoingStatusName='" + getOutgoingStatusName() + "'" +
            ", oldStatus=" + getOldStatus() +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", outgoingBook=" + getOutgoingBook() +
            ", releaseOrg=" + getReleaseOrg() +
            ", composeOrg=" + getComposeOrg() +
            ", orgOwned=" + getOrgOwned() +
            ", signer=" + getSigner() +
            ", documentType=" + getDocumentType() +
            ", priorityLevel=" + getPriorityLevel() +
            ", securityLevel=" + getSecurityLevel() +
            ", outgoingDocStatus=" + getOutgoingDocStatus() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", organizations=" + getOrganizations() +
            "}";
    }
}
