package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.IncomingDoc} entity.
 */
public class IncomingDocDTO implements Serializable {

    private Long id;

    private String incomingDocName;

    private String incomingDocCode;

    private String incomingDocNumber;

    private String placeSendName;

    private String placeSendCode;

    private Instant incommingDocDate;

    private String incomingDocTypeName;

    private String incomingDocTypeCode;

    private String compendium;

    private String priorityName;

    private String priorityCode;

    private String securityName;

    private String securityCode;

    private Instant comingDate;

    private String finalScanDocName;

    private Instant intoOutgingBookDate;

    private String numberOfBook;

    private Instant intoIncomingBookDate;

    private String incomingBookName;

    private String incomingBookCode;

    private String incomingStatusName;

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

    private IncomingBookDTO incomingBook;

    private OrganizationDTO orgOwned;

    private IncomingDocTypeDTO incomingDocType;

    private OutOrganizationDTO outOrganization;

    private PriorityLevelDTO priorityLevel;

    private SecurityLevelDTO securityLevel;

    private IncomingDocStatusDTO incomingDocStatus;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingDocName() {
        return incomingDocName;
    }

    public void setIncomingDocName(String incomingDocName) {
        this.incomingDocName = incomingDocName;
    }

    public String getIncomingDocCode() {
        return incomingDocCode;
    }

    public void setIncomingDocCode(String incomingDocCode) {
        this.incomingDocCode = incomingDocCode;
    }

    public String getIncomingDocNumber() {
        return incomingDocNumber;
    }

    public void setIncomingDocNumber(String incomingDocNumber) {
        this.incomingDocNumber = incomingDocNumber;
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

    public Instant getIncommingDocDate() {
        return incommingDocDate;
    }

    public void setIncommingDocDate(Instant incommingDocDate) {
        this.incommingDocDate = incommingDocDate;
    }

    public String getIncomingDocTypeName() {
        return incomingDocTypeName;
    }

    public void setIncomingDocTypeName(String incomingDocTypeName) {
        this.incomingDocTypeName = incomingDocTypeName;
    }

    public String getIncomingDocTypeCode() {
        return incomingDocTypeCode;
    }

    public void setIncomingDocTypeCode(String incomingDocTypeCode) {
        this.incomingDocTypeCode = incomingDocTypeCode;
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

    public Instant getComingDate() {
        return comingDate;
    }

    public void setComingDate(Instant comingDate) {
        this.comingDate = comingDate;
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

    public Instant getIntoIncomingBookDate() {
        return intoIncomingBookDate;
    }

    public void setIntoIncomingBookDate(Instant intoIncomingBookDate) {
        this.intoIncomingBookDate = intoIncomingBookDate;
    }

    public String getIncomingBookName() {
        return incomingBookName;
    }

    public void setIncomingBookName(String incomingBookName) {
        this.incomingBookName = incomingBookName;
    }

    public String getIncomingBookCode() {
        return incomingBookCode;
    }

    public void setIncomingBookCode(String incomingBookCode) {
        this.incomingBookCode = incomingBookCode;
    }

    public String getIncomingStatusName() {
        return incomingStatusName;
    }

    public void setIncomingStatusName(String incomingStatusName) {
        this.incomingStatusName = incomingStatusName;
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

    public IncomingBookDTO getIncomingBook() {
        return incomingBook;
    }

    public void setIncomingBook(IncomingBookDTO incomingBook) {
        this.incomingBook = incomingBook;
    }

    public OrganizationDTO getOrgOwned() {
        return orgOwned;
    }

    public void setOrgOwned(OrganizationDTO orgOwned) {
        this.orgOwned = orgOwned;
    }

    public IncomingDocTypeDTO getIncomingDocType() {
        return incomingDocType;
    }

    public void setIncomingDocType(IncomingDocTypeDTO incomingDocType) {
        this.incomingDocType = incomingDocType;
    }

    public OutOrganizationDTO getOutOrganization() {
        return outOrganization;
    }

    public void setOutOrganization(OutOrganizationDTO outOrganization) {
        this.outOrganization = outOrganization;
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

    public IncomingDocStatusDTO getIncomingDocStatus() {
        return incomingDocStatus;
    }

    public void setIncomingDocStatus(IncomingDocStatusDTO incomingDocStatus) {
        this.incomingDocStatus = incomingDocStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomingDocDTO)) {
            return false;
        }

        IncomingDocDTO incomingDocDTO = (IncomingDocDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, incomingDocDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomingDocDTO{" +
            "id=" + getId() +
            ", incomingDocName='" + getIncomingDocName() + "'" +
            ", incomingDocCode='" + getIncomingDocCode() + "'" +
            ", incomingDocNumber='" + getIncomingDocNumber() + "'" +
            ", placeSendName='" + getPlaceSendName() + "'" +
            ", placeSendCode='" + getPlaceSendCode() + "'" +
            ", incommingDocDate='" + getIncommingDocDate() + "'" +
            ", incomingDocTypeName='" + getIncomingDocTypeName() + "'" +
            ", incomingDocTypeCode='" + getIncomingDocTypeCode() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", priorityName='" + getPriorityName() + "'" +
            ", priorityCode='" + getPriorityCode() + "'" +
            ", securityName='" + getSecurityName() + "'" +
            ", securityCode='" + getSecurityCode() + "'" +
            ", comingDate='" + getComingDate() + "'" +
            ", finalScanDocName='" + getFinalScanDocName() + "'" +
            ", intoOutgingBookDate='" + getIntoOutgingBookDate() + "'" +
            ", numberOfBook='" + getNumberOfBook() + "'" +
            ", intoIncomingBookDate='" + getIntoIncomingBookDate() + "'" +
            ", incomingBookName='" + getIncomingBookName() + "'" +
            ", incomingBookCode='" + getIncomingBookCode() + "'" +
            ", incomingStatusName='" + getIncomingStatusName() + "'" +
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
            ", incomingBook=" + getIncomingBook() +
            ", orgOwned=" + getOrgOwned() +
            ", incomingDocType=" + getIncomingDocType() +
            ", outOrganization=" + getOutOrganization() +
            ", priorityLevel=" + getPriorityLevel() +
            ", securityLevel=" + getSecurityLevel() +
            ", incomingDocStatus=" + getIncomingDocStatus() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            "}";
    }
}
