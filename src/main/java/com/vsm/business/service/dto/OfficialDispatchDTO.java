package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.vsm.business.domain.OfficialDispatch} entity.
 */
public class OfficialDispatchDTO implements Serializable {

    private Long id;

    private String officialDispatchName;

    private String officialDispatchCode;

    private String officialDispatchNumber;

    private String officialDispatchTypeName;

    private String officialDispatchTypeCode;

    private String compendium;

    private String priorityName;

    private String priorityCode;

    private String securityName;

    private String securityCode;

    private String documentTypeName;

    private String documentTypeCode;

    private String releaseOrgName;

    private String releaseOrgAvatar;

    private Instant releaseDate;

    private String composeOrgName;

    private String composeOrgAvatar;

    private String signName;

    private String signAvatar;

    private Instant signDate;

    private Instant outgoingDate;

    private String receiveOrgText;

    private String placeSendName;

    private String placeSendCode;

    private Instant dispatchOfficialDate;

    private Instant commingDate;

    private String finalScanDocName;

    private Instant intoOutgingBookDate;

    private String numberOfBook;

    private String dispatchBookName;

    private String dispatchBookCode;

    private String dispatchBookType;

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

    private DispatchBookDTO dispatchBook;

    private OrganizationDTO releaseOrg;

    private OrganizationDTO composeOrg;

    private OrganizationDTO ownerOrg;

    private UserInfoDTO signer;

    private OfficialDispatchTypeDTO officialDispatchType;

    private DocumentTypeDTO documentType;

    private PriorityLevelDTO priorityLevel;

    private SecurityLevelDTO securityLevel;

    private OfficialDispatchStatusDTO officialDispatchStatus;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private OutOrganizationDTO outOrganization;

    private Set<UserInfoDTO> offDispatchUserReads = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficialDispatchName() {
        return officialDispatchName;
    }

    public void setOfficialDispatchName(String officialDispatchName) {
        this.officialDispatchName = officialDispatchName;
    }

    public String getOfficialDispatchCode() {
        return officialDispatchCode;
    }

    public void setOfficialDispatchCode(String officialDispatchCode) {
        this.officialDispatchCode = officialDispatchCode;
    }

    public String getOfficialDispatchNumber() {
        return officialDispatchNumber;
    }

    public void setOfficialDispatchNumber(String officialDispatchNumber) {
        this.officialDispatchNumber = officialDispatchNumber;
    }

    public String getOfficialDispatchTypeName() {
        return officialDispatchTypeName;
    }

    public void setOfficialDispatchTypeName(String officialDispatchTypeName) {
        this.officialDispatchTypeName = officialDispatchTypeName;
    }

    public String getOfficialDispatchTypeCode() {
        return officialDispatchTypeCode;
    }

    public void setOfficialDispatchTypeCode(String officialDispatchTypeCode) {
        this.officialDispatchTypeCode = officialDispatchTypeCode;
    }

    public String getCompendium() {
        return compendium;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
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

    public Instant getOutgoingDate() {
        return outgoingDate;
    }

    public void setOutgoingDate(Instant outgoingDate) {
        this.outgoingDate = outgoingDate;
    }

    public String getReceiveOrgText() {
        return receiveOrgText;
    }

    public void setReceiveOrgText(String receiveOrgText) {
        this.receiveOrgText = receiveOrgText;
    }

    public String getPlaceSendName() {
        return placeSendName;
    }

    public void setPlaceSendName(String placeSendName) {
        this.placeSendName = placeSendName;
    }

    public String getPlaceSendCode() {
        return placeSendCode;
    }

    public void setPlaceSendCode(String placeSendCode) {
        this.placeSendCode = placeSendCode;
    }

    public Instant getDispatchOfficialDate() {
        return dispatchOfficialDate;
    }

    public void setDispatchOfficialDate(Instant dispatchOfficialDate) {
        this.dispatchOfficialDate = dispatchOfficialDate;
    }

    public Instant getCommingDate() {
        return commingDate;
    }

    public void setCommingDate(Instant commingDate) {
        this.commingDate = commingDate;
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

    public String getDispatchBookName() {
        return dispatchBookName;
    }

    public void setDispatchBookName(String dispatchBookName) {
        this.dispatchBookName = dispatchBookName;
    }

    public String getDispatchBookCode() {
        return dispatchBookCode;
    }

    public void setDispatchBookCode(String dispatchBookCode) {
        this.dispatchBookCode = dispatchBookCode;
    }

    public String getDispatchBookType() {
        return dispatchBookType;
    }

    public void setDispatchBookType(String dispatchBookType) {
        this.dispatchBookType = dispatchBookType;
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

    public DispatchBookDTO getDispatchBook() {
        return dispatchBook;
    }

    public void setDispatchBook(DispatchBookDTO dispatchBook) {
        this.dispatchBook = dispatchBook;
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

    public OrganizationDTO getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(OrganizationDTO ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public UserInfoDTO getSigner() {
        return signer;
    }

    public void setSigner(UserInfoDTO signer) {
        this.signer = signer;
    }

    public OfficialDispatchTypeDTO getOfficialDispatchType() {
        return officialDispatchType;
    }

    public void setOfficialDispatchType(OfficialDispatchTypeDTO officialDispatchType) {
        this.officialDispatchType = officialDispatchType;
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

    public OfficialDispatchStatusDTO getOfficialDispatchStatus() {
        return officialDispatchStatus;
    }

    public void setOfficialDispatchStatus(OfficialDispatchStatusDTO officialDispatchStatus) {
        this.officialDispatchStatus = officialDispatchStatus;
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

    public OutOrganizationDTO getOutOrganization() {
        return outOrganization;
    }

    public void setOutOrganization(OutOrganizationDTO outOrganization) {
        this.outOrganization = outOrganization;
    }

    public Set<UserInfoDTO> getOffDispatchUserReads() {
        return offDispatchUserReads;
    }

    public void setOffDispatchUserReads(Set<UserInfoDTO> offDispatchUserReads) {
        this.offDispatchUserReads = offDispatchUserReads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficialDispatchDTO)) {
            return false;
        }

        OfficialDispatchDTO officialDispatchDTO = (OfficialDispatchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, officialDispatchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficialDispatchDTO{" +
            "id=" + getId() +
            ", officialDispatchName='" + getOfficialDispatchName() + "'" +
            ", officialDispatchCode='" + getOfficialDispatchCode() + "'" +
            ", officialDispatchNumber='" + getOfficialDispatchNumber() + "'" +
            ", officialDispatchTypeName='" + getOfficialDispatchTypeName() + "'" +
            ", officialDispatchTypeCode='" + getOfficialDispatchTypeCode() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", priorityName='" + getPriorityName() + "'" +
            ", priorityCode='" + getPriorityCode() + "'" +
            ", securityName='" + getSecurityName() + "'" +
            ", securityCode='" + getSecurityCode() + "'" +
            ", documentTypeName='" + getDocumentTypeName() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", releaseOrgName='" + getReleaseOrgName() + "'" +
            ", releaseOrgAvatar='" + getReleaseOrgAvatar() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", composeOrgName='" + getComposeOrgName() + "'" +
            ", composeOrgAvatar='" + getComposeOrgAvatar() + "'" +
            ", signName='" + getSignName() + "'" +
            ", signAvatar='" + getSignAvatar() + "'" +
            ", signDate='" + getSignDate() + "'" +
            ", outgoingDate='" + getOutgoingDate() + "'" +
            ", receiveOrgText='" + getReceiveOrgText() + "'" +
            ", placeSendName='" + getPlaceSendName() + "'" +
            ", placeSendCode='" + getPlaceSendCode() + "'" +
            ", dispatchOfficialDate='" + getDispatchOfficialDate() + "'" +
            ", commingDate='" + getCommingDate() + "'" +
            ", finalScanDocName='" + getFinalScanDocName() + "'" +
            ", intoOutgingBookDate='" + getIntoOutgingBookDate() + "'" +
            ", numberOfBook='" + getNumberOfBook() + "'" +
            ", dispatchBookName='" + getDispatchBookName() + "'" +
            ", dispatchBookCode='" + getDispatchBookCode() + "'" +
            ", dispatchBookType='" + getDispatchBookType() + "'" +
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
            ", dispatchBook=" + getDispatchBook() +
            ", releaseOrg=" + getReleaseOrg() +
            ", composeOrg=" + getComposeOrg() +
            ", ownerOrg=" + getOwnerOrg() +
            ", signer=" + getSigner() +
            ", officialDispatchType=" + getOfficialDispatchType() +
            ", documentType=" + getDocumentType() +
            ", priorityLevel=" + getPriorityLevel() +
            ", securityLevel=" + getSecurityLevel() +
            ", officialDispatchStatus=" + getOfficialDispatchStatus() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", outOrganization=" + getOutOrganization() +
            ", offDispatchUserReads=" + getOffDispatchUserReads() +
            "}";
    }
}
