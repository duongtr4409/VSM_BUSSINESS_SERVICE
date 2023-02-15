package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.vsm.business.domain.TransferHandle} entity.
 */
public class TransferHandleDTO implements Serializable {

    private Long id;

    private String transferName;

    private String transferAvatar;

    private String transferEmail;

    private String statusName;

    private String orgName;

    private String orgCode;

    private String dispatchBookName;

    private String dispatchBookCode;

    private Instant expiredTime;

    private Instant processDate;

    private String content;

    private String attachFileList;

    private Long order;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private StatusTransferHandleDTO statusTransferHandle;

    private OrganizationDTO organization;

    private DispatchBookDTO dispatchBook;

    private StepDataDTO stepData;

    private UserInfoDTO transfer;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private Set<UserInfoDTO> receiversHandles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferName() {
        return transferName;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getTransferAvatar() {
        return transferAvatar;
    }

    public void setTransferAvatar(String transferAvatar) {
        this.transferAvatar = transferAvatar;
    }

    public String getTransferEmail() {
        return transferEmail;
    }

    public void setTransferEmail(String transferEmail) {
        this.transferEmail = transferEmail;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public Instant getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Instant expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Instant getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Instant processDate) {
        this.processDate = processDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public StatusTransferHandleDTO getStatusTransferHandle() {
        return statusTransferHandle;
    }

    public void setStatusTransferHandle(StatusTransferHandleDTO statusTransferHandle) {
        this.statusTransferHandle = statusTransferHandle;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public DispatchBookDTO getDispatchBook() {
        return dispatchBook;
    }

    public void setDispatchBook(DispatchBookDTO dispatchBook) {
        this.dispatchBook = dispatchBook;
    }

    public StepDataDTO getStepData() {
        return stepData;
    }

    public void setStepData(StepDataDTO stepData) {
        this.stepData = stepData;
    }

    public UserInfoDTO getTransfer() {
        return transfer;
    }

    public void setTransfer(UserInfoDTO transfer) {
        this.transfer = transfer;
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

    public Set<UserInfoDTO> getReceiversHandles() {
        return receiversHandles;
    }

    public void setReceiversHandles(Set<UserInfoDTO> receiversHandles) {
        this.receiversHandles = receiversHandles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferHandleDTO)) {
            return false;
        }

        TransferHandleDTO transferHandleDTO = (TransferHandleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferHandleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferHandleDTO{" +
            "id=" + getId() +
            ", transferName='" + getTransferName() + "'" +
            ", transferAvatar='" + getTransferAvatar() + "'" +
            ", transferEmail='" + getTransferEmail() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", orgCode='" + getOrgCode() + "'" +
            ", dispatchBookName='" + getDispatchBookName() + "'" +
            ", dispatchBookCode='" + getDispatchBookCode() + "'" +
            ", expiredTime='" + getExpiredTime() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            ", content='" + getContent() + "'" +
            ", attachFileList='" + getAttachFileList() + "'" +
            ", order=" + getOrder() +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", statusTransferHandle=" + getStatusTransferHandle() +
            ", organization=" + getOrganization() +
            ", dispatchBook=" + getDispatchBook() +
            ", stepData=" + getStepData() +
            ", transfer=" + getTransfer() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", receiversHandles=" + getReceiversHandles() +
            "}";
    }
}
